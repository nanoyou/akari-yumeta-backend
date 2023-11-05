package com.github.nanoyou.akariyumetabackend.service;

import cn.hutool.core.collection.CollUtil;
import com.github.nanoyou.akariyumetabackend.dao.MessageDao;
import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import com.github.nanoyou.akariyumetabackend.entity.user.User;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final MessageDao messageDao;
    private final SubscriptionService subscriptionService;

    @Autowired
    public ChatService(MessageDao messageDao, SubscriptionService subscriptionService) {
        this.messageDao = messageDao;
        this.subscriptionService = subscriptionService;
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

    /**
     * 根据信息发送人 ID 找到收信人 ID 列表
     *
     * @param senderID 发信人 ID
     * @return 不重复的收信人 ID 列表
     */
    public List<String> getReceiverIDList(@Nonnull String senderID) {
        val receiverIDProjBySenderID = messageDao.findDistinctBySenderID(senderID);
        if (receiverIDProjBySenderID.isEmpty()) {
            return new ArrayList<>();
        }
        return receiverIDProjBySenderID.stream().map(
                MessageDao.ReceiverIDProj::getReceiverID
        ).toList();
    }

    /**
     * 获取聊天列表中的用户 ID 列表
     *
     * @param userID 登录用户 ID
     * @return 聊天列表中的用户 ID 列表（去除重复 ID）
     */
    public List<String> getReceiverAndFolloweeIDList(@Nonnull String userID) {
        val receiverIDList = getReceiverIDList(userID);
        val followeeIDList = subscriptionService.getFolloweeIDList(userID);
        var friendIDList = new ArrayList<>(receiverIDList);
        friendIDList.addAll(followeeIDList);
        friendIDList = CollUtil.distinct(followeeIDList);
        return friendIDList;
    }

    public List<Pair<String, Message>> getMyChat(@Nonnull String senderID) {

        val friendList = getReceiverAndFolloweeIDList(senderID);

        List<Pair<String, Message>> result = new ArrayList<>();

        friendList.forEach(
                id -> {
                    val message = messageDao.findFirstBySenderIDAndReceiverIDOrderBySendTimeDesc(senderID, id);
                    message.ifPresent(value -> result.add(Pair.of(id, value)));
                }
        );

        return result;
    }
}