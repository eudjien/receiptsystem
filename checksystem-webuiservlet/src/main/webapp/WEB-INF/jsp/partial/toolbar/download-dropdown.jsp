<%@ page import="ru.clevertec.checksystem.core.io.FormatType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <button id="downloadButton"
            class="btn btn-sm small text-muted text-decoration-none dropdown-toggle"
            data-bs-toggle="dropdown" role="button" aria-expanded="false">
        Сохранить
    </button>
    <ul class="dropdown-menu">
        <li><h6 class="dropdown-header">Печать</h6></li>
        <li>
            <button class="dropdown-item" name="download" value="<%=FormatType.PDF_PRINT.toString()%>">
                &nbsp;-&nbsp;PDF
            </button>
        </li>
        <li>
            <button class="dropdown-item" name="download" value="<%=FormatType.HTML_PRINT.toString()%>">
                &nbsp;-&nbsp;HTML
            </button>
        </li>
        <li>
            <button class="dropdown-item" name="download" value="<%=FormatType.TEXT_PRINT.toString()%>">
                &nbsp;-&nbsp;TEXT
            </button>
        </li>
        <li><h6 class="dropdown-header">Структура</h6></li>
        <li>
            <button class="dropdown-item" name="download" value="<%=FormatType.JSON_STRUCTURE.toString()%>">
                &nbsp;-&nbsp;JSON
            </button>
        </li>
        <li>
            <button class="dropdown-item" name="download" value="<%=FormatType.XML_STRUCTURE.toString()%>">
                &nbsp;-&nbsp;XML
            </button>
        </li>
    </ul>
</div>
