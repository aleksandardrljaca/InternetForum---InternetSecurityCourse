package org.unibl.etf.springlearning.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.springlearning.exceptions.NotFoundException;
import org.unibl.etf.springlearning.models.dto.User;
import org.unibl.etf.springlearning.models.dto.UserApprovalRestrictionRequest;
import org.unibl.etf.springlearning.models.dto.UserUpdateRequest;
import org.unibl.etf.springlearning.services.UserEntityService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserEntityService userEntityService;

    public UserController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }
    @GetMapping("")
    public List<User> findAll(){
        return userEntityService.findAll();
    }
    @PutMapping("/{id}/update")
    public User update(@PathVariable Integer id, @RequestBody UserUpdateRequest request) throws NotFoundException {
        return userEntityService.update(id,request);
    }

    @PutMapping("{id}/restrict")
    public User restrict(@PathVariable Integer id,@RequestBody UserApprovalRestrictionRequest request) throws NotFoundException{
        return userEntityService.restrict(id,request);
    }
    @PutMapping("{id}/verify")
    public User approve(@PathVariable Integer id,@RequestBody UserApprovalRestrictionRequest request) throws NotFoundException{
        return userEntityService.approve(id,request);
    }

}
