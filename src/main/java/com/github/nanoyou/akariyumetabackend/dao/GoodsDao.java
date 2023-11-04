package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GoodsDao extends JpaRepository<GoodsInfo,String> {

public Optional<GoodsInfo[]> findAllByDescriptionLikeOrNameLike(String description, String name);


}
