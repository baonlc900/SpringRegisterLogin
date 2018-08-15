function patientSelect() {
	$('#ajaxLoader').show();
	var defaultOption = $("#patientIdSelect option[value=default] ").attr('selected','selected');
	if (defaultOption) defaultOption.remove();
	//change select id
	var patientId = $('#patientIdSelect').val();
	
	//get url
	var url = "./api/patient/" + patientId;
	
	$.get(url, callPatientInfo);
}


function callPatientInfo(data) {
	console.log(data);
	
	var status = data.responseStatus;
	console.log(status);
	
	if(status =="OK") {
		var response = data.response;
	
	
		var patientId = response.patientId;
		var patientName = response.patientName;
		var age = response.age;
		var gender = response.gender;
		var time = response.time;
		
		
	//	$('#patientName').html(patientName);
	//	$('#age').html(age);
		
		$('#patientName').val(patientName);
		$('#patientId').val(patientId);
		$('#age').val(age);
		$('#gender').val(gender);
		$('#time').val(time);
		
		$('#profileRow').css('visibility','visible');
	}
	$('#ajaxLoader').hide();
	
}