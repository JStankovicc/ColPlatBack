package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.PostMovableAssetRequest;
import com.ColPlat.Backend.model.dto.request.PostMovableAssetStatusChangeRequest;
import com.ColPlat.Backend.model.dto.request.UpdateMovableAssetRequest;
import com.ColPlat.Backend.model.dto.response.MovableAssetResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.MovableAsset;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.MovableAssetService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/asset")
@RequiredArgsConstructor
public class AssetController {

    private final MovableAssetService movableAssetService;
    private final JwtService jwtService;
    private final CompanyService companyService;
    private final UserService userService;

    @GetMapping("/movableAsset/all")
    public List<MovableAssetResponse> getAllMovableAssets(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return movableAssetService.getAllByCompany(companyService.getCompanyFromToken(token));
    }

    @PostMapping("/movableAsset")
    public MovableAsset createMovableAsset(@RequestHeader("Authorization") String authororizationHeader, @RequestBody PostMovableAssetRequest movableAsset) {
        String token = authororizationHeader.replace("Bearer ", "");
        Company company = companyService.getCompanyFromToken(token);
        User issuedBy = userService.findByEmail(jwtService.extractUserName(token));
        return movableAssetService.create(movableAsset, company, issuedBy);
    }

    @PostMapping("/movableAsset/changeStatus")
    public void updateMovableAsset(@RequestHeader("Authorization") String authororizationHeader, @RequestBody PostMovableAssetStatusChangeRequest movableAssetStatusChange, @RequestParam Long assetId) {
        String token = authororizationHeader.replace("Bearer ", "");
        User issuedBy = userService.findByEmail(jwtService.extractUserName(token));
        movableAssetService.changeStatus(assetId, issuedBy, movableAssetStatusChange);
    }

    @PutMapping("/movableAsset")
    public ResponseEntity<MovableAsset> updateMovableAsset(@RequestHeader("Authorization") String authororizationHeader, @RequestBody UpdateMovableAssetRequest movableAsset) {
        String token = authororizationHeader.replace("Bearer ", "");
        User issuedBy = userService.findByEmail(jwtService.extractUserName(token));
        return ResponseEntity.ok(movableAssetService.update(movableAsset, issuedBy));
    }

    @DeleteMapping("/movableAsset")
    public void deleteMovableAsset(@RequestHeader("Authorization") String authororizationHeader, @RequestParam Long assetId) {
        movableAssetService.deleteMovableAsset(assetId);
    }
}
