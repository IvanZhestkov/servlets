package pojo;

/**
 * Класс, описывающий тег темы.
 * Тег - короткое ключевое слово,
 * по которому можно найти темы.
 */
public class Tag {

    private int tagId;
    private String tagName;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
