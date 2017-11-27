package pojo;

import java.util.List;

/**
 * Класс, определяющий структуру секции (раздела).
 * Раздел - это отдельная категория,
 * соответствующая тематике форума.
 */
public class Section {

    private int sectionId;
    private String sectionName;
    private Forum forum;
    private String sectionDescription;
    private Section parentSection;
    private long sectionTopicNum;
    private Topic lastTopic;
    private List<Section> subsections;

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public String getSectionDescription() {
        return sectionDescription;
    }

    public void setSectionDescription(String sectionDescription) {
        this.sectionDescription = sectionDescription;
    }

    public Section getParentSection() {
        return parentSection;
    }

    public void setParentSection(Section parentSection) {
        this.parentSection = parentSection;
    }

    public long getSectionTopicNum() {
        return sectionTopicNum;
    }

    public void setSectionTopicNum(long sectionTopicNum) {
        this.sectionTopicNum = sectionTopicNum;
    }

    public Topic getLastTopic() {
        return lastTopic;
    }

    public void setLastTopic(Topic lastTopic) {
        this.lastTopic = lastTopic;
    }

    public List<Section> getSubsections() {
        return subsections;
    }

    public void setSubsections(List<Section> subsections) {
        this.subsections = subsections;
    }
}
