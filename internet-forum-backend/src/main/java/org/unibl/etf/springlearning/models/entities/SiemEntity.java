package org.unibl.etf.springlearning.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;


@Data
@Entity
@Table(name = "siem", schema = "forum", catalog = "")
public class SiemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "log")
    private String log;
    @Basic
    @Column(name = "log_time")
    private Time logTime;
    @Basic
    @Column(name = "log_date")
    private Date logDate;

}
