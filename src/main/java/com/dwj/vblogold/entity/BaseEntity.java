package com.dwj.vblogold.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dwj
 * @date 2020-06-17 15:57
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Accessors(chain = true)
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "create_time")
    protected Date createTime;

    @Column(name = "update_time")
    protected Date updateTime;
}
