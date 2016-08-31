addEventHandler(window, 'load', invokeMessage);

function ajaxProcess(data) {
	try {
		if (data.status == 'begin') {
			RichFaces.$('ajax').show();
		} else if (data.status == 'complete') {

		} else if (data.status == 'success') {
			RichFaces.$('ajax').hide();
			invokeMessage();
		}
	} catch (error) {
		RichFaces.$('ajax').hide();
		alert('Error=' + error);
	}
}