package pojo.user;

/**
 * Класс описывающий администратора.
 * Наследуется от Модератора.
 * @see Moderator
 * Самый главный на форуме.
 * Имеет все возможности.
 */
public class Admin extends Moderator {

    public Admin() {
        super.setUserRole(UserTypes.ADMIN.toString());
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
