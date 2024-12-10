package org.unibl.etf.springlearning.services;


import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.models.dto.User;
import org.unibl.etf.springlearning.models.dto.UserApprovalRestrictionRequest;
import org.unibl.etf.springlearning.models.dto.UserRegistrationRequest;
import org.unibl.etf.springlearning.models.dto.UserUpdateRequest;

import java.util.List;

public interface UserEntityService{
    List<User> findAll();
    User findByUsername(String username);
    User findById(Integer id);
    User findByEmail(String email);
    User insert(UserRegistrationRequest request);
    User updateVerificationCode(Integer id,String code);
    User update(Integer id, UserUpdateRequest request) throws NotFoundException;
    User approve(Integer id, UserApprovalRestrictionRequest request) throws NotFoundException;
    User restrict(Integer id,UserApprovalRestrictionRequest request) throws NotFoundException;
}
