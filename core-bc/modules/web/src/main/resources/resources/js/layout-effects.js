function initForm() {
    // Init
    jQuery('.subselect').hide();
    jQuery('.contact-mail').hide();
    jQuery('.contact-phone').hide();
    jQuery('.contact-method-input').hide();

    if (jQuery('#contentCase').is(':checked')) {
        showContentCase();
    }
    if (jQuery('#functionCase').is(':checked')) {
        showFunctionCase();
    }
    if (jQuery('#customCase').is(':checked')) {
        showCustomCase();
    }
    if (jQuery('#otherCase').is(':checked')) {
        showOtherCase();
    }

    hideShowContactUserOptions(jQuery('#shouldContactUser').is(':checked'));
    hideShowContactMethod(jQuery("input[name='userContact.contactOption']:checked").val());
    hideShowAttachmentDetail(jQuery('#attachScreenDump').is(':checked'));

    addEventsToCaseSubject();
    addEventsShouldContactUser();
    addEventToContactOption();
    addEventToAttachments();

    addEventToInput();
}

function addEventToInput() {
    $(".defaultRubrik").focus(function(srcc) {
        if ($(this).val() == $(this)[0].title) {
            $(this).removeClass("defaultRubrikActive");
            $(this).val("");
        }
    });

    $(".defaultRubrik").blur(function() {
        if ($(this).val() == "") {
            $(this).addClass("defaultRubrikActive");
            $(this).val($(this)[0].title);
        }
    });

    $(".defaultMessage").focus(function(srcc) {
        if ($(this).val() == $(this)[0].title) {
            $(this).removeClass("defaultMessageActive");
            $(this).val("");
        }
    });

    $(".defaultMessage").blur(function() {
        if ($(this).val() == "") {
            $(this).addClass("defaultMessageActive");
            $(this).val($(this)[0].title);
        }
    });

    $(".defaultRubrik").blur();
    $(".defaultMessage").blur();
    $(".msglabel").hide();
}

function addEventsToCaseSubject() {
    // Actions
    jQuery('#contentCase').click(function() {
        showContentCase()
    });
    jQuery('#functionCase').click(function() {
        showFunctionCase()
    });
    jQuery('#customCase').click(function() {
        showCustomCase();
    });
    jQuery('#otherCase').click(function() {
        showOtherCase();
    });
}

function showContentCase() {
    jQuery('div#contentSubCase').show('fast');
    jQuery('div#functionSubCase').hide('fast');
    jQuery('div#customSubCase').hide('fast');
}

function showFunctionCase() {
    jQuery('div#contentSubCase').hide('fast');
    jQuery('div#functionSubCase').show('fast');
    jQuery('div#customSubCase').hide('fast');
}

function showCustomCase() {
    jQuery('div#contentSubCase').hide('fast');
    jQuery('div#functionSubCase').hide('fast');
    jQuery('div#customSubCase').show('fast');
}

function showOtherCase() {
    jQuery('div#contentSubCase').hide('fast');
    jQuery('div#functionSubCase').hide('fast');
    jQuery('div#customSubCase').hide('fast');
}

function addEventsShouldContactUser() {
    // Actions
    jQuery('#shouldContactUser').change(function() {
        hideShowContactUserOptions(this.checked);
    });
}

function hideShowContactUserOptions(value) {
    if (value) {
        jQuery('div#contactInfo').show('fast');
    } else {
        jQuery('div#contactInfo').hide('fast');
    }
}

function addEventToContactOption() {
    // Actions
    jQuery("input[name='userContact.contactOption']").change(function() {
        hideShowContactMethod(this.value);
    });
}

function hideShowContactMethod(value) {
    switch (value) {
        case 'email':
            jQuery('.contact-mail').show();
            jQuery('.contact-method-input').show();
            jQuery('.contact-phone').hide();
            break;
        case 'telephone':
            jQuery('.contact-mail').hide();
            jQuery('.contact-method-input').show();
            jQuery('.contact-phone').show();
            break;
        default:
            jQuery('.contact-mail').hide('fast');
            jQuery('.contact-phone').hide('fast');
            jQuery('.contact-method-input').hide('fast');
    }
}

function addEventToAttachments() {
    // Actions
    jQuery('#attachScreenDump').change(function() {
        hideShowAttachmentDetail(this.checked);
    });
}

function hideShowAttachmentDetail(value) {
    if (value) {
        jQuery('div#attachmentDetail').show('fast');
    } else {
        jQuery('div#attachmentDetail').hide('fast');
    }
}

function showhideBackendInit() {
    try {
        var cb = $('.defaultBackendActive');
        showhide(cb[0], 'defaultBackend');

        var cbSub = $('.backendActive');
        for (var i = 0; i < cbSub.length; i++) {
            showhide(cbSub[i], 'backend' + i);
        }
    } catch (e) {
        alert(e.message);
    }
}

function showhide(cb, class) {
    if (cb.checked) {
        jQuery('.' + class).show('fast');
    } else {
        jQuery('.' + class).hide('fast');
    }
}

function loadjscssfile(filename, filetype) {
    if (filetype == "js") { //if filename is a external JavaScript file
        var fileref = document.createElement('script')
        fileref.setAttribute("type", "text/javascript")
        fileref.setAttribute("src", filename)
    }
    else if (filetype == "css") { //if filename is an external CSS file
        var fileref = document.createElement("link")
        fileref.setAttribute("rel", "stylesheet")
        fileref.setAttribute("type", "text/css")
        fileref.setAttribute("href", filename)
    }
    if (typeof fileref != "undefined")
        document.getElementsByTagName("head")[0].appendChild(fileref)
}
