package pojo;

import pojo.user.User;

import java.sql.Date;
import java.util.List;

/**
 * Класс, описывающий сообщения на форуме.
 * Сообщения - любой ответ пользователя
 * к топику.
 */
public class ForumMessage {

    private int messageId;
    private User messageAuthor;
    private String messageText;
    private Date messageDate;
    private String messageTime;
    private Topic messageTopic;
    private Date messageEditDate;
    private String messageEditTime;
    private int messageRating;
    private List<User> liked;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public User getMessageAuthor() {
        return messageAuthor;
    }

    public void setMessageAuthor(User messageAuthor) {
        this.messageAuthor = messageAuthor;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Date messageDate) {
        this.messageDate = messageDate;
    }

    public Topic getMessageTopic() {
        return messageTopic;
    }

    public void setMessageTopic(Topic messageTopic) {
        this.messageTopic = messageTopic;
    }

    public Date getMessageEditDate() {
        return messageEditDate;
    }

    public void setMessageEditDate(Date messageEditDate) {
        this.messageEditDate = messageEditDate;
    }

    public int getMessageRating() {
        return messageRating;
    }

    public void setMessageRating(int messageRating) {
        this.messageRating = messageRating;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageEditTime() {
        return messageEditTime;
    }

    public void setMessageEditTime(String messageEditTime) {
        this.messageEditTime = messageEditTime;
    }

    public List<User> getLiked() {
        return liked;
    }

    public void setLiked(List<User> liked) {
        this.liked = liked;
    }
}
