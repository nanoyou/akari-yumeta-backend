package com.github.nanoyou.akariyumetabackend.dto.user;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserUpdateDTO {
    private String id;
    private String nickname;
    private Gender gender;
    private String introduction;
    private String avatarURL;
    private List<String> tags;
}
