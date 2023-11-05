package com.github.nanoyou.akariyumetabackend.dto.filestore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileRecordDTO {
    /**
     *文件ID(哈希值)
     */
    private String id;
    /**
     * 文件的MIME类型，如image/png image/jpeg
     */
    private String mimeType;
}
