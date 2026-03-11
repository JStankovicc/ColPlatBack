package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.Location;

public interface LocationService {
    Location getLocationById(Long id);

    public Location createLocation(Location location);

    public String getLocationStringFromId(Long id);
}
