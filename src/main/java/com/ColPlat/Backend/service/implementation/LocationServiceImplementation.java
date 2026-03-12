package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.repository.LocationRepository;
import com.ColPlat.Backend.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationServiceImplementation implements LocationService {

    private final LocationRepository locationRepository;
    private final CityService cityService;
    private final DistrictService districtService;
    private final RegionService regionService;
    private final CountryService countryService;

    @Override
    public Location getLocationById(Long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        return optionalLocation.orElse(null);
    }

    @Override
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public String getLocationStringFromId(Long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();
            City city = cityService.getCityByName(location.getCity());
            if (city != null) {
                District district = districtService.getDistrictById(city.getDistrictId());
                Region region = regionService.getRegionById(district.getRegionId());
                Country country = countryService.getCountryById(region.getCountryId());
                StringBuilder locationString = new StringBuilder();
                locationString.append(location.getAddress());
                locationString.append(", ");
                locationString.append(location.getCity());
                locationString.append(", ");
                locationString.append(district.getName());
                locationString.append(", ");
                locationString.append(region.getName());
                locationString.append(", ");
                locationString.append(country.getName());
                return locationString.toString();
            } else {
                return location.getAddress() + ", " + location.getCity();
            }

        }
        return null;
    }

    @Override
    public Location updateLocation(Location location) {
        Optional<Location> optionalLocation = locationRepository.findById(location.getId());
        if (optionalLocation.isPresent()) {
            Location locationToUpdate = optionalLocation.get();
            locationToUpdate.setAddress(location.getAddress());
            locationToUpdate.setCity(location.getCity());
            locationToUpdate.setDistrictId(location.getDistrictId());
            locationToUpdate.setRegionId(location.getRegionId());
            locationToUpdate.setCountryId(location.getCountryId());
            locationRepository.save(locationToUpdate);
            return locationToUpdate;
        }
        return null;
    }
}
