package com.dd.campsites.service.campsitesindia;

import com.dd.campsites.domain.Photos;
import com.dd.campsites.domain.Room;
import java.util.List;
import java.util.Optional;

public interface RoomBusinessService {
    /**
     * Get the "id" Features.
     *
     * @param id the id of the Listing.
     * @return the entity.
     */
    List<Room> findByListingId(Long id);
}
