
var flag1 = false;
function checkParams(value,flag){
		
		var message = "";
		var successMessage = '';
		if (flag == "username") {
			if (value.length == "" || value.length<6 || value.length >16) {
				message = "用户名不能为空且长度必须介于6到16位之间";
			}
		}else if(flag == "password") {
			if (value.length == "" || value.length<6 || value.length >16) {
				message = "密码不能为空且长度必须介于6到16位之间";
			}
		}else if(flag == "confirmPassword") {
			var password = $("#password").val();
			if (value.length == "" || value.length<6 || value.length >16) {
				message = "确认密码不能为空且长度必须介于6到16位之间";
			}else if(password != value){
				message = "两次密码不一致";
			}
		}else if(flag == "showName") {
			if (value.length == "" || value.length == 0) {
				message = "昵称不能为空";
			}
		}else if(flag == "realName") {
			if (value.length == "" || value.length == 0) {
				message = "真实姓名不能为空";
			}
		}else if(flag == "phone") {
			if (value.length == "" || value.length == 0) {
				message = "手机号码不能为空";
			}else if(!$("#phone").val().match(/^1[0-9]{10}$/)){
				message = "手机号码格式不正确";
			}
		}else if(flag == 'registerCode'){
			if(value ==''){
				message = "验证码不能为空";
			}else{
				$.ajax({ 
				       type: "post", 
				       url: "/user/checkValidateCode", 
				       cache:false, 
				       async:false, 
				       dataType: "json", 
				       data:{"code":value,"name":"imgCode"},
				       success: function(data){ 
				    	   if(data.success){
				    		   successMessage=data.message;
				    	   }else{
				    		   message=data.message;
				    	   }
				       } 

				});
			}
		}
		if(message != ""){
			$("#t_"+flag).css("color","red");
			$("#t_"+flag).html(message);
			$("#btn").prop("disabled",true);
			return false;
		}else{
			$("#t_"+flag).css("color","#00CC00");
			$("#t_"+flag).html(successMessage);
			$("#btn").prop("disabled",false);
			return true;
		}
	}
	function checkForm(){
		var $btn = $("#btn").button('loading');
		if(!flag1){
			$(":input").each(function(a,b){
			       var id = $(b).attr("id");//获取id属性
			       var value = $(b).val();
			       var s = checkParams(value,id);
			       if(s == false) {
			    	   index++;
			       }
			    });
			$btn.button('reset');
			return;
		}
		var flag;
		var index = 0;
		$(":input").each(function(a,b){
	       var id = $(b).attr("id");//获取id属性
	       var value = $(b).val();
	       var s = checkParams(value,id);
	       if(s == false) {
	    	   index++;
	       }
	    });
		if(index==0){
			
			 var options = {
		                url: '/user/register',
		                type: 'post',
		                dataType: 'json',
		                data: $("#registerForm").serialize(),
		                success: function (data) {
		                    if (data.success){
		                    	toastr.success("恭喜您，注册成功");
		                    	window.setTimeout(function(){
		                    		toastr.info("3秒后跳转到登录界面...");
		                    	},"2000");
		                    	window.setTimeout(function(){
		                    		window.location.href="/user/toLogin";
		                    	},"5000");
		                    }else{
		                    	var strs = data.message.split(" ");
		                    	$("#t_username").css("color","red");
		                    	$("#"+strs[0]).html(strs[1]);
		                    	$btn.button('reset');
		                    }
		                }
		         };
		         $.ajax(options);
		}
		
	}
	function checkUsername(value){
		if (checkParams(value,"username")) {
			$.post("/user/checkUsername",{username:value},function(data){
				
				if("用户名可以使用"==data.message) {
					$("#t_username").css("color","#00CC00");
					$("#t_username").html(data.message);
					flag1 =  true;
				}else {
					$("#t_username").css("color","red");
					$("#t_username").html(data.message);
					flag1 =  false;
				}
			},"json");
		}
	}
	
	function changeCode(){
		$("#registerCodeImg").attr("src","/code/registerCode?time="+new Date());
	}