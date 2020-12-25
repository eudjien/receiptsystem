<%
    int[] queryIds = (int[]) request.getAttribute("ids");
%>

<script type="text/javascript">
    const formatSelect = document.querySelector('#formatSelect');
    let allSaveChecks = [];
    const selectedCountSpan = document.querySelector('#selectedCount');
    const overallDownloadCheck = document.querySelector('#overallDownloadCheck');
    const overallDownloadButton = document.querySelector('#overallDownloadButton');
    const contentContainer = document.querySelector('#content');

    let idsToDownload = [];

    formatSelect.addEventListener('change', function () {
        loadData(formatSelect.value);
    });

    overallDownloadCheck.addEventListener('change', ev => {
        const overallChecked = ev.target.checked;
        allSaveChecks.forEach(elem => elem.checked = overallChecked);
        const selectedChecks = getSelectedCheckElements(allSaveChecks);
        updateIdsToDownload(selectedChecks.map(a => a.getAttribute('data-checkId')));
        updateCheckedCount(getSelectedCheckElements(allSaveChecks).length);
        overallDownloadButton.disabled = selectedChecks.length < 1;
    });

    function updateCheckedCount(count) {
        selectedCountSpan.innerHTML = count;
    }

    function updateIdsToDownload(ids) {
        idsToDownload = Array.isArray(ids) ? ids : [];
    }

    function updateOverallCheck(selectedCount, allCount) {
        if (selectedCount === 0) {
            overallDownloadCheck.checked = false;
            overallDownloadCheck.indeterminate = false;
        } else if (selectedCount === allCount) {
            overallDownloadCheck.checked = true;
            overallDownloadCheck.indeterminate = false;
        } else {
            overallDownloadCheck.checked = false;
            overallDownloadCheck.indeterminate = true;
        }
    }

    function getSelectedCheckElements() {
        return Array.from(allSaveChecks).filter(elem => elem.checked) || [];
    }

    function loadData(format) {

        const ids = [];
        <% if (queryIds != null) {%>
        <% for (int id : queryIds) { %>
        ids.push('<%=id%>');
        <% } %>
        <% } %>

        const params = {
            id: ids,
            type: 'print',
            format: format + '-json',
            source: '<%=request.getAttribute("source")%>',
        }

        console.log(params);

        $.ajax({
            url: '<%= request.getContextPath() %>/api/checks',
            method: "get",
            dataType: 'json',
            data: params,
            traditional: true
        })
            .done(function (data) {
                console.log(data);
                if (Array.isArray(data)) {
                    contentContainer.innerHTML = null;
                    for (const item of data) {
                        contentContainer.appendChild(createCheckItem(item, format));
                    }
                    allSaveChecks = document.querySelectorAll('input[name=saveFileCheck]');
                    for (const check of allSaveChecks) {
                        if (idsToDownload.includes(check.getAttribute('data-checkId'))) {
                            check.checked = true;
                        }
                    }
                }
            });
    }

    function createCheckItem(data, format) {
        const checkItem = document.createElement('div');
        checkItem.classList.add('check-item');

        const checkItemBody = createCheckItemBody(format, data.data);
        const checkItemFooter = createCheckItemFooter(data.id);

        checkItem.append(checkItemBody);
        checkItem.append(checkItemFooter);
        return checkItem;
    }

    function createCheckItemBody(format, data) {
        const body = document.createElement(format === 'text' ? 'pre' : 'div');
        body.classList.add('check-item__body', 'check-item__body--' + format);
        body.insertAdjacentHTML('afterbegin', data);
        return body;
    }

    function createCheckItemFooter(checkId) {
        const footer = document.createElement('div');
        footer.classList.add('check-item__footer');

        footer.innerHTML = '<div class="d-flex justify-content-between align-items-center">' +
            '        <div>' + checkId + '</div>' +
            '        <div>' +
            '           <label class="mb-0">' +
            '                   <input name="saveFileCheck" id="downloadFileCheck-' + checkId + '" type="checkbox" ' +
            (idsToDownload.includes(checkId) ? 'checked' : '') +
            '                    data-checkId="' + checkId + '">' +
            '           </label>' +
            '        </div>' +
            '    </div>';

        const form = footer.querySelector('form[name=download]');
        const buttons = footer.querySelectorAll('button[data-name=download]');
        const check = footer.querySelector('#downloadFileCheck-' + checkId);

        buttons.forEach(function (button) {
            button.addEventListener('click', function (ev) {
                ev.preventDefault();

                const type = button.getAttribute('data-dlType');
                const format = button.getAttribute('data-dlFormat');

                // find & set print type
                const typeHiddenInput = form.querySelector('input[name=type]');
                typeHiddenInput.value = type;

                // find format input
                let formatHiddenInput = form.querySelector('input[name=format]');
                if (!formatHiddenInput) { // create if not exist;
                    formatHiddenInput = document.createElement('input');
                    formatHiddenInput.type = 'hidden';
                    formatHiddenInput.name = 'format';
                }
                formatHiddenInput.value = format;
                form.append(formatHiddenInput);

                // del exists id inputs
                const idHiddenInputs = form.querySelectorAll('input[name=id]');
                idHiddenInputs.forEach(idInput => {
                    form.removeChild(idInput);
                });

                // create id inputs
                idsToDownload.forEach(id => {
                    const idHiddenInput = document.createElement('input');
                    idHiddenInput.type = 'hidden';
                    idHiddenInput.name = 'id';
                    idHiddenInput.value = id;
                    form.append(idHiddenInput);
                });

                form.submit();
            });
        });

        check.addEventListener('change', function (ev) {
            const selectedChecks = getSelectedCheckElements(allSaveChecks);
            updateIdsToDownload(selectedChecks.map(a => a.getAttribute('data-checkId')));
            updateCheckedCount(selectedChecks.length);
            updateOverallCheck(selectedChecks.length, allSaveChecks.length);
            overallDownloadButton.disabled = selectedChecks.length < 1;
        });

        return footer;
    }

    const overallDownloadForm = document.querySelector('#overallDownloadForm');
    const downloadButtons = overallDownloadForm.querySelectorAll('button[data-name=download]');
    downloadButtons.forEach(function (button) {
        button.addEventListener('click', function (ev) {
            ev.preventDefault();

            const type = button.getAttribute('data-dlType');
            const format = button.getAttribute('data-dlFormat');
            const source = '<%=request.getAttribute("source")%>';

            // find & set print type
            let typeHiddenInput = overallDownloadForm.querySelector('input[name=type]');
            if (!typeHiddenInput) { // create if not exist;
                typeHiddenInput = document.createElement('input');
                typeHiddenInput.type = 'hidden';
                typeHiddenInput.name = 'type';
                overallDownloadForm.append(typeHiddenInput);
            }
            typeHiddenInput.value = type;

            // find & set source
            let sourceHiddenInput = overallDownloadForm.querySelector('input[name=source]');
            if (!sourceHiddenInput) { // create if not exist;
                sourceHiddenInput = document.createElement('input');
                sourceHiddenInput.type = 'hidden';
                sourceHiddenInput.name = 'source';
                overallDownloadForm.append(sourceHiddenInput);
            }
            sourceHiddenInput.value = source;

            // find format input
            let formatHiddenInput = overallDownloadForm.querySelector('input[name=format]');
            if (!formatHiddenInput) { // create if not exist;
                formatHiddenInput = document.createElement('input');
                formatHiddenInput.type = 'hidden';
                formatHiddenInput.name = 'format';
                overallDownloadForm.append(formatHiddenInput);
            }
            formatHiddenInput.value = format;

            // del exists id inputs
            const idHiddenInputs = overallDownloadForm.querySelectorAll('input[name=id]');
            idHiddenInputs.forEach(idInput => {
                overallDownloadForm.removeChild(idInput);
            });

            // create id inputs
            idsToDownload.forEach(id => {
                const idHiddenInput = document.createElement('input');
                idHiddenInput.type = 'hidden';
                idHiddenInput.name = 'id';
                idHiddenInput.value = id;
                overallDownloadForm.append(idHiddenInput);
            });

            overallDownloadForm.submit();
        });
    });

    (function init() {
        selectedCountSpan.innerHTML = '0';
        overallDownloadButton.disabled = true;
        loadData('html');
    })();

</script>