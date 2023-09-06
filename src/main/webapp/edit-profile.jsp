<%--
  Created by IntelliJ IDEA.
  User: MADINA
  Date: 7/18/2023
  Time: 9:46 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Photo</title>
</head>
       <body>
       <form action="/edit-profile" method="post" enctype="multipart/form-data">
           <label for="img">Set profile photo:</label>
           <input type="file" id="img" name="img" accept="image/*">

           <label for="bio">Bio:</label>
           <input type="text" id="bio" name="bio" placeholder="Enter your bio">

           <button type="submit" class="button">Submit</button>
       </form>
       <a href="/home.jsp">Go back</a>
       </body>
</html>
