$(document).ready(function() {
    $("#fileUpload").change(function () {
        $("#dvPreview").html("");
        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/;
        if (regex.test($(this).val().toLowerCase())) {
            if ($.browser && parseFloat(jQuery.browser.version) <= 9.0) {
                $("#dvPreview").show();
                $("#dvPreview")[0].filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = $(this).val();
            }
            else {
                if (typeof (FileReader) != "undefined") {
                    $("#dvPreview").show();
                    $("#dvPreview").append("<img />");
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#dvPreview img").attr("src", e.target.result);
                    }
                    reader.readAsDataURL($(this)[0].files[0]);
                } else {
                    alert("This browser does not support FileReader.");
                }
            }
        } else {
            alert("Please upload a valid image file.");
        }
    });
    
    $("#btnRestartStationYes").click(function() {
		$("#restart-station-modal").modal('hide');
		$.ajax({
	        url : '/stations/restart/'+$('#stationNumber').val(),
	        success : function(data) {
	        	data = JSON.parse(data);
	       	  if(data.status == 1){
	       		  swal("", "", "success");
	       	  }else{
	       		swal("ERROR", data.message, "error");
	       	  }
	        },
	        error : function(error) {
	        	swal("ERROR", data.message, "error");
	        }
	    });
    });
    
    $("#btnDeleteStationYes").click(function() {
		$("#delete-station-modal").modal('hide');
		$.ajax({
	        url : '/stations/delete/'+$('#id').val(),
	        success : function(data) {
	        	data = JSON.parse(data);
	       	  if(data.status == 1){
	       		  swal("", "", "success");
	       	  }else{
	       		swal("ERROR", data.message, "error");
	       	  }
	        },
	        error : function(error) {
	        	swal("ERROR", data.message, "error");
	        }
	    });
    });
    
    $("#locationId").change(function() {
 	   $.ajax({
 	        url : '/partners/getPartnerNameByLocationId/'+$('#locationId').val(),
 	        success : function(data) {
 	        	$("#partnerName").val(data);
 	        },
 	        error : function(error) {
 	        	swal("ERROR", data.message, "error");
 	        }
 	    });
 	});

    
    
    
});

function goBack(){
	window.history.back();
}

function addStationComplain(stationId, selectedElement){
	alert(stationId);
	$.ajax({
        url : '/stations/addStationComplain/'+stationId+'/'+selectedElement.checked,
        success : function(data) {
        	data = JSON.parse(data);
       	  if(data.status == 1){
       		  swal("", "", "success");
       	  }else{
       		swal("ERROR", data.message, "error");
       	  }
        },
        error : function(error) {
        	swal("ERROR", data.message, "error");
        }
    });
}

// ======================= Load data into datatable =======================
// ======================= loadEmployees ==================================
function deleteLocationById(id){
	$.ajax({
        url : '/locations/delete/'+id,
        success : function(data) {
       	  if(data.status == 1){
//       	swal("TitleText", "MsgText", "Icon");
       		  swal("", "", "success");
       	  }else{
       		swal("", "", "error");
       	  }
        },
        error : function(error) {
        	swal("", "", "error");
        }
    });
}

function scrollMessagesDown(){
	$('#messagesBody').animate({scrollTop: $('#messagesBody').get(0).scrollHeight});
}

//=================================== Stations ==================================
function createQRCode(){
	var regex = RegExp('.+[\\s|\\t|\\n|\\r]+.+');
	var qrCodeText = $('#qrcode').val().trim();
	if(qrCodeText.length==0 || regex.test(qrCodeText)){
		alert('Please enter valid QR-CODE');
	}else{
		$.ajax({
			url : '/stations/create-qrcode-image/'+qrCodeText,
	        success : function(data) {
	        	data = JSON.parse(data);
	        	if(data.status==1){
		        	$('#imagePathReal').hide();
		        	$("#imagePathTemp").delay(5000).show(0);
	        		$('#imagePathTemp').attr("src", ""+data.qrCodeImage);
	        		$('#imagePath').val(data.qrCodeImage);
	        	}else{
	        		alert(data.message);
	        	}
	        },
	        error : function(error) {
	       	  alert(JSON.stringify(error));
	        }
	    });
	}
}

$("#appLocales").change(function () {
    /* var selectedOption = $('#appLocales').val();
    if (selectedOption != ''){
        window.location.replace('international?lang=' + selectedOption);
    } */
});


