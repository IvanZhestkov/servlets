package pojo;

import java.util.List;

/**
 * Класс, определяющий структуру объекта форума
 * Форум - основная часть, определенной тематики,
 * включающая в себя разделы данной тематики.
 */
public class Forum {

    private int forumId;
    private String forumName;
    private List<Section> forumSections;
    private String forumDescription;

    public int getForumId() {
        return forumId;
    }

    public void setForumId(int forumId) {
        this.forumId = forumId;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public List<Section> getForumSections() {
        return forumSections;
    }

    public void setForumSections(List<Section> forumSections) {
        this.forumSections = forumSections;
    }

    public String getForumDescription() {
        return forumDescription;
    }

    public void setForumDescription(String forumDescription) {
        this.forumDescription = forumDescription;
    }

}
