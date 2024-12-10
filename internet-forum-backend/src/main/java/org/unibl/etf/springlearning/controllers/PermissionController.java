package org.unibl.etf.springlearning.controllers;

import org.springframework.web.bind.annotation.*;
import org.unibl.etf.springlearning.models.dto.Permission;
import org.unibl.etf.springlearning.models.dto.PermissionRequest;
import org.unibl.etf.springlearning.services.PermissionEntityService;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    private final PermissionEntityService service;

    public PermissionController(PermissionEntityService service) {
        this.service = service;
    }
    @PostMapping("")
    public Permission insert(@RequestBody PermissionRequest request){
        return service.insert(request);
    }
    @DeleteMapping("/user/{id}")
    public void deleteByUserId(@PathVariable Integer id){
       try{
           service.deleteByUserId(id);
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
