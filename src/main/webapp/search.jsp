<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Jamshidbek_Karimov1
  Date: 7/13/2023
  Time: 11:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search Chat</title>
    <link rel="stylesheet" href="profile-photo.css">
</head>
<body>

<form action="${pageContext.request.contextPath}/search" method="post">
  <input type="text" name="phoneNumber" placeholder="type phoneNumber">
  <input type="submit">
</form>

<c:choose>
    <c:when test="${empty pageContext.request.getAttribute('chats')}">
        <p>${message}</p>
    </c:when>
    <c:otherwise>
        <c:forEach items="${chats}" var="chat">
            <form action="${pageContext.request.contextPath}/open-chat" method="post">
                <input type="hidden" name="phoneNumber" value="${chat.phoneNumber().toString()}">
                <c:if test="${not empty chat.avatar()}">
                    <img class="avatar-img" src="/${chat.avatar()}">
                </c:if>
                <button class="button-style"> ${chat.name()}</button>
            </form>
        </c:forEach>

    </c:otherwise>
</c:choose>

</body>
</html>
