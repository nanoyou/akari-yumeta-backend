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


    /**
     * 不能注册管理员
     */
    ADMIN_REGISTER_REFUSED(100),
    /**
     * 登录失败
     */
    LOGIN_FAIL(101),
    /**
     * 登录成功
     */
    LOGIN_SUCCESS(102),
    /**
     * 课程不存在
     */
    NO_SUCH_TASK_COURSE(103),
    /**
     * 学习任务创建成功
     */
    TASK_UPLOAD_SUCCESS(104),
    /**
     * 学习任务创建失败
     */
    TASK_UPLOAD_FAIL(105),
    /**
     * 查询我的任务失败
     */
    MY_TASK_FAILED(106),
    /**
     * 时间约束错误(结束时间不能在开始时间之前)
     */
    TIME_CONSTRAINT_ERR(107),
    /**
     * 必须选择性别
     */
    EMPTY_GENDER(108),
    /**
     * 昵称不能为空
     */
    EMPTY_NICKNAME(109),
    /**
     * 必须选择一名角色
     */
    EMPTY_ROLE(110),
    /**
     * 视频观看任务创建成功
     */
    TASK_RECORD_SUCCESS(112),
    /**
     * 视频观看任务创建失败
     */
    TASK_RECORD_FAIL(113),
    /**
     * 视频观看完成
     */
    VIDEO_COMPLETED(114),
    /**
     * 视频观看未完成
     */
    VIDEO_UNCOMPLETED(115),
    /**
     * 学习积分获取成功
     */
    SCORE_GET_SUCCESS(116),
    /**
     * 学习积分获取失败
     */
    SCORE_GET_FAIL(117);



    public final int value;

}
