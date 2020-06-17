package com.dwj.vblogold.entity;

import lombok.Data;
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
@Table(name = "message_board", indexes = {@Index(columnList = "id", name = "id")})
public class MessageBoardEntity extends BaseEntity {

    @Column(name = "nick_name")
    private String nickname;

    @Column(name = "message")
    private String message;
}
