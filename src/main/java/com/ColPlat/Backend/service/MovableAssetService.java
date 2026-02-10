package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.PostMovableAssetRequest;
import com.ColPlat.Backend.model.dto.request.PostMovableAssetStatusChangeRequest;
import com.ColPlat.Backend.model.dto.request.UpdateMovableAssetRequest;
import com.ColPlat.Backend.model.dto.response.MovableAssetResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.MovableAsset;
import com.ColPlat.Backend.model.entity.User;

import java.util.List;

public interface MovableAssetService {
    List<MovableAssetResponse> getAllByCompany(Company companyFromToken);

    MovableAsset getMovableAssetById(Long id);
    MovableAsset update(UpdateMovableAssetRequest movableAsset, User issuedBy);
    MovableAsset create(PostMovableAssetRequest movableAsset, Company company, User issuedBy);
    void deleteMovableAsset(Long id);

    void changeStatus(Long assetId, User issuedBy, PostMovableAssetStatusChangeRequest movableAssetStatusChange);
}
