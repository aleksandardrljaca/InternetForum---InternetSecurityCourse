package org.unibl.etf.springlearning.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unibl.etf.springlearning.models.entities.SiemEntity;

@Repository
public interface SiemEntityRepository extends JpaRepository<SiemEntity,Integer> {
}
