package com.github.nanoyou.akariyumetabackend.dao;

import com.github.nanoyou.akariyumetabackend.entity.user.Tag;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagDao extends JpaRepository<Tag, Tag.CombinedPrimaryKey> {
    List<TagContentProjection> findTagContentProjectionByCombinedPrimaryKeyUserId(@Nonnull String userID);

    interface TagContentProjection {
        String getCombinedPrimaryKeyTagContent();
    }

}
