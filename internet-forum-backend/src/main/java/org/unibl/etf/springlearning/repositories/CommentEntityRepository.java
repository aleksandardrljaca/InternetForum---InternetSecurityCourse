package org.unibl.etf.springlearning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unibl.etf.springlearning.models.entities.CommentEntity;

import java.util.List;
@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity,Integer> {
    @Query("SELECT c FROM CommentEntity c WHERE c.categoryByCategoryId.name=:name")
    List<CommentEntity> findCommentEntitiesByCategoryName(String name);

}
