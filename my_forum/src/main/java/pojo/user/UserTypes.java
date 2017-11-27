package pojo.user;

/**
 * Перечисления типов пользователей
 */
public enum UserTypes {

    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    MEMBER("MEMBER");

    private final String userType;

    private UserTypes(String userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return userType;
    }
}
