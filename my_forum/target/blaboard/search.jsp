<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Результаты поиска по запросу ${searchKey}</title>
</head>
<body>
    <ul class="breadcrumbs">
        <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_parent" href="main">Главная</a></li>
        <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_child" href="search">Страница поиска</a></li>
    </ul>
    <div class="clearfix"></div>
    <div class="search-results">
        <h2 class="search-results__header">Результаты поиска по запросу <span class="search-results__key">${searchKey}</span></h2>
        <c:if test="${s_topics.iterator().hasNext()}">
            <p class="search-results__header_p">Найденные темы</p>
            <ul class="search-results__list">
                <c:forEach items="${s_topics}" var="topic">
                    <li class="search-results__elem">
                        <a class="search-results__link" href="topic?id=${topic.topicId}">${topic.topicName}</a>
                        <span>в разделе <a href="sect?id=${topic.topicSection.sectionId}">${topic.topicSection.sectionName}</a> </span>
                        <span>автором <a href="user?name=${topic.topicAuthor.username}">${topic.topicAuthor.username}</a></span>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${s_sections.iterator().hasNext()}">
            <p class="search-results__header_p">Найденные разделы</p>
            <ul class="search-results__list">
                <c:forEach items="${s_sections}" var="section">
                    <li class="search-results__elem">
                        <a class="search-results__link" href="sect?id=${section.sectionId}">${section.sectionName}</a>
                        <span> на форуме "${section.forum.forumName}"</span>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
</body>
</html>
