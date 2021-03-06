<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.clevertec.checksystem.core.entity.check.CheckItem" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<jsp:useBean id="check" scope="request" type="ru.clevertec.checksystem.core.entity.check.Check"/>

<%
    DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    DateFormat timeFormatter = new SimpleDateFormat("hh:mm");
%>

<div class="card check-card border-0 shadow">
    <div class="card-body">
        <h6 class="card-title text-center mb-3">
            <%=check.getName()%>
        </h6>
        <div class="card-subtitle fw-light fs-6 text-muted text-center mb-3">
            <p>
                <%=check.getDescription()%><br>
                <%=check.getAddress()%><br>
                <%=check.getPhoneNumber()%>
            </p>
        </div>
        <div class="card-subtitle fw-bold text-muted mb-3">
            <div class="row g-4">
                <div class="col">
                    КАССИР: <%=check.getCashier()%>
                </div>
                <div class="col-auto">
                    <div>ДАТА: <%= dateFormatter.format(check.getDate()) %>
                    </div>
                    <div>ВРЕМЯ: <%= timeFormatter.format(check.getDate()) %>
                    </div>
                </div>
            </div>
        </div>
        <div class="overflow-auto">
            <table class="table table-striped">
                <thead>
                <tr class="text-muted">
                    <th scope="col" class="border-bottom">КОЛИЧЕСТВО</th>
                    <th scope="col" class="border-bottom">НАЗВАНИЕ</th>
                    <th scope="col" class="border-bottom text-end">ЦЕНА</th>
                    <th scope="col" class="border-bottom"></th>
                    <th scope="col" class="border-bottom text-end">ВСЕГО</th>
                </tr>
                </thead>
                <tbody>

                <% for (Object checkItemObj : check.getCheckItems()) { %>

                <%
                    CheckItem checkItem = (CheckItem) checkItemObj;
                    request.setAttribute("checkItem", checkItem);
                %>
                <tr>
                    <td class="text-center">
                        <strong class="text-muted"><%=checkItem.getQuantity()%>
                        </strong>
                    </td>
                    <td>
                        <%=checkItem.getProduct().getName()%>
                    </td>
                    <td class="text-end">
                        <strong class="text-muted">
                            $<%=checkItem.getProduct().getPrice()%>
                        </strong>
                    </td>
                    <td class="text-end">
                        <%if (checkItem.discountsAmount().compareTo(BigDecimal.ZERO) != 0) { %>
                        <span class="badge bg-secondary text-white">
                        -$<%=checkItem.discountsAmount()%>
                    </span>
                        <% } %>
                    </td>
                    <td class="text-end">
                        <strong class="text-success">
                            $<%=checkItem.totalAmount()%>
                        </strong>
                    </td>
                </tr>

                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <div class="card-footer">
        <div class="d-flex justify-content-between">
            <b class="text-muted">ПРОМЕЖУТОЧНЫЙ ИТОГ:</b>
            <strong class="text-success">$<%=check.subTotalAmount()%>
            </strong>
        </div>
        <div class="d-flex justify-content-between">
            <b class="text-muted">СКИДКИ:</b>
            <strong class="text-success">$<%=check.discountsAmount()%>
            </strong>
        </div>
        <div class="d-flex justify-content-between">
            <b class="text-muted">ИТОГ:</b>
            <strong class="text-success">$<%=check.totalAmount()%>
            </strong>
        </div>
    </div>
    <div class="card-footer">
        <div class="form-check d-flex justify-content-end">
            <label class="form-check-label text-muted" for="checkInput<%=check.getId()%>">
                CHECK #<%=check.getId()%>
            </label>
            <input class="form-check-input ms-2" type="checkbox"
                   name="checkId" value="<%=check.getId()%>" id="checkInput<%=check.getId()%>">
        </div>
    </div>
</div>
