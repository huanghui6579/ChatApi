$(function () {
    $('#fileupload').fileupload({
    	url: 'rest/controller/upload',
        dataType: 'json',
        formData: {'namedesc':"张三"},
        autoUpload:true,
        maxFileSize: 2097152,// 2 MB
        done: function (e, data) {
        	$("tr:has(td)").remove();
            $.each(data.result, function (index, file) {
            	var previewClass = "imagePreview" + index;
                $("#uploaded-files").append(
                		$('<tr/>')
                		.append($('<td/>').addClass(previewClass).html("<img width='100' height='100' src='rest/controller/get/"+index+"' />"))
                		.append($('<td/>').text(file.fileName))
                		.append($('<td/>').text(file.fileSize))
                		.append($('<td/>').text(file.fileType))
                		.append($('<td/>').html("<a href='rest/controller/get/"+index+"'>Click</a>"))
                		);//end $("#uploaded-files").append()
            });
        },
        
        progressall: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('#progress .bar').css(
	            'width',
	            progress + '%'
	        );
   		},
   		
		dropZone: $('#dropzone')
    })/*.on('fileuploadadd', function(e, data) {
    	var previewClass = "imagePreview_" + new Date().getTime();
    	var file = data.files[0];
    	$("#uploaded-files").append(
        		$('<tr/>')
        		.append($('<td/>').addClass(previewClass))
        		.append($('<td/>').text(file.name))
        		.append($('<td/>').text(file.size))
        		.append($('<td/>').text(file.type))
        		.append($('<td/>').html("<a href='#'>Click</a>"))
        		);
    	var loadingImage = loadImage(
	        	file,
		        function (img) {
		            $('.' + previewClass).html(img);
		        },
			    {maxWidth: 100,maxHeight: 100 // Options
            });
    })*/;
    /*
     * var loadingImage = loadImage(
    		        	file,
    			        function (img) {
    		        		console.info(img);
    			            $('.' + previewClass).html(img);
    			        },
        			    {maxWidth: 150,maxHeight: 150 // Options
    	            });
     */
});