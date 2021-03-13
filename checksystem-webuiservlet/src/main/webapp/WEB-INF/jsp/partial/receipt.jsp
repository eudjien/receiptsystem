<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.clevertec.checksystem.core.entity.receipt.ReceiptItem" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="ru.clevertec.checksystem.core.entity.receipt.ReceiptItem" %>
<jsp:useBean id="receipt" scope="request" type="ru.clevertec.checksystem.core.entity.receipt.Receipt"/>

<%
    DateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
    DateFormat timeFormatter = new SimpleDateFormat("hh:mm");
%>

<div class="card receipt-card border-0 shadow">
    <div class="card-body">
        <h6 class="card-title text-center mb-3">
            <%=receipt.getName()%>
        </h6>
        <div class="card-subtitle fw-light fs-6 text-muted text-center mb-3">
            <p>
                <%=receipt.getDescription()%><br>
                <%=receipt.getAddress()%><br>
                <%=receipt.getPhoneNumber()%>
            </p>
        </div>
        <div class="card-subtitle fw-bold text-muted mb-3">
            <div class="row g-4">
                <div class="col">
                    КАССИР: <%=receipt.getCashier()%>
                </div>
                <div class="col-auto">
                    <div>ДАТА: <%= dateFormatter.format(receipt.getDate()) %>
                    </div>
                    <div>ВРЕМЯ: <%= timeFormatter.format(receipt.getDate()) %>
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

                <% for (Object receiptItemObj : receipt.getReceiptItems()) { %>

                <%
                    ReceiptItem receiptItem = (ReceiptItem) receiptItemObj;
                    request.setAttribute("receiptItem", receiptItem);
                %>
                <tr>
                    <td class="text-center">
                        <strong class="text-muted"><%=receiptItem.getQuantity()%>
                        </strong>
                    </td>
                    <td>
                        <%=receiptItem.getProduct().getName()%>
                    </td>
                    <td class="text-end">
                        <strong class="text-muted">
                            $<%=receiptItem.getProduct().getPrice()%>
                        </strong>
                    </td>
                    <td class="text-end">
                        <%if (receiptItem.discountsAmount().compareTo(BigDecimal.ZERO) != 0) { %>
                        <span class="badge bg-secondary text-white">
                        -$<%=receiptItem.discountsAmount()%>
                    </span>
                        <% } %>
                    </td>
                    <td class="text-end">
                        <strong class="text-success">
                            $<%=receiptItem.totalAmount()%>
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
            <strong class="text-success">$<%=receipt.subTotalAmount()%>
            </strong>
        </div>
        <div class="d-flex justify-content-between">
            <b class="text-muted">СКИДКИ:</b>
            <strong class="text-success">$<%=receipt.discountsAmount()%>
            </strong>
        </div>
        <div class="d-flex justify-content-between">
            <b class="text-muted">ИТОГ:</b>
            <strong class="text-success">$<%=receipt.totalAmount()%>
            </strong>
        </div>
    </div>
    <div class="card-footer">
        <div class="form-receipt d-flex justify-content-end">
            <label class="form-receipt-label text-muted" for="checkInput<%=receipt.getId()%>">
                ЧЕК #<%=receipt.getId()%>
            </label>
            <input class="form-receipt-input ms-2" type="checkbox"
                   name="checkId" value="<%=receipt.getId()%>" id="checkInput<%=receipt.getId()%>">
        </div>
    </div>
</div>
