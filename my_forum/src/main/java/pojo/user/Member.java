package pojo.user;

import pojo.Topic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс, описывающий участника форума.
 * Участник имеет возможность:
 * создавать темы и комнаты,
 * участвовать в обсуждениях,
 * писать сообщения другим участникам,
 * повышать рейтинг другим участникам
 * @see User
 */
public class Member extends User {

    List<Topic> topicsByMember = new ArrayList<Topic>();

    public void removeTopicByMember(Topic topic) {
        Iterator<Topic> topicIterator = topicsByMember.iterator();
        Topic temp = null;
        while(topicIterator.hasNext()) {
            temp = topicIterator.next();
            if(temp.getTopicId() == topic.getTopicId()) {
                topicIterator.remove();
                break;
            }
        }
    }

    public Member() {
        super.setUserRole(UserTypes.MEMBER.toString());
    }

    public List<Topic> getTopicsByMember() {
        return topicsByMember;
    }

    public void setTopicsByMember(List<Topic> topicsByMember) {
        this.topicsByMember = topicsByMember;
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean isModerator() {
        return false;
    }

    @Override
    public boolean isMember() {
        return true;
    }
}
