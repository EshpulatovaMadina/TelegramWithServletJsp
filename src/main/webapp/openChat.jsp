<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Jamshidbek_Karimov1
  Date: 7/13/2023
  Time: 11:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Chat</title>
    <link rel="stylesheet" href="chatStyle.css">
<%--    <link rel="stylesheet" href="profile-modal.css">--%>
    <style>
        /* Modalning asosiy konteyneri */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.7);
        }

        /* Modal oynasi */
        .modal-content {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 20px;
            width: 300px;
            height: 300px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            border-radius: 8px;
            background-image: url("public/birnarsa.jpg");
        }

        /* Rasm uchun stil */
        .avatar-img {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 10px;
        }

        /* Malumotlar uchun stil */
        .user-info {
            text-align: center;
            margin-bottom: 20px;
        }

        .user-name {
            font-size: 20px;
            font-weight: bold;
        }

        .user-bio {
            font-size: 16px;
        }

        /* Ruchka uchun stil */
        .close-button {
            position: absolute;
            top: 5px;
            right: 10px;
            cursor: pointer;
            font-size: 24px;
            color: #555;
        }
    </style>

</head>
<body>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>



   <button onclick="openModal()">${receiver.name}</button>
<a href="/home.jsp">⏪</a>
<div class="container">
    <div>
        <c:if test="${not empty chat}">
            <a href="/delete-chat?chatId=${chat.id}" >delete chat ✖️</a>
        </c:if>
    </div>
    <div class="messages-box">

        <c:forEach items="${messages}" var="message">
            <c:set var="datestring" value="${message.createdDate.toString().split(\"T\")[1]}"></c:set>
            <c:choose>
                <c:when test="${message.senderId == userId}">
                    <div class="message-orange">
                        <p>${message.text}</p>
                        <div class="message-timestamp-right">
                            <a href="/write?messageId=${message.id}&chatId=${message.chatId}&receiverId=${receiver.id}">🗑</a>
                            SMS${datestring.substring(0,5)}
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="message-blue">
                        <p>${message.text}</p>
                        <div class="message-timestamp-left">
                            SMS${datestring.substring(0,5)}
                            <a href="/write?messageId=${message.id}&chatId=${message.chatId}&receiverId=${receiver.id}">🗑</a>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </c:forEach>


    </div>
    <div>
        <form action="/write" method="post">
            <input type="text" name="text" placeholder="type here">
            <input type="hidden" name="receiverId" value="${receiver.id}">
            <button>Send</button>
        </form>
    </div>
</div>

<!-- Modal -->
<div id="profileModal" class="modal">
    <div class="modal-content">
        <span onclick="closeModal()" style="float: right; cursor: pointer;">&times;</span>

        <div class="user-info">
            <img class="avatar-img" src="/${receiver.avatar}" alt="User Avatar">
            <p class="user-name">name: ${receiver.name}</p>
            <p class="user-name">username: ${receiver.username}</p>
            <p class>Phone number: ${receiver.phoneNumber}</p>
            <p class = user-bio> bio:  ${receiver.bio}</p>
        </div>

        <!-- Boshqa ma'lumotlar uchun kerakli elementlarni qo'shing -->
    </div>
</div>

<script>
    function openModal() {
        var modal = document.getElementById('profileModal');
        modal.style.display = 'block';
    }

    function closeModal() {
        var modal = document.getElementById('profileModal');
        modal.style.display = 'none';
    }
</script>

</body>
</html>