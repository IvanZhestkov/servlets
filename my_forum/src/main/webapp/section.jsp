<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="home" scope="session" value="blaboard"/>
<html>
<head>
    <title>${section.forum.forumName} > ${section.sectionName}</title>
</head>
<body>
<ul class="breadcrumbs">
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_parent" href="main">Главная</a></li>
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_child" href="forum?id=${section.forum.forumId}">${section.forum.forumName}</a></li>
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_child" href="sect?id=${section.sectionId}">${section.sectionName}</a></li>
</ul>
<div class="clearfix"></div>
<section class="section-page">
    <header class="section-page__header">
        <div class="float-xs-left">
            <h2 class="section-page__title">${section.sectionName}</h2>
            <p class="section-page__desc">${section.sectionDescription}</p>
        </div>
        <c:if test="${user != null}">
            <div class="float-xs-right">
                <a class="section-page__create button" href="create_topic">Новая тема</a>
            </div>
        </c:if>
    </header>
    <div class="clearfix"></div>
    <div class="subsections">
        <c:if test="${subsections.size() > 0}">
            <header class="subsections__header">
                <h3 class="subsections__title">Подразделы</h3>
            </header>
            <div class="row">

                    <c:forEach var="subsection" items="${subsections}">
                        <div class="col-xs-12 col-lg-4">
                            <div class="section">
                                <header class="clearfix section__header"><a class="float-xs-left section__link" href="sect?id=${subsection.sectionId}">${subsection.sectionName}</a><span class="float-xs-right section__count-of-message">Сообщ. ${subsection.sectionTopicNum}</span></header>
                                <div class="section__desc">${subsection.sectionDescription}</div>
                                <c:if test="${subsection.lastTopic != null}">
                                <footer class="section__footer">
                                    <div class="lastmessage"><img class="lastmessage__avatar" src="img/last_message_avatar.png">
                                        <div class="lastmessage__author"><a class="lastmessage__link" href="topic?id=${subsection.lastTopic.topicId}">${subsection.lastTopic.topicName}</a><a class="lastmessage__author-link" href="#author">Автор: ${subsection.lastTopic.topicAuthor.username}</a>

                                        </div>
                                    </div>
                                </footer>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
            </div>
        </c:if>
    </div>
    <div class="topics">
        <header class="topics__header">
            <h3 class="topics__title float-xs-left">Темы</h3>
            <c:if test="${numberOfPages > 1}">
                <ul class="pages float-xs-right">
                    <c:forEach begin="0" var="i" end="${numberOfPages-1}" step="1">
                        <li class="pages__elem"><a href="sect?id=${section.getSectionId()}&p=${i+1}" class="pages__link button"><c:out value="${i+1}"/></a></li>
                    </c:forEach>
                    <li class="pages__elem"><a href="sect?id=${section.getSectionId()}&p=${currentPage+1}" class="pages__link button">вперед</a></li>
                    <li class="pages__elem"><a href="sect?id=${section.getSectionId()}&p=${numberOfPages}" class="pages__link button">посл.</a></li>
                </ul>
            </c:if>
        </header>
        <div class="clearfix"></div>
        <c:if test="${topics.size() > 0}">
            <div class="topics__list">
                <c:forEach var="topic" items="${topics}">
                    <article class="topics__topic topics__topic_newmessage">
                        <div class="float-xs-left">
                            <div class="topic__desc float-xs-right">
                                <h4 class="topic__title"><a href="topic?id=${topic.getTopicId()}" class="topic__link">${topic.getTopicName()}</a></h4>
                                <p class="topic__author">Тема создана пользователем ${topic.getTopicAuthor().getUsername()}, ${topic.getCreateDatetime().toString()}</p>
                                <c:if test="${topic.topicTags.iterator().hasNext()}">
                                    <ul class="tags tags_in-topic">
                                        <c:forEach items="${topic.topicTags}" var="tag">
                                            <li class="tags__elem"><a href="tag?name=${tag.tagName}" class="tags__link">${tag.tagName}</a></li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </div>
                        </div>
                        <div class="float-xs-right">
                            <div class="topic__counts">
                                <div class="topic__answers">
                                    <p class="topic__count-of-answers">${topic.getTopicAnswersCount()}</p>
                                    ответ.
                                </div>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </article>
                </c:forEach>

                <c:if test="${numberOfPages > 1}">
                <footer class="topics__footer">
                    <ul class="pages float-xs-right">
                        <c:forEach begin="0" var="i" end="${numberOfPages-1}" step="1">
                            <li class="pages__elem"><a href="sect?id=${section.getSectionId()}&p=${i+1}" class="pages__link button"><c:out value="${i+1}"/></a></li>
                        </c:forEach>
                        <li class="pages__elem"><a href="sect?id=${section.getSectionId()}&p=${currentPage+1}" class="pages__link button">вперед</a></li>
                        <li class="pages__elem"><a href="sect?id=${section.getSectionId()}&p=${numberOfPages}" class="pages__link button">посл.</a></li>
                    </ul>
                </footer>
                </c:if>
            </div>
        </c:if>
        <c:if test="${topics.size() == 0}">
            <p class="topics__none">В данном разделе нет тем. Создайте первую тему.</p>
        </c:if>
    </div>
</section>
</body>
</html>
