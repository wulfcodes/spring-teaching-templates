package io.wulfcodes.serdes.model;

import java.util.List;
import java.util.Map;

public class User {
    private Integer userId;
    private String username;
    private Boolean isActive;
    private Double balance;
    private String nickname;
    private List<String> tags;
    private Map<String, Boolean> verifications;
    private Preferences preferences;
    private List<LoginHistory> loginHistories;

    public static class Preferences {
        private String theme;
        private Boolean notifications;

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public Boolean getNotifications() {
            return notifications;
        }

        public void setNotifications(Boolean notifications) {
            this.notifications = notifications;
        }

        @Override
        public String toString() {
            return "Preferences{" +
                "theme='" + theme + '\'' +
                ", notifications=" + notifications +
                '}';
        }
    }

    public static class LoginHistory {
        private String ip;
        private Map<String, String> proxies;
        private Long timestamp;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Map<String, String> getProxies() {
            return proxies;
        }

        public void setProxies(Map<String, String> proxies) {
            this.proxies = proxies;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "LoginHistory{" +
                "ip='" + ip + '\'' +
                ", proxies=" + proxies +
                ", timestamp=" + timestamp +
                '}';
        }
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, Boolean> getVerifications() {
        return verifications;
    }

    public void setVerifications(Map<String, Boolean> verifications) {
        this.verifications = verifications;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }

    public List<LoginHistory> getLoginHistories() {
        return loginHistories;
    }

    public void setLoginHistories(List<LoginHistory> loginHistories) {
        this.loginHistories = loginHistories;
    }

    @Override
    public String toString() {
        return "User{" +
            "userId=" + userId +
            ", username='" + username + '\'' +
            ", isActive=" + isActive +
            ", balance=" + balance +
            ", nickname='" + nickname + '\'' +
            ", tags=" + tags +
            ", verifications=" + verifications +
            ", preferences=" + preferences +
            ", loginHistories=" + loginHistories +
            '}';
    }
}
