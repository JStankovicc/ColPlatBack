package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.Location;
import com.ColPlat.Backend.repository.LocationRepository;
import com.ColPlat.Backend.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LocationServiceImplementation implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location getLocationById(Long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        return optionalLocation.orElse(null);
    }
}
