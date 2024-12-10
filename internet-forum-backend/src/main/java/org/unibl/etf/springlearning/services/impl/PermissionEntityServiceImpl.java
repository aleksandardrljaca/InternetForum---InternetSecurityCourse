package org.unibl.etf.springlearning.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.models.dto.Permission;
import org.unibl.etf.springlearning.models.dto.PermissionRequest;
import org.unibl.etf.springlearning.models.entities.PermissionsEntity;
import org.unibl.etf.springlearning.repositories.PermissionEntityRepository;
import org.unibl.etf.springlearning.services.PermissionEntityService;
@Service
@Transactional
public class PermissionEntityServiceImpl implements PermissionEntityService {
    private final ModelMapper modelMapper;
    private final PermissionEntityRepository repository;
    @PersistenceContext
    private EntityManager entityManager;

    public PermissionEntityServiceImpl(ModelMapper modelMapper, PermissionEntityRepository repository) {
        this.modelMapper = modelMapper;
        this.repository = repository;
    }

    @Override
    public Permission insert(PermissionRequest request) {
        PermissionsEntity permissionEntity=modelMapper.map(request,PermissionsEntity.class);
        permissionEntity.setId(null);
        permissionEntity=repository.saveAndFlush(permissionEntity);
        entityManager.refresh(permissionEntity);
        return modelMapper.map(permissionEntity,Permission.class);
    }

    @Override
    public void deleteByUserId(Integer id) {
        repository.deletePermissionsEntitiesByUserByUserId_Id(id);
    }
}
