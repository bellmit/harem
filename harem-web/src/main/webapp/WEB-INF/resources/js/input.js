// 只能输入浮点数 START
$(document).delegate(".double-only","keyup",function(){
	if(isNaN(this.value)) {
		document.execCommand('undo');
	}
});
$(document).delegate(".double-only","onafterpaste",function(){
	if(isNaN(this.value)) {
		document.execCommand('undo');
	}
});
// 只能输入浮点数 END
// 只能输入整数 START
$(document).delegate(".int-only","keyup",function(){
	if(isNaN(this.value)) {
		document.execCommand('undo');
	} else {
		this.value = parseInt(this.value);
	}
});
$(document).delegate(".int-only","onafterpaste",function(){
	if(isNaN(this.value)) {
		document.execCommand('undo');
	} else {
		this.value = parseInt(this.value);
	}
});
// 只能输入整数 END