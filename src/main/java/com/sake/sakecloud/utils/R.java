package com.sake.sakecloud.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * r
 * 统一响应体封装
 *
 * @author WSY
 * @date 2020/12/17
 */
public class R<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    private R(int status) {
        this.status = status;
    }

    private R(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private R(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private R(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    /**
     * 使之不在json序列化结果当中
     *
     * @return boolean
     */
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResultCode.SUCCESS.getCode();
    }

    /**
     * 获得状态码
     *
     * @return int
     */
    public int getStatus() {
        return status;
    }


    /**
     * 获取数据
     *
     * @return {@link T}
     */
    public T getData() {
        return data;
    }

    /**
     * 获得响应消息
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 成功
     *
     * @return {@link R<T>}
     */
    public static <T> R<T> success() {
        return new R<T>(ResultCode.SUCCESS.getCode());
    }

    /**
     * 成功消息
     *
     * @param msg 响应消息
     * @return {@link R<T>}
     */
    public static <T> R<T> successMessage(String msg) {
        return new R<T>(ResultCode.SUCCESS.getCode(), msg);
    }

    /**
     * 成功
     *
     * @param data 数据
     * @return {@link R<T>}
     */
    public static <T> R<T> success(T data) {
        return new R<T>(ResultCode.SUCCESS.getCode(), data);
    }

    /**
     * 成功
     *
     * @param msg  响应消息
     * @param data 数据
     * @return {@link R<T>}
     */
    public static <T> R<T> success(String msg, T data) {
        return new R<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 错误
     *
     * @return {@link R<T>}
     */
    public static <T> R<T> error() {
        return new R<T>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getDesc());
    }

    /**
     * 错误
     *
     * @param errorCode    错误代码
     * @param errorMessage 错误消息
     * @return {@link R<T>}
     */
    public static <T> R<T> error(int errorCode, String errorMessage) {
        return new R<T>(errorCode, errorMessage);
    }

    /**
     * 错误消息
     *
     * @param errorMessage 错误消息
     * @return {@link R<T>}
     */
    public static <T> R<T> errorMessage(String errorMessage) {
        return new R<T>(ResultCode.ERROR.getCode(), errorMessage);
    }

    /**
     * 错误代码的信息
     *
     * @param errorCode    错误代码
     * @param errorMessage 错误消息
     * @return {@link R<T>}
     */
    public static <T> R<T> errorCodeMessage(int errorCode, String errorMessage) {
        return new R<T>(errorCode, errorMessage);
    }

    /**
     * 错误
     *
     * @param errorMessage 错误消息
     * @param data         数据
     * @return {@link R<T>}
     */
    public static <T> R<T> error(String errorMessage, T data) {
        return new R<T>(ResultCode.ERROR.getCode(), errorMessage, data);
    }

    /**
     * 返回系统状态码以及相应说明
     *
     * @param ResultCode 结果代码
     * @return {@link R<T>}
     */
    public static <T> R<T> response(ResultCode ResultCode) {
        return new R<T>(ResultCode.getCode(), ResultCode.getDesc());
    }

    @Override
    public String toString() {
        return "R{" +
                "status=" + status +
                ", msg='" + msg + "'" +
                ", data=" + data +
                '}';
    }
}

@Getter
@AllArgsConstructor
enum  ResultCode {
    /**
     * Success response code.
     *
     * @desc 请求操作成功
     */
    SUCCESS(0, "操作成功!"),
    /**
     * Error response code.
     *
     * @desc 请求操作失败
     */
    ERROR(1, "操作失败!"),
    /**
     * Illegal argument response code.
     *
     * @desc 参数错误
     */
    ILLEGAL_ARGUMENT(2, "请求参数错误!"),
    /**
     * Need login response code.
     *
     * @desc 需要登录
     */
    NEED_LOGIN(50000, "登录超时,请重新登录!"),
    /**
     * Exception response code.
     *
     * @desc 服务器内部错误
     */
    EXCEPTION(500, "该请求发生异常,请稍后重试!"),
    /**
     * Sys not found error response code.
     */
    SYS_NOT_FOUND_ERROR(404, "未找到相应资源!"),

    DATABASE_ERROR(6000,"数据库异常,请稍后重试!"),
    /**
     * Sys not use error response code.
     */
    SYS_NOT_USE_ERROR(403, "资源不可用!");

    private final int code;

    private final String desc;

    /**
     * 根据code获取枚举
     * @param code
     * @return
     */
    public static ResultCode getEnumByCode(int code){
        return Arrays.stream(values())
                .filter(x-> Objects.equals(x,code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("没有找到对应的枚举类型"));
    }

    /**
     * 根据code获取枚举值
     * @param code
     * @return
     */
    public static String getDescByCode(int code){
        return Arrays.stream(values())
                .filter(x->Objects.equals(x,code))
                .map(ResultCode::getDesc)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("没有找到对应的枚举值"));
    }
}
