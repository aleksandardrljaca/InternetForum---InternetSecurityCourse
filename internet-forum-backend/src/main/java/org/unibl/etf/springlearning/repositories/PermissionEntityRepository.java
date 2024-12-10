package org.unibl.etf.springlearning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.springlearning.models.entities.PermissionsEntity;
@Repository
public interface PermissionEntityRepository extends JpaRepository<PermissionsEntity,Integer> {

    void deletePermissionsEntitiesByUserByUserId_Id(Integer id);
}
