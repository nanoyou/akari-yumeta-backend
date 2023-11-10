package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MessageDao extends JpaRepository<Message, String> {
    List<Message> findBySenderIDAndReceiverID(@Nonnull String senderID, @Nonnull String receiverID);

    Optional<Message> findFirstBySenderIDAndReceiverID(@Nonnull String senderID, @Nonnull String receiverID);

    List<ReceiverIDProj> findDistinctBySenderIDOrderBySendTimeDesc(@Nonnull String senderID);

    interface ReceiverIDProj {
        String getReceiverID();
        LocalDateTime getSendTime();
    }
}
