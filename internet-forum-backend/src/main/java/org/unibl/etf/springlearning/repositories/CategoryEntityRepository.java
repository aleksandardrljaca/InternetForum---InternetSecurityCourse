package org.unibl.etf.springlearning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unibl.etf.springlearning.models.entities.CategoryEntity;
@Repository
public interface CategoryEntityRepository extends JpaRepository<CategoryEntity,Integer> {
    @Query("SELECT c FROM CategoryEntity c where c.name=:name")
    CategoryEntity findCategoryEntitiesByName(String name);
}
