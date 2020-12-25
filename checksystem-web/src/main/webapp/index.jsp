<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Checks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
</head>
<body class="d-flex justify-content-center align-items-center" style="background-color: #efefef">

<div class="card shadow-sm" style="width: 320px">
    <div class="card-header">
        Load from:
    </div>
    <div class="card-body">
        <a href="<%=request.getContextPath()%>/checks?source=memory" class="btn btn-primary btn-block">
            Memory
        </a>
        <button class="btn btn-primary btn-block" type="button" data-toggle="collapse" data-target="#collapseFromFile"
                aria-expanded="false" aria-controls="collapseFromFile">
            File
        </button>
    </div>
    <div class="collapse" id="collapseFromFile">
        <div class="card-body">
            <form action="<%=request.getContextPath()%>/" method="post" enctype="multipart/form-data">
                <input type="hidden" name="source" value="file">
                <div class="custom-file mb-3">
                    <input type="file" name="file" class="custom-file-input" id="customFile"
                           accept="application/json, application/xml">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
                <button type="submit" class="btn btn-secondary btn-block">Submit</button>
            </form>

        </div>

    </div>

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.5.1.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/popper.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
    <script type="application/javascript">
        const customFile = document.querySelector('.custom-file');
        customFileInput = customFile.querySelector('.custom-file-input');
        customFileLabel = customFile.querySelector('.custom-file-label');
        customFileInput.addEventListener('change', function (e) {
            customFileLabel.innerText = customFileInput.files[0].name;
        });
    </script>
</body>
</html>
