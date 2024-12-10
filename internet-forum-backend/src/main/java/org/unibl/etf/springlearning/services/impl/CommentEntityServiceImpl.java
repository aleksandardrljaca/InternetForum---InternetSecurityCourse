package org.unibl.etf.springlearning.services.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.modelmapper.ModelMapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.exceptions.UnauthorizedException;
import org.unibl.etf.springlearning.models.dto.*;

import org.unibl.etf.springlearning.models.entities.CommentEntity;

import org.unibl.etf.springlearning.repositories.CommentEntityRepository;

import org.unibl.etf.springlearning.services.CommentEntityService;


import java.sql.Time;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentEntityServiceImpl implements CommentEntityService {
    private final CommentEntityRepository repository;

    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;
    public CommentEntityServiceImpl(CommentEntityRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Comment> findAllByCategory(String category) {
        List<Comment> comments= repository.findCommentEntitiesByCategoryName(category)
                .stream().sorted(Comparator.comparing(CommentEntity::getDate).reversed())
                .map(c->modelMapper.map(c,Comment.class)).limit(20)
                .collect(Collectors.toList());
        comments.forEach(c-> System.out.println(c.getContent()+" "+c.getDate()+" "+c.getTime()));
        return comments;
    }

    @Override
    public List<Comment> findAll() {
        return repository.findAll().stream().map(c->modelMapper.map(c,Comment.class)).collect(Collectors.toList());
    }
    @Override
    public Comment findById(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new),Comment.class);
    }

    @Override
    public Comment insert(CommentRequest request) throws NotFoundException,UnauthorizedException {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser= (JwtUser) authentication.getPrincipal();
        if(!jwtUser.getId().equals(request.getUserByUserIdId()) &&
                jwtUser.getRole().name().equals("USER") && jwtUser.getPermissions()
                .stream().noneMatch(p -> p.getType().equals("create"))) throw new UnauthorizedException();
       CommentEntity commentEntity=modelMapper.map(request,CommentEntity.class);
       commentEntity.setId(null);
       commentEntity.setDate(new Date());
       commentEntity.setTime(new Time(System.currentTimeMillis()));
       commentEntity.setApproved(false);
       commentEntity=repository.saveAndFlush(commentEntity);
       entityManager.refresh(commentEntity);
       return findById(commentEntity.getId());
    }

    @Override
    public Comment update(Integer id,CommentRequest request) throws NotFoundException,UnauthorizedException{
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser= (JwtUser) authentication.getPrincipal();
        if(jwtUser.getRole().name().equals("USER") && jwtUser.getPermissions()
                .stream().noneMatch(p -> p.getType().equals("update"))) throw new UnauthorizedException();
        CommentEntity commentEntity=modelMapper.map(request,CommentEntity.class);
        commentEntity.setId(id);
        commentEntity.setDate(new Date());
        commentEntity.setTime(new Time(System.currentTimeMillis()));
        commentEntity.setApproved(false);
        commentEntity=repository.saveAndFlush(commentEntity);
        entityManager.refresh(commentEntity);
        return findById(commentEntity.getId());
    }
    @Override
    public Comment approve(Integer id, CommentApprovalRequest request) throws NotFoundException{
        CommentEntity commentEntity=repository.findById(id).orElseThrow(NotFoundException::new);
        commentEntity.setId(id);
        commentEntity.setApproved(true);
        commentEntity=repository.saveAndFlush(commentEntity);
        entityManager.refresh(commentEntity);
        return findById(commentEntity.getId());
    }
    @Override
    public void delete(Integer id) throws UnauthorizedException {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser= (JwtUser) authentication.getPrincipal();
        if(jwtUser.getRole().name().equals("USER") && jwtUser.getPermissions()
                .stream().noneMatch(p -> p.getType().equals("delete"))) throw new UnauthorizedException();
        repository.deleteById(id);
    }


}
