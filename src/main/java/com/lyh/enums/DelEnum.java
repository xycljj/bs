package com.lyh.enums;

/**
 * @author lyh
 * @ClassName DelEnum
 * @createTime 2021/12/16 15:02:00
 */
public enum DelEnum {

    IS_DEL(1,"已删除"),
    IS_NOT_DEL(0,"未删除");




    private Integer value;
    private String message;


    DelEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

}
