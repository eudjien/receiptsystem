<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<button id="mailButton" type="button" class="btn btn-sm btn-outline-secondary" data-bs-toggle="modal" data-bs-target="#emailModal">
    Отпрвить Email
</button>
<div class="modal fade" id="emailModal" tabindex="-1" aria-labelledby="emailModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Отправка на Email</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label for="mailSubject" class="form-label">Заголовок</label>
                    <input name="text" type="text" class="form-control" id="mailSubject">
                </div>
                <div class="mb-3">
                    <label for="mailAddress" class="form-label">Email адрес</label>
                    <input name="email" type="email" class="form-control" id="mailAddress" placeholder="name@example.com">
                </div>
                <div class="mb-3">
                    <label for="formatSelect" class="form-label">Формат</label>
                    <select id="formatSelect" class="form-select" aria-label="formatSelect">
                        <optgroup label="Печать">
                            <option selected value="1" data-type="print" data-format="pdf">PDF</option>
                            <option value="2" data-type="print" data-format="text">TEXT</option>
                            <option value="3" data-type="print" data-format="html">HTML</option>
                        </optgroup>
                        <optgroup label="Структура">
                            <option value="4" data-type="serialize" data-format="json">JSON</option>
                            <option value="5" data-type="serialize" data-format="xml">XML</option>
                        </optgroup>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <div id="emailSentProcess" class="text-dark me-auto" style="display: none"></div>
                <div id="emailSentError" class="text-danger me-auto" style="display: none"></div>
                <div id="emailSentSuccess" class="text-success me-auto" style="display: none"></div>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                <button type="button" class="btn btn-primary" id="sendMailButton">Отправить</button>
            </div>
        </div>
    </div>
</div>
