package pojo.user;

import pojo.Section;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс, описывающий модератора.
 * Наследуется от Участника
 * @see Member
 * Модератор имеет возможность:
 * редактировать, удалять, переносить в архив темы раздела,
 * удалять сообщения, которые были оставлены участниками в темах
 * модерируемого раздела
 */
public class Moderator extends Member {

    List<Section> sectionsInModerate = new ArrayList<Section>();

    public Moderator() {
        super.setUserRole(UserTypes.MODERATOR.toString());
    }

    public void removeSectionInModerate(Section section) {
        Iterator<Section> sectionIterator = sectionsInModerate.iterator();
        Section temp = null;
        while (sectionIterator.hasNext()) {
            temp = sectionIterator.next();
            if(temp.getSectionId() == section.getSectionId()) {
                sectionIterator.remove();
                break;
            }
        }
    }

    public List<Section> getSectionsInModerate() {
        return sectionsInModerate;
    }

    public void setSectionsInModerate(List<Section> sectionsInModerate) {
        this.sectionsInModerate = sectionsInModerate;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isModerator() {
        return true;
    }
}
