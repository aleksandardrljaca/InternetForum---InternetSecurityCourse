package org.unibl.etf.springlearning.controllers;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.exceptions.UnauthorizedException;
import org.unibl.etf.springlearning.models.dto.Comment;
import org.unibl.etf.springlearning.models.dto.CommentApprovalRequest;
import org.unibl.etf.springlearning.models.dto.CommentRequest;
import org.unibl.etf.springlearning.services.CommentEntityService;
import org.unibl.etf.springlearning.services.WafService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentEntityService commentEntityService;
    private final WafService wafService;
    public CommentController(CommentEntityService commentEntityService, WafService wafService) {
        this.commentEntityService = commentEntityService;
        this.wafService = wafService;
    }
    @GetMapping("")
    public List<Comment> findAll(){
        return commentEntityService.findAll();
    }
    @GetMapping("/{name}")
    public List<Comment> findAllByCategoryName(@PathVariable String name){
        return commentEntityService.findAllByCategory(name);
    }
    @PostMapping("")
    public Comment insert(@Valid @RequestBody CommentRequest request, BindingResult bindingResult) throws NotFoundException,UnauthorizedException {
        wafService.check(bindingResult);
        return commentEntityService.insert(request);
    }
    @PutMapping("/{id}")
    public Comment update(@PathVariable Integer id,@Valid @RequestBody CommentRequest request,BindingResult bindingResult) throws NotFoundException, UnauthorizedException {
        wafService.check(bindingResult);
        return commentEntityService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws UnauthorizedException{
        commentEntityService.delete(id);
    }
    @PutMapping("/approve/{id}")
    public Comment approve(@PathVariable Integer id, @RequestBody CommentApprovalRequest request) throws NotFoundException{
        return commentEntityService.approve(id,request);
    }
}
