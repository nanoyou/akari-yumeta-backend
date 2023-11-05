package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.FileItemDao;
import com.github.nanoyou.akariyumetabackend.entity.filestore.FileItem;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {

    private final FileItemDao fileItemDao;

    @Autowired
    private FileService(FileItemDao fileItemDao) {
        this.fileItemDao = fileItemDao;
    }

    public Optional<FileItem> upload(@Nonnull FileItem fileItem) {
        return Optional.of(fileItemDao.saveAndFlush(fileItem));
    }

}
