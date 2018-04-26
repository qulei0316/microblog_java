package com.qulei.common.enums;

public enum ArticleTypeEnum {
    NORMAL(1,"normal"),
    DRAFT(2,"draft"),
    RECYCLE(3,"recycle bin");

    private int code;

    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private ArticleTypeEnum(int code, String desc){
        this.code=code;
        this.desc=desc;
    }
}
