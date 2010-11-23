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
    jQuery('#attachmentDetail').hide();

}

function addEventsToCaseSubject() {
    // Actions
    jQuery('#contentCase').click(function() {
        jQuery('div#contentSubCase').show('fast');
        jQuery('div#functionSubCase').hide('fast');
        jQuery('div#healthSubCase').hide('fast');
    });
    jQuery('#functionCase').click(function() {
        jQuery('div#contentSubCase').hide('fast');
        jQuery('div#functionSubCase').show('fast');
        jQuery('div#healthSubCase').hide('fast');
    });
    jQuery('#healthCase').click(function() {
        jQuery('div#contentSubCase').hide('fast');
        jQuery('div#functionSubCase').hide('fast');
        jQuery('div#healthSubCase').show('fast');
    });
    jQuery('#otherCase').click(function() {
        jQuery('div#contentSubCase').hide('fast');
        jQuery('div#functionSubCase').hide('fast');
        jQuery('div#healthSubCase').hide('fast');
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
    jQuery("input[name='contactOption']").change(function() {
        switch (this.value) {
            case 'email':
                jQuery('.contact-mail').show();
                jQuery('.contact-phone').hide();
                break;
            case 'telephone':
                jQuery('.contact-mail').hide();
                jQuery('.contact-phone').show();
                break;
            default:
                jQuery('.contact-mail').hide('fast');
                jQuery('.contact-phone').hide('fast');

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

// ----

function addEventsToContractCard() {
    //Init
    var selected = jQuery('[name="contractCard"]:checked').val();
    if (selected == 'false') {
        jQuery('li.cardNumber').hide();
    }
    //Actions
    jQuery('#contractCardYes').click(function() {
        jQuery('li.cardNumber').show('fast');
    });
    jQuery('#contractCardNo').click(function() {
        jQuery('li.cardNumber').hide('fast');
    });
}


function addEventsToDeliveryOption() {
    //Init
    var value = jQuery('#deliveryOptions :selected').val();
    hideShowDeliveryOptions(value);
    //Actions
    jQuery('#deliveryOptions').change(function() {
        hideShowDeliveryOptions(this.value);
    });
}

function hideShowDeliveryOptions(value) {
    if (value == "SJ biljettautomat") {
        jQuery("li.delivery-user-personnumber").hide("fast");
        jQuery("li.delivery-address").hide("fast");
    } else if (value == "SMS biljettlöst") {
        jQuery("li.delivery-user-personnumber").show("fast");
        jQuery("li.delivery-address").hide("fast");
    } else if (value == "Biljett per post") {
        jQuery("li.delivery-user-personnumber").hide("fast");
        jQuery("li.delivery-address").show("fast");

    }
}

function addEventsToTicketBookingType() {
    //Init
    var value = jQuery('#ticketBookingType :selected').val();
    hideShowTicketBookingTypeOptions(value);
    //Actions
    jQuery('#ticketBookingType').change(function() {
        hideShowTicketBookingTypeOptions(this.value);
    });
}

function hideShowTicketBookingTypeOptions(value) {
    if (value == "Enkel resa") {
        jQuery("li.ticket-tor").hide("fast");
        jQuery("li.ticket-enkel").show("fast");
        jQuery("li.ticket-travel").show("fast");
        jQuery("li.travel-option").show("fast");
    } else if (value == "Tur och retur") {
        jQuery("li.ticket-tor").show("fast");
        jQuery("li.ticket-enkel").show("fast");
        jQuery("li.ticket-travel").show("fast");
        jQuery("li.travel-option").show("fast");
    } else if (value == "Endast hotell") {
        jQuery("li.ticket-tor").hide("fast");
        jQuery("li.ticket-enkel").hide("fast");
        jQuery("li.ticket-travel").hide("fast");
        jQuery("li.travel-option").hide("fast");
    }
}
function addEventsToHotelBookingOptions() {
    //Init
    var value = jQuery('#hotelGuests :selected').val();
    hideShowHotelBookingOptions(value);
    //Actions
    jQuery('#hotelGuests').change(function() {
        hideShowHotelBookingOptions(this.value);
    });
}

function hideShowHotelBookingOptions(value) {
    if (value == "0") {
        jQuery("li.hotel-options").hide("fast");
    } else {
        jQuery("li.hotel-options").show("fast");
    }
}

function addEventsToHotelChoice() {
    //Init
    var id = jQuery('#hotelName :selected').attr('id');
    hideShowHotelChoice(id);
    //Actions
    jQuery('#hotelName').change(function() {
        hideShowHotelChoice(this.options[this.selectedIndex].id);
    });
}

function hideShowHotelChoice(id) {
    if (id == "ovr") {
        jQuery(".ovr").show();
    } else {
        jQuery(".ovr").hide();
    }
}