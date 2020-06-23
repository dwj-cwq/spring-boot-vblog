package com.dwj.vblogold.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@Table(name = "log", indexes = {@Index(columnList = "id", name = "id")})
public class LogEntity extends BaseEntity {

    @Column(name = "ip")
    private String ip;

    @Column(name = "operation")
    private String operation;

    @Column(name = "method")
    private String method;

    @Column(name = "params")
    private String params;

    @Column(name = "spend_time")
    private Integer spendTime;

    @Column(name = "response", columnDefinition = "TEXT")
    private String response;

    @Column(name = "user_name")
    private String userName;

}
