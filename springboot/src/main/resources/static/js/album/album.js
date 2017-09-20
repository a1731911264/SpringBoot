$('body').scrollspy({
	target : '#navbar-example'
});
function changeText(obj) {
	if (obj == '1') {
		$("#addAndEdit").html("新建相册：");
		$('#addOrEditForm')[0].reset();
	} else {
		$("#addAndEdit").html("编辑相册：");
		$('#addOrEditForm')[0].reset();
	}
}
function checkInputSize(obj) {

	var size = obj.value.trim().length;
	var id = obj.id;
	if (id == 'albumName') {
		$("#albumNameSize").html(size);

	} else {
		$("#albumDescSize").html(size);

	}
}
function checkBox(obj) {
	var state = $(obj).children(':first').prop('checked');
	if (state) {
		$(obj).children(':first').prop('checked', false);
	} else {
		$(obj).children(':first').prop('checked', true);
	}
}
$("#albumSave").click(function() {

	var $btn = $("#albumSave").button('loading');

	var options = {
		url : '/album/saveOrUpdateAlbum',
		type : 'post',
		dataType : 'json',
		data : $("#addOrEditForm").serialize(),
		success : function(data) {

			if (data.success == true) {
				toastr.success("保存成功(*_*)");
				$('#addOrEditAlbum').modal('hide');
				var val = $("#serch").val();
				reload(val);
				$btn.button('reset');
			} else {
				toastr.error(data.message);
				$btn.button('reset');
			}

		}
	};
	$.ajax(options);

});
function reload(obj) {
	$.ajax({
		type : "post",
		url : "/album/reload",
		data : {
			"albumName" : obj.trim(),
		},
		dataType : "json",
		success : function(data) {
			$('#albumParentDiv').empty(); // 清空albumParentDiv里面的所有内容
			if(data.success){
				var dataList = JSON.parse(data.message);
//				var dataList = data.message;
//				alert(dataList);
				for(var i=0;i<dataList.length;i++){
					var album = dataList[i];
					$("#albumParentDiv").append(
							'<div class="col-md-3 col-sm-6 col-padding text-center animate-box">'+
								'<a href="javascript:void(0)" class="work image-popup" style="background-image: url(\''+album.cover +'\')" >'+
									'<div class="desc" style="width: 100%; height: 100%; position: relative;">'+
									 	'<div style="position: absolute; top: 2%; left: 2%;">&nbsp; '+
											'<span class="glyphicon glyphicon-edit" title="编辑" style="display: inline-block; font-size: 15px" data-toggle="modal" data-target="#addOrEditAlbum" onclick="changeText(\'2\')"></span>&nbsp;&nbsp;'+
											'<span class="glyphicon glyphicon-upload" title="上传" style="display: inline-block; font-size: 15px"></span>&nbsp;&nbsp;'+
										'</div>'+
										'<div style="position: absolute; top: 2%; right: 2%;">'+
											'<span class="glyphicon glyphicon-trash" title="删除" style="display: inline-block; font-size: 15px"></span>&nbsp;'+
										'</div>'+
										'<h3>'+album.albumName+'</h3>'+
										'<span>'+album.createDate+'</span>'+
									'</div>'+
								'</a>'+
							'</div>');
				}
				loadDiv();
				toastr.info("加载完成");
			}else {
				toastr.error(data.message);
			}
			
		}
	});
}

function loadDiv() {
	var i = 0;
	$('.animate-box').waypoint( function( direction ) {

		if( direction === 'down' && !$(this.element).hasClass('animated') ) {
			
			i++;

			$(this.element).addClass('item-animate');
			setTimeout(function(){

				$('body .animate-box.item-animate').each(function(k){
					var el = $(this);
					setTimeout( function () {
						var effect = el.data('animate-effect');
						if ( effect === 'fadeIn') {
							el.addClass('fadeIn animated');
						} else if ( effect === 'fadeInLeft') {
							el.addClass('fadeInLeft animated');
						} else if ( effect === 'fadeInRight') {
							el.addClass('fadeInRight animated');
						} else {
							el.addClass('fadeInUp animated');
						}

						el.removeClass('item-animate');
					},  k * 200, 'easeInOutExpo' );
				});
				
			}, 100);
			
		}

	} , { offset: '85%' } );
};