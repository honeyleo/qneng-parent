package cn.lfy.base.model;

import java.io.Serializable;

public class AdminMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long adminId;

    private Long menuId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
