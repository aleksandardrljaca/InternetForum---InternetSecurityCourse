package org.unibl.etf.springlearning.services;

import org.springframework.stereotype.Repository;
import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.exceptions.UnauthorizedException;
import org.unibl.etf.springlearning.models.dto.Comment;
import org.unibl.etf.springlearning.models.dto.CommentApprovalRequest;
import org.unibl.etf.springlearning.models.dto.CommentRequest;

import java.util.List;

@Repository
public interface CommentEntityService {
    List<Comment> findAllByCategory(String category);
    List<Comment> findAll();
    Comment findById(Integer id) throws NotFoundException;
    Comment insert(CommentRequest request) throws NotFoundException,UnauthorizedException;
    Comment update(Integer id,CommentRequest request) throws NotFoundException, UnauthorizedException;
    Comment approve(Integer id, CommentApprovalRequest request) throws NotFoundException;
    void delete(Integer id) throws UnauthorizedException;
}
