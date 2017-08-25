function checkForm(str){
	var username = $("#username").val();
	var password = $("#password").val();
	if(str =="username"){
		if(username==''|| username.trim().length==0){
			$("#warnDiv").html("请您输入用户名");
			return false;
		}else{
			$("#warnDiv").html("");
			return;
		}
	}
	if(str =='password'){
		if(password==''){
			$("#warnDiv").html("请您输入密码");
			return false;
		}else{
			$("#warnDiv").html("");
			return;
		}
	}
	var username = $("#username").val();
	var password = $("#password").val();
	if(username=='' || username.length==0){
		$("#warnDiv").html("请您输入用户名");
		return false;
	}else{
		$("#warnDiv").html("");
	}
	if(password==''){
		$("#warnDiv").html("请您输入密码");
		return false;
	}else{
		$("#warnDiv").html("");
	}
	return true;
}
function submitForm(){
	var flag = checkForm('');
	if(flag){
		var $btn = $("#btn").button('loading');
		 var options = {
	                url: '/user/login',
	                type: 'post',
	                dataType: 'json',
	                data: $("#loginForm").serialize(),
	                success: function (data) {
	                    if (data.success){
	                    	window.location.href="/user/index";
	                    }else{
	                    	$("#warnDiv").html(data.message);
	                    	$btn.button('reset');
	                    }
	                }
	         };
	         $.ajax(options);
	}
	
}
$("body").bind("keyup",function(event) { 
	  if(event.keyCode==13){ 
		  submitForm();
	  }   
	});  
