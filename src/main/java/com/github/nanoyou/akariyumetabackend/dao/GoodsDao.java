package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface GoodsDao extends JpaRepository<GoodsInfo, String> {

    Optional<GoodsInfo[]> findAllByDescriptionLikeOrNameLike(String description, String name);

    Optional<GoodsInfo> findById(String id);
}
