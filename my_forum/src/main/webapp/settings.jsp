<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Изменение профиля ${user.username}</title>
</head>
<body class="body_profile">
<div class="profile_centered">
    <p class="text_background">Изменить пользователя</p>
    <div class="profile">
        <form action="settings" method="post" class="edit-user" autocomplete="off">
            <p class="profile__username">${cuser.username}</p>
            <div class="profile__prop">
                <p class="profile__prop_text">Имя пользователя:</p>
                <input type="text" class="create-topic__input" value="${user.userFname}" name="fname">
            </div>

            <div class="profile__prop">
                <p class="profile__prop_text">Фамилия пользователя:</p>
                <input type="text" class="create-topic__input" value="${user.userLname}" name="lname">
            </div>

            <div class="profile__prop">
                <p class="profile__prop_text">Информация о пользователе:</p>
                <textarea class="create-topic__input" name="info"></textarea>
            </div>

            <div class="profile__prop">
                <p class="profile__prop_text">E-mail пользователя:</p>
                <input type="text" class="create-topic__input" value="${user.userEmail}" name="email">
            </div>

            <div class="profile__prop">
                <p class="profile__prop_text">Новый пароль:</p>
                <input type="password" class="create-topic__input" name="password">
            </div>
            <input type="submit" class="profile__edit_button button" value="Изменить данные">
        </form>
        <div class="clearfix"></div>
    </div>
</div>
</body>
</html>

