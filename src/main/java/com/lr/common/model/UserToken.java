package com.lr.common.model;

import java.util.Map;

/**
 * 用户token详情
 *
 * @author shijie.xu
 * @since 2019年02月15日
 */
public class UserToken {
    private static final long serialVersionUID = 1L;

    public UserToken() {
    }

    public UserToken(String username, String userId, int expireMinite) {
        this.userId = userId;
        this.username = username;
        this.expireMinite = expireMinite;
    }

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户登录名
     */
    private String username;

    /**
     * 附加字段
     */
    private Map<String, String> plugininfo;

    /**
     * 登录状态有效期
     */
    private int expireMinite;

    /**
     * 刷新时间
     */
    private long expireFreshTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getPlugininfo() {
        return plugininfo;
    }

    public void setPlugininfo(Map<String, String> plugininfo) {
        this.plugininfo = plugininfo;
    }

    public int getExpireMinite() {
        return expireMinite;
    }

    public void setExpireMinite(int expireMinite) {
        this.expireMinite = expireMinite;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }

    @Override
    public String toString() {
        return "UserToken{" + "userId='" + userId + '\'' + ", username='" + username + '\'' + ", plugininfo=" + plugininfo + ", expireMinite=" + expireMinite + ", expireFreshTime=" + expireFreshTime + '}';
    }
}
