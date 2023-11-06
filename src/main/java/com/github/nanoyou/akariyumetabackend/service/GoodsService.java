package com.github.nanoyou.akariyumetabackend.service;

import cn.hutool.db.handler.StringHandler;
import com.github.nanoyou.akariyumetabackend.dao.GoodsDao;
import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service

public class GoodsService {

    private final GoodsDao goodsDao;

    @Autowired
    public GoodsService (GoodsDao goodsDao) {
        this.goodsDao = goodsDao;
    }

    public Optional<GoodsInfo[]> getGoodByDescription(String description) {
        return goodsDao.findAllByDescriptionLikeOrNameLike(description,description);
    }

    public Optional<GoodsInfo> findGoodsById(@Nonnull UUID goodsID) {
        return goodsDao.findById(goodsID);
    }


    // Todo: debug 使用
    public GoodsInfo saveGoods(GoodsInfo goodsInfo) {
        return goodsDao.save(goodsInfo);
    }

}
