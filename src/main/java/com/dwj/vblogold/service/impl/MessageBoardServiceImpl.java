package com.dwj.vblogold.service.impl;

import com.dwj.vblogold.entity.MessageBoardEntity;
import com.dwj.vblogold.entity.PageList;
import com.dwj.vblogold.repository.MessageBoardRepository;
import com.dwj.vblogold.service.MessageBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Service
public class MessageBoardServiceImpl implements MessageBoardService {
    @Autowired
    private MessageBoardRepository messageBoardRepository;

    @Override
    public void saveMessage(MessageBoardEntity messageBoardEntity) {
        messageBoardEntity.setCreateTime(new Date());
        messageBoardRepository.save(messageBoardEntity);
    }

    @Override
    public PageList<MessageBoardEntity> queryMessageList(Integer offset, Integer limit) {
        PageList<MessageBoardEntity> messageBoardPageList = new PageList<>();
        List<MessageBoardEntity> messageBoardEntityList = messageBoardRepository.queryMessageList(offset - 1, limit);
        int total = (int) messageBoardRepository.count();

        messageBoardPageList.setRows(messageBoardEntityList);
        messageBoardPageList.setTotal(total);

        return messageBoardPageList;
    }

}
