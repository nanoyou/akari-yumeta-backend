package com.github.nanoyou.akariyumetabackend.common.enumeration;

import lombok.AllArgsConstructor;

/**
 * API 专用码从100开始
 */
@AllArgsConstructor
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(0),
    /**
     * 参数错误
     */
    PARAM_ERR(1),
    /**
     * 需要登录
     */
    LOGIN_REQUIRE(2),
    /**
     * 无权限访问(角色错误)
     */
    UNAUTHORIZED(3),
    /**
     * 密码不能为空
     */
    EMPTY_PASSWORD(4),
    /**
     * 用户名不能为空
     */
    EMPTY_USERNAME(5),

    // *********** 登录 / 注册
    /**
     * 登录失败
     */
    LOGIN_FAIL(100),
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(101),
    /**
     * 不能注册管理员
     */
    ADMIN_REGISTER_REFUSED(102),
    /**
     * 必须选择性别
     */
    EMPTY_GENDER(103),
    /**
     * 昵称不能为空
     */
    EMPTY_NICKNAME(104),
    /**
     * 必须选择一名角色
     */
    EMPTY_ROLE(105),
    /**
     * 用户名或密码不正确
     */
    WRONG_USERNAME_OR_PASSWORD(106),

    // ********************* 学习任务
    /**
     * 课程不存在
     */
    NO_SUCH_TASK_COURSE(200),
    /**
     * 学习任务创建成功
     */
    TASK_UPLOAD_SUCCESS(201),
    /**
     * 学习任务创建失败
     */
    TASK_UPLOAD_FAIL(202),
    /**
     * 查询我的任务失败
     */
    MY_TASK_FAILED(203),
    /**
     * 视频观看任务创建成功
     */
    TASK_RECORD_SUCCESS(204),
    /**
     * 视频观看任务创建失败
     */
    TASK_RECORD_FAIL(205),
    /**
     * 视频观看完成
     */
    VIDEO_COMPLETED(206),
    /**
     * 视频观看未完成
     */
    VIDEO_UNCOMPLETED(207),

    // ***************** 动态
    /**
     * 评论内容不能为空
     */
    EMPTY_COMMENT_CONTENT(300),
    /**
     * 评论不存在
     */
    NO_SUCH_COMMENT_OR_DYNAMIC(301),
    /**
     * 评论或动态创建失败
     */
    CREATE_COMMENT_OR_DYNAMIC_FAILED(302);


    public final int value;

}
