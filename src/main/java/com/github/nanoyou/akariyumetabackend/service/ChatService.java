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
    private final SubscriptionService subscriptionService;
    private final UserDao userDao;

    @Autowired
    public ChatService(MessageDao messageDao, SubscriptionService subscriptionService, UserDao userDao) {
        this.messageDao = messageDao;
        this.subscriptionService = subscriptionService;
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

    /**
     * 根据信息发送人 ID 找到收信人 ID 列表
     *
     * @param senderID 发信人 ID
     * @return 不重复的收信人 ID 列表
     */
    public List<String> getReceiverIDList(@Nonnull String senderID) {
        val receiverIDProjBySenderID = messageDao.findDistinctBySenderIDOrderBySendTimeDesc(senderID);
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
    @Deprecated
    public List<String> getReceiverAndFolloweeIDList(@Nonnull String userID) {
        val receiverIDList = getReceiverIDList(userID);
        val followeeIDList = subscriptionService.getFolloweeIDList(userID);
        var friendIDList = new ArrayList<>(receiverIDList);
        friendIDList.addAll(followeeIDList);
        friendIDList = CollUtil.distinct(friendIDList);
        return friendIDList;
    }

    @Deprecated
    public List<Pair<String, Message>> getMyChat(@Nonnull String senderID) {

        val friendList = getReceiverAndFolloweeIDList(senderID);
        friendList.add(senderID);

        List<Pair<String, Message>> result = new ArrayList<>();

        friendList.forEach(
                id -> {
                    val message = messageDao.findFirstBySenderIDAndReceiverID(senderID, id);
                    message.ifPresent(value -> result.add(Pair.of(id, value)));
                }
        );

        result.sort(
                (m1, m2) -> {
                    if (m1.getSecond().getSendTime().isEqual(m2.getSecond().getSendTime())) {
                        return 0;
                    }
                    return m1.getSecond().getSendTime().isAfter(m2.getSecond().getSendTime()) ? 1 : -1;
                }
        );

        return result;
    }

    public List<Pair<User, Message>> getMyChat2(@Nonnull String loginUserID) {
        if (userDao.existsById(loginUserID)) {
            List<String> friendIdList = new ArrayList<>();
            friendIdList.addAll(messageDao.findDistinctBySenderID(loginUserID).stream().map(MessageDao.ReceiverID::getReceiverID).toList());
            friendIdList.addAll(messageDao.findDistinctByReceiverID(loginUserID).stream().map(MessageDao.ReceiverID::getReceiverID).toList());
            friendIdList = CollUtil.distinct(friendIdList);

            return friendIdList.stream().map(
                    userId -> userDao.findById(userId).map(
                            user -> {
                                val message1 = messageDao.findFirstBySenderIDOrReceiverIDOrderBySendTimeDesc(userId, loginUserID);
                                val message2 = messageDao.findFirstBySenderIDOrReceiverIDOrderBySendTimeDesc(loginUserID, userId);
                                return message1.getSendTime().isAfter(message2.getSendTime()) ? Pair.of(user, message1) : Pair.of(user, message2);
                            }
                    ).orElse(null)
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
