<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${topic.topicSection.sectionName} > ${topic.topicName}</title>
</head>
<body>
<ul class="breadcrumbs">
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_parent" href="main">Главная</a></li>
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_child" href="forum?id=${topic.topicSection.forum.forumId}">${topic.topicSection.forum.forumName}</a></li>
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_child" href="sect?id=${topic.topicSection.sectionId}">${topic.topicSection.sectionName}</a></li>
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_child" href="topic?id=${topic.topicId}">${topic.topicName}</a></li>
</ul>
<div class="clearfix"></div>
<section class="section-page">
    <header class="section-page__header section-page__header_topic">
        <div class="float-xs-left">
            <h2 class="section-page__title">${topic.topicName}</h2>
            <c:if test="${topic.topicTags.iterator().hasNext()}">
                <ul class="tags tags_in-topic">
                    <c:forEach items="${topic.topicTags}" var="tag">
                        <li class="tags__elem"><a href="tag?name=${tag.tagName}" class="tags__link">${tag.tagName}</a></li>
                    </c:forEach>
                </ul>
            </c:if>
        </div>
        <c:if test="${user != null && user.userId != topic.topicAuthor.userId}">
            <c:if test="${!isFeeded}">
                <div class="float-xs-right">
                    <span data-topic="${topic.topicId}" class="section-page__subscribe button" onclick="feed(this)">Подписаться</span>
                </div>
            </c:if>
            <c:if test="${isFeeded}">
                <div class="float-xs-right">
                    <span data-topic="${topic.topicId}" class="section-page__unscribe button" onclick="unfeed(this)">Отписаться</span>
                </div>
            </c:if>
        </c:if>
    </header>
    <div class="clearfix"></div>
    <div class="topic__starter">
        <div class="float-xs-left">
            <div class="user">
                <img src="img/noavatar.gif" alt="нет аватара" class="user__avatar">
                <c:choose>
                    <c:when test="${topic.topicAuthor.userRole.equals(\"MEMBER\")}">
                        <p class="user__role user__role_member">участник</p>
                    </c:when>
                    <c:when test="${topic.topicAuthor.userRole.equals(\"MODERATOR\")}">
                        <p class="user__role user__role_moderator">модератор</p>
                    </c:when>
                    <c:when test="${topic.topicAuthor.userRole.equals(\"ADMIN\")}">
                        <p class="user__role user__role_admin">админ</p>
                    </c:when>
                </c:choose>

                <p class="user__rating">${topic.topicAuthor.userRating}</p>
            </div>
        </div>

        <div class="topic">
            <div class="topic-info">
                <div class="float-xs-left">
                    <a href="user?name=${topic.topicAuthor.username}" class="topic-info__authorlink">${topic.topicAuthor.username}</a>
                    <span class="topic-info__topicnum">
                    написал ${topic.topicAuthor.answersNumber} сообщений и создал ${topic.topicAuthor.topicsNumber} тем
                  </span>
                </div>
                <div class="float-xs-right topic-info__publicatedate">
                    опубликовано ${topic.createDatetime} <c:if test="${!topic.editDatetime.equals(topic.createDatetime)}">(изменен)</c:if>
                </div>
            </div>
            <div class="clearfix"></div>
            <div class="topic__text">
                ${topic.topicText}
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="topic-messages">
        <h3 class="topic-messages__header">Ответы</h3>

            <c:if test="${forumMessages.iterator().hasNext()}">
                <c:forEach var="message" items="${forumMessages}">
                    <div class="message" id="message${message.messageId}">
                        <div class="float-xs-left">
                            <div class="user">
                                <img src="img/noavatar.gif" alt="нет аватара" class="user__avatar">
                                <c:choose>
                                    <c:when test="${message.messageAuthor.userRole.equals(\"MEMBER\")}">
                                        <p class="user__role user__role_member">участник</p>
                                    </c:when>
                                    <c:when test="${message.messageAuthor.userRole.equals(\"MODERATOR\")}">
                                        <p class="user__role user__role_moderator">модератор</p>
                                    </c:when>
                                    <c:when test="${message.messageAuthor.userRole.equals(\"ADMIN\")}">
                                        <p class="user__role user__role_admin">админ</p>
                                    </c:when>
                                </c:choose>
                                <p class="user__rating">${message.messageAuthor.userRating}</p>
                            </div>
                        </div>

                        <div class="topic">
                            <div class="topic-info">
                                <div class="float-xs-left">
                                    <span class="topic-info__topicnum"><a href="#message${message.messageId}">Сообщение #${message.messageId}</a></span>
                                    <a href="user?name=${message.messageAuthor.username}" class="topic-info__authorlink">${message.messageAuthor.username}</a>
                                    <span class="topic-info__topicnum">
		                    написал ${message.messageAuthor.answersNumber} сообщений и создал ${message.messageAuthor.topicsNumber} тем
		                  </span>
                                </div>
                                <div class="float-xs-right topic-info__publicatedate">
                                    опубликовано ${message.messageDate}<c:if test="${!message.messageEditDate.equals(message.messageDate)}">(изменен)</c:if>
                                </div>
                            </div>
                            <div class="clearfix"></div>
                            <div class="topic__text">
                                    ${message.messageText}
                            </div>
                            <c:if test="${user != null}">
                                <footer class="topic__footer">
                                    <div class="float-xs-left">
                                        <span class="button button_quote" onclick="quote(this)">процитировать</span>
                                        <span class="message${message.messageId}"></span>
                                    </div>
                                </footer>
                            </c:if>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </c:forEach>
            </c:if>
            <c:if test="${!forumMessages.iterator().hasNext()}">
                <p class="topic__none">Сообщений к данной теме нет. Напишите первым!</p>
            </c:if>
    </div>

    <c:if test="${user != null}">
        <form class="form-message" action="topic" method="POST">
            <h3 class="form-message__header">Ответить</h3>
            <input type="hidden" name="id" value="${topic.topicId}">
            <textarea name="editor_text" class="required"></textarea>
            <input type="submit" class="button create_message__submit">
        </form>
    </c:if>
    <c:if test="${user == null}">
        <p>Войдите для того, чтобы написать сообщение.</p>
    </c:if>
</section>
</body>
</html>
