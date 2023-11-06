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
     * 学习任务创建失败
     */
    TASK_UPLOAD_FAIL(201),
    /**
     * 查询我的任务失败
     */
    MY_TASK_FAILED(202),
    /**
     * 视频观看任务创建失败
     */
    TASK_RECORD_FAIL(203),
    /**
     * 视频观看未完成
     */
    VIDEO_UNCOMPLETED(204),
    /**
     * 无法获取积分
     */
    SCORE_GET_FAIL(205),
    /**
     * 视频不见喽~
     */
    VIDEO_DISAPPEARED(206),
    /**
     * 学习任务重复开启
     */
    TASK_OPEN_AGAIN(207),
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
    CREATE_COMMENT_OR_DYNAMIC_FAILED(302),

    // *********** 文件存储
    /**
     * 临时存储失败
     */
    TEMPORARY_STORE_FAILED(400),
    /**
     * 空文件类型
     */
    EMPTY_CONTENT_TYPE(401),
    /**
     * 找不到文件
     */
    NO_SUCH_FILE(402),
    // ***************** 用户
    /**
     * 关注失败
     */
    FOLLOW_FAIL(500),
    /**
     * 取消关注失败
     */
    UNFOLLOW_FAIL(501),
    /**
     * 没有关注
     */
    NOT_FOLLOW(502),
    /**
     * 用户不存在
     */
    NO_SUCH_USER(503),
    /**
     * 个人信息修改失败
     */
    PERSONAL_INFO_MODIFY_FAIL(504),
    /**
     * 获取关注列表失败，没有关注任何人
     */
    NOT_FOLLOW_ANYONE(505),
    /**
     * 空消息
     */
    EMPTY_MESSAGE_CONTENT(600),
    NO_SUCH_MESSAGE(601);

    public final int value;

}
