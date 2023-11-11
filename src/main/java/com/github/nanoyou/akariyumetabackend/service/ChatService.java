package com.github.nanoyou.akariyumetabackend.service;

import cn.hutool.core.collection.CollUtil;
import com.github.nanoyou.akariyumetabackend.dao.MessageDao;
import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    private final MessageDao messageDao;
    private final UserDao userDao;

    @Autowired
    public ChatService(MessageDao messageDao, UserDao userDao) {
        this.messageDao = messageDao;
        this.userDao = userDao;
    }

    public Message addMessage(@Nonnull Message message) {
        return messageDao.saveAndFlush(message);
    }

    public List<Message> getMessageListByPair(@Nonnull String loginUserID, @Nonnull String objectiveUserID) {
        val l1 = messageDao.findBySenderIDAndReceiverID(loginUserID, objectiveUserID);
        val l2 = messageDao.findBySenderIDAndReceiverID(objectiveUserID, loginUserID);
        l1.addAll(l2);
        val result = l1.stream().sorted(
                (message1, message2) -> {
                    if (message1.getSendTime().isEqual(message2.getSendTime())) {
                        return 0;
                    }
                    return message1.getSendTime().isAfter(message2.getSendTime()) ? 1 : -1;
                }
        ).toList();
        return CollUtil.distinct(result);
    }

    public List<Pair<User, Message>> getMyChat(@Nonnull String loginUserID) {
        if (userDao.existsById(loginUserID)) {
            List<String> friendIdList = new ArrayList<>();
            friendIdList.addAll(messageDao.findDistinctBySenderID(loginUserID).stream().map(MessageDao.ReceiverID::getReceiverID).toList());
            friendIdList.addAll(messageDao.findDistinctByReceiverID(loginUserID).stream().map(MessageDao.SenderID::getSenderID).toList());
            friendIdList = CollUtil.distinct(friendIdList);

            return friendIdList.stream().map(
                    friendId -> {
                        val friend = userDao.findById(friendId).orElse(null);
                        val list = getMessageListByPair(loginUserID, friendId);
                        if ((friend!=null) && (!list.isEmpty())) {
                            return Pair.of(friend, CollUtil.getLast(list));
                        }
                        return null;
                    }
            ).toList();
        }
        return new ArrayList<>();
    }

    public Optional<Message> read(@Nonnull String messageID) {
        val message = messageDao.findById(messageID);
        return message.map(
                m -> {
                    m.setRead(true);
                    messageDao.saveAndFlush(m);
                    return m;
                }
        );
    }
}
