package com.github.nanoyou.akariyumetabackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TagDTO {
    String userID;
    List<String> tagContentList;
}