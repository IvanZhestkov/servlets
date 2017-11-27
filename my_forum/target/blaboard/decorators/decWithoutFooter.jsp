<!DOCTYPE html>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html lang="ru">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <meta name="author" content="Azat Akhmetshin">
        <link rel="stylesheet" href="css/screen.css">
        <link rel="stylesheet" href="http://meyerweb.com/eric/tools/css/reset/reset.css"><!-- Bootstrap core CDN -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css" integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi" crossorigin="anonymos"><!-- Google Fonts -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Rubik:400,500,700&amp;amp;subset=cyrillic"><!-- Font Awesome -->
        <script src="https://use.fontawesome.com/3388f1cd1b.js"></script>
        <title><decorator:title/></title>
    </head>
    <body class="blaboard">
        <div class="container-fluid">
            <header class="row blaboard__header blaboard__header_blue">
                <div class="col-xs-12">
                    <div class="clearfix">
                        <div class="float-xs-left"><a class="brand" href="main"><img class="brand__logo brand__logo_header" src="img/blab-logo.png" alt="BlaBoard"></a>
                            <form class="form-search" action="search" method="GET">
                                <input class="form-search__input form-search__input_header" type="text" name="key"><span class="form-search__icon form-search__icon_header"><i class="fa fa-search"></i></span>
                            </form>
                        </div>
                        <div class="float-xs-right">
                            <div class="user-block">
                                <c:if test="${user == null}">
                                    <a class="button button_userblock user-block__login user-block__notification_header" href="login">
                                        Войти или зарегистрироваться
                                    </a>
                                </c:if>
                                <c:if test="${user != null}">
                                    <span class="button button_userblock user-block__profile user-block__profile_header" data-rel="popup">
                                        <span class="user-block__username">${user.username}</span>
                                    </span>
                                    <div class="js-popup-hidden popup" id="profile" data-role="popup">
                                        <header class="popup__header">
                                            <span class="popup__name">${user.username} (usr${user.userId})</span>
                                            <span class="popup__close"><i class="fa fa-close"></i></span>
                                        </header>
                                        <ul class="menu">
                                            <li>
                                                <a class="menu__elem menu__elem_popup" href="user?name=${user.username}">
                                                    <p class="elem-name">Мой профиль</p>
                                                    <span class="elem-desc">Мои личные данные</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a class="menu__elem menu__elem_popup" href="settings">
                                                    <p class="elem-name">Настройки</p>
                                                    <span class="elem-desc">Редактирование личных данных</span>
                                                </a>
                                            </li>
                                            <li class="menu__divider"></li>
                                            <li>
                                                <a class="menu__elem menu__elem_popup" href="logout">
                                                    <p class="elem-name">Выйти</p>
                                                    <span class="elem-desc">Завершить сессию пользователя</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <span class="button button_userblock user-block__add user-block__add_header">
                                        <span class="button__icon button__icon_header"><i class="fa fa-plus"></i></span>
                                    </span>
                                    <div class="js-popup-hidden popup" id="add" data-role="popup">
                                        <header class="popup__header">
                                            <span class="popup__name">Создать</span>
                                            <span class="popup__close"><i class="fa fa-close"></i></span>
                                        </header>
                                        <ul class="menu">
                                            <li>
                                                <a class="menu__elem menu__elem_popup" href="create_topic">
                                                    <p class="elem-name">Создать тему</p>
                                                    <span class="elem-desc">Создание темы в нужном Вам разделе форума. Темы являются основной единицей форума и несут главную ценность - коммуницирование людей.</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                    <span class="button button_userblock user-block__notification user-block__notification_header">
                                        <span class="button__icon button__icon_header"><i class="fa fa-bell"></i></span>
                                    </span>
                                    <div class="js-popup-hidden popup" id="notifications" data-role="popup">
                                        <header class="popup__header">
                                            <span class="popup__name">Уведомления</span>
                                            <span class="popup__close"><i class="fa fa-close"></i></span>
                                        </header>
                                        <c:if test="${user.notificationList.iterator().hasNext()}">
                                            <ul class="notif">
                                                <c:forEach items="${user.notificationList}" var="notif">
                                                    <li>
                                                        <a class="notif__elem notif__elem_popup" href="topic?id=${notif.topic.topicId}#message${notif.message.messageId}">
                                                            <p class="elem-name">Новый ответ
                                                                <span class="notif-delete"><i class="fa fa-close"></i></span>
                                                            </p>
                                                            <span class="elem-desc">В ${notif.topic.topicName} участник ${notif.user.username}</span>
                                                        </a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                        <c:if test="${!user.notificationList.iterator().hasNext()}">
                                            <p style="text-align: center; margin: 20px 0;">Уведомлений нет.</p>
                                        </c:if>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
            <div class="container forums sections">
                <decorator:body/>
            </div>
        </div>
    <script src="https://code.jquery.com/jquery-2.2.4.min.js" integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.3.7/js/tether.min.js" integrity="sha384-XTs3FgkjiBgo8qjEjBk0tGmf3wPrWtA6coPfQDfFEY8AnYJwjalXCiosYRBIBZX8" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/js/bootstrap.min.js" integrity="sha384-BLiI7JTZm+JWlgKa0M0kGRpJbF2J8q+qreVrKBC47e3K6BW78kGLrCkeRX6I9RoK" crossorigin="anonymous"></script>
    <script src="js/index.js"></script>
    <script type="text/javascript" src="ckeditor/ckeditor.js"></script>
    <script src="js/core.js"></script>
    <script src="js/validate.js"></script>
        <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
        <script>tinymce.init({ selector:'textarea' });</script>
    </body>
</html>