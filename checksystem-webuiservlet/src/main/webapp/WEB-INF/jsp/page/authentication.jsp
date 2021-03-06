<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="incorrectAnswer" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="question" scope="request" type="java.lang.String"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/lib/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/style.css">
    <title>Аунтификация</title>
</head>
<body>

<jsp:include page="../shared/header.jsp"/>

<div class="container mb-5">

    <div class="card border-0 shadow" style="width: 35rem;">
        <div class="card-body">
            <h5 class="card-title">Аунтификация</h5>
            <h6 class="card-subtitle mb-4 text-muted">Ответь на вопрос...</h6>

            <form class="px-2" method="post" action="<%=request.getContextPath()%>/authentication">
                <div class="mb-3 row">
                    <div class="col-sm-2">Вопрос</div>
                    <div class="col-sm-10"><p><%= question %>
                    </p></div>
                </div>
                <div class="mb-3 row">
                    <label for="answer" class="col-sm-2 col-form-label">Ответ</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="answer" name="answer">
                    </div>
                </div>

                <%if (incorrectAnswer) { %>
                <div class="text-danger">НЕ ПРАВИЛЬНО!!!</div>
                <% } %>

                <div class="d-flex justify-content-end">
                    <button type="submit" class="btn btn-primary">Отправить</button>
                </div>
            </form>

        </div>
    </div>

</div>

<script src="<%=request.getContextPath()%>/static/lib/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
