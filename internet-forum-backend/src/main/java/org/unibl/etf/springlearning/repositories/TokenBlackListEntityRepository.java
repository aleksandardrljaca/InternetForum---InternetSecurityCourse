package org.unibl.etf.springlearning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.springlearning.models.entities.TokenBlackListEntity;

public interface TokenBlackListEntityRepository extends JpaRepository<TokenBlackListEntity,Integer> {
    Boolean existsTokenBlackListEntityByToken(String token);
}
