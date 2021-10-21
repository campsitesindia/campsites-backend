package com.dd.campsites.service.campsitesindia;

import com.dd.campsites.domain.Room;
import com.dd.campsites.domain.RoomsForListing;
import com.dd.campsites.repository.RoomRepository;
import com.dd.campsites.repository.RoomsForListingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoomBusinessServiceImpl implements RoomBusinessService {

    private final RoomsForListingRepository roomsForListingRepository;

    public RoomBusinessServiceImpl(RoomsForListingRepository roomsForListingRepository) {
        this.roomsForListingRepository = roomsForListingRepository;
    }

    @Override
    public List<Room> findByListingId(Long id) {
        List<Room> rooms = new ArrayList<Room>();
        List<RoomsForListing> rfl = roomsForListingRepository.findAllByListingIdEquals(id);

        for (RoomsForListing roomsList : rfl) {
            rooms.add(roomsList.getRoom());
        }
        return rooms;
    }
}
