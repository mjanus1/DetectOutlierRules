function ajaxProcess(data) {
	try {
		if (data.status == 'begin') {
			PF('ajaxStatus').show();
		} else if (data.status == 'complete') {

		} else if (data.status == 'success') {
			PF('ajaxStatus').hide();
		}
	} catch (error) {
		PF('ajaxStatus').hide();
		alert('Error=' + error);
	}
}


var DOR = {
	
	showPopup:function(id) {
		
			PF.$id.show();
	}	
}