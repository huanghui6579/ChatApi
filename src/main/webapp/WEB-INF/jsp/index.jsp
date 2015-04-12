<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>

<script type="text/javascript" src="resources/js/jquery-2.1.3.js"></script>
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
	});
</script>
</head>
<body>
	<form action="upload" method="post" enctype="multipart/form-data">
		用户名：<input type="text" name="jsonStr" /><br />
		选择文件:<input type="file" name="uploadFile"><br />
		选择文件2:<input type="file" name="uploadFile"> <input type="submit" value="上传"><br />
		
	</form>
	
	<button id="sendData" style="margin-top: 20px;">发送数据</button>
</body>
</html>