package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonateGoodsDao extends JpaRepository<DonateGoods, String> {

    public Optional<DonateGoods> save(@NonNull DonateGoods donateGoods);

}
