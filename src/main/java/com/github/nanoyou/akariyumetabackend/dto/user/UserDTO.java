package com.github.nanoyou.akariyumetabackend.dto.user;

import com.github.nanoyou.akariyumetabackend.entity.enumeration.Gender;
import com.github.nanoyou.akariyumetabackend.entity.enumeration.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserDTO {
    private String id;
    private String username;
    private String nickname;
    private Role role;
    private Gender gender;
    private String introduction;
    private String avatarURL;
    private Integer usageDuration;
    private List<String> tags;
    private String token;
    private Integer score;
    private Integer amount;
}
