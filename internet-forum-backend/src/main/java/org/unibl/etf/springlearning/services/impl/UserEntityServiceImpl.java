package org.unibl.etf.springlearning.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.models.dto.User;
import org.unibl.etf.springlearning.models.dto.UserApprovalRestrictionRequest;
import org.unibl.etf.springlearning.models.dto.UserRegistrationRequest;
import org.unibl.etf.springlearning.models.dto.UserUpdateRequest;
import org.unibl.etf.springlearning.models.entities.PermissionsEntity;
import org.unibl.etf.springlearning.models.entities.UserEntity;
import org.unibl.etf.springlearning.models.enums.Role;
import org.unibl.etf.springlearning.repositories.UserEntityRepository;
import org.unibl.etf.springlearning.services.EmailService;
import org.unibl.etf.springlearning.services.UserEntityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;
    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<User> findAll(){
        return userEntityRepository.findAll().stream().map(u->modelMapper.map(u,User.class)).collect(Collectors.toList());
    }

    @Override
    public User findByUsername(String username){
        return modelMapper.map(userEntityRepository.findUserEntityByUsername(username),User.class);
    }

    @Override
    public User findById(Integer id) {
        return modelMapper.map(userEntityRepository.findUserEntityById(id),User.class);
    }

    @Override
    public User findByEmail(String email) {
        return modelMapper.map(userEntityRepository.findUserEntityByEmail(email),User.class);
    }

    @Override
    public User insert(UserRegistrationRequest request){
        UserEntity userEntity=modelMapper.map(request,UserEntity.class);
        userEntity.setId(null);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setVerified(false);
        userEntity.setRestricted(false);
        userEntity.setRole(Role.valueOf("USER"));
        userEntity.setVerificationCode("");
        userEntity=userEntityRepository.saveAndFlush(userEntity);
        entityManager.refresh(userEntity);
        emailService.send(userEntity.getEmail(),"Registration","You will be notified about your profile approval");
        return findById(userEntity.getId());
    }
    @Override
    public User updateVerificationCode(Integer id,String code){
        UserEntity userEntity=userEntityRepository.findUserEntityById(id);
        userEntity.setId(id);
        userEntity.setVerificationCode(code);
        userEntity = userEntityRepository.saveAndFlush(userEntity);
        entityManager.refresh(userEntity);
        return findById(userEntity.getId());
    }

    @Override
    public User update(Integer id, UserUpdateRequest request) throws NotFoundException {
        UserEntity userEntity=userEntityRepository.findById(id).orElseThrow(NotFoundException::new);
        userEntity.setId(id);
        userEntity.setRole(Role.valueOf(request.getRole()));
        userEntity=userEntityRepository.saveAndFlush(userEntity);
        entityManager.refresh(userEntity);
        return findById(userEntity.getId());
    }

    @Override
    public User approve(Integer id, UserApprovalRestrictionRequest request) throws NotFoundException {
        UserEntity userEntity=userEntityRepository.findById(id).orElseThrow(NotFoundException::new);
        userEntity.setId(id);
        userEntity.setVerified(request.isApprove());
        userEntity=userEntityRepository.saveAndFlush(userEntity);
        entityManager.refresh(userEntity);
        emailService.send(userEntity.getEmail(),"Profile verification","You are a verified user now!");
        return findById(userEntity.getId());
    }

    @Override
    public User restrict(Integer id, UserApprovalRestrictionRequest request) throws NotFoundException {
        UserEntity userEntity=userEntityRepository.findById(id).orElseThrow(NotFoundException::new);
        userEntity.setId(id);
        userEntity.setRestricted(request.isApprove());
        userEntity=userEntityRepository.saveAndFlush(userEntity);
        entityManager.refresh(userEntity);
        return findById(userEntity.getId());
    }
}
