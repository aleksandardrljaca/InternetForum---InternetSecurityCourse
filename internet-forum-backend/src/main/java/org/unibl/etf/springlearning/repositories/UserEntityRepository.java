package org.unibl.etf.springlearning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.springlearning.models.entities.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity,Integer> {
    UserEntity findUserEntityByUsername(String username);
    UserEntity findUserEntityById(Integer id);
    UserEntity findUserEntityByEmail(String email);
    boolean existsUserEntityByEmail(String email);
}
