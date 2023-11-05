package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.DonateGoodsDao;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DonateGoodsService {
    private DonateGoodsDao donateGoodsDao;

    @Autowired
    public DonateGoodsService (DonateGoodsDao donateGoodsDao){
        this.donateGoodsDao = donateGoodsDao;
    }

    public DonateGoods saveDonateGoods(DonateGoods donateGoods){
        Optional<DonateGoods> optional = donateGoodsDao.save(donateGoods);
        DonateGoods result = optional.orElse(null);
        return result;
    }
}
