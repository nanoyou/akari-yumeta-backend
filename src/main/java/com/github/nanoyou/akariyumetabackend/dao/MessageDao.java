package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.chat.Message;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, String> {
    List<Message> findBySenderIDAndReceiverID(@Nonnull String senderID, @Nonnull String receiverID);
}
