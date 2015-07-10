<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<style type="text/css">
	div {
		margin: 20px;
	}
</style>
<link href="resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="resources/js/jquery-2.1.3.js"></script>
<script type="text/javascript" src="resources/bootstrap/js/bootstrap.js"></script>


<script type="text/javascript">
	$(function() {
		$("#sendData").click(function() {
			$.ajax({
				url : "http://localhost:9090/chatapi/api/upload",
			    type : "POST", 
			    dataType:"json",
			    contentType:'application/json;charset=UTF-8',
			    data:JSON.stringify({"sender":"aaa","receiver":"bbb","fileName":"abc.txt","thumbName":"abc.txt_thumb"}),
			    success : function() {
			        alert("ok");   
			    },
				error:function(e){
				    alert("error");
			    }
			});
		});
		$("#getUserVcard").click(function() {
			var vcardResultObj = $("#vcardResult");
			vcardResultObj.html("正在加载...");
			$.ajax({
				url : "http://localhost:8080/chatapi/api/user/vcard/" + $("#username").val(),
			    type : "POST", 
			    success : function(data) {
			    	vcardResultObj.html(data);
			    },
				error:function(e){
				    vcardResultObj.html("加载失败:" + e);
			    }
			});
		});
		$("#getRosterVcards").click(function() {
			var friendVcardResultObj = $("#friendsVcardResult");
			friendVcardResultObj.html("正在加载...");
			$.ajax({
				url : "http://localhost:8080/chatapi/api/user/rosterVcards/" + $("#username").val(),
			    type : "POST", 
			    success : function(data) {
			    	friendVcardResultObj.html(data);
			    },
				error:function(e){
					friendVcardResultObj.html("加载失败:" + e);
			    }
			});
		});
		
	});
</script>
</head>
<body>
	<form action="upload" method="post" enctype="multipart/form-data">
		用户名：<input type="text" name="jsonStr" /><br />
		选择文件:<input type="file" name="uploadFile"><br />
		选择文件2:<input type="file" name="uploadFile"> <input type="submit" value="上传"><br />
		
	</form>
	
	<div>
		<input type="text" id="username" placeholder="请输入用户名" /><br/>
	</div>
	<div>
		<input type="button" id="getUserVcard" value="获得用户电子名片" />
		<div id="vcardResult"></div>
	</div>
	
	<div>
		<input type="button" id="getRosterVcards" value="获取好友头像" />
		<div id="friendsVcardResult"></div>
	</div>
	
	<!-- <div>
		<input type="button" id="uploadAvatar" value="上传头像" />
		<div id="friendsVcardResult"></div>
	</div> -->
</body>
</html>