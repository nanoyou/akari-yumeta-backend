package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.GoodsDao;
import com.github.nanoyou.akariyumetabackend.dao.UserDao;
import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
}
