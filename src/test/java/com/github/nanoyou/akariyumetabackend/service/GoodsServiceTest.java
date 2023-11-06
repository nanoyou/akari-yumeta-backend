package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Component
@SpringBootTest
class GoodsServiceTest {

    @Autowired
    private GoodsService goodsService;

    @Test
    void findGoodsById() {
        System.out.println(goodsService.findGoodsById(("62346264-6661-3765-3135-353233346636"))
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
        goodsService.saveGoodsInfo(goodsInfo);
    }
}