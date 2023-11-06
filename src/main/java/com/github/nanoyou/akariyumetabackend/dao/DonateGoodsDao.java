package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.donate.DonateGoods;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DonateGoodsDao extends JpaRepository<DonateGoods, String> {

    List<DonateGoods> findAllByDonatorID(@Nonnull String donatorID);

}
