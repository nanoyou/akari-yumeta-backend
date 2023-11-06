package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.donate.DonateMoney;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DonateMoneyDao extends JpaRepository<DonateMoney, String> {

    @Override
    public DonateMoney save(@NonNull DonateMoney donateMoney);


    List<DonateMoney> findAllByDonatorID(UUID donatorID);
}
