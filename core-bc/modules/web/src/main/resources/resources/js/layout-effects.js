jQuery(document).ready(function() {
    jQuery.fn.extend(
    {
        optionDisable:function() {
            var ths = jQuery(this);
            if (ths.attr('tagName').toLowerCase() == 'option') {
                ths.before(jQuery('<optgroup>&nbsp;</optgroup>').css({color:'#ccc',height:ths.height()}).attr({id:ths.attr('value'),label:ths.text()})).remove();
            }
            return ths;
        },
        optionEnable:function() {
            var ths = jQuery(this);
            var tag = ths.attr('tagName').toLowerCase();
            if (tag == 'option') {
                ths.removeAttr('disabled');
            }
            else if (tag == 'optgroup') {
                ths.before($('<option />').attr({value:ths.attr('id')}).text(ths.attr('label'))).remove();
            }
            return ths;
        }
    });

    initForm();
    addEventsToCaseSubject();
    addEventsShouldContactUser();
    addEventToContactOption();
    addEventToAttachments();
    //  addEventsToContractCard();
    //  addEventsToDeliveryOption();
    //  addEventsToTicketBookingType();
    //  addEventsToHotelBookingOptions();
    //  addEventsToHotelChoice();
    //
    //  jQuery(document).copyValue({fromSelector: '#outboundFrom', toSelector:'#inboundTo'});
    //  jQuery(document).copyValue({fromSelector: '#outboundTo', toSelector:'#inboundFrom'});
    //  jQuery(document).copyValue({fromSelector: '#outboundDate', toSelector:'#hotelArrivalDate'});
    //  jQuery(document).copyValue({fromSelector: '#inboundDate', toSelector:'#hotelDepatureDate'});


});

function initForm() {
    // Init
    jQuery('.subselect').hide();
    jQuery('.contact-mail').hide();
    jQuery('.contact-phone').hide();
    jQuery('.contact-method-input').hide();
    jQuery('#attachmentDetail').hide();

}

function addEventsToCaseSubject() {
    // Actions
    jQuery('#contentCase').click(function() {
        jQuery('div#contentSubCase').show('fast');
        jQuery('div#functionSubCase').hide('fast');
        jQuery('div#customSubCase').hide('fast');
    });
    jQuery('#functionCase').click(function() {
        jQuery('div#contentSubCase').hide('fast');
        jQuery('div#functionSubCase').show('fast');
        jQuery('div#customSubCase').hide('fast');
    });
    jQuery('#customCase').click(function() {
        jQuery('div#contentSubCase').hide('fast');
        jQuery('div#functionSubCase').hide('fast');
        jQuery('div#customSubCase').show('fast');
    });
    jQuery('#otherCase').click(function() {
        jQuery('div#contentSubCase').hide('fast');
        jQuery('div#functionSubCase').hide('fast');
        jQuery('div#customSubCase').hide('fast');
    });
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
        switch (this.value) {
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
    });
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
