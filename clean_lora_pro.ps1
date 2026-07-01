$inputFile = "e:\DOCS\Projekti\ABPLAT\ColPlatBack\newabp_with_alldb_geo.sql"
$outputFile = "e:\DOCS\Projekti\ABPLAT\ColPlatBack\newabp_with_alldb_geo.sql"

$lines = Get-Content $inputFile
$outLines = New-Object System.Collections.Generic.List[string]

# State tracking
$currentTable = ""
$inInsert = $false
$modifiedInsert = $false
$insertBuffer = New-Object System.Collections.Generic.List[string]

# Tables and their filter rules
$filterRules = @{
    "_company" = @{type="column"; col=0; values=@("2")}  # remove company_id=2 row (second row)
    "_company_support" = @{type="column"; col=0; values=@("2")}  # remove company_id=2
    "_user" = @{type="column"; col=0; values=@("3","4","5","6","7","8","9","10","11","12")}  # remove Lora-Pro users
    "_user_profile" = @{type="column"; col=0; values=@("3","4","5","6","7","8","9","10","11","12")}  # remove Lora-Pro profiles
    "_user_roles" = @{type="column"; col=0; values=@("3","4","5","6","7","8","9","10","11","12")}  # remove roles for Lora-Pro users
    "_project" = @{type="column"; col=0; values=@("2","3","4","5","6")}  # remove Lora-Pro projects
    "_project_task" = @{type="column"; col=2; values=@("2","3","4","6")}  # remove tasks for Lora-Pro projects
    "_project_task_users" = @{type="custom"}  # custom handling
    "_project_users" = @{type="custom"}  # custom handling
    "_task_status" = @{type="column"; col=1; values=@("2","3","4","6")}  # remove status for Lora-Pro projects
    "_movable_asset" = @{type="column"; col=9; values=@("2")}  # remove with movable_asset_company_id=2
    "_calendar_events" = @{type="custom"}  # custom handling
    "_event_participants" = @{type="custom"}  # custom handling
    "_conversation" = @{type="column"; col=1; values=@("2")}  # remove with company_id=2
    "_conversation_participant" = @{type="custom"}  # custom handling
    "_message" = @{type="custom"}  # custom handling
}

