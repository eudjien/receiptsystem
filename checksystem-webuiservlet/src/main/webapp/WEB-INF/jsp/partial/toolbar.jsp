<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="checks" scope="request" type="java.util.Collection"/>

<div class="check-toolbar text-muted bg-white px-4 py-2 rounded shadow mb-4">
    <div class="row gy-2 gx-4 d-flex align-items-center">
        <div class="col">
            <div class="row g-4 d-flex align-items-center flex-nowrap">
                <div class="col-auto border-end">
                    <jsp:include page="toolbar/overall-checkbox.jsp"/>
                </div>
                <div class="col-auto border-end">
                    <span>выбрано:</span> <span id="checkedCount"></span>
                </div>
                <div class="col col-md-auto d-flex justify-content-end">
                    <jsp:include page="toolbar/download-dropdown.jsp"/>
                </div>
            </div>
        </div>
        <% if (request.getParameter("source") != null && request.getParameter("source").equals("file")) { %>
        <div class="col-12 col-md border-end border-">
            <jsp:include page="toolbar/choose-file-form.jsp"/>
        </div>
        <% } %>
        <div class="col col-md-auto">
            <div class="row g-4 d-flex align-items-center flex-nowrap">
                <div class="col-auto border-end">
                    <span>Всего: <%=checks.size()%></span>
                </div>
                <div class="col d-flex justify-content-end">
                    <jsp:include page="toolbar/source-select.jsp"/>
                </div>
            </div>
        </div>
    </div>
</div>
