package org.unibl.etf.springlearning.services;

import org.unibl.etf.springlearning.models.dto.Permission;
import org.unibl.etf.springlearning.models.dto.PermissionRequest;

public interface PermissionEntityService {
    Permission insert(PermissionRequest request);
    void deleteByUserId(Integer id);
}
