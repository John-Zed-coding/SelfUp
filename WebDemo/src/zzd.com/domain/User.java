package zzd.com.domain;

/**
 * 定义用户信息
 */
public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String token;
    private Integer[] roles;
    private String lastLoginIP;
    private String lastLoginTime;
    private String creatorId;
    private String createTime;
    private boolean deleted;
    public User(){}
    public User(String username, String password, String nickname, String email, String token, Integer[] roles, String lastLoginIP, String lastLoginTime, String creatorId, String createTime, boolean deleted) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.token = token;
        this.roles = roles;
        this.lastLoginIP = lastLoginIP;
        this.lastLoginTime = lastLoginTime;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.deleted = deleted;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer[] getRoles() {
        return roles;
    }

    public void setRoles(Integer[] roles) {
        this.roles = roles;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
