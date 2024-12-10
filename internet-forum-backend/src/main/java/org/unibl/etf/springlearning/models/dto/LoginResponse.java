package org.unibl.etf.springlearning.models.dto;

import lombok.Data;


@Data
public class LoginResponse extends JwtUser{
    private String token;
    //private List<Permission> permissions;
}
