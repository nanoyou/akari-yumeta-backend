package com.github.nanoyou.akariyumetabackend.entity.filestore;

import com.github.nanoyou.akariyumetabackend.common.constant.FileConfig;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件项 FileItem
 * 属于: 文件存储
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileItem {
    /**
     *文件ID(哈希值)
     */
    @Id
    private String id;
    /**
     * 文件的MIME类型，如image/png image/jpeg
     */
    @NotNull
    private String mimeType;
    /**
     * 文件数据(二进制格式)
     */
    @Lob
    @NotNull
    @Column(length = FileConfig.MAX_FILE_BYTE_LENGTH)
    private byte[] data;
}
