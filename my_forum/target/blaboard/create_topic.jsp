<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Создание новой темы</title>
</head>
<body>
<ul class="breadcrumbs">
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_parent" href="main">Главная</a></li>
    <li class="breadcrumbs__elem"><a class="breadcrumbs__link breadcrumbs__link_child" href="#">Создание новой темы</a></li>
</ul>
<div class="clearfix"></div>
<section class="section-page">
    <header class="section-page__header">
        <div class="float-xs-left">
            <h2 class="section-page__title">Создать новую тему</h2>
            <p class="section-page__desc">Создание новой темы на форуме. Прочитайте <a href="#">правила оформления</a> тем прежде, чем опубликовать её.</p>
        </div>
    </header>
    <div class="clearfix"></div>
    <form class="create-topic" name="create_topic" action="create_topic" method="POST">
        <div class="topic-section">
            <label for="create-topic__section" class="create-topic__label">Раздел темы <span class="require">*</span></label>
            <select required id="create-topic__section" class="create-topic__select" name="topic_section">
                <c:forEach items="${forums}" var="forum">
                    <optgroup label="${forum.getForumName()}">
                        <c:forEach items="${forum.getForumSections()}" var="section">
                            <option value="${section.getSectionId()}">${section.getSectionName()}</option>
                            <c:if test="${section.getSubsections().iterator().hasNext()}">
                                <c:forEach items="${section.getSubsections()}" var="subsection">
                                    <option class="sub" value="${subsection.getSectionId()}">--${subsection.getSectionName()}</option>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </optgroup>
                </c:forEach>
            </select>
        </div>
        <div class="topic-name">
            <label for="create-topic__name" class="create-topic__label">Название темы <span class="require">*</span></label>
            <input type="text" id="create-topic__name" class="create-topic__input" name="topic_name">
        </div>
        <div class="topic-text">
            <label for="create-topic__text" class="create-topic__label">Содержимое темы <span class="require">*</span></label>
            <textarea id="create-topic__text" class="create-topic__input required" name="editor_text"></textarea>
        </div>
        <div class="topic-tags">
            <label for="create-topic__tags" class="create-topic__label">Метки (через запятую)</label>
            <input type="text" id="create-topic__tags" class="create-topic__input" name="topic_tags">
        </div>
        <input class="button" id="create_topic__submit" type="submit" name="create" value="Создать тему">
    </form>

    </div>
</section>
</body>
</html>
