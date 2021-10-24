package com.dd.campsites.repository;

import com.dd.campsites.domain.Images;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Images entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImagesRepository extends JpaRepository<Images, Long>, JpaSpecificationExecutor<Images> {
    @Query("select images from Images images where images.user.login = ?#{principal.username}")
    List<Images> findByUserIsCurrentUser();
}
