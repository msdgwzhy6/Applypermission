package com.feng.ApplyPermission;

/**
 * @author feng
 * @Description: 申请权限的业务类
 * @date 2017/3/6
 */
public class PermissionBean {
    private String permissionName;
    private String explain;
    private int applyCode;

    public PermissionBean() {

    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(int applyCode) {
        this.applyCode = applyCode;
    }

    @Override
    public String toString() {
        return "PermissionBean{" +
                "permissionName='" + permissionName + '\'' +
                ", explain='" + explain + '\'' +
                ", applyCode=" + applyCode +
                '}';
    }
}
