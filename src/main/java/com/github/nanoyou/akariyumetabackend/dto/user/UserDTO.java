package com.github.nanoyou.akariyumetabackend.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDTO {
    private String id;
    private String username;
    private String nickname;
    private String role;
    private String gender;
    private String introduction;
    private String avatarURL;
    private Integer usageDuration;
}
