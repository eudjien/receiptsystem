<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head lang="ru">
    <meta charset="UTF-8">
    <title>Checks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style/style.css">
</head>
<body>
<div class="header">
    <div class="header__type">
        <fieldset style="padding: 16px 9px;border: 1px solid #595b63;">
            <legend class="w-100">Format:</legend>
            <div class="form-group mb-0">
                <label class="mb-0">
                    <select class="form-control form-control-sm" id="formatSelect" name="format">
                        <option value="html" selected>html</option>
                        <option value="text">text</option>
                    </select>
                </label>
            </div>
        </fieldset>
    </div>
    <div class="header__download">
        <form class="mb-0" id="overallDownloadForm" method="get" action="<%=request.getContextPath()%>/api/checks/">
            <input name="download" value="true" type="hidden">
            <fieldset style="padding: 16px 9px;border: 1px solid #595b63;">
                <legend>Download all selected:</legend>

                <div class="d-flex align-items-center">

                    <div class="form-check form-check-inline mr-3">
                        <label class="mb-0">
                            <input class="form-check-input" type="checkbox" id="overallDownloadCheck">
                        </label>
                    </div>

                    <div class="btn-group">
                        <button class="btn btn-sm btn-light dropdown-toggle" type="submit" id="overallDownloadButton"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span>Download</span>
                            <span>(<span id="selectedCount">0</span>)</span>
                        </button>
                        <div class="dropdown-menu">
                            <h6 class="dropdown-header">Print</h6>
                            <button data-name="download" data-dlType="print"
                                    data-dlFormat="html" class="dropdown-item">- HTML
                            </button>
                            <button data-name="download" data-dlType="print"
                                    data-dlFormat="text" class="dropdown-item">- Text
                            </button>
                            <button data-name="download" data-dlType="print"
                                    data-dlFormat="pdf" class="dropdown-item">- PDF
                            </button>
                            <div class="dropdown-divider"></div>
                            <h6 class="dropdown-header">Serialize</h6>
                            <button data-name="download" data-dlType="serialize"
                                    data-dlFormat="json" class="dropdown-item">- JSON
                            </button>
                            <button data-name="download" data-dlType="serialize"
                                    data-dlFormat="xml" class="dropdown-item">- XML
                            </button>
                        </div>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<div class="content" id="content"></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/popper.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<%@include file="script.jsp" %>
</body>
</html>
