<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Профиль пользователя ${cuser.username}</title>
</head>
<body class="body_profile">
<div class="profile_centered">
    <p class="text_background">Профиль пользователя</p>
    <div class="profile">
        <header class="profile__header">
            <p class="profile__username">${cuser.username}</p>
            <p class="profile__realname">${cuser.userFname} ${cuser.userLname}(usr${cuser.userId})</p>
            <c:if test="${user.userId == cuser.userId}">
                <a class="profile__edit_button button" href="settings">Редактировать</a>
            </c:if>
        </header>
        <div class="clearfix"></div>
        <ul class="tabs">
            <li class="tabs__elem"><span class="tabs__link button tabs__link_active">О пользователе</span></li>
            <li class="tabs__elem"><span class="tabs__link button">Темы</span></li>
        </ul>
        <div class="clearfix"></div>
        <div class="profile__information tabs__content tabs__content_active">
            <div class="profile__info">
                <h3>Информация</h3>
                <p class="info__elem">Дата регистрации: <span>${cuser.registerDate}</span></p>
                <p class="info__elem">E-mail адрес: <a href="mailto:${cuser.userEmail}">${cuser.userEmail}</a></p>
                <c:if test="${cuser.userInfo != null}">
                    <p class="info__elem">Информация о пользователе:</p>
                    <p>${cuser.userInfo}</p>
                </c:if>
            </div>
            <p class="divider"></p>
            <div class="profile__activity">
                <h3>Активность</h3>
                <p class="info__elem">Сообщений на форуме: <span>${allanswers}</span></p>
                <p class="info__elem">Создано тем: <span>${alltopics}</span></p>
                <p class="info__elem">Очков: <span>${cuser.userRating}</span></p>
            </div>
        </div>
        <div class="profile__topics tabs__content">
            <h3>Последние темы</h3>
            <c:if test="${topics.iterator().hasNext()}">
                <ul class="topic-list">
                    <c:forEach items="${topics}" var="topic">
                        <li class="topic-list__elem"><a href="topic?id=${topic.topicId}">${topic.topicName}</a>
                            в разделе <a href="section/id=${topic.topicSection.sectionId}">${topic.topicSection.sectionName}</a> форума <a href="forum?id=${topic.topicSection.forum.forumId}">${topic.topicSection.forum.forumName}</a></li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${!topics.iterator().hasNext()}">
                <p>Пользователь не создал ни одной темы</p>
            </c:if>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script>
    (function($) {
        $(function() {
            $('.tabs__elem').on('click',function() {
                $('.tabs__link').removeClass('tabs__link_active')
                $(this).find('.tabs__link').addClass('tabs__link_active')
                $('.tabs__content').removeClass('tabs__content_active')
                console.log($(this).index())
                $('.tabs__content').eq($(this).index()).addClass('tabs__content_active')
            });
        });
    })(jQuery);
</script>
</body>
</html>

