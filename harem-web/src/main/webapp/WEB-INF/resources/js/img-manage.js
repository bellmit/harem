(function($){
	
	Array.prototype.indexOf = function(val) {              
		for (var i = 0; i < this.length; i++) {  
			if (this[i] == val) return i;  
		}  
		return -1;  
	};

    Array.prototype.remove = function(val) {  
        var index = this.indexOf(val);  
        if (index > -1) {  
            this.splice(index, 1);  
        }  
    };  
	$.setimagesInputVal = function(options){
		var $this = $(this);
		var init = {
			itemClass : '.itemlist li',
			inputId : 'imgids'
		}
		var opts = $.extend(init,options);
		var objArr = $("#"+opts.inputId).val()==""?[]:JSON.parse($("#"+opts.inputId).val());
		$(opts.itemClass).each(function(i){
			var itemObj = {}
			if($(this).attr("data-value")==0){
				itemObj.id = 0;
				itemObj.index = $(this).index();
				itemObj.src = $(this).find("img").attr("src");
				itemObj.istop = false;
				itemObj.isdel = false;
				itemObj.modify = false;
				itemObj.istop = _istop?false:true;
				objArr.push(itemObj);
			}
		});
		var jsonStr = objArr.length>0?JSON.stringify(objArr):"";
		$("#"+opts.inputId).val(jsonStr);		
	};
	$.fn.setimages = function(options){
		var $this = $(this);
		var init = {
			settopClass : '.settop',
			delClass : '.delimg',
			itemClass : '.itemlist li',
			formId : '#saveimgform',
			inputId : 'imgids',
			inputName : 'imgids',
			topNum : 3,
			delCallback : function(){},
			settopCallback : function(){}
		}
		var opts = $.extend(init,options);
		var input = '<input type="hidden" name="'+opts.inputName+'" id="'+opts.inputId+'" value="" />';
		
		var objArr,topFlag = {};
		
		if($("#"+opts.inputId).length == 0 ){
			objArr = [];
			$(opts.formId).append(input);
		}else{
			objArr = $("#"+opts.inputId).val()==""?[]:JSON.parse($("#"+opts.inputId).val());
		}
		
		
		var getObjIndex = function(index){
			for(var i=0;i<objArr.length;i++){
				if(objArr[i].index==index){
					return i;
				}
			}
			return -1;
		}
		
		var getTopNumber = function(){
			var num = 0;
			$(opts.itemClass).each(function(i){
				if($(this).attr("istop")) num++;
			});
			return num;
		}
		
		var setInputVal = function(){
			var jsonStr = objArr.length>0?JSON.stringify(objArr):"";
			$("#"+opts.inputId).val(jsonStr);
		}
		
		var setItemInitVal = function(){
			$(opts.itemClass).each(function(i){
				var _istop = $(this).attr("istop")?true:false;
				var _id = $(this).attr("data-value");
				var _src = $(this).find("img").attr("src");
				topFlag[i]=_istop;
			});
		}
		
		
		var setTopEvent = function(){
			
			var itemObj = {}
			var _parent = $(this).closest("li");
			var _index = _parent.index();
			var _arrIndex = getObjIndex(_index);
			var _istop = _parent.attr("istop")?true:false;
			var _id = _parent.attr("data-value")?_parent.attr("data-value"):0;
			
			if(!_istop && getTopNumber()>=opts.topNum){
				alert("您只能置顶"+opts.topNum+"个图片");
				return false;
			}
			
			objArr = $("#"+opts.inputId).val()==""?[]:JSON.parse($("#"+opts.inputId).val());
			
			if(topFlag[_index]==_istop){
				itemObj.id = _id;
				itemObj.index = _index;
				itemObj.src = _parent.find("img").attr("src");
				itemObj.isdel = false;
				itemObj.modify = true;
				itemObj.istop = _istop?false:true;
				objArr.push(itemObj);
			}else{	
				if(_arrIndex>-1) objArr.splice(_arrIndex,1);
			}
			
			setInputVal();
			opts.settopCallback($(this),_parent);
			return false;
		}
		
		var delImgEvent = function(){
			var itemObj = {};
			var _parent = $(this).closest("li");
			var _index = _parent.index();
			var _arrIndex = getObjIndex(_index);
			
			itemObj.id = _parent.attr("data-value")?_parent.attr("data-value"):0;
			itemObj.index = _index;
			itemObj.src = _parent.find("img").attr("src");
			itemObj.modify = false;
			itemObj.isdel = true;
			
			objArr = $("#"+opts.inputId).val()==""?[]:JSON.parse($("#"+opts.inputId).val());
			
			if(_arrIndex==-1){
				objArr.push(itemObj);
			}else{
				objArr[_arrIndex] = itemObj;
			}
			
			setInputVal();
			opts.delCallback($(this),_parent);
			return false;
		}
		
		setItemInitVal();
		
		$(document).on("click",opts.settopClass,setTopEvent);
		$(document).on("click",opts.delClass,delImgEvent);
		
	}

})(jQuery);

$(function(){
	$("#img-manage").setimages({
		settopClass : '.settop',
		delClass : '.delimg',
		itemClass : '.itemlist li',
		formId : '#saveimgform',
		inputId : 'imgids',
		inputName : 'imgids',
		topNum : 3,		
		delCallback : function($this){
			var _parent = $this.closest("li");
			_parent.remove();
		},
		settopCallback : function($this){
			var _parent = $this.closest("li");
			if(_parent.attr("istop")){
				_parent.removeAttr("istop");
				$this.html("置顶");
			}else{
				_parent.attr("istop",true);
				$this.html("取消");
			}
		}
	});
	var batchCallBack = function(dataVal){
		if(dataVal && dataVal.status == 200){
			for(key in dataVal.data){
				if(dataVal.data.hasOwnProperty(key)){
					var addImgs = '<li data-value="0" istop="false">'
						+'<a href="" class="img"><img src="' + tfsRootPath + dataVal.data[key] + '" width="240"/></a>'
						+'<span><a href="javascript:" class="settop">置顶</a><a href="javascript:" class="delimg">删除</a></span>'
						+'</li>';

					$(".itemlist").append(addImgs);
				}
			}
			$.setimagesInputVal();
		}
	}
	/****************批量上传*****************/
	$(document).delegate("#batchUploadBtn",'change',function(){
		fileUpload('#batchUploadBtn',2,batchCallBack);
	})
});	