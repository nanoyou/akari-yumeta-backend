package com.github.nanoyou.akariyumetabackend;

import com.github.nanoyou.akariyumetabackend.dao.CommentDao;
import com.github.nanoyou.akariyumetabackend.dto.dynamic.DynamicTreeDTO;
import com.github.nanoyou.akariyumetabackend.entity.dynamic.Comment;
import com.github.nanoyou.akariyumetabackend.service.DynamicService;
import com.github.nanoyou.akariyumetabackend.service.LikeService;
import com.github.nanoyou.akariyumetabackend.service.SubscriptionService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class DynamicServiceTest {
    @Autowired
    private DynamicService dynamicService;



    @Test
    void addCommenTest(){
//
    }

    @Test
    void getDynamicsByCommenterIDTest() {

        String CommenterId = "23";
        assertTrue(dynamicService.getDynamicsByCommenterID(CommenterId).isEmpty());
    }
    @Test
    void getDynamicsByFollowerIDTest(){
        String CommenterId = "1";
        assertTrue(dynamicService.getDynamicsByFollowerID(CommenterId).isEmpty());

    }

    @Test
    void getDynamicTreeTest(){
        String commentId = "1";
        DynamicTreeDTO dynamicTreeDTO = dynamicService.getDynamicTree(commentId);
        assertTrue(dynamicTreeDTO != null);
    }

    @Test
    void replyTest(){
        String content = "sdsd";
        String replyTo = "sdasdad";
        String whoReply = " sdsd";

        assertTrue(dynamicService.reply(content,replyTo,whoReply).isPresent());
    }

    @Test
    void existByIdTest(){
        String id = "1";
        assertTrue(dynamicService.existByID(id));
    }
}
