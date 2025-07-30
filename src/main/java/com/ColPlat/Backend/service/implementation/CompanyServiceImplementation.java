package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.dto.response.CompanySettingsInfoResponse;
import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.repository.CompanyRepository;
import com.ColPlat.Backend.service.*;
import com.ColPlat.Backend.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImplementation implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final LocationService locationService;
    private final CountryService countryService;
    private final RegionService regionService;
    private final CityService cityService;

    @Override
    public CompanyResponse getCompanyInfoFromToken(String token) {
        String email = jwtService.extractUserName(token);
        User user = userService.findByEmail(email);
        Optional<Company> company = companyRepository.findById(user.getCompanyId());
        if(company.isPresent()) {
            Company companyData = company.get();
            CompanyResponse companyResponse = null;

            try {
                companyResponse = companyData.getCompanyResponse();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return companyResponse;
        }
        return null;
    }

    @Override
    public CompanySettingsInfoResponse getCompanySettingsInfoFromToken(String token) {
        String email = jwtService.extractUserName(token);
        User user = userService.findByEmail(email);
        Optional<Company> company = companyRepository.findById(user.getCompanyId());
        if(company.isPresent()) {
            Company companyData = company.get();
            CompanySettingsInfoResponse companySettingsInfoResponse = new CompanySettingsInfoResponse();
            Location location = locationService.getLocationById(companyData.getLocationId());
            City city = cityService.getCityById(location.getCityId());
            Region region = regionService.getRegionById(city.getRegionId());
            Country country = countryService.getCounytryById(region.getCountryId());


            try {
                companySettingsInfoResponse.setCompanyName(companyData.getName());
                companySettingsInfoResponse.setRegistrationNumber(companyData.getRegistryNum());
                companySettingsInfoResponse.setAddress(location.getAddress());
                companySettingsInfoResponse.setLogoPic(ImageUtils.getInstance().compressPngImageToThumbnail(companyData.getCompanyLogoPic()));
                companySettingsInfoResponse.setCountry(country.getName());
                companySettingsInfoResponse.setRegion(region.getName());
                companySettingsInfoResponse.setCity(city.getName());
                companySettingsInfoResponse.setSupportTypes(companyData.getSupportTypes().stream().map(Enum::name).toList());
                companySettingsInfoResponse.setBasicPackages(companyData.getBasicProfilesNum());
                companySettingsInfoResponse.setStandardPackages(companyData.getAdvancedProfilesNum());
                companySettingsInfoResponse.setPremiumPackages(companyData.getPremiumProfilesNum());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return companySettingsInfoResponse;
        }
        return null;
    }

    @Override
    public Company findById(Long companyId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if(companyOptional.isPresent()){
            return companyOptional.get();
        }
        return null;
    }

    @Override
    public void replaceLogo(Company company, byte[] imageBytes) {
        company.setCompanyLogoPic(imageBytes);
        companyRepository.save(company);
    }

    @Override
    public Company findCompanyFromUser(User user) {
        Optional<Company> company = companyRepository.findById(user.getCompanyId());
        return company.orElse(null);
    }


}
