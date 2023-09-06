<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: MADINA
  Date: 7/17/2023
  Time: 11:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chats</title>
    <link rel="stylesheet" href="profile-photo.css">
    <link rel="stylesheet" href="chats.css">
</head>
<body>
<jsp:useBean id="chats" type="java.util.List<com.example.telegramwithservletjsp.model.Chats>" scope="request"/>

<c:if test="${empty chats}">
    <p>No chats</p>
</c:if>
<table>
    <div>
        <a href="/home.jsp"></a>
<%--        <img src="D:/Telegram/Library/59bf06ec-9997-4148-bc4a-46726da98242.png" alt="asdasd"/>--%>
    </div>
    <c:forEach items="${chats}" var="chat">
        <tr>
            <td>
                <form action="${pageContext.request.contextPath}/open-chat" method="post">
                    <input type="hidden" name="phoneNumber" value="${chat.phoneNumber().toString()}">
                    <img class="avatar-img" src="/${chat.avatar()}">
                    <button class="button-style">
                            ${chat.name()}
                    </button>
                </form>
            </td>
        </tr>

    </c:forEach>
</table>

</body>
</html>
