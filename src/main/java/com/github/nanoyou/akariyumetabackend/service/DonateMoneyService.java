package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.DonateMoneyDao;
import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
public class DonateMoneyService {
    private DonateMoneyDao donateMoneyDao;

    @Autowired
    public DonateMoneyService(DonateMoneyDao donateMoneyDao) {
        this.donateMoneyDao = donateMoneyDao;
    }

    public DonateMoney saveDonateMoney(DonateMoney donateMoney) {
        return donateMoneyDao.save(donateMoney);
    }

}
