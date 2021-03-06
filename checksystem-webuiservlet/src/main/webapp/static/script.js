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

let checkedCount = 0;

initialSetUp();
setSourceChangeListener();
setDownloadClickListener();
setOverallChangeListener();
setSingleChangeListener();
if (fileUploadButton)
    setFileChangeListener();

function setDownloadClickListener() {

    downloadButtons.forEach(downloadButton => {

        downloadButton.addEventListener('click', evt => {

            const type = evt.target.getAttribute('data-type');
            const format = evt.target.getAttribute('data-format');

            const form = createDownloadForm(type, format);
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
        updateDownloadButton();
    });
}

function setSingleChangeListener() {
    checkInputs.forEach(checkInput => {
        checkInput.addEventListener('change', evt => {
            updateCount(evt.target.checked);
            updateOverall();
            updateDownloadButton();
        });
    });
}

function setSourceChangeListener() {
    sourceSelect.addEventListener('change', evt => {
        window.location.href = contextPath + '?source=' + evt.target.value;
    });
}

function setFileChangeListener() {
    chooseFileInput.addEventListener('change', evt => {
        fileUploadButton.disabled = false;
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

function updateDownloadButton() {
    downloadButton.disabled = checkedCount === 0;
}

function initialSetUp() {
    overallInput.disabled = checkInputs.length === 0;
    checkedCountElem.innerHTML = checkedCount + '';
    downloadButton.disabled = true;
    if (fileUploadButton)
        fileUploadButton.disabled = true;
    sourceSelect.querySelectorAll('option').forEach(option => {
        if (option.value === source)
            option.selected = true;
    });
}

function createDownloadForm(type, format) {

    const form = document.createElement('form');
    form.style.display = 'none';
    form.method = 'get';
    form.action = contextPath + '/download';

    document.createElement('input')

    form.appendChild(createHiddenInput('type', type));
    form.appendChild(createHiddenInput('format', format));

    const checkIdInputs = document.querySelectorAll('input[name=checkId]');

    checkIdInputs.forEach(checkIdInput => {
        if (checkIdInput.checked)
            form.appendChild(createHiddenInput('id', checkIdInput.value));
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
