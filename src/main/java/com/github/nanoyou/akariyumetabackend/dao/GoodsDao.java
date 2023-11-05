package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import jakarta.persistence.Converter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;


public interface GoodsDao extends JpaRepository<GoodsInfo,UUID> {

    public Optional<GoodsInfo[]> findAllByDescriptionLikeOrNameLike(String description, String name);

    public Optional<GoodsInfo> findById(UUID id);
}
