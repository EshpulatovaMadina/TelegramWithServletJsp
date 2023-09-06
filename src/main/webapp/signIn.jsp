<%--
  Created by IntelliJ IDEA.
  User: Jamshidbek_Karimov1
  Date: 4/17/2023
  Time: 5:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>


<form action="${pageContext.request.contextPath}/sign-in" method="post">
    <input type="text" name="phoneNumber" placeholder="phoneNumber">
    <input type="password" name="password" placeholder="password">
    <button class="button">Submit</button>
</form>

</body>
</html>
