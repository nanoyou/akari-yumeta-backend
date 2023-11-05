package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.filestore.FileItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileItemDao extends JpaRepository<FileItem, String> {
}
