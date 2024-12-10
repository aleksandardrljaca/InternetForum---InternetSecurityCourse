package org.unibl.etf.springlearning.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.unibl.etf.springlearning.models.enums.Role;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "user", schema = "forum", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "verification_code")
    private String verificationCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Basic
    @Column(name = "restricted")
    private Boolean restricted;
    @Basic
    @Column(name = "verified")
    private Boolean verified;
    @OneToMany(mappedBy = "userByUserId")
    private List<CommentEntity> commentsById;
    @OneToMany(mappedBy = "userByUserId")
    private List<PermissionsEntity> permissionsById;

}
