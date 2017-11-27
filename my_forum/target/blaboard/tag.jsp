<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Список тем по тегу ${tagName}</title>
</head>
<body>
<ul class="breadcrumbs">
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_parent" href="main">Главная</a></li>
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_child" href="search">Страница поиска</a></li>
</ul>
<div class="clearfix"></div>
<div class="search-results">
    <h2 class="search-results__header">Темы по <span class="search-results__key">${tagName}</span></h2>
    <c:if test="${topics.iterator().hasNext()}">
        <ul class="search-results__list">
            <c:forEach items="${topics}" var="topic">
                <li class="search-results__elem">
                    <a class="search-results__link" href="topic?id=${topic.topicId}">${topic.topicName}</a>
                    <span>в разделе <a href="sect?id=${topic.topicSection.sectionId}">${topic.topicSection.sectionName}</a> </span>
                    <span>автором <a href="user?name=${topic.topicAuthor.username}">${topic.topicAuthor.username}</a></span>
                </li>
            </c:forEach>
        </ul>
    </c:if>
<c:if test="${topics.iterator().hasNext() == null}">
    Тем не найдено.
</c:if>
</div>
</body>
</html>
