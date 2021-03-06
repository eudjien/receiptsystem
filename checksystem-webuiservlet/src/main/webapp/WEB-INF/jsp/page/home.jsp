<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.clevertec.checksystem.core.entity.check.Check" %>
<jsp:useBean id="checks" scope="request" type="java.util.Collection"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="contextPath" content="<%=request.getContextPath()%>">
    <meta name="source" content="<%=request.getAttribute("source")%>">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/style.css">
    <title>Чеки</title>
</head>
<body>

<jsp:include page="../shared/header.jsp"/>

<div class="container mb-5">

    <jsp:include page="../partial/toolbar.jsp"/>

    <div class="row g-3">

        <% for (Object checkObj : checks) { %>
        <%
            Check check = (Check) checkObj;
            request.setAttribute("check", check);
        %>
        <div class="col-12 col-lg-6 col-xl-4">
            <jsp:include page="../partial/check.jsp"/>
        </div>
        <% } %>

    </div>
</div>

<script src="<%=request.getContextPath()%>/static/script.js"></script>
<script src="<%=request.getContextPath()%>/static/lib/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
