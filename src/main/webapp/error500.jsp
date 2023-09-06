<%--
  Created by IntelliJ IDEA.
  User: MADINA
  Date: 7/16/2023
  Time: 6:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>

<h1>${pageContext.request.getAttribute("jakarta.servlet.error.exception").message}</h1>
<a href="${pageContext.request.getAttribute("jakarta.servlet.error.request_uri")}">Back</a>

</body>
</html>
