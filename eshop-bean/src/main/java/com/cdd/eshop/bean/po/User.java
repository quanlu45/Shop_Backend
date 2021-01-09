package com.cdd.eshop.bean.po;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String userName;

    private Date regTime;

    private String avatarUrl;

    private String pwd;

    private Short status;

    private Boolean isAdmin=false;
}
