"use strict"

const contextPath = document.querySelector('meta[name=contextPath]').content;
const source = document.querySelector('meta[name=source]').content;

const overallInput = document.querySelector('#overallInput');
const checkInputs = document.querySelectorAll('input[name=checkId]');
const downloadButton = document.querySelector('#downloadButton');
const checkedCountElem = document.querySelector('#checkedCount');
const downloadButtons = document.querySelectorAll('button[name=download]');
const sourceSelect = document.querySelector('#sourceSelect');
const chooseFileInput = document.querySelector('#chooseFileInput');
const fileUploadButton = document.querySelector('#fileUploadButton');
const mailButton = document.querySelector('#mailButton');
const sendMailButton = document.querySelector('#sendMailButton');
const emailModal = document.querySelector('#emailModal');

let checkedCount = 0;

initialSetUp();

setSourceChangeListener();
setDownloadClickListener();
setOverallChangeListener();
setSingleChangeListener();
setSendMailClickListener();
setFileChangeListener();

function setDownloadClickListener() {

    downloadButtons.forEach(downloadButton => {

        downloadButton.addEventListener('click', evt => {

            const type = evt.target.getAttribute('data-type');
            const format = evt.target.getAttribute('data-format');

            const form = createDownloadForm(type, format, source);
            document.body.appendChild(form);
            form.submit();
            document.body.removeChild(form);
        });
    });
}

function setOverallChangeListener() {
    overallInput.addEventListener('change', evt => {
        evt.preventDefault();
        const checked = checkedCount < checkInputs.length;
        checkInputs.forEach(checkInput => updateSingle(checkInput, checked));
        updateActionButtons();
    });
}

function setSingleChangeListener() {
    checkInputs.forEach(checkInput => {
        checkInput.addEventListener('change', evt => {
            updateCount(evt.target.checked);
            updateOverall();
            updateActionButtons();
        });
    });
}

function setSourceChangeListener() {
    sourceSelect.addEventListener('change', evt => {
        window.location.href = contextPath + '?source=' + evt.target.value;
    });
}

function setFileChangeListener() {
    if (fileUploadButton) {
        chooseFileInput.addEventListener('change', evt => {
            fileUploadButton.disabled = !evt.target.value;
        });
    }
}

function setSendMailClickListener() {

    const formatSelect = document.querySelector('#formatSelect');

    sendMailButton.addEventListener('click', evt => {

        resetEmailModalStatus();

        const selectedOption = formatSelect.options[formatSelect.selectedIndex];

        const type = selectedOption.getAttribute('data-type');
        const format = selectedOption.getAttribute('data-format');
        const subject = document.querySelector('#mailSubject').value;
        const address = document.querySelector('#mailAddress').value;

        const queryString = contextPath + "/mail" +
            "?source=" + source +
            "&type=" + type +
            "&format=" + format +
            "&subject=" + subject +
            "&address=" + address +
            "&" + getIdQueryString();

        console.log("Sending mail...");
        console.log(queryString);

        disableEmailModal(true);

        const process = emailModal.querySelector('#emailSentProcess');
        process.style.display = '';
        process.innerHTML = 'Отправка письма...'

        fetch(queryString, {method: "get"})
            .then(response => {
                if (!response.ok) {
                    console.log("Email send error");
                    console.log(response);
                    resetEmailModalStatus();
                    const success = emailModal.querySelector('#emailSentError');
                    success.style.display = '';
                    success.innerHTML = 'Ошибка отправки письма'
                } else {
                    resetEmailModalStatus();
                    const success = emailModal.querySelector('#emailSentSuccess');
                    success.style.display = '';
                    success.innerHTML = 'Письмо успешно отправлено'
                    console.log("Email has been sent");
                }
            })
            .finally(() => {
                disableEmailModal(false);
            });
    });
}

function updateOverall() {
    if (checkedCount === 0) {
        overallInput.checked = false;
        overallInput.indeterminate = false;
    } else if (checkedCount === checkInputs.length) {
        overallInput.checked = true;
        overallInput.indeterminate = false;
    } else {
        overallInput.checked = false;
        overallInput.indeterminate = true;
    }
}

function updateSingle(checkInput, checked) {
    if (checkInput.checked !== checked)
        updateCount(checked);
    checkInput.checked = checked;
}

function updateCount(checked) {
    if (checked) checkedCount++;
    else checkedCount--;
    checkedCountElem.innerHTML = checkedCount + "";
}

function updateActionButtons() {
    downloadButton.disabled = checkedCount === 0;
    mailButton.disabled = checkedCount === 0;
}

function initialSetUp() {
    overallInput.disabled = checkInputs.length === 0;
    checkedCountElem.innerHTML = checkedCount + '';
    downloadButton.disabled = true;
    mailButton.disabled = true;
    if (fileUploadButton)
        fileUploadButton.disabled = true;
    sourceSelect.querySelectorAll('option').forEach(option => {
        if (option.value === source)
            option.selected = true;
    });
}

function createDownloadForm(type, format, source) {

    const form = document.createElement('form');
    form.style.display = 'none';
    form.method = 'get';
    form.action = contextPath + '/download';

    document.createElement('input')

    form.appendChild(createHiddenInput('source', source));
    form.appendChild(createHiddenInput('type', type));
    form.appendChild(createHiddenInput('format', format));

    collectCheckedIds().forEach(id => {
        form.appendChild(createHiddenInput('id', id));
    });

    return form;
}

function createHiddenInput(name, value) {
    const input = document.createElement('input');
    input.type = 'hidden';
    input.name = name;
    input.value = value;
    return input;
}

function collectCheckedIds() {
    const ids = [];
    checkInputs.forEach(checkInput => {
        if (checkInput.checked)
            ids.push(checkInput.value);
    });
    return ids;
}

function getIdQueryString() {
    return collectCheckedIds().map(id => 'id=' + id).reduce((a, b) => a + '&' + b);
}

function disableEmailModal(lock) {
    emailModal.querySelectorAll('input, select, button').forEach(input => {
        input.disabled = lock;
    });
}

function resetEmailModalStatus() {
    const success = emailModal.querySelector('#emailSentSuccess');
    const error = emailModal.querySelector('#emailSentError');
    const process = emailModal.querySelector('#emailSentProcess');
    success.style.display = 'none';
    error.style.display = 'none';
    process.style.display = 'none';
}
