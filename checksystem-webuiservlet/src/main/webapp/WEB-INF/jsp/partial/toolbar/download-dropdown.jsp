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
            <button class="dropdown-item" name="download" value="" data-type="print" data-format="pdf"> -
                PDF
            </button>
        </li>
        <li>
            <button class="dropdown-item" name="download" value="" data-type="print" data-format="html"> -
                HTML
            </button>
        </li>
        <li>
            <button class="dropdown-item" name="download" value="" data-type="print" data-format="text"> -
                TEXT
            </button>
        </li>
        <li><h6 class="dropdown-header">Структура</h6></li>
        <li>
            <button class="dropdown-item" name="download" value="" data-type="serialize" data-format="json">
                - JSON
            </button>
        </li>
        <li>
            <button class="dropdown-item" name="download" value="" data-type="serialize" data-format="xml">
                - XML
            </button>
        </li>
    </ul>
</div>
