package pojo;

/**
 * Перечисление статусов темы
 */
public enum TopicStatus {

    OPENED(1),
    CLOSED(2),
    RESOLVED(3),
    ARCHIVED(4);

    private int status;

    private TopicStatus(int status) {
        this.status = status;
    }

}
