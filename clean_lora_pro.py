import re

input_file = r"e:\DOCS\Projekti\ABPLAT\ColPlatBack\newabp_with_alldb_geo.sql"

with open(input_file, 'r', encoding='utf-8') as f:
    content = f.read()

lines = content.split('\n')

# Which user ids are Lora-Pro
lora_users = set(str(i) for i in range(3, 13))
lora_projects = {"2", "3", "4", "5", "6"}
lora_tasks = {"2", "3", "12"}
lora_conversations = {"2", "3", "4", "5"}
lora_events = {"4", "5", "6"}

def get_col(vals, idx):
    """Get column value at index, stripped of b' prefix and quotes."""
    if idx >= len(vals):
        return None
    val = vals[idx].strip()
    val = val.replace("b'", "").strip("'")
    return val

def parse_row_vals(row_text):
    """Parse a SQL value row like (val1, val2, 'str3') into value list."""
    t = row_text.strip()
    if not t.startswith('('):
        return []
    inner = t[1:]
    if inner.endswith(',') or inner.endswith(';'):
        inner = inner[:-1]
    if inner.endswith(')'):
        inner = inner[:-1]
    vals = []
    cur = ""
    depth = 0
    instr = False
    for c in inner:
        if c == "'" and (not cur or cur[-1] != '\\'):
            instr = not instr
            cur += c
        elif c == '(' and not instr:
            depth += 1
            cur += c
        elif c == ')' and not instr:
            depth -= 1
            cur += c
        elif c == ',' and depth == 0 and not instr:
            vals.append(cur.strip())
            cur = ""
        else:
            cur += c
    if cur.strip():
        vals.append(cur.strip())
    return vals

def should_skip(table, row_text):
    """Return True if this row should be removed."""
    vals = parse_row_vals(row_text)
    if not vals:
        return False
    
    if table == "_company":
        return get_col(vals, 6) == "2"
    elif table == "_company_support":
        return get_col(vals, 0) == "2"
    elif table == "_user":
        return get_col(vals, 5) in lora_users
    elif table == "_user_profile":
        return get_col(vals, 1) in lora_users
    elif table == "_user_roles":
        return get_col(vals, 0) in lora_users
    elif table == "_project":
        return get_col(vals, 2) in lora_projects
    elif table == "_project_task":
        return get_col(vals, 2) in lora_projects
    elif table == "_task_status":
        return get_col(vals, 1) in lora_projects
    elif table == "_movable_asset":
        return get_col(vals, 7) == "2"
    elif table == "_conversation":
        return get_col(vals, 1) == "2"
    elif table == "_conversation_participant":
        return get_col(vals, 2) in lora_conversations or get_col(vals, 6) in lora_users
    elif table == "_message":
        return get_col(vals, 0) in lora_conversations or get_col(vals, 3) in lora_users
    elif table == "_project_task_users":
        return get_col(vals, 0) in lora_tasks or get_col(vals, 1) in lora_users
    elif table == "_project_users":
        return get_col(vals, 0) in lora_projects or get_col(vals, 1) in lora_users
    elif table == "_calendar_events":
        return get_col(vals, 1) in lora_users or get_col(vals, 3) in lora_events
    elif table == "_event_participants":
        # Keep only event_id=7 with user_id 1 or 2
        eid = get_col(vals, 1)
        uid = get_col(vals, 2)
        if eid == "7" and uid in {"1", "2"}:
            return False
        return True
    
    return False

# Process the file
out = []
i = 0
current_table = ""

while i < len(lines):
    line = lines[i]
    
    # Track table from CREATE TABLE
    m = re.match(r"^CREATE TABLE IF NOT EXISTS `_(\w+)`", line)
    if m:
        current_table = "_" + m.group(1)
        out.append(line)
        i += 1
        continue
    
    # Track table from Dumping data
    m = re.match(r"^-- Dumping data for table `_(\w+)`", line)
    if m:
        current_table = "_" + m.group(1)
    
    # Detect INSERT IGNORE INTO
    m = re.match(r"^(INSERT IGNORE INTO `_(\w+)` .*)$", line)
    if m:
        tbl = "_" + m.group(2)
        
        # Collect the full INSERT block (all lines until semicolon at end of a row)
        insert_lines = [line]
        i += 1
        while i < len(lines):
            insert_lines.append(lines[i])
            stripped = lines[i].rstrip()
            if stripped.endswith(";"):
                i += 1
                break
            i += 1
        
        # Join into one block
        block = '\n'.join(insert_lines)
        
        # Extract the header and the VALUES part
        # Find the VALUES keyword line
        header_end = m.group(1)
        values_part = block[len(header_end):].strip()
        
        # Now parse individual rows from values_part
        # Each row is (val1, val2, ...), and the last ends with );
        rows = []
        depth = 0
        cur_row = ""
        instr = False
        for c in values_part:
            if c == "'" and (not cur_row or cur_row[-1] != '\\'):
                instr = not instr
                cur_row += c
            elif c == '(' and not instr:
                if depth == 0:
                    cur_row = "("
                else:
                    cur_row += c
                depth += 1
            elif c == ')' and not instr:
                depth -= 1
                if depth == 0:
                    cur_row += c
                    rows.append(cur_row)
                    cur_row = ""
                else:
                    cur_row += c
            elif depth > 0:
                cur_row += c
        
        # Filter rows
        kept = [r for r in rows if not should_skip(tbl, r)]
        
        if kept:
            out.append(header_end)
            out.append("VALUES")
            for idx, r in enumerate(kept):
                if idx < len(kept) - 1:
                    out.append(r + ",")
                else:
                    out.append(r + ";")
        
        continue
    
    # Fix AUTO_INCREMENT values
    m = re.match(r"^(ALTER TABLE `_(\w+)` MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=)(\d+);$", line)
    if m:
        tbl = "_" + m.group(2)
        prefix = m.group(1)
        old_val = int(m.group(3))
        
        adjustments = {
            "_company": 2,
            "_user": 3,
            "_user_profile": 3,
            "_conversation": 2,
            "_conversation_participant": 3,
        }
        
        if tbl in adjustments:
            new_val = adjustments[tbl]
            if new_val != old_val:
                out.append(f"{prefix}{new_val};")
                i += 1
                continue
        
        out.append(line)
        i += 1
        continue
    
    out.append(line)
    i += 1

# Write output
output_text = '\n'.join(out)
with open(input_file, 'w', encoding='utf-8') as f:
    f.write(output_text)

print(f"Done! Cleaned file written to {input_file}")
print(f"Original lines: {len(lines)}, Output lines: {len(out)}")