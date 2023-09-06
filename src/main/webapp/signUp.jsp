<%--
  Created by IntelliJ IDEA.
  User: Jamshidbek_Karimov1
  Date: 4/17/2023
  Time: 5:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SignUp</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/sign-up" method="post">
    <input type="text" name="name" placeholder="enter name">
    <input type="text" name="phoneNumber" placeholder="enter phone number">
    <input type="email" name="username" placeholder="username">
    <input type="password" name="password" placeholder="enter password">
    <button class="button">Submit</button>
</form>

</body>
</html>
