package pojo.user;

import org.apache.commons.codec.digest.DigestUtils;
import pojo.Notification;
import pojo.Topic;

import java.sql.Date;
import java.util.List;

/**
 * Класс, определяющий пользователя системы.
 */
public abstract class User {

    private int userId;
    private String username;
    private String passwordMd5;
    private String userRole;
    private String userEmail;
    private String userFname;
    private String userLname;
    private String userInfo;
    private boolean confirmed;
    private boolean active;
    private Date deleteDate;
    private int userRating;
    private int answersNumber;
    private int topicsNumber;
    private Topic lastTopic;
    private Date registerDate;
    private List<Notification> notificationList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordMd5() {
        return passwordMd5;
    }

    public void setPasswordMd5(String passwordMd5) {
        this.passwordMd5 = DigestUtils.md5Hex(passwordMd5);
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFname() {
        return userFname;
    }

    public void setUserFname(String userFname) {
        this.userFname = userFname;
    }

    public String getUserLname() {
        return userLname;
    }

    public void setUserLname(String userLname) {
        this.userLname = userLname;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public int getUserRating() {
        return userRating;
    }

    public void setUserRating(int userRating) {
        this.userRating = userRating;
    }

    public int getAnswersNumber() {
        return answersNumber;
    }

    public void setAnswersNumber(int answersNumber) {
        this.answersNumber = answersNumber;
    }

    public Topic getLastTopic() {
        return lastTopic;
    }

    public void setLastTopic(Topic lastTopic) {
        this.lastTopic = lastTopic;
    }

    public int getTopicsNumber() {
        return topicsNumber;
    }

    public void setTopicsNumber(int topicsNumber) {
        this.topicsNumber = topicsNumber;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public abstract boolean isAdmin();
    public abstract boolean isModerator();
    public abstract boolean isMember();
}
