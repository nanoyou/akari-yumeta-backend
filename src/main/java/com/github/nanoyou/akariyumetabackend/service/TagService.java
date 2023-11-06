package com.github.nanoyou.akariyumetabackend.service;

import com.github.nanoyou.akariyumetabackend.dao.TagDao;
import com.github.nanoyou.akariyumetabackend.entity.user.Tag;
import com.github.nanoyou.akariyumetabackend.dto.TagDTO;
import jakarta.annotation.Nonnull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagDao tagDao;

    @Autowired
    private TagService(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public TagDTO addTags(@Nonnull String userID, @Nonnull List<String> tagContentList) {
        var tagList = tagContentList.stream().map(
                tagContent -> Tag.builder()
                        .combinedPrimaryKey(
                                Tag.CombinedPrimaryKey.builder()
                                        .userId(userID)
                                        .tagContent(tagContent)
                                        .build()
                        ).build()
        ).toList();

        tagList = tagDao.saveAllAndFlush(tagList);

        tagContentList = tagList.stream().map(tag -> tag.getCombinedPrimaryKey().getTagContent()).toList();

        return TagDTO.builder()
                .userID(tagList.get(0).getCombinedPrimaryKey().getUserId())
                .tagContentList(tagContentList)
                .build();

    }

    public TagDTO getTags(@Nonnull String userID) {
        val tagContentList = tagDao.findTagContentProjectionByCombinedPrimaryKeyUserId(userID).stream().map(
                TagDao.TagContentProjection::getCombinedPrimaryKeyTagContent
        ).toList();

        return TagDTO.builder()
                .userID(userID)
                .tagContentList(tagContentList)
                .build();
    }

}
