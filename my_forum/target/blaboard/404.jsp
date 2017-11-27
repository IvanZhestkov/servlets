<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ошибка!</title>
    <link rel="stylesheet" href="css/screen.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Rubik:400,500,700&amp;amp;subset=cyrillic">
</head>
<body class="error__body">
    <div class="error">
        <div class="error__img"></div>
        <h3 class="error__header">${error_code}.Oooops...</h3>
        <p class="error__message">${error_message}</p>
        <a class="error__mainpage" href="main">Вернуться на главную</a>
    </div>
</body>
</html>