function Should-Remove-Row($table, $rowText) {
    # Parse the row values (parenthesized comma-separated)
    # Strip outer parens and split by comma respecting quotes
    
    if (-not $rowText.StartsWith("(")) { return $false }
    
    $rule = $filterRules[$table]
    if (-not $rule) { return $false }
    
    if ($rule.type -eq "column") {
        $colIndex = $rule.col
        $removeValues = $rule.values
        
        # Simple split - careful with commas inside quotes/strings
        $inside = $rowText.TrimStart("(").TrimEnd(")").TrimEnd(",")
        
        # More robust parsing - find the Nth value
        $depth = 0
        $inString = $false
        $cols = @()
        $current = ""
        for ($i = 0; $i -lt $inside.Length; $i++) {
            $c = $inside[$i]
            if ($c -eq "'" -and ($i -eq 0 -or $inside[$i-1] -ne "\")) { $inString = -not $inString }
            elseif ($c -eq "(" -and -not $inString) { $depth++ }
            elseif ($c -eq ")" -and -not $inString) { $depth-- }
            elseif ($c -eq "," -and $depth -eq 0 -and -not $inString) {
                $cols += $current.Trim()
                $current = ""
                continue
            }
            $current += $c
        }
        if ($current.Trim() -ne "") { $cols += $current.Trim() }
        
        if ($colIndex -lt $cols.Count) {
            $val = $cols[$colIndex].Trim()
            $val = $val.TrimStart("b'").TrimEnd("'")
            $val = $val.TrimStart("'").TrimEnd("'")
            if ($removeValues -contains $val) { return $true }
        }
    }
    
    return $false
}

# Specific handlers for complex tables
function Handle-Complex-Table($table, $rowText) {
    if ($table -eq "_calendar_events") {
        # Parse row, check if created_by_user_id (col 1) is 3,11 (Lora-Pro) or id (col 3) is 4,5,6
        $inside = $rowText.TrimStart("(").TrimEnd(")").TrimEnd(",")
        $depth = 0; $inString = $false; $cols = @(); $current = ""
        for ($i = 0; $i -lt $inside.Length; $i++) {
            $c = $inside[$i]
            if ($c -eq "'" -and ($i -eq 0 -or $inside[$i-1] -ne "\")) { $inString = -not $inString }
            elseif ($c -eq "(" -and -not $inString) { $depth++ }
            elseif ($c -eq ")" -and -not $inString) { $depth-- }
            elseif ($c -eq "," -and $depth -eq 0 -and -not $inString) { $cols += $current.Trim(); $current = ""; continue }
            $current += $c
        }
        if ($current.Trim() -ne "") { $cols += $current.Trim() }
        if ($cols.Count -ge 4) {
            $userId = $cols[1].Trim().TrimStart("'").TrimEnd("'")
            $eventId = $cols[3].Trim().TrimStart("'").TrimEnd("'")
            # Remove if created by Lora-Pro user (3,11) or event 4,5,6
            if ($userId -in @("3","11") -or $eventId -in @("4","5","6")) { return $true }
        }
        return $false
    }
    
    if ($table -eq "_event_participants") {
        # Keep only events 7 (ABPlat events) with ABPlat users (1,2)
        $inside = $rowText.TrimStart("(").TrimEnd(")").TrimEnd(",")
        $depth = 0; $inString = $false; $cols = @(); $current = ""
        for ($i = 0; $i -lt $inside.Length; $i++) {
            $c = $inside[$i]
            if ($c -eq "'" -and ($i -eq 0 -or $inside[$i-1] -ne "\")) { $inString = -not $inString }
            elseif ($c -eq "(" -and -not $inString) { $depth++ }
            elseif ($c -eq ")" -and -not $inString) { $depth-- }
            elseif ($c -eq "," -and $depth -eq 0 -and -not $inString) { $cols += $current.Trim(); $current = ""; continue }
            $current += $c
        }
        if ($current.Trim() -ne "") { $cols += $current.Trim() }
        if ($cols.Count -ge 3) {
            $eventId = $cols[1].Trim().TrimStart("'").TrimEnd("'")
            $userId = $cols[2].Trim().TrimStart("'").TrimEnd("'")
            # Keep only event_id=7 with user_id 1 or 2
            if ($eventId -eq "7" -and $userId -in @("1","2")) { return $false }
            return $true
        }
        return $false
    }
    
    if ($table -eq "_conversation_participant") {
        # Remove if user_id (col 6) is 3-12 or conversation_id (col 2) is 2,3,4,5
        $inside = $rowText.TrimStart("(").TrimEnd(")").TrimEnd(",")
        $depth = 0; $inString = $false; $cols = @(); $current = ""
        for ($i = 0; $i -lt $inside.Length; $i++) {
            $c = $inside[$i]
            if ($c -eq "'" -and ($i -eq 0 -or $inside[$i-1] -ne "\")) { $inString = -not $inString }
            elseif ($c -eq "(" -and -not $inString) { $depth++ }
            elseif ($c -eq ")" -and -not $inString) { $depth-- }
            elseif ($c -eq "," -and $depth -eq 0 -and -not $inString) { $cols += $current.Trim(); $current = ""; continue }
            $current += $c
        }
        if ($current.Trim() -ne "") { $cols += $current.Trim() }
        if ($cols.Count -ge 7) {
            $convId = $cols[2].Trim().TrimStart("'").TrimEnd("'")
            $userId = $cols[6].Trim().TrimStart("'").TrimEnd("'")
            if ($convId -in @("2","3","4","5") -or $userId -in @("3","4","5","6","7","8","9","10","11","12")) { return $true }
        }
        return $false
    }
    
    if ($table -eq "_message") {
        # Remove if conversation_id is 2,3,4,5 (Lora-Pro) or sender_id in 3-12
        $inside = $rowText.TrimStart("(").TrimEnd(")").TrimEnd(",")
        $depth = 0; $inString = $false; $cols = @(); $current = ""
        for ($i = 0; $i -lt $inside.Length; $i++) {
            $c = $inside[$i]
            if ($c -eq "'" -and ($i -eq 0 -or $inside[$i-1] -ne "\")) { $inString = -not $inString }
            elseif ($c -eq "(" -and -not $inString) { $depth++ }
            elseif ($c -eq ")" -and -not $inString) { $depth-- }
            elseif ($c -eq "," -and $depth -eq 0 -and -not $inString) { $cols += $current.Trim(); $current = ""; continue }
            $current += $c
        }
        if ($current.Trim() -ne "") { $cols += $current.Trim() }
        if ($cols.Count -ge 3) {
            $convId = $cols[0].Trim().TrimStart("'").TrimEnd("'")
            $senderId = $cols[3].Trim().TrimStart("'").TrimEnd("'")
            if ($convId -in @("2","3","4","5") -or $senderId -in @("3","4","5","6","7","8","9","10","11","12")) { return $true }
        }
        return $false
    }
    
    if ($table -eq "_project_task_users") {
        # Remove if user_id (col 1) in 3-12 OR project_task_id references Lora-Pro tasks
        # Lora-Pro tasks are: task 2 (project 3), task 3 (project 3), task 12 (project 6)
        $inside = $rowText.TrimStart("(").TrimEnd(")").TrimEnd(",")
        $depth = 0; $inString = $false; $cols = @(); $current = ""
        for ($i = 0; $i -lt $inside.Length; $i++) {
            $c = $inside[$i]
            if ($c -eq "'" -and ($i -eq 0 -or $inside[$i-1] -ne "\")) { $inString = -not $inString }
            elseif ($c -eq "(" -and -not $inString) { $depth++ }
            elseif ($c -eq ")" -and -not $inString) { $depth-- }
            elseif ($c -eq "," -and $depth -eq 0 -and -not $inString) { $cols += $current.Trim(); $current = ""; continue }
            $current += $c
        }
        if ($current.Trim() -ne "") { $cols += $current.Trim() }
        if ($cols.Count -ge 2) {
            $taskId = $cols[0].Trim().TrimStart("'").TrimEnd("'")
            $userId = $cols[1].Trim().TrimStart("'").TrimEnd("'")
            # Lora-Pro task ids: 2,3,12 | Lora-Pro user ids: 3-12
            if ($taskId -in @("2","3","12") -or $userId -in @("3","4","5","6","7","8","9","10","11","12")) { return $true }
        }
        return $false
    }
    
    if ($table -eq "_project_users") {
        # Remove if project_id (col 0) in 2,3,4,6 OR user_id (col 1) in 3-12
        $inside = $rowText.TrimStart("(").TrimEnd(")").TrimEnd(",")
        $depth = 0; $inString = $false; $cols = @(); $current = ""
        for ($i = 0; $i -lt $inside.Length; $i++) {
            $c = $inside[$i]
            if ($c -eq "'" -and ($i -eq 0 -or $inside[$i-1] -ne "\")) { $inString = -not $inString }
            elseif ($c -eq "(" -and -not $inString) { $depth++ }
            elseif ($c -eq ")" -and -not $inString) { $depth-- }
            elseif ($c -eq "," -and $depth -eq 0 -and -not $inString) { $cols += $current.Trim(); $current = ""; continue }
            $current += $c
        }
        if ($current.Trim() -ne "") { $cols += $current.Trim() }
        if ($cols.Count -ge 2) {
            $projId = $cols[0].Trim().TrimStart("'").TrimEnd("'")
            $userId = $cols[1].Trim().TrimStart("'").TrimEnd("'")
            if ($projId -in @("2","3","4","6") -or $userId -in @("3","4","5","6","7","8","9","10","11","12")) { return $true }
        }
        return $false
    }
    
    return $false
}

for ($i = 0; $i -lt $lines.Count; $i++) {
    $line = $lines[$i]
    
    # Detect table from CREATE TABLE or -- Dumping data comments
    if ($line -match "^CREATE TABLE IF NOT EXISTS `_(\w+)`") {
        $currentTable = "_$($matches[1])"
    }
    elseif ($line -match "^-- Dumping data for table `_(\w+)`") {
        $currentTable = "_$($matches[1])"
    }
    
    # Detect INSERT IGNORE INTO
    if ($line -match "^INSERT IGNORE INTO `_(\w+)`") {
        $inInsert = $true
        $modifiedInsert = $false
        $currentTable = "_$($matches[1])"
        $insertBuffer.Clear()
        $insertBuffer.Add($line)  # Keep the header line
        continue
    }
    
    if ($inInsert) {
        # Check if this is the last row of the insert (ends with semicolon)
        $isLastRow = $line.TrimEnd().EndsWith(";")
        
        if ($isLastRow) {
            # Last row - process it
            $rawRow = $line.TrimEnd().TrimEnd(";")
            
            $shouldRemove = $false
            if ($filterRules.ContainsKey($currentTable)) {
                $rule = $filterRules[$currentTable]
                if ($rule.type -eq "custom") {
                    $shouldRemove = Handle-Complex-Table $currentTable $rawRow
                } else {
                    $shouldRemove = Should-Remove-Row $currentTable $rawRow
                }
            }
            
            if (-not $shouldRemove) {
                if ($modifiedInsert) {
                    # We previously removed some rows - add comma separator before this one
                    $insertBuffer.Add(",")
                }
                $insertBuffer.Add("$rawRow;")
            } else {
                # Last row removed, need to end properly
                if ($modifiedInsert) {
                    # There were previous rows kept, so we need to remove the last trailing comma
                    # by just ending the semicolon-less row
                    $insertBuffer.Add(";")
                }
                # else: all rows removed, just keep nothing
            }
            
            # Write out the buffer
            if ($insertBuffer.Count -gt 1) {
                foreach ($bufLine in $insertBuffer) {
                    $outLines.Add($bufLine)
                }
            } elseif ($insertBuffer.Count -eq 1) {
                # Only the header was added - no rows kept
                # Skip entire insert
                # Nothing to add
            }
            
            $inInsert = $false
            continue
        }
        
        # Not the last row - process the row
        $rawRow = $line.TrimEnd().TrimEnd(",")
        
        $shouldRemove = $false
        if ($filterRules.ContainsKey($currentTable)) {
            $rule = $filterRules[$currentTable]
            if ($rule.type -eq "custom") {
                $shouldRemove = Handle-Complex-Table $currentTable $rawRow
            } else {
                $shouldRemove = Should-Remove-Row $currentTable $rawRow
            }
        }
        
        if (-not $shouldRemove) {
            if ($modifiedInsert) {
                $insertBuffer.Add(",")
            }
            $insertBuffer.Add("$rawRow,")
            $modifiedInsert = $true
        }
        # else: skip the row
        
        continue
    }
    
    # Handle non-insert lines
    # For AUTO_INCREMENT values that reference removed sequences
    if ($line -match "^ALTER TABLE `_(\w+)` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=(\d+);$") {
        $tbl = "_$($matches[1])"
        $autoInc = [int]$matches[2]
        
        if ($tbl -eq "_company" -and $autoInc -eq 3) {
            # Company had id=1 and id=2, removed id=2, so auto_inc should be 2
            $outLines.Add("ALTER TABLE `_company` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;")
        }
        elseif ($tbl -eq "_user" -and $autoInc -eq 13) {
            # Users ids 1-12, removed 3-12, kept 1-2
            $outLines.Add("ALTER TABLE `_user` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;")
        }
        elseif ($tbl -eq "_user_profile" -and $autoInc -eq 13) {
            $outLines.Add("ALTER TABLE `_user_profile` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;")
        }
        elseif ($tbl -eq "_conversation" -and $autoInc -eq 6) {
            # Only conversation 1 is kept
            $outLines.Add("ALTER TABLE `_conversation` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;")
        }
        elseif ($tbl -eq "_conversation_participant" -and $autoInc -eq 11) {
            # Participants 1,2 kept (ABPlat conv)
            $outLines.Add("ALTER TABLE `_conversation_participant` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;")
        }
        elseif ($tbl -eq "_message" -and $autoInc -eq 14) {
            # Messages from conv 1 kept
            $outLines.Add("ALTER TABLE `_message` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;")
        }
        elseif ($tbl -eq "_company_support") {
            # No primary key auto_inc, skip
            $outLines.Add($line)
        }
        elseif ($tbl -eq "_project" -and $autoInc -eq 8) {
            # Only projects 1 and 7 kept
            $outLines.Add("ALTER TABLE `_project` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;")
        }
        elseif ($tbl -eq "_project_task" -and $autoInc -eq 26) {
            # Tasks: ABPlat tasks kept (ids 4,5,6,7,8,10,11,13,14,15,17-25)
            $outLines.Add("ALTER TABLE `_project_task` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;")
        }
        elseif ($tbl -eq "_task_status" -and $autoInc -eq 23) {
            # Status entries for projects 1 and 7 kept
            $outLines.Add("ALTER TABLE `_task_status` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;")
        }
        elseif ($tbl -eq "_calendar_events" -and $autoInc -eq 8) {
            # Only event 7 kept
            $outLines.Add("ALTER TABLE `_calendar_events` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;")
        }
        elseif ($tbl -eq "_event_participants") {
            # Keep as is with auto_inc
            $outLines.Add($line)
        }
        elseif ($tbl -eq "_movable_asset") {
            $outLines.Add($line)
        }
        else {
            $outLines.Add($line)
        }
    }
    else {
        $outLines.Add($line)
    }
}

# Write the output
$outLines | Set-Content $outputFile -Encoding UTF8

Write-Host "Done! Cleaned file written to $outputFile"
