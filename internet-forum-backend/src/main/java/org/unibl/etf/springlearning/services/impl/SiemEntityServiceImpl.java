package org.unibl.etf.springlearning.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.models.entities.SiemEntity;
import org.unibl.etf.springlearning.repositories.SiemEntityRepository;
import org.unibl.etf.springlearning.services.SiemEntityService;

import java.sql.Time;
import java.util.Date;
@Service
@Transactional
public class SiemEntityServiceImpl implements SiemEntityService {
    private final SiemEntityRepository repository;
    @PersistenceContext
    private EntityManager entityManager;

    public SiemEntityServiceImpl(SiemEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public void log(String log) {
        SiemEntity siemEntity=new SiemEntity();
        siemEntity.setId(null);
        siemEntity.setLog(log);
        siemEntity.setLogDate(new Date());
        siemEntity.setLogTime(new Time(System.currentTimeMillis()));
        siemEntity=repository.saveAndFlush(siemEntity);
        entityManager.refresh(siemEntity);
    }
}
