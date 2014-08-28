$(document).ajaxSend( function(event, jqXHR, ajaxOptions){
	if(ajaxOptions.loading){
		$(ajaxOptions.loading).show();
	}
});

$(document).ajaxComplete(function(event, jqXHR, ajaxOptions) {
	if(ajaxOptions.loading){
		$(ajaxOptions.loading).hide();
	}
});
function validateEmail(email) { 
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
} 
