<%@ page import="ru.clevertec.checksystem.webuiservlet.Constants.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form class="d-flex m-0" method="post" enctype="multipart/form-data"
      action="<%=request.getContextPath()%>/upload">
    <input id="chooseFileInput" name="checksFile" class="form-control form-control-sm" type="file"
           accept="application/json, application/xml" aria-label="chooseFileInput">
    <input type="hidden" name="returnUrl" value="<%=request.getContextPath() + UrlPatterns.ROOT_PATTERN%>?source=file">
    <button id="fileUploadButton" type="submit" class="btn btn-sm btn-outline-secondary ms-2">Отправить</button>
</form>
