package com.dwj.vblogold.service;

import com.dwj.vblogold.entity.MessageBoardEntity;
import com.dwj.vblogold.entity.PageList;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public interface MessageBoardService {

    /**
     * 保存留言板
     * @param messageBoardEntity 留言板对象
     */
    void saveMessage(MessageBoardEntity messageBoardEntity);

    /**
     * 根据模糊字段查询
     * @param offset 偏移量
     * @param limit 条数
     * @return 留言列表
     */
    PageList<MessageBoardEntity> queryMessageList(Integer offset, Integer limit);
}
