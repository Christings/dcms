package com.web.entity;

/**
 * 用户域关系实体（用户组织结构）
 *
 * @author 杜延雷
 * @date 2016-10-22
 */
public class UserDoMain {
    private String id;

    private String domainId;

    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDomainId() {
        return domainId;
    }

    public void setDomainId(String domainId) {
        this.domainId = domainId == null ? null : domainId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
}