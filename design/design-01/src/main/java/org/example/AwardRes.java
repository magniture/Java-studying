package org.example;

/**
 * @BelongsProject: design
 * @BelongsPackage: org.example
 * @Author: zhangq
 * @CreateTime: 2022-10-25  14:39
 * @Description: TODO
 */
public class AwardRes {

    private String code; // 编码
    private String info; // 描述

    public AwardRes(String code, String info) {
        this.code = code;
        this.info = info;
    }
    /**
     * get field
     *
     * @return code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * set field
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * get field
     *
     * @return info
     */
    public String getInfo() {
        return this.info;
    }

    /**
     * set field
     *
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }
}
