package com.github.nanoyou.akariyumetabackend.service;

import cn.hutool.core.collection.CollUtil;
import com.github.nanoyou.akariyumetabackend.dao.MessageDao;
import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Message> getMessageListByPair(@Nonnull String loginUserID, @Nonnull String objectiveUserID) {
        val l1 = messageDao.findBySenderIDAndReceiverID(loginUserID, objectiveUserID);
        val l2 = messageDao.findBySenderIDAndReceiverID(objectiveUserID, loginUserID);
        l1.addAll(l2);
        return CollUtil.distinct(l1);
    }
}
