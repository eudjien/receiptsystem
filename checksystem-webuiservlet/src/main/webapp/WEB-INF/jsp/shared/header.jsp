<%@ page import="ru.clevertec.checksystem.webuiservlet.Constants.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="authentication" scope="session" type="ru.clevertec.checksystem.webuiservlet.Authentication"/>

<nav class="navbar navbar-expand navbar-dark header shadow shadow-sm mb-4">
    <div class="container">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <a class="navbar-brand me-auto" href="#">checksystem</a>

            <% if (authentication.isAuthenticated()) { %>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%><%=UrlPatterns.LOGOUT_PATTERN%>">Выйти</a>
                </li>
            </ul>
            <% } %>
        </div>
    </div>
</nav>
