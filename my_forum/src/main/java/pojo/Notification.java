package pojo;

import pojo.user.User;

/**
 * Объект уведомления
 */
public class Notification {

    private int id;
    private User user;
    private Topic topic;
    private ForumMessage message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public ForumMessage getMessage() {
        return message;
    }

    public void setMessage(ForumMessage message) {
        this.message = message;
    }
}
