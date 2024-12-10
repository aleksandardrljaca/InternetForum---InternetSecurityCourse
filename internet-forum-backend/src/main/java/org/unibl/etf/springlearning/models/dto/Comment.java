package org.unibl.etf.springlearning.models.dto;

import lombok.Data;

import java.sql.Time;
import java.sql.Date;

@Data
public class Comment {
    private Integer id;
    private String content;
    private Date date;
    private Time time;
    private Integer userByUserIdId;
    private String userByUserIdUsername;
    private String categoryByCategoryIdId;
    private String categoryByCategoryIdName;
    private Boolean approved;
}
