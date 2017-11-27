package pojo;

import pojo.user.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, описывающий структуру тем раздела.
 */
public class Topic {

    private int topicId;
    private String topicName;
    private Section topicSection;
    private User topicAuthor;
    private int topicStatus;
    private String topicText;
    private int topicViews;
    private ForumMessage lastMessage;
    private Date createDatetime;
    private String createTime;
    private Date editDatetime;
    private int topicAnswersCount;
    private List<Tag> topicTags;

    private List<ForumMessage> forumMessageList = new ArrayList<ForumMessage>();

    public List<ForumMessage> getForumMessageList() {
        return forumMessageList;
    }

    public void setForumMessageList(List<ForumMessage> forumMessageList) {
        this.forumMessageList = forumMessageList;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Section getTopicSection() {
        return topicSection;
    }

    public void setTopicSection(Section topicSection) {
        this.topicSection = topicSection;
    }

    public User getTopicAuthor() {
        return topicAuthor;
    }

    public void setTopicAuthor(User topicAuthor) {
        this.topicAuthor = topicAuthor;
    }

    public int getTopicStatus() {
        return topicStatus;
    }

    public void setTopicStatus(int topicStatus) {
        this.topicStatus = topicStatus;
    }

    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    public int getTopicViews() {
        return topicViews;
    }

    public void setTopicViews(int topicViews) {
        this.topicViews = topicViews;
    }

    public ForumMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(ForumMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Date getEditDatetime() {
        return editDatetime;
    }

    public void setEditDatetime(Date editDatetime) {
        this.editDatetime = editDatetime;
    }

    public int getTopicAnswersCount() {
        return topicAnswersCount;
    }

    public void setTopicAnswersCount(int topicAnswersCount) {
        this.topicAnswersCount = topicAnswersCount;
    }

    public List<Tag> getTopicTags() {
        return topicTags;
    }

    public void setTopicTags(List<Tag> topicTags) {
        this.topicTags = topicTags;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
