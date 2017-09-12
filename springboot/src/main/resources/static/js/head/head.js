$(window).load(function() {
	var options =
	{
		thumbBox: '.thumbBox',
		spinner: '.spinner',
		imgSrc: ''
	}
	var cropper = $('.imageBox').cropbox(options);
	$('#file').on('change', function(){
		var reader = new FileReader();
		reader.onload = function(e) {
			options.imgSrc = e.target.result;
			cropper = $('.imageBox').cropbox(options);
		}
		reader.readAsDataURL(this.files[0]);
		this.files = [];
		window.setTimeout(function (){
			var img = cropper.getDataURL();
			$('.cropped').html('');
//			$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:80px;margin-top:4px;border-radius:80px;box-shadow:0px 0px 12px #7E7E7E;"><p>80px*80px</p>');
			$('#headUrl').val(img);
		},'100');
	})
	$('#load').on('mouseup', function(){
		var img = cropper.getDataURL();
// 		$('.cropped').html('');
// 		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:64px;margin-top:4px;border-radius:64px;box-shadow:0px 0px 12px #7E7E7E;" ><p>64px*64px</p>');
// 		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:128px;margin-top:4px;border-radius:128px;box-shadow:0px 0px 12px #7E7E7E;"><p>128px*128px</p>');
		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:80px;margin-top:4px;border-radius:80px;box-shadow:0px 0px 12px #7E7E7E;"><p>80px*80px</p>');
// 		$('.cropped').append('<img src="'+img+'" align="absmiddle" style="width:180px;margin-top:4px;border-radius:180px;box-shadow:0px 0px 12px #7E7E7E;"><p>180px*180px</p>');
		$('#headUrl').val(img);
	})
	$('#btnZoomIn').on('click', function(){
		cropper.zoomIn();
		var img = cropper.getDataURL();
		$('#headUrl').val(img);
	})
	$('#btnZoomOut').on('click', function(){
		cropper.zoomOut();
		var img = cropper.getDataURL();
		$('#headUrl').val(img);
	})
});
function upload(){
	var file =$("#headUrl").val();
	if(file == null || file == '' || file == undefined){
		alert("请选择一张图片");
		return;
	}
	var options = {
	        url: '/user/uploadHead',
	        type: 'post',
	        dataType: 'json',
	        data: $("#uploadHead").serialize(),
	        success: function (data) {
	            if (data.success){
	            	$("#imageHead").prop("src",data.message);
	            	toastr.success("头像上传成功");
	            }else{
	            	toastr.error(data.message);
	            }
	        }
	 };
	 $.ajax(options);
}