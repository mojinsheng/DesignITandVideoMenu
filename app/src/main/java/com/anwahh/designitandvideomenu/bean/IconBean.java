package com.anwahh.designitandvideomenu.bean;

/**
 * @describe 按钮文件的实体类
 * @author Anwahh
 * @date 2020-04-10
 */
public class IconBean {

    private String iconId; // ID
    private String name; // 图标名字
    private String icon; // 图标

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String iconiv) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "IconBean [iconId =" + iconId + ", name=" + name + ", icon=" + icon + "]";
    }
}
