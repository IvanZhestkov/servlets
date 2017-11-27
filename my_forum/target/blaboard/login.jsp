<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1251">
        <title>Страница входа и регистрации</title>
    </head>
    <body>
        <div class="float-xs-left auth-block">
            <h3 class="login__header">Войти на сайт</h3>
            <form class="user-auth" method="POST">
                <div class="user-auth__login">
                    <label for="login-input">Логин</label>
                    <input class="user-input" type="text" id="login-input" name="user_login">
                </div>
                <div class="user-auth__password">
                    <label for="password-input">Пароль</label>
                    <input class="user-input" type="password" id="password-input" name="user_password">
                </div>
                <label class="remember">
                    <input class="rememberme" type="checkbox" value="true" name="rememberme"> Запомнить меня
                </label>
                <input type="submit" class="button create_topic__submit" value="Войти">
            </form>
        </div>
        <div class="register-block">
            <h3 class="login__header">Зарегистрироваться на сайте</h3>
            <form action="register" class="register_form" method="POST">
                <div class="user-reg__login">
                    <label for="username-input">Логин <span class="require">*</span></label>
                    <input class="user-reg-input" type="text" onmouseup="" required id="username-input" name="username">
                    <span class="vmessage_username"></span>
                </div>
                <div class="user-reg__login">
                    <label for="fname-input">Имя</label>
                    <input class="user-reg-input" type="text" id="fname-input" name="fname">
                </div>
                <div class="user-reg__login">
                    <label for="lname-input">Фамилия</label>
                    <input class="user-reg-input" type="text" id="lname-input" name="lname">
                </div>
                <div class="user-reg__login">
                    <label for="email-input">Email <span class="require">*</span></label>
                    <input class="user-reg-input" type="text" id="email-input" name="email">
                    <span class="vmessage_email"></span>
                </div>
                <div class="user-reg__password">
                    <label for="newpassword-input">Пароль <span class="require">*</span></label>
                    <input class="user-reg-input" type="password" id="newpassword-input" name="password">
                </div>
                <input type="submit" class="button register__submit" value="Зарегистрироваться">
            </form>
        </div>
    </body>
</html>
