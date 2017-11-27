<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title></title>
    </head>

    <body>
    <c:forEach var="forum" items="${forums}">
        <div class="forum">
            <header class="forum__header">
                <h2 class="forum__name">${forum.forumName}</h2>
            </header>
            <div class="row">
                <c:forEach var="section" items="${forum.forumSections}">
                    <div class="col-xs-12 col-lg-4">
                        <div class="section">
                            <header class="clearfix section__header"><a class="float-xs-left section__link" href="sect?id=${section.sectionId}">${section.sectionName}</a><span class="float-xs-right section__count-of-message">Сообщ. ${section.sectionTopicNum}</span></header>
                            <div class="section__desc"><c:out value="${section.sectionDescription}"/></div>
                            <c:if test="${section.subsections.size() > 0}">
                                <ul class="subsections">
                                    <c:forEach var="subsection" items="${section.subsections}">
                                        <li class="subsections__li"><a class="subsections__elem" href="sect?id=${subsection.sectionId}">${subsection.sectionName}</a></li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                            <c:if test="${section.lastTopic != null}">
                                <footer class="section__footer">
                                    <div class="lastmessage"><img class="lastmessage__avatar" src="img/last_message_avatar.png">
                                        <div class="lastmessage__author"><a class="lastmessage__link" href="topic?id=${section.lastTopic.topicId}">${section.lastTopic.topicName}</a><a class="lastmessage__author-link" href="user?name=${section.lastTopic.topicAuthor.username}">Автор: ${section.lastTopic.topicAuthor.username}</a>

                                        </div>
                                    </div>
                                </footer>
                            </c:if>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:forEach>
    </body>
</html>
