<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="authenticated" scope="session" type="java.lang.Boolean"/>

<nav class="navbar navbar-expand navbar-dark header shadow shadow-sm mb-4">
    <div class="container">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <a class="navbar-brand me-auto" href="#">checksystem</a>

            <% if (authenticated) { %>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="<%=request.getContextPath()%>/logout">Выйти</a>
                </li>
            </ul>
            <% } %>
        </div>
    </div>
</nav>
