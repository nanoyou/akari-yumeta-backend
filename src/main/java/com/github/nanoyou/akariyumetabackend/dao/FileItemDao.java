package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.filestore.FileItem;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileItemDao extends JpaRepository<FileItem, String> {

    Optional<IdAndMimeTypeProjection> findIdAndMimeTypeProjectionById(@Nonnull String id);
    interface IdAndMimeTypeProjection {
        String getId();

        String getMimeType();
    }
}
