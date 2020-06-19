package com.dwj.vblogold.controller;

import com.dwj.vblogold.entity.MessageBoardEntity;
import com.dwj.vblogold.response.JsonResponse;
import com.dwj.vblogold.service.MessageBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author dwj
 * @date 2020-06-09 22:46
 */
@Slf4j
@RestController("MessageBoardController")
@RequestMapping("/api/messageBoard")
public class MessageBoardController {

    @Autowired private MessageBoardService messageBoardService;

    @ResponseBody
    @GetMapping("/getMessageBoard")
    public JsonResponse getMessageBoardController(int offset, int limit) {
        return JsonResponse.success(messageBoardService.queryMessageList(offset, limit));
    }

    @ResponseBody
    @PostMapping("/saveMessageBoard")
    public JsonResponse saveMessageBoardController(
            HttpServletRequest request, MessageBoardEntity messageBoardEntity) throws IOException {
        log.debug(messageBoardEntity.getMessage());
        log.debug(messageBoardEntity.getNickname());
        log.warn(messageBoardEntity.getNickname());
        request.setCharacterEncoding("utf-8");
        messageBoardService.saveMessage(messageBoardEntity);
        return JsonResponse.success();
    }
}
