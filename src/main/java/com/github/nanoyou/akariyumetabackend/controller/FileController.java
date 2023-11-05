package com.github.nanoyou.akariyumetabackend.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.github.nanoyou.akariyumetabackend.common.constant.FileConfig;
import com.github.nanoyou.akariyumetabackend.common.constant.SessionConst;
import com.github.nanoyou.akariyumetabackend.common.enumeration.ResponseCode;
import com.github.nanoyou.akariyumetabackend.common.util.Sha256Util;
import com.github.nanoyou.akariyumetabackend.dto.filestore.FileRecordDTO;
import com.github.nanoyou.akariyumetabackend.entity.Result;
import com.github.nanoyou.akariyumetabackend.entity.filestore.FileItem;
import com.github.nanoyou.akariyumetabackend.service.FileService;
import lombok.val;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {

    private final FileService fileService;

    private FileController(FileService fileService) {

        this.fileService = fileService;
    }

    @ResponseBody
    @RequestMapping(path = "/file", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public Result file(@RequestParam("file") MultipartFile file,
                       @ModelAttribute(SessionConst.LOGIN_USER_ID) String loginUserID) {
        try {
            // 需要登录
            assert StringUtils.hasText(loginUserID);

            // 构造实体类
            val bytes = file.getBytes();

            val id = DigestUtil.sha512Hex(bytes);
            val existedFile = fileService.getFileIdAndMimeType(id);
            if (existedFile.isPresent()) {
                return Result.builder()
                        .ok(true)
                        .code(ResponseCode.SUCCESS.value)
                        .message("文件上传成功：文件已经存在，无需上传")
                        .data(FileRecordDTO.builder()
                                .id(existedFile.get().getId())
                                .mimeType(existedFile.get().getMimeType())
                                .build())
                        .build();
            }

            val contentType = file.getContentType();

            if (!StringUtils.hasText(contentType)) {
                return Result.builder()
                        .ok(false)
                        .code(ResponseCode.EMPTY_CONTENT_TYPE.value)
                        .message("文件上传失败：无法识别的文件类型")
                        .data(null)
                        .build();
            }

            if (bytes.length >= FileConfig.MAX_FILE_BYTE_LENGTH) {
                return Result.builder()
                        .ok(false)
                        .code(ResponseCode.SUCCESS.value)
                        .message("文件上传失败：文件大小不能大于" + FileConfig.MAX_FILE_BYTE_LENGTH + "字节")
                        .data(null)
                        .build();
            }

            val fileItem = FileItem.builder()
                    .id(id)
                    .mimeType(contentType)
                    .data(bytes)
                    .build();

            // 检测是否保存成功
            return fileService.upload(fileItem).map(
                    fi -> {
                        System.out.println(fi);
                        return Result.builder()
                                .ok(true)
                                .code(ResponseCode.SUCCESS.value)
                                .message("文件上传成功")
                                .data(FileRecordDTO.builder()
                                        .id(fi.getId())
                                        .mimeType(fi.getMimeType())
                                        .build())
                                .build();
                    }
            ).orElse(
                    Result.builder()
                            .ok(false)
                            .code(ResponseCode.SUCCESS.value)
                            .message("文件上传失败：文件未成功保存至数据库")
                            .data(null)
                            .build()
            );
        } catch (IOException e) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.TEMPORARY_STORE_FAILED.value)
                    .message("文件上传失败：文件无法访问，临时存储失败")
                    .data(null)
                    .build();
        }

    }

    @ResponseBody
    @RequestMapping(path = "/file/{fileHash}", method = RequestMethod.GET,
            headers = "Accept=application/json")
    public Result file(@PathVariable String fileHash) {
        if (!Sha256Util.isSha256(fileHash)) {
            return Result.builder()
                    .ok(false)
                    .code(ResponseCode.PARAM_ERR.value)
                    .message("获取文件失败：文件 ID 不合法，应为 SHA-256 格式")
                    .data(null)
                    .build();
        }

        return fileService.getFile(fileHash).map(
                fileItem -> Result.builder()
                        .ok(true)
                        .code(ResponseCode.SUCCESS.value)
                        .message("获取文件成功")
                        .data(fileItem)
                        .build()
        ).orElse(
                Result.builder()
                        .ok(false)
                        .code(ResponseCode.NO_SUCH_FILE.value)
                        .message("获取文件失败：找不到文件")
                        .data(null)
                        .build()
        );
    }

}
