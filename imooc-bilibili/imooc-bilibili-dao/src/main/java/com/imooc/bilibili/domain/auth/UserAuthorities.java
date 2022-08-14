package com.imooc.bilibili.domain.auth;

import java.util.List;

public class UserAuthorities {

    List<AuthRoleElementOperation> roleElementOperationList;

    List<AuthRoleMenu> roleMenuList;

    public List<AuthRoleElementOperation> getRoleElementOperationList() {
        return roleElementOperationList;
    }

    public void setRoleElementOperationList(List<AuthRoleElementOperation> roleElementOperationList) {
        this.roleElementOperationList = roleElementOperationList;
    }

    public List<AuthRoleMenu> getRoleMenuList() {
        return roleMenuList;
    }

    public void setRoleMenuList(List<AuthRoleMenu> roleMenuList) {
        this.roleMenuList = roleMenuList;
    }
}
