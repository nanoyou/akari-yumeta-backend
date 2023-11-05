package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.GoodsDao;
import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Component
@SpringBootTest
class GoodsServiceTest {

    @Autowired
    private GoodsService goodsService;

    @Test
    void findGoodsById() {
        System.out.println(goodsService.findGoodsById(UUID.fromString("62346264-6661-3765-3135-353233346636"))
                .toString());
    }

    @Test
    void saveGoods() {
        var goodsInfo = GoodsInfo.builder()
                .description("DESCRIPTION")
                .name("笔记本")
                .unitPrice(1000l)
                .imageURL("url")
                .build();
        goodsService.saveGoods(goodsInfo);
    }
}