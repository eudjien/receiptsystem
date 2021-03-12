<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.clevertec.checksystem.core.entity.receipt.Receipt" %>
<%@ page import="ru.clevertec.checksystem.webuiservlet.Constants.*" %>
<jsp:useBean id="receipts" scope="request" type="java.util.Collection"/>
<jsp:useBean id="source" scope="request" type="java.lang.String"/>

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

    <% if (source.equals(Sources.FILE) && request.getSession().getAttribute(Sessions.RECEIPTS_SESSION) == null) { %>
    <jsp:include page="../partial/file-not-loaded.jsp"/>
    <% } else { %>
    <div class="row g-3">
        <% for (Object checkObj : receipts) { %>
        <%
            Receipt receipt = (Receipt) checkObj;
            request.setAttribute("receipt", receipt);
        %>
        <div class="col-12 col-lg-6 col-xl-4">
            <jsp:include page="../partial/receipt.jsp"/>
        </div>
        <% } %>
    </div>
    <% } %>
</div>

<script src="<%=request.getContextPath()%>/static/script.js"></script>
<script src="<%=request.getContextPath()%>/static/lib/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
