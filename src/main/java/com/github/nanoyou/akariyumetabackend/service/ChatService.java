package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.MessageDao;
import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final MessageDao messageDao;

    @Autowired
    public ChatService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public Message addMessage(@Nonnull Message message) {
        return messageDao.saveAndFlush(message);
    }
}
