

var ImgList = function(id, tfsPath, limitNum, isHide){
	this.id = "#" + id;
	
	this.uploadBtnSelector = this.id + "UploadBtn";
	
	this.picWrapClass = id + "PicWrap";
	this.btnDelPicClass = id + "BtnDelPic";
	
	this.picWrapSelector = "."+ id + "PicWrap";
	this.btnDelPicSelector = "."+ id + "BtnDelPic";
	this.isHide = isHide;
	
	this.limitNum = limitNum;
	this.tfsPath = tfsPath;
	
	this.init();
	this.bindUploadEvent();
	this.bindDelPicEvent();
	this.bindPicWrapEvent();
};

ImgList.prototype = {
	init: function(){
	},
	originalImage: "data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==",
	bindUploadEvent: function(){
		var _self = this;
		
    	$(_self.id).delegate(_self.uploadBtnSelector, 'change', function(){
	   		fileUpload(_self.uploadBtnSelector, 2, function(dataVal){
	   			
	   			var uploadNum = $(_self.picWrapSelector).length;
	    		if(uploadNum == _self.limitNum){
	    			layer.alert('最多上传'+ _self.limitNum +'张图片');
	    			return;
	    		}
	   			
	   			for(var key in dataVal.data){
	   				
	   				var path =  dataVal.data[key];
   					var picWrapContent = [];
   					picWrapContent.push('<div class="pic-wrap '+ _self.picWrapClass +'">');
   						picWrapContent.push('<img class="picUrlsUrl uploadImgFile dimg2" imgUrl="'+ path +'" isdel="false" src="'+ tfsRootPath + path +'" />');
   						picWrapContent.push('<button type="button" class="btn btn-del-pic '+ _self.btnDelPicClass +'" style="display:none;width:116px;">删除</button>');
   					picWrapContent.push('</div>');
   					
					$(_self.id).append(picWrapContent.join(""));
		        }
	   		}, 500);
    	});
    },
    bindDelPicEvent: function() {
    	var _self = this;
    	$(_self.id).delegate(_self.btnDelPicSelector, 'click', function(){
    		
    		if(_self.isHide){
    			$(this).parent().css("display", "none");
    			$(this).prev().attr("isdel", true);
    		}else{
    			$(this).parent().remove();
    		}
    	});
    },
    bindPicWrapEvent: function(){
    	var _self = this;
		$(_self.id).delegate(_self.picWrapSelector, 'mouseover mouseout', function(e){
			var imgUrl = $(this).find("img").attr("imgUrl");
		    if(!imgUrl){
    			return;
    		};
		    	
		    var type= e.type;
		    if(type == "mouseover"){
    			$(this).find("button").css("display", "");
	    	}else if(type == "mouseout"){
	    		$(this).find("button").css("display", "none");
    		}
		});
    },
    getPicUrls: function(){
    	
    	var picUrls = [];
    	var _self = this;
    	$(_self.picWrapSelector).each(function(){
    		picUrls.push($(this).find("img").attr("imgUrl"));
    	});
    	return picUrls;
    },
    getPicUrls2: function(){	
    	var picUrls = [];
    	var _self = this;
    	$(_self.picWrapSelector).each(function(){
    		var img = $(this).find("img");
    		var obj = {
    			"id": $(img).attr("pid") ? $(img).attr("pid") : 0,
    			"value": $(img).attr("imgUrl"), 	
    			"isdel": $(img).attr("isdel") 
    		};
    		picUrls.push(obj);
    	});
    	return picUrls;
    }
};