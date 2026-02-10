package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.PostMovableAssetRequest;
import com.ColPlat.Backend.model.dto.request.PostMovableAssetStatusChangeRequest;
import com.ColPlat.Backend.model.dto.request.UpdateMovableAssetRequest;
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
    public MovableAsset update(UpdateMovableAssetRequest movableAsset, User issuedBy) {
        Optional<MovableAsset> movableAssetOptional = movableAssetRepository.findById(movableAsset.getId());
        if (!movableAssetOptional.isPresent()) { return null; }

        User currentUser = null;
        if (movableAsset.getCurrentUserId() != null) {
            currentUser = userService.findById(movableAsset.getCurrentUserId());
        }

        MovableAsset movableAsset1 = movableAssetOptional.get();
        movableAsset1.setIdentifier(movableAsset.getIdentifier());
        movableAsset1.setName(movableAsset.getName());
        movableAsset1.setBarcode(movableAsset.getBarcode());
        movableAsset1.setType(movableAsset.getType());
        movableAsset1.setModel(movableAsset.getModel());
        movableAsset1.setManufacturer(movableAsset.getManufacturer());
        movableAsset1.setCategory(movableAsset.getCategory());
        movableAsset1.setSerialNumber(movableAsset.getSerialNumber());
        movableAsset1.setUnit(movableAsset.getUnit());
        movableAsset1.setAmount(movableAsset.getAmount());
        movableAsset1.setIssuedBy(issuedBy);
        movableAsset1.setPurchaseDate(movableAsset.getPurchaseDate());
        movableAsset1.setInsuranceDate(movableAsset.getInsuranceDate());
        movableAsset1.setComment(movableAsset.getComment());
        movableAsset1.setCurrentUser(currentUser);
        return movableAssetRepository.save(movableAsset1);
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
    public void deleteMovableAsset(Long id) {
        movableAssetRepository.deleteById(id);
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
