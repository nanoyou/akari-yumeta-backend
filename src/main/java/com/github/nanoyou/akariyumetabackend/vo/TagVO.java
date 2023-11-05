package com.github.nanoyou.akariyumetabackend.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TagVO {
    String userID;
    List<String> tagContentList;
}