package com.web.entity;

import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 菜单以树方式返回
 *
 * @author 杜延雷
 * @date 2016-06-29
 */
public class MenuTree {
    private String id;

    private Short level;

    private String name;

    private String order;

    private String url;

    private String parentId;

    private String iconId;

    private Short type;

    private List<MenuTree> childMenu;

    /**
     * 将菜单转换成菜单树  返回到前端结果集
     *
     * @param menu 菜单
     * @return 菜单结果集
     */
    public static MenuTree convert(Menu menu) {
        if (null == menu) {
            return null;
        }

        MenuTree result = new MenuTree();
        BeanUtils.copyProperties(menu, result);

        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order == null ? null : order.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId == null ? null : iconId.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public List<MenuTree> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<MenuTree> childMenu) {
        this.childMenu = childMenu;
    }
}