package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.PostMovableAssetRequest;
import com.ColPlat.Backend.model.dto.request.PostMovableAssetStatusChangeRequest;
import com.ColPlat.Backend.model.dto.response.MovableAssetResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.MovableAsset;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.repository.MovableAssetRepository;
import com.ColPlat.Backend.service.LocationService;
import com.ColPlat.Backend.service.MovableAssetService;
import com.ColPlat.Backend.service.UserProfileService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovableAssetServiceImplementation implements MovableAssetService {
    private final MovableAssetRepository movableAssetRepository;
    private final LocationService locationService;
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Override
    public List<MovableAssetResponse> getAllByCompany(Company companyFromToken) {
        List<MovableAsset> movableAssets = movableAssetRepository.findAllByCompany(companyFromToken);
        List<MovableAssetResponse> movableAssetResponses = new ArrayList<>();
        for(MovableAsset movableAsset : movableAssets){
            MovableAssetResponse movableAssetResponse = MovableAssetResponse.builder()
                    .id(movableAsset.getId())
                    .identifier(movableAsset.getIdentifier())
                    .company(movableAsset.getCompany())
                    .name(movableAsset.getName())
                    .barcode(movableAsset.getBarcode())
                    .type(movableAsset.getType())
                    .model(movableAsset.getModel())
                    .manufacturer(movableAsset.getManufacturer())
                    .category(movableAsset.getCategory())
                    .serialNumber(movableAsset.getSerialNumber())
                    .location(locationService.getLocationById(movableAsset.getCompany().getLocationId()))
                    .currentUser(
                            Optional.ofNullable(movableAsset.getCurrentUser())
                                    .map(userProfileService::getUserResponseFromUser)
                                    .orElse(null)
                    )
                    .issuedBy(
                            Optional.ofNullable(movableAsset.getIssuedBy())
                                    .map(userProfileService::getUserResponseFromUser)
                                    .orElse(null)
                    )

                    .movableAssetStatus(movableAsset.getMovableAssetStatus())
                    .purchaseDate(movableAsset.getPurchaseDate())
                    .insuranceDate(movableAsset.getInsuranceDate())
                    .comment(movableAsset.getComment())
                    .unit(movableAsset.getUnit())
                    .amount(movableAsset.getAmount())
                    .build();
                    movableAssetResponses.add(movableAssetResponse);
        }
        return movableAssetResponses;
    }

    @Override
    public MovableAsset getMovableAssetById(Long id) {
        Optional<MovableAsset> movableAssetOptional = movableAssetRepository.findById(id);
        if (movableAssetOptional.isPresent()) {
            return movableAssetOptional.get();
        }else return null;
    }

    @Override
    public MovableAsset update(PostMovableAssetRequest movableAsset) {
        return null;
    }

    @Override
    public MovableAsset create(PostMovableAssetRequest movableAsset, Company company, User issuedBy) {

        User currentUser = null;
        if (movableAsset.getCurrentUserId() != null) {
            currentUser = userService.findById(movableAsset.getCurrentUserId());
        }

        MovableAsset movableAsset1 = MovableAsset.builder()
                .identifier(movableAsset.getIdentifier())
                .company(company)
                .name(movableAsset.getName())
                .barcode(movableAsset.getBarcode())
                .type(movableAsset.getType())
                .model(movableAsset.getModel())
                .manufacturer(movableAsset.getManufacturer())
                .category(movableAsset.getCategory())
                .serialNumber(movableAsset.getSerialNumber())
                .location(locationService.getLocationById(company.getLocationId()))
                .currentUser(currentUser)
                .issuedBy(issuedBy)
                .movableAssetStatus(movableAsset.getMovableAssetStatus())
                .purchaseDate(movableAsset.getPurchaseDate())
                .insuranceDate(movableAsset.getInsuranceDate())
                .comment(movableAsset.getComment())
                .unit(movableAsset.getUnit())
                .amount(movableAsset.getAmount())
                .build();

        movableAssetRepository.save(movableAsset1);
        return movableAsset1;
    }

    @Override
    public void changeStatus(Long assetId, User issuedBy, PostMovableAssetStatusChangeRequest movableAssetStatusChange) {
        MovableAsset movableAsset = getMovableAssetById(assetId);
        if (movableAsset != null) {
            movableAsset.setMovableAssetStatus(movableAssetStatusChange.getStatus());
            movableAsset.setIssuedBy(issuedBy);
            movableAsset.setCurrentUser(userService.findById(movableAssetStatusChange.getCurrentUserId()));
            movableAssetRepository.save(movableAsset);
        }
    }


}
