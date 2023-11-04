package com.github.nanoyou.akariyumetabackend.dao;


import com.github.nanoyou.akariyumetabackend.entity.donate.GoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GoodsDao extends JpaRepository<GoodsInfo,String> {

@Query("SELECT g FROM GoodsInfo g WHERE g.description LIKE CONCAT('%', :description, '%') OR g.name LIKE CONCAT('%', :name, '%')")
public Optional<GoodsInfo[]> findByDescriptionOrName(String description, String name);


}
