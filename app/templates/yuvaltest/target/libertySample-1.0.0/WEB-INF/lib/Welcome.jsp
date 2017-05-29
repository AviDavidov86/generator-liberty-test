<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="javax.security.auth.Subject" %>
    <%@ page import="com.ibm.websphere.security.auth.WSSubject" %>
    <%@page import="java.util.Set" %>
    <%@page import="java.util.Hashtable" %>
    <%@page import="org.apache.commons.codec.binary.Base64" %>

<!DOCTYPE html>
<html lang=“en”>
<head>
    <link rel=‘stylesheet’ type="text/css" href="${pageContext.request.contextPath}/images/notprotected.css"></link>
</head>
<body>
<div id=“maindiv” class=“main-container”>
    <div class=“top-container”>
        <div class=“flex-top”>
            <img id=“userProfileImg” class=“user-profile” src="${pageContext.request.contextPath}/images/anonymous.svg"/>
        </div>
     
        <div class=“flex-top”>
            <h1>Hi <span id=“userNameSpan”></span>,</h1>
            <h1>you are on the right track   
            <p>Access token is: ${access_token}</p>
            <p>Id Token is: ${id_token}</p>
            
            </h1>
            <div class=“flex-top”>

                <input type="button" value="View token" onclick="window.location='token.jsp'" >                              
            </div>
        </div>
    </div>
    <div class=“bottom-container”>
        <div class=“flex-bottom”>
            <h2>Next steps:<br/>Test that everything is working and customize the UI from the console.</h2>
        </div>
    </div>
</div>


</body>
</html>