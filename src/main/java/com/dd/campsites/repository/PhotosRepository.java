package com.dd.campsites.repository;

import com.dd.campsites.domain.Photos;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Photos entity.
 */
@Repository
public interface PhotosRepository extends JpaRepository<Photos, Long>, JpaSpecificationExecutor<Photos> {
    @Query(
        value = "select distinct photos from Photos photos left join fetch photos.tags",
        countQuery = "select count(distinct photos) from Photos photos"
    )
    Page<Photos> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct photos from Photos photos left join fetch photos.tags")
    List<Photos> findAllWithEagerRelationships();

    @Query("select photos from Photos photos left join fetch photos.tags where photos.id =:id")
    Optional<Photos> findOneWithEagerRelationships(@Param("id") Long id);

    List<Photos> findAllByListingIdEquals(long id);
}
