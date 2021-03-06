<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<!-- we code these -->
<!-- <link href="resources/css/dropzone.css" type="text/css" rel="stylesheet" /> -->
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="resources/css/jquery.fileupload-ui.css">
<link href="resources/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="resources/js/jquery-2.1.3.js"></script>
<!-- <script src="resources/js/vendor/jquery.ui.widget.js"></script>
<script src="resources/js/jquery.iframe-transport.js"></script>
<script src="resources/js/jquery.fileupload.js"></script>
<script src="resources/js/load-image.js"></script> -->

<!-- bootstrap just to have good looking page -->
<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
<!-- <script src="resources/js/myuploadfunction.js"></script> -->


<link href="http://hayageek.github.io/jQuery-Upload-File/uploadfile.min.css" rel="stylesheet">

<!-- DataTables CSS -->
<link rel="stylesheet" type="text/css" href="resources/DataTables/css/jquery.dataTables.css">
<!-- <link rel="stylesheet" type="text/css" href="http://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.css"> -->

<style type="text/css">
	div {
		margin: 20px;
	}
	.inputSpan {
		display: block;
	}
	#dropzone {
		clear:both;
	}
	.span7 {
		margin-left:0;
		margin-bottom: 20px;
	}
	.testBlock {margin: 20px auto; background: #eee; border-radius: 10px; padding: 15px }
	.progress { position:relative; width:400px; border: 1px solid #ddd; padding: 1px; border-radius: 3px; }
	.bar { background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; }
	.percent { position:absolute; display:inline-block; top:3px; left:48%; }
	label {
		display: inline;
	}
</style>
<script type="text/javascript" src="http://hayageek.github.io/jQuery-Upload-File/jquery.uploadfile.min.js"></script>

<script type="text/javascript" src="resources/js/jquery.form.js"></script>
<script type="text/javascript" src="resources/js/spark-md5.js"></script>
<script type="text/javascript" src="resources/js/jquery.myplugin.js"></script>
 
<!-- DataTables -->
<script type="text/javascript" charset="utf8" src="resources/DataTables/js/jquery.dataTables.js"></script>
<!-- <script type="text/javascript" charset="utf8" src="http://cdn.datatables.net/plug-ins/1.10.7/integration/bootstrap/3/dataTables.bootstrap.js"></script> -->

<script type="text/javascript">
	$(function() {
		$("#sendData").click(function() {
			$.ajax({
				url : "upload",
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
				url : "user/vcard/" + $("#username").val(),
			    type : "POST", 
			    success : function(data) {
			    	vcardResultObj.html(data);
			    },
				error:function(e){
				    $(vcardResultObj).html("加载失败:" + e);
			    }
			});
		});
		$("#getRosterVcards").click(function() {
			var friendVcardResultObj = $("#friendsVcardResult");
			friendVcardResultObj.html("正在加载...");
			$.ajax({
				url : "user/rosterVcards/" + $("#username").val(),
			    type : "POST", 
			    success : function(data) {
			    	$(friendVcardResultObj).html(data);
			    },
				error:function(e){
					$(friendVcardResultObj).html("加载失败:" + e);
			    }
			});
		});
		
		$("#downloadAvatar").click(function() {
			var fileType = $('input:radio[name=fileType]:checked');
			window.location = 'user/avatar/' + $("#username").val() + '?fileType=' + fileType.val();
			/* $.ajax({
				url : "user/avatar/" + $("#username").val(),
				type : "POST", 
				data: fileType.serialize(),
			    success : function(data) {
			    	console.log(data);
			    },
				error:function(e){
					console.log("加载失败:" + e);
			    }
			}); */
		});
		
		
		/* var bar = $('.bar');
		var percent = $('.percent');
		var status = $('#status');
		var uploadObj = $("#fileuploader").uploadFile({
			url:"uploadTest",
			allowedTypes:"png,gif,jpg,jpeg",
			multiple:true,
			autoSubmit:false,
			formData: {"name":"Ravi","age":31},
			dragDropStr: "拖到此区域上传",
			abortStr:"停止上传",
			cancelStr:"取消上传",
			doneStr:"上传完成",
			uploadErrorStr:"文件上传失败",
			fileName:"myfile"
		});
		$("#startUpload").click(function() {
			uploadObj.startUpload();
		}); */
		
		$('#addFile').click(function() {
			var inputSpan = $('#myForm .inputSpan:last');
			inputSpan.after('<span class="inputSpan"><input type="file" name="avatarFile"></span>')
			inputSpan = $('#myForm .inputSpan:last');
			var inputFile = inputSpan.children('input[type="file"]');
			inputFile.on('change', handleFiles);
			//inputFile.after('<input type="file" name="myfile" multiple>');
			//console.log(inputFile.after('<br/>'));
		});
		
		var changeInput = $('#myForm input[type="file"]');
		changeInput.on('change', handleFiles);
		function handleFiles(e) {
			var currentInput = e.currentTarget;
			if (!$(currentInput).next().is('canvas')) {
				$(currentInput).after('<canvas width="100" height="100"/>');
			}
			
			var blobSlice = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice,
	        file = e.target.files[0],
	        chunkSize = 2097152,                             // Read in chunks of 2MB
	        chunks = Math.ceil(file.size / chunkSize),
	        currentChunk = 0,
	        spark = new SparkMD5.ArrayBuffer(),
	        fileReader = new FileReader();

		    fileReader.onload = function (e) {
		        spark.append(e.target.result);                   // Append array buffer
		        currentChunk++;
	
		        if (currentChunk < chunks) {
		            loadNext();
		        } else {// Compute hash
		        	if ($(currentInput).hasClass('originalFile')) {	//原始图片
			        	var fileName = file.name;
			        	var fileSize = file.size;
			        	var fileType = file.type;
			        	var hash = spark.end();
			        	$('#fileName').val(fileName);
			        	$('#mimeType').val(fileType);
			        	$('#hash').val(hash);
		        	}
		        }
		    };
	
		    fileReader.onerror = function () {
		        console.warn('oops, something went wrong.');
		    };
	
		    function loadNext() {
		        var start = currentChunk * chunkSize,
		            end = ((start + chunkSize) >= file.size) ? file.size : start + chunkSize;
	
		        fileReader.readAsArrayBuffer(blobSlice.call(file, start, end));
		    }
	
		    loadNext();
			
			var reader = new FileReader();
			reader.onload = function(event){
		        var img = new Image();
		        img.onload = function(){
		        	var canvas = $(currentInput).next('canvas')[0];
		        	var ctx = canvas.getContext("2d");
		            ctx.drawImage(img,0, 0, 100, 100 * img.height / img.width);
		        }
		        img.src = event.target.result;
		    }
		    reader.readAsDataURL(file);
		}
		var bar = $('.bar');
		var percent = $('.percent');
		var status = $('#status');
		$('#myForm').ajaxForm({
			dataType:  'json',
			beforeSerialize: function(form, options) {
				options.data = {
						'jsonStr': JSON.stringify($('#myForm').serializeObject())
				}
			},
			beforeSend: function() {
				status.empty();
		        var percentVal = '0%';
		        bar.width(percentVal)
		        percent.html(percentVal);
			}, 
			uploadProgress: function(event, position, total, percentComplete) {
				var percentVal = percentComplete + '%';
		        bar.width(percentVal)
		        percent.html(percentVal);
			},
			success: function() {
		        var percentVal = '100%';
		        bar.width(percentVal)
		        percent.html(percentVal);
		    },
			complete: function(xhr) {
				status.html(xhr.responseText);
			}
		});
		
		
		$('#nickButton').click(function() {
			var nickModifyResult = $("#nickModifyResult");
			nickModifyResult.html("正在提交...");
			$.ajax({
				url : "http://localhost:8080/chatapi/api/user/vcard/" + $("#username").val(),
			    type : "POST", 
			    success : function(data) {
			    	$(nickModifyResult).html(data);
			    },
				error:function(e){
				    $(nickModifyResult).html("加载失败:" + e);
			    }
			});
		});
		
		$('#nickModifyButton').click(function() {
			var nickModifyResultObj = $('#nickModifyResult');
			$.ajax({
			  method: "POST",
			  url: 'user/modify/nick/' + $('#nickModifyForm input:first').val(),
			  data: $('#nickModifyForm').serialize(),
			  beforeSend: function(xhr, o) {
				  nickModifyResultObj.html("正在提交...");
			  }
			})
		  	.always(function(msg) {
		  		nickModifyResultObj.html(msg);
		  	});
		});
		
		$('#genderModifyButton').click(function() {
			var genderModifyResultObj = $('#genderModifyResult');
			$.ajax({
			  method: "POST",
			  url: 'user/modify/gender/' + $('#genderModifyForm input:first').val(),
			  data: $('#genderModifyForm').serialize(),
			  beforeSend: function(xhr, o) {
				  genderModifyResultObj.html("正在提交...");
			  }
			})
		  	.always(function(msg) {
		  		genderModifyResultObj.html(msg);
		  	});
		});
		
		$('#addressModifyButton').click(function() {
			var addressModifyResultObj = $('#addressModifyResult');
			$.ajax({
			  method: "POST",
			  contentType: "application/json;charset=UTF-8",
			  url: 'user/modify/address/' + $('#addressModifyForm input:first').val(),
			  data: JSON.stringify($('#addressModifyForm').serializeObject()),
			  beforeSend: function(xhr, o) {
				  addressModifyResultObj.html("正在提交...");
			  }
			})
		  	.always(function(msg) {
		  		addressModifyResultObj.html(msg);
		  	});
		});
		
		$('#signatureModifyButton').click(function() {
			var signatureModifyResultObj = $('#signatureModifyResult');
			$.ajax({
			  method: "POST",
			  url: 'user/modify/signature/' + $('#signatureModifyForm input:first').val(),
			  data: $('#signatureModifyForm').serialize(),
			  beforeSend: function(xhr, o) {
				  signatureModifyResultObj.html("正在提交...");
			  }
			})
		  	.always(function(msg) {
		  		signatureModifyResultObj.html(msg);
		  	});
		});
		
		var vcardAddResultObj = $('#vcardAddResult');
		$('#vcardAddForm').ajaxForm({
			url: 'user/vcard/add',
			dataType:  'json',
			method: "POST",
			data: {
				  'jsonStr': JSON.stringify($('#vcardAddForm').serializeObject()),
			},
			beforeSend: function(xhr, o) {
				vcardAddResultObj.html("正在提交...");
			}, 
			complete: function(xhr) {
				vcardAddResultObj.html(xhr.responseText);
			}
		});
		
		$('#vcardAddButton').click(function() {
			$('#vcardAddForm').submit();
			/* var vcardAddResultObj = $('#vcardAddResult');
			$.ajax({
			  method: "POST",
			  url: 'user/vcard/add',
			  data: {
				  'jsonStr': JSON.stringify($('#vcardAddForm').serializeObject()),
			  },
			  beforeSend: function(xhr, o) {
				  vcardAddResultObj.html("正在提交...");
			  }
			})
		  	.always(function(msg) {
		  		vcardAddResultObj.html(msg);
		  	}); */
		});
		
		var table = $('#table_id').DataTable({
			//deferRender: true,	//延迟渲染
			//"info": false,	//是否显示左下角的提示信息
			"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "全部"]],
			"pagingType": "full_numbers",
			"processing": true,	//是否显示正在处理的提示语
			//"serverSide": true,	//服务器端处理
			"language": {
				'lengthMenu': "每页显示 _MENU_条记录",
			    "info": "当前第 _PAGE_ 页，共  _PAGES_ 页， 共_TOTAL_条记录",
			    "processing": "正在处理...",
			    "loadingRecords": "正是加载，请稍候...",
			    "search": "搜索:",
			    'searchPlaceholder': '输入关键字搜索',
			    "zeroRecords": "无数据",
			    "emptyTable": "无数据",
			    "paginate": {
	            	"first": "首页",
	            	"last": "尾页",
	            	"next": "下一页",
	            	"previous": "上一页"
	           	}
			},
			"rowCallback": function( row, data, displayIndex, displayIndexFull ) {
				//console.info(row);	//<tr class="odd" role="row">
				var thumbTd = $(row).children('td:first');
				var originalTd = $(thumbTd).next('td');
				var avatarName = data.avatarPath;
				var imgThumbUrl = '#';
				var imgOriginalUrl = '#';
				if (avatarName != null && avatarName != 'undefined') {
					imgThumbUrl = "user/avatar/show/" + data.username + "?fileType=1&avatarName=" + avatarName;
					imgOriginalUrl = "user/avatar/show/" + data.username + "?fileType=2&avatarName=" + avatarName;
				}
				$(thumbTd).html("<img src='" + imgThumbUrl + "' width='64px' height='64px' title='" + data.username + "' />");
				$(originalTd).html("<img src='" + imgOriginalUrl + "' width='100px' height='100px' title='" + data.username + "' />");
				//console.info(data.username);	//data obj
				//console.info(displayIndex);	//行号
	         	// Bold the grade for all 'A' grade browsers
		     	/* if ( data[4] == "A" ) {
		        	$('td:eq(4)', row).html( '<b>A</b>' );
		     	} */
	        },
			ajax: {
				'url': 'user/vcard/vcards',
				'dataType': 'json',
				'data': {
					'pageNumber': 1,
					'pageCount': 10,
					'pageable':'0'	//0表示不分页，1分页
				},
				'dataSrc': function(data) {
					return data.data;
					//var resultCode = data.resultCode;
					//if (resultCode == 100) {	//成功，且有数据
					//}
				}
			},
			columns: [
			    {
			    	'data': '',
			    	'defaultContent': ''
			    },
			    {
			    	'data': '',
			    	'defaultContent': ''
			    },
				{
					'data': 'username',
					'defaultContent': ''
				},
				{
					'data': 'nickName',
					'defaultContent': ''
				},
				{
					'data': 'realName',
					'defaultContent': ''
				},
				{
					'data': 'country',
					'defaultContent': ''
				},
				{
					'data': 'province',
					'defaultContent': ''
				},
				{
					'data': 'city',
					'defaultContent': ''
				},
				{
					'data': 'street',
					'defaultContent': ''
				},
				{
					'data': 'mobilePhone',
					'defaultContent': ''
				},
				{
					'data': 'telephone',
					'defaultContent': ''
				},
				{
					'data': 'avatarPath',
					'defaultContent': ''
				},
				{
					'data': 'mimeType',
					'defaultContent': ''
				},
				{
					'data': 'gender',
					'render': function ( data, type, full, meta ) {
						if (data == 1) {	//男
							return '男';
						} else if (data ==2) {	//女
							return '女';
						} else {	//未知
							return '未知';
						}
				    }
				},
				{
					'data': 'signature',
					'defaultContent':''
				},
				{
					'data': 'hash',
					'defaultContent':''
				}
				
			]
		});
		
		$('#table_id tbody').on('click', 'tr', function() {
			$(this).toggleClass('selected');
		});
		
		$('#deleteRow').click( function () {
			var selectArray = table.rows('.selected').data();
			var selectLength = selectArray.length;
			if (selectLength > 0) {	//选择了行
				var confirm = window.confirm('确定删除选中项?');
				if (confirm) {
					var ids = '';
					$.each(selectArray, function(index,value) {
						ids += value.username + ",";
					});
					ids = ids.substring(0, ids.length - 1);
					$.ajax({
						method: "POST",
						url: 'user/vcard/delete',
						dataType: 'json',
						data: {
							'ids': ids,
						}
					})
				  	.always(function(msg) {
				  		var resultCode = msg.resultCode;
				  		if (resultCode == 100) {	//删除成功
					  		table.rows('.selected').remove().draw( false );
				  		} else {
				  			alert("删除失败!");
				  		}
				  	});
				}
			} else {
				alert("请选择要删除的项");
			}
			//table.rows('.selected').remove().draw( false );
	        //alert( table.rows('.selected').data().length +' row(s) selected' );
	    } );
		
		//修改昵称
		/* $('#nickModifyForm').ajaxForm({
			url: 'user/modify/nick/' + $('#nickModifyForm input:first').val(),
			dataType:  'json',
			beforeSend: function(xhr, o) {
				nickModifyResultObj.html("正在提交...");
			}, 
			complete: function(xhr) {
				nickModifyResultObj.html(xhr.responseText);
			}
		}); */
		
	});
</script>
</head>
<body>

	<!-- 表格 -->
	<div class="testBlock">
		<h2>表格</h2>
		<button type="button" id="deleteRow">删除选中行</button>
		<table id="table_id" class="display">
		    <thead>
		        <tr>
		            <th>缩略图</th>
		            <th>原始图</th>
		            <th>账号</th>
		            <th>昵称</th>
		            <th>姓名</th>
		            <th>国家</th>
		            <th>省份</th>
		            <th>城市</th>
		            <th>街道</th>
		            <th>手机号</th>
		            <th>座机号</th>
		            <th>头像名称</th>
		            <th>头像类型</th>
		            <th>性别</th>
		            <th>签名</th>
		            <th>头像hash</th>
		        </tr>
		    </thead>
		    <tbody>
		        <!-- <tr>
		            <td>Row 1 Data 1</td>
		            <td>Row 1 Data 2</td>
		        </tr>
		        <tr>
		            <td>Row 2 Data 1</td>
		            <td>Row 2 Data 2</td>
		        </tr> -->
		    </tbody>
		</table>
	</div>

	<!-- <div class="testBlock">
		<form action="upload" method="post" enctype="multipart/form-data">
			用户名：<input type="text" name="jsonStr" /><br />
			选择文件:<input type="file" name="uploadFile"><br />
			选择文件2:<input type="file" name="uploadFile"> <input type="submit" value="上传"><br />
			
		</form>
	</div> -->
	
	<div class="testBlock">
		<h2>获取用户电子名片</h2>
		<div>
			<input type="text" id="username" placeholder="请输入用户名" /><br/>
		</div>
		
		<div>
			<input type="button" id="getUserVcard" value="获得用户电子名片" />
			<div id="vcardResult"></div>
		</div>
		
		<div>
			<input type="button" id="getRosterVcards" value="获取好友头像信息" />
			<div id="friendsVcardResult"></div>
		</div>
		
		<div>
			<label><input type="radio" name="fileType" value="1">下载缩略图</label>
			<label><input type="radio" name="fileType" value="2">下载原始图</label><br/>
			<input type="button" id="downloadAvatar" value="下载头像" />
		</div>
	</div>
	
	<!-- 
	<div class="testBlock" id="uploadAvatar">
		<div class="span7">
	        The fileinput-button span is used to style the file input field as button
	        <span class="btn btn-success fileinput-button">
		        <i class="icon-plus icon-white"></i>
		        <span>Add files...</span>
				<input id="fileupload" type="file" name="files[]" multiple>
		    </span>
	        <button type="submit" class="btn btn-primary start	">
	            <i class="icon-upload icon-white"></i>
	            <span>Start upload</span>
	        </button>
	        <button type="reset" class="btn btn-warning cancel">
	            <i class="icon-ban-circle icon-white"></i>
	            <span>Cancel upload</span>
	        </button>
	    </div>
	
		<div id="dropzone" class="fade well">Drop files here</div>
		
		<div id="progress" class="progress">
	    	<div class="bar" style="width: 0%;"></div>
		</div>
	
		<table id="uploaded-files" class="table">
			<tr>
				<th>Preview</th>
				<th>File Name</th>
				<th>File Size</th>
				<th>File Type</th>
				<th>Download</th>
			</tr>
		</table>
	</div> -->
	
	<!-- <div class="testBlock">
		<h2>测试文件上传</h2>
		<div id="fileuploader">选择文件</div>
		<div id="startUpload" class="ajax-file-upload-green">开始上传</div>
	</div> -->
	
	<div class="testBlock">
		<h2>头像上传</h2><!-- uploadTest -->
		<form id="myForm" action="user/uploadAvatar" method="post"  enctype="multipart/form-data"> 
			用户名: <input type="text" name="sender" /> <br/>
			文件名: <input type="text" name="fileName" id="fileName" /> <br/>
			文件类型: <input type="text" name="mimeType" id="mimeType" /> <br/>
			文件MD5: <input type="text" name="hash" id="hash" /> <br/>
   		    <span class="inputSpan"><input type="file" class="originalFile" name="avatarFile"> </span>
   		    <span class="inputSpan"><input type="file" name="avatarFile"> </span>
		    <input type="submit" id="submitBtn" value="开始上传" /> 
			<input type="button" id="addFile" value="添加文件"/>
		</form>
		
		<div id="progress" class="progress">
	    	<div class="bar"></div>
	    	<div class="percent">0%</div >
		</div>
	    
	    <div id="status"></div>
	</div>
	
	<div class="testBlock">
		<h2>修改昵称</h2>
		<form action="#" id="nickModifyForm" method="post">
			用户名：<input type="text" name="username" placeholder="请输入用户名"><br/>
			新昵称：<input type="text" name="nickname" placeholder="请输入新昵称"/><br/>
			<input type="button" id="nickModifyButton" value="提交昵称" />
			<!-- <input type="button" id="realnameModifyButton" value="提交真实姓名" /> -->
		</form>
		<div id="nickModifyResult"></div>
	</div>
	
	<div class="testBlock">
		<h2>修改性别</h2>
		<form action="#" id="genderModifyForm" method="post">
			用户名：<input type="text" name="username" placeholder="请输入用户名"><br/>
			性别：<label><input type="radio" name="gender" value="1" />男</label><label><input type="radio" name="gender" value="2" />女</label><br/>
			<input type="button" id="genderModifyButton" value="提交性别" />
			<!-- <input type="button" id="realnameModifyButton" value="提交真实姓名" /> -->
		</form>
		<div id="genderModifyResult"></div>
	</div>
	
	<div class="testBlock">
		<h2>修改地址</h2>
		<form action="#" id="addressModifyForm" method="post">
			用户名：<input type="text" name="username" placeholder="请输入用户名"><br/>
			国家：<input type="text" name="country" placeholder="请输入国家名称"/><br/>
			省份：<input type="text" name="province" placeholder="请输入省份名称"/><br/>
			城市：<input type="text" name="city" placeholder="请输入城市名称"/><br/>
			街道地址：<input type="text" name="street" placeholder="请输入详细街道地址"/><br/>
			<input type="button" id="addressModifyButton" value="提交地址" />
			<!-- <input type="button" id="realnameModifyButton" value="提交真实姓名" /> -->
		</form>
		<div id="addressModifyResult"></div>
	</div>
	
	<div class="testBlock">
		<h2>修改个性签名</h2>
		<form action="#" id="signatureModifyForm" method="post">
			用户名：<input type="text" name="username" placeholder="请输入用户名"><br/>
			个性签名：<input type="text" name="signature" placeholder="请输入个性签名"/><br/>
			<input type="button" id="signatureModifyButton" value="提交签名" />
			<!-- <input type="button" id="realnameModifyButton" value="提交真实姓名" /> -->
		</form>
		<div id="signatureModifyResult"></div>
	</div>
	
	<div class="testBlock">
		<h2>添加电子名片信息</h2>
		<form action="#" id="vcardAddForm" method="post"  enctype="multipart/form-data">
			用户名：<input type="text" name="username" placeholder="请输入用户名"><br/>
			昵称：<input type="text" name="nickName" placeholder="请输入个性签名"/><br/>
			性别：<label><input type="radio" name="gender" value="1" />男</label><label><input type="radio" name="gender" value="2" />女</label><br/>
			<input type="button" id="vcardAddButton" value="提交签名" />
			<!-- <input type="button" id="realnameModifyButton" value="提交真实姓名" /> -->
		</form>
		<div id="vcardAddResult"></div>
	</div>
	
</body>
</html>