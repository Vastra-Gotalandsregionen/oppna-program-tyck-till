/*
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 */

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
    $(dialogDivId).dialog({
        title: title,
        height: 600,
        width: 610});
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
    $(dialogDivId).dialog({
        title: title,
        height: 600,
        width: 610});
    $(dialogDivId).dialog("open");
    $(iFrameContentId).attr('src', url + '?' + args);
    return false;
}
