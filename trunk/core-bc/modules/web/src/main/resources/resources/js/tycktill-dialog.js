function initDialog(dialogDivId, dialogContentId, actionId, url, args, title) {
    $(dialogDivId).dialog({
        modal: true,
        autoOpen: false,
        height: 600,
        width: 600,
        draggable: true,
        resizeable: true,
        show: 'slide',
        hide: {effect: 'fade', duration: 1500},
        title: 'Tyck till'
    });
    $(actionId).click(
            function() {
                openDialogAction(dialogDivId, dialogContentId, url, args, title);
                return false;
            });
}

function openDialogAction(dialogDivId, dialogContentId, url, args, title) {
    $(dialogDivId).dialog({title: title});
    $(dialogDivId).dialog("open");
    $(dialogContentId).load(url, args, function() {
        showInDialogInit();
        initForm();
    });
    return false;
}

function initIFrameDialog(dialogDivId, iFrameContentId, actionId, url, args, title) {
    $(dialogDivId).dialog({
        modal: true,
        autoOpen: false,
        height: 600,
        width: 600,
        draggable: true,
        resizeable: true,
        show: 'slide',
        hide: {effect: 'fade', duration: 1500},
        title: 'Tyck till'
    });
    $(actionId).click(
            function() {
                openIFrameDialogAction(dialogDivId, iFrameContentId, url, args, title);
                return false;
            });
}

function openIFrameDialogAction(dialogDivId, iFrameContentId, url, args, title) {
    $(dialogDivId).dialog({title: title});
    $(dialogDivId).dialog("open");
    $(iFrameContentId).attr('src', url+'?'+args);
    return false;
}
