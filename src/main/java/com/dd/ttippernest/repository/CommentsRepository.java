package com.dd.ttippernest.repository;

import com.dd.ttippernest.domain.Comments;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Comments entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long>, JpaSpecificationExecutor<Comments> {
    @Query("select comments from Comments comments where comments.user.login = ?#{principal.username}")
    List<Comments> findByUserIsCurrentUser();
}
