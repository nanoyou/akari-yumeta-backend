package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.DonateGoodsDao;
import com.github.nanoyou.akariyumetabackend.dao.DonateMoneyDao;
import com.github.nanoyou.akariyumetabackend.dto.donate.DonateHistoryDTO;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DonateService {
    private final DonateGoodsDao donateGoodsDao;
    private final DonateMoneyDao donateMoneyDao;

    @Autowired
    public DonateService(DonateGoodsDao donateGoodsDao,DonateMoneyDao donateMoneyDao){
        this.donateGoodsDao = donateGoodsDao;
        this.donateMoneyDao = donateMoneyDao;

    }

    public DonateGoods saveDonateGoods(DonateGoods donateGoods){
        Optional<DonateGoods> optional = Optional.of(donateGoodsDao.save(donateGoods));
        DonateGoods result = optional.orElse(null);
        return result;
    }

    public DonateMoney saveDonateMoney(DonateMoney donateMoney) {
        return donateMoneyDao.save(donateMoney);
    }


    public DonateHistoryDTO getAllDonateHistory(String  donatorID) {
        List<DonateGoods> donateGoodsList = donateGoodsDao.findAllByDonatorID(donatorID);
        Integer totalGoodsCount = donateGoodsList.size();
        List<DonateMoney> donateMoneyList = donateMoneyDao.findAllByDonatorID(donatorID);
        Long totalMoney = donateMoneyList.stream().mapToLong(DonateMoney::getAmount).sum();
        return new DonateHistoryDTO(donateGoodsList, totalGoodsCount, donateMoneyList, totalMoney);
    }

}
