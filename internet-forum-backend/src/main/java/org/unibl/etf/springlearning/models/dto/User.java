package org.unibl.etf.springlearning.models.dto;

import lombok.Data;
import org.unibl.etf.springlearning.models.enums.Role;

import java.util.List;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String verificationCode;
    private Boolean verified;
    private Boolean restricted;
    private List<Permission> permissions;


}
