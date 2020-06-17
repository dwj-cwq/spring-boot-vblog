package com.dwj.vblogold.service;

import com.dwj.vblogold.entity.LogEntity;
import com.dwj.vblogold.entity.PageList;


/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
public interface LogService {

    /**
     * 根据模糊字段查询
     * @param key 字段
     * @param offset 偏移量
     * @param limit 条数
     * @return 日志列表
     */
    PageList<LogEntity> queryList(String key, Integer offset, Integer limit);

    /**
     * 保存日志
     * @param logEntity 日志对象
     */
    void saveLog(LogEntity logEntity);
}
