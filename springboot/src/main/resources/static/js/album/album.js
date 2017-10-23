$('body').scrollspy({
	target : '#navbar-example'
});
$(function() {
	$('#uploadModel').on('hide.bs.modal', function() {
		$("#removeImage").click();
		$(".file-drop-zone-title").show();
	});
})
function changeText(obj) {
	if (obj == '' || obj == undefined || obj == null) {
		$("#addAndEdit").html("新建相册：");
		$('#addOrEditForm')[0].reset();
		checkInputSize($("#albumName")[0]);
		checkInputSize($("#albumDesc")[0]);
		$('#addOrEditAlbum').modal('show');
	} else {
		$("#addAndEdit").html("编辑相册：");
		$('#addOrEditForm')[0].reset();
		$
				.ajax({
					type : "post",
					url : "/album/getAlbum",
					data : {
						"albumId" : obj.trim(),
					},
					dataType : "json",
					success : function(data) {
						debugger;
						if (data.success) {
							var album = data.message;
							$("#albumId").val(album.albumId);
							$("#albumName").val(album.albumName);
							$("#albumDesc").val(album.albumDesc);
							$("#albumClassify").val(album.albumClassify);
							$("#systemAuthority").val(album.systemAuthority);
							$(
									"#addOrEditForm input[type='radio'][name='albumTheme'][value='"
											+ album.albumTheme + "']").prop(
									"checked", true);
							$("#addOrEditForm input[type='checkbox']")
									.each(
											function(index, element) {
												$(element).prop("checked",
														false);
												var pageValue = $(element)
														.val();
												var otherAuthoritys = album.otherAuthority
														.split(",");
												for (var i = 0; i < otherAuthoritys.length; i++) {
													if (pageValue == otherAuthoritys[i]) {
														$(element)
																.prop(
																		"checked",
																		true);
													}
												}
											});
							checkInputSize($("#albumName")[0]);
							checkInputSize($("#albumDesc")[0]);
							$('#addOrEditAlbum').modal('show');
						} else {
							toastr.error(data.message);
						}

					}
				});
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
$("#albumSave")
		.click(
				function() {

					var $btn = $("#albumSave").button('loading');
					var otherAuthority = "";
					$("#addOrEditForm input[type='checkbox']:checked")
							.each(
									function(index, element) {
										if (index == ($("#addOrEditForm input[type='checkbox']:checked").length - 1)) {
											otherAuthority += $(element).val();
										} else {
											otherAuthority += $(element).val()
													+ ",";
										}

									});
					var jsonObj = $("#addOrEditForm").serialize();
					jsonObj = jsonObj + "&otherAuthority=" + otherAuthority;
					var options = {
						url : '/album/saveOrUpdateAlbum',
						type : 'post',
						dataType : 'json',
						data : jsonObj,
						success : function(data) {

							if (data.success == true) {
								toastr.success("保存成功(*_*)");
								$('#addOrEditAlbum').modal('hide');
								reload();
								$btn.button('reset');
							} else {
								toastr.error(data.message);
								$btn.button('reset');
							}

						}
					};
					$.ajax(options);

				});
function reload() {
	$
			.ajax({
				type : "post",
				url : "/album/reload",
				data : {
					"albumName" : $("#serch").val().trim(),
				},
				dataType : "json",
				success : function(data) {
					$('#albumParentDiv').empty(); // 清空albumParentDiv里面的所有内容
					if (data.success) {
						var dataList = JSON.parse(data.message);
						// var dataList = data.message;
						// alert(dataList);
						if (dataList.length != 0) {
							$("#defaultDiv").hide();

							for (var i = 0; i < dataList.length; i++) {
								var album = dataList[i];

								$("#albumParentDiv")
										.append(
												'<form action="/common/blog" method="post" >'
														+ '<input type="hidden" name="albumId" value="'
														+ album.albumId
														+ '"/>'
														+ '<div class="col-md-3 col-sm-6 col-padding text-center animate-box">'
														+ '<a id="'
														+ i+"album"
														+ '" onclick="redirectImage(event,\''
														+ i+"album"
														+ '\')" href="javascript:void()" class="work image-popup" style="background-image: url(\''
														+ album.cover
														+ '\')" >'
														+ '<div class="desc" style="width: 100%; height: 100%; position: relative;">'
														+ '<div style="position: absolute; top: 2%; left: 2%;">&nbsp; '
														+ '<span class="glyphicon glyphicon-edit" title="编辑" style="display: inline-block; font-size: 15px" onclick="changeText(\''
														+ album.albumId
														+ '\')"></span>&nbsp;&nbsp;'
														+ '<span class="glyphicon glyphicon-upload" title="上传" onclick="changePreviewPosition(\''
														+ album.albumId
														+ '\')" style="display: inline-block; font-size: 15px"></span>&nbsp;&nbsp;'
														+ '</div>'
														+ '<div style="position: absolute; top: 2%; right: 2%;">'
														+ '<span class="glyphicon glyphicon-trash" onclick="alertModel(\''
														+ album.albumId
														+ '\')"  title="删除" style="display: inline-block; font-size: 15px"></span>&nbsp;'
														+ '</div>'
														+ '<h3>'
														+ album.albumName
														+ '</h3>'
														+ '<span>'
														+ album.createDataString
														+ '</span>'
														+ '</div>'
														+ '</a>'
														+ '</div>'
														+ '</form>');
							}
							$(".glyphicon-edit").click(function(event){ event.stopPropagation(); });
							$(".glyphicon-upload").click(function(event){ event.stopPropagation(); });
							$(".glyphicon-trash").click(function(event){ event.stopPropagation(); });
							$("#albumParentDiv").show();
							loadDiv();
							toastr.info("加载完成");
						} else {
							$("#albumParentDiv").hide();
							$("#defaultDiv").fadeIn(1000);
						}

					} else {
						toastr.error(data.message);
					}

				}
			});
}

function loadDiv() {
	var i = 0;
	$('.animate-box').waypoint(function(direction) {

		if (direction === 'down' && !$(this.element).hasClass('animated')) {

			i++;

			$(this.element).addClass('item-animate');
			setTimeout(function() {

				$('body .animate-box.item-animate').each(function(k) {
					var el = $(this);
					setTimeout(function() {
						var effect = el.data('animate-effect');
						if (effect === 'fadeIn') {
							el.addClass('fadeIn animated');
						} else if (effect === 'fadeInLeft') {
							el.addClass('fadeInLeft animated');
						} else if (effect === 'fadeInRight') {
							el.addClass('fadeInRight animated');
						} else {
							el.addClass('fadeInUp animated');
						}

						el.removeClass('item-animate');
					}, k * 200, 'easeInOutExpo');
				});

			}, 100);

		}

	}, {
		offset : '85%'
	});
};

function deleteAlbum() {
	$('#deleteAlbumModel').modal();
	$.ajax({
		type : "post",
		url : "/album/deleteAlbum",
		data : {
			"albumId" : albumId.trim(),
		},
		dataType : "json",
		success : function(data) {
			if (data.success) {
				$('#deleteAlbumModel').modal("hide");
				reload();
				toastr.success("删除成功");
			} else {
				toastr.error(data.message);
			}

		}
	});
}
var albumId = "";
function alertModel(obj) {
	albumId = obj;
	$('#deleteAlbumModel').modal();
}
var uploadUrl = '';
$(document).on('ready', function() {
	fileUpload = $("#input-b8").fileinput({
		theme : "gly",
		uploadUrl : "/common/uploadImg",
		rtl : true,
		language : 'zh',
		allowedFileExtensions : [ "jpg", "png", "gif", "jpeg" ],
		maxFileSize : 10240,
		uploadAsync : true,
		showPreview : true,
		autoReplace : true,
		showClose : false,
		showAjaxErrorDetails : false,
		uploadExtraData : function() {
			return {
				'albumId' : uploadUrl
			};
		},
		fileSingle : "图片"

	});
});
var div = '';
var deletePreviewImgge = '';
function changePreviewPosition(albumId) {
	uploadUrl = albumId;
	if (div == '') {
		div = $("#removePreviewDiv .file-preview").remove();
	}
	$("#previewFileDiv").html(div);
	// 预览框复制到 另一个div中 x失去了移除预览图片功效 将移除按钮的时间加给x
	if (deletePreviewImgge == '') {
		$(".fileinput-remove-button").prop("id", "removeImage");
		// deletePreviewImgge = $(".fileinput-remove").on("click",function(){
		// $(".fileinput-remove-button").trigger("click");
		// })
	}
	$('#uploadModel').modal();
}
$(function() {
	$("#input-b8").change(function() {
		$(".file-drop-zone-title").hide();
	});
	$("#input-b8").on("fileclear", function(event, data, msg) {
		// 将预览框重新添加到模态框内
		$(".file-drop-zone-title").css("display", "block");
	});
})

function redirectImage(event, albumId) {
	$("#" + albumId).parent().parent().submit();
}

