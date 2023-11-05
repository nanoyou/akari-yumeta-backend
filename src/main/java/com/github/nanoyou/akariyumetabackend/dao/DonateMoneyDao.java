package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonateMoneyDao extends JpaRepository<DonateMoney, String> {

    @Override
    public DonateMoney save(@NonNull DonateMoney donateMoney);



}
