package com.dwj.vblogold.service.impl;

import com.dwj.vblogold.entity.LogEntity;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.repository.LogRepository;
import com.dwj.vblogold.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogRepository logRepository;

    @Override
    public PageList<LogEntity> queryList(String key, Integer offset, Integer limit) {
        PageList<LogEntity> logPageList = new PageList<>();
        List<LogEntity> userList = logRepository.queryLogList(key, offset, limit);
        int total = logRepository.queryLogTotal(key);
        logPageList.setRows(userList);
        logPageList.setTotal(total);
        return logPageList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLog(LogEntity logEntity) {
        logEntity.setCreateTime(new Date());
        logRepository.save(logEntity);
    }
}
