package org.unibl.etf.springlearning.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Data
@Entity
@Table(name = "token_black_list", schema = "forum", catalog = "")
public class TokenBlackListEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "token")
    private String token;

}
