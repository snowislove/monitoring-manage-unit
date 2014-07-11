//FORM字段检查
function checkOwnRule(objfrm){
    var inputObj = objfrm.getElementsByTagName("input");
    var firstErrInput;
    var errText="";
    var lineStr="\n";
    for (i = 0; i < inputObj.length; i++) {
        if (!inputObj[i].getAttribute("hint") || inputObj[i].getAttribute("hint") == "") {
            strHint = inputObj[i].name;
        }
        else {
            strHint = inputObj[i].getAttribute("hint");
        }
        //是否为空
        if (inputObj[i].getAttribute("allowNull") == "false") {
            if (check_allownull(inputObj[i].value)) {
                errText=errText+strHint + " 不能为空。"+lineStr;
                if (inputObj[i].name != "resAttachment") {
                   if(firstErrInput==null){
                   		firstErrInput=inputObj[i];
                   }
                }
            }
        }
        //验证输入是否为数字型
        if (inputObj[i].getAttribute("isDigital") == "true") {
            if (check_isDigital(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要是一个数字型。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //验证输入是否是整型	
        if (inputObj[i].getAttribute("isInt") == "true") {
            if (check_isInt(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要是一个整型。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        if (inputObj[i].getAttribute("isLong") == "true") {
            if (check_isLong(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要是一个长整型。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //验证输入是否为端口型
        if (inputObj[i].getAttribute("isPortNo") == "true") {
            if (check_isPortNo(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要是一个合法端口[1-65535]。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //验证输入项是否是FLOAT型
        if (inputObj[i].getAttribute("isFloat") == "true") {
            if (check_isFloat(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要一个FLOAT值。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
         //验证输入项是否是FLOAT型
        if (inputObj[i].getAttribute("isDouble") == "true") {
            if (check_isFloat(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要一个Double值。"+lineStr;
               	if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //验证输入项是否为V4IP型
        if (inputObj[i].getAttribute("isIp") == "true") {
            if (check_isIp(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要一个合法的IP型值。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //验证输入项是否是0
        if (inputObj[i].getAttribute("allowZero") == "false") {
            if (inputObj[i].value == 0) {
                errText=errText+strHint + " 需要一个非0的值。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //是否为IP v4掩码
        if (inputObj[i].getAttribute("isMaskBit") == "true") {
            if (check_isMaskBit(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要一个合法的IP地址掩码[1-32]。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //验证输入法是否为日期型
        if (inputObj[i].getAttribute("isDate") == "true") {
            if (check_isDate(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要一个合法的日期型值[YYYY-MM-DD]。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //判断是否为MAIL型
        if (inputObj[i].getAttribute("isMail") == "true") {
            if (check_isMail(inputObj[i].value) == false) {
                errText=errText+strHint + " 需要一个合法的MAIL地址。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //判断是否为全法的MAC地址
        if (inputObj[i].getAttribute("isMac") == "true") {
            if (check_isMac(inputObj[i].value) == false) {
                errText=errText+strHint + inputObj[i].value + " 需要一个全法的MAC地址值。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
        //判断是否为域名型
        if (inputObj[i].getAttribute("isArea") == "true") {
            if (check_isArea(inputObj[i].value) == false) {
                errText=errText+strHint + inputObj[i].value + " 需要一个合法的域名值。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=inputObj[i];
                }
            }
        }
    }
    //SELECT 判断
    var selectObj = objfrm.getElementsByTagName("select");
    for (i = 0; i < selectObj.length; i++) {
        if (!selectObj[i].getAttribute("hint") || selectObj[i].getAttribute("hint") == "") {
            selectHint = selectObj[i].name;
        }
        else {
            selectHint = selectObj[i].getAttribute("hint");
        }
        if (selectObj[i].getAttribute("allowNull") == "false") {
            if (selectObj[i].value == "") {
                errText=errText+selectHint + " 需要一个非空值。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=selectObj[i];
                }
            }
        }
    }
    //textArea判断
    var textareaObj = objfrm.getElementsByTagName("textarea");
    for (i = 0; i < textareaObj.length; i++) {
        if (!textareaObj[i].getAttribute("hint") || textareaObj[i].getAttribute("hint") == "") {
            selectHint = textareaObj[i].name;
        }
        else {
            selectHint = textareaObj[i].getAttribute("hint");
        }
        if (textareaObj[i].getAttribute("allowNull") == "false") {
            if (textareaObj[i].value == "") {
                errText=errText+selectHint + " 需要一个非空值。"+lineStr;
                if(firstErrInput==null){
                   	firstErrInput=textareaObj[i];
                }
            }
        }
    }
    if(firstErrInput==null){
   	   return true;
    }else{
       alert(errText);
       firstErrInput.focus();
       return false;
    }
}

/////////////////检查单个字段
function checkppvalue(ppname){
    var checkpp = document.getElementById(ppname);
    if (!checkpp.getAttribute("hint") || checkpp.getAttribute("hint") == "") {
        strHint = checkpp.name;
    }
    else {
        strHint = checkpp.getAttribute("hint");
    }
    //是否为空
    if (checkpp.getAttribute("allowNull") == "false") {
        if (check_allownull(checkpp.value)) {
            alert(strHint + "  不能为空。");
            checkpp.focus();
            return false;
        }
    }
    //验证输入是否为数字型
    if (checkpp.getAttribute("isDigital") == "true") {
        if (check_isDigital(checkpp.value) == false) {
            alert(strHint + " 需要是一个数字型。");
            checkpp.focus();
            return false;
        }
    }
    //验证输入是否是整型	
    if (checkpp.getAttribute("isInt") == "true") {
        if (check_isInt(checkpp.value) == false) {
            alert(strHint + " 需要是一个整型。");
            checkpp.focus();
            return false;
        }
    }
    //验证输入为LONG
    if (checkpp.getAttribute("isLong") == "true") {
        if (check_isInt(checkpp.value) == false) {
            alert(strHint + " 需要是一个整数。");
            checkpp.focus();
            return false;
        }
    }
    //验证输入是否为端口型
    if (checkpp.getAttribute("isPortNo") == "true") {
        if (check_isPortNo(checkpp.value) == false) {
            alert(strHint + " 需要是一个合法端口[1-65535]。");
            checkpp.focus();
            return false;
        }
    }
    //验证输入项是否是FLOAT型
    if (checkpp.getAttribute("isFloat") == "true") {
        if (check_isFloat(checkpp.value) == false) {
            alert(strHint + " 需要一个FLOAT值。");
            checkpp.focus();
            return false;
        }
    }
    //验证输入项是否为V4IP型
    if (checkpp.getAttribute("isIp") == "true") {
        if (check_isIp(checkpp.value) == false) {
            alert(strHint + " 需要一个合法的IP型值。");
            checkpp.focus();
            return false;
        }
    }
    //验证输入项是否是0
    if (checkpp.getAttribute("allowZero") == "false") {
        if (checkpp.value == 0) {
            alert(strHint + " 需要一个非0的值。");
            checkpp.focus();
            return false;
        }
    }
    //是否为IP v4掩码
    if (checkpp.getAttribute("isMaskBit") == "true") {
        if (check_isMaskBit(checkpp.value) == false) {
            alert(strHint + " 需要一个合法的IP地址掩码[1-32]。");
            checkpp.focus();
            return false;
        }
    }
    //验证输入法是否为日期型
    if (checkpp.getAttribute("isDate") == "true") {
        if (check_isDate(checkpp.value) == false) {
            alert(strHint + " 需要一个合法的日期型值[YYYY-MM-DD]。");
            checkpp.focus();
            return false;
        }
    }
    //判断是否为MAIL型
    if (checkpp.getAttribute("isMail") == "true") {
        if (check_isMail(checkpp.value) == false) {
            alert(strHint + " 需要一个合法的MAIL地址。");
            checkpp.focus();
            return false;
        }
    }
    //判断是否为全法的MAC地址
    if (checkpp.getAttribute("isMac") == "true") {
        if (check_isMac(checkpp.value) == false) {
            alert(strHint + checkpp.value + " 需要一个全法的MAC地址值。");
            checkpp.focus();
            return false;
        }
    }
    //判断是否为域名型
    if (checkpp.getAttribute("isArea") == "true") {
        if (check_isArea(checkpp.value) == false) {
            alert(strHint + checkpp.value + " 需要一个合法的域名值。");
            checkpp.focus();
            return false;
        }
    }
    if(document.getElementById(ppname+"_display")!=undefined){
    	return checkppvalue(ppname+"_display");
    }
    //判断约束内容
    
    return true;
}

////////////////////////////////////////////////////////////////////功能检查
//检查是否为数值型数据
function check_isDigital(inputvalue){
    if (inputvalue != "") {
        if (isNaN(inputvalue)) {
            return false;
        }
    }
    return true;
}

//检查是否是Int型
function check_isInt(inputvalue){
    if (inputvalue != "") {
        if (isNaN(inputvalue)) {
            return false;
        }
        if (inputvalue.indexOf(".") >= 0) {
            return false;
        }
    }
    return true;
}

//检查是否是Long型
function check_isLong(inputvalue){
    if (inputvalue != "") {
        if (isNaN(inputvalue)) {
            return false;
        }
        if (inputvalue.indexOf(".") >= 0) {
            return false;
        }
    }
    return true;
}

//检查是否为isPortNo
function check_isPortNo(inputvalue){
    if (inputvalue != "") {
        if (isNaN(inputvalue)) {
            return false;
        }
        if (inputvalue.indexOf(".") >= 0) {
            return false;
        }
        if (inputvalue < 0 || inputvalue > 65535) {
            return false;
        }
        if (inputvalue.indexOf("0") == 0) {
            return false;
        }
    }
    return true;
}

//检查isFloat
function check_isFloat(inputval){
    if (inputval != "") {
        if (isNaN(inputval)) {
            return false;
        }
        //不判断是否代点
        //if (inputval.indexOf(".") < 0) {
           // return false;
       // }
    }
    return true;
}

//检查isMaskBit
function check_isMaskBit(inputval){
    if (inputval != "") {
        if (isNaN(inputval)) {
            return false;
        }
        if (inputval.indexOf(".") >= 0) {
            return false;
        }
        if (inputval < 1 || inputval > 32) {
            return false;
        }
    }
    return true;
}

//检查日期
function check_isDate(str){
    if (str != "") {
        var reg = /^(\d+)-(\d{1,2})-(\d{1,2})\s(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
        var r = str.match(reg);
        if (r == null) {
            return false;
        }
        r[2] = r[2] - 1;
        var d = new Date(r[1], r[2], r[3], r[4], r[5], r[6]);
        if (d.getFullYear() != r[1]) {
            return false;
        }
        if (d.getMonth() != r[2]) {
            return false;
        }
        if (d.getDate() != r[3]) {
            return false;
        }
        if (d.getHours() != r[4]) {
            return false;
        }
        if (d.getMinutes() != r[5]) {
            return false;
        }
        if (d.getSeconds() != r[6]) {
            return false;
        }
    }
    return true;
}

//检查isIp
function check_isIp(inputval){
    if (inputval != "") {
        var pattern = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$/;
        flag_ip = pattern.test(inputval);
        if (flag_ip) {
            return true;
        }
        else {
            return false;
        }
    }
    return true;
}

//检查MAIL
function check_isMail(email){
    if (email != "") {
        var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+$/;
        flag = pattern.test(email);
        if (flag) {
            return true;
        }
        else {
            return false;
        }
    }
    return true;
}

//检查文档长度
function counttextlen(ttarea){
    if (ttarea.value.length > 100) {
        alert("长度需要在100之内。");
        ttarea.value = ttarea.value.substring(0, 100);
    }
    return true;
}

//检查是否是MAC地址
function check_isMac(chkstr){
    if (inputObj[i].value != "") {
        var pattern = /^([0-9a-fA-F]{2})(([\s-][0-9a-fA-F]{2}){5})$/;
        if (pattern.test(chkstr)) {
            return true;
        }
        else {
            return false;
        }
    }
    return true;
}

//检查是否是域名
function check_isArea(str){
    if (inputObj[i].value != "") {
        var regs = /^[a-z0-9]([a-z0-9-]+\.){2,}[a-z]{2,4}$/;
        if (regs.test(str)) {
            return true;
        }
        else {
            return false;
        }
    }
    return true;
}

//检查是否为空
function check_allownull(str){
    if (str == null || str == "") {
        return true;
    }
    var regs = /^\s+$/;
    return regs.test(str);
}

//检查是否为公网IP
function checkispublicip(val){
    var ipstr = val.value;
    if (ipstr == null || ipstr == "") {
        return false;
    }
    else {
        if (check_isIp(ipstr) == false) {
            alert(ipstr + " 需要一个合法的公网IP地址值。");
            val.value = "";
            val.focus();
            return false;
        }
        if (ipstr.indexOf("172.") == 0) {
            var iparr = ipstr.split(".");
            if ((iparr[1] - 1 + 1) >= 16 && (iparr[1] - 1 + 1) <= 32) {
                alert(ipstr + " 需要一个合法的公网IP地址值。");
                val.value = "";
                val.focus();
                return false;
            }
        }
        if (ipstr.indexOf("10.") == 0 || ipstr.indexOf("010.") == 0 || ipstr.indexOf("127.") == 0 || ipstr.indexOf("192.168.") == 0 || ipstr.indexOf("169.254.") == 0) {
            alert(ipstr + " 需要一个合法的公网IP地址值。");
            val.value = "";
            val.focus();
            return false;
        }
    }
    return true;
}

//检查是否为私网IP
function checkisprivateip(val){
    var ipstr = val.value;
    if (ipstr == null || ipstr == "") {
        return false;
    }
    else {
        if (check_isIp(ipstr) == false) {
            alert(ipstr + " 需要一个合法的私网IP地址值。");
            val.value = "";
            val.focus();
            return false;
        }
        if (ipstr.indexOf("172.") == 0) {
            var iparr = ipstr.split(".");
            if ((iparr[1] - 1 + 1) >= 16 && (iparr[1] - 1 + 1) <= 32) {
                return true;
            }
            else {
                alert(ipstr + " 需要一个合法的私网IP地址值。");
                val.value = "";
                val.focus();
                return false;
            }
        }
        if (ipstr.indexOf("10.") == 0 || ipstr.indexOf("010.") == 0 || ipstr.indexOf("127.") == 0 || ipstr.indexOf("192.168.") == 0 || ipstr.indexOf("169.254.") == 0) {
            return true;
        }
        else {
            alert(ipstr + " 需要一个合法的私网IP地址值。");
            val.value = "";
            val.focus();
            return false;
        }
    }
    return true;
}

//判断日期先后
function check_adate_bdate(stra, strb){
    var reg = /^(\d+)-(\d{1,2})-(\d{1,2})\s(\d{1,2}):(\d{1,2}):(\d{1,2})$/;
    var ra = stra.match(reg);
    var rb = strb.match(reg);
    if (ra == null) {
        return 4;
    }
    if (rb == null) {
        return 5;
    }
    var adtate = new Date(ra[1], ra[2], ra[3], ra[4], ra[5], ra[6]);
    var bdtate = new Date(rb[1], rb[2], rb[3], rb[4], rb[5], rb[6]);
    if (Date.parse(adtate) - Date.parse(bdtate) == 0) {
        return 2;
    }
    if (Date.parse(adtate) - Date.parse(bdtate) < 0) {
        return 1;
    }
    if (Date.parse(adtate) - Date.parse(bdtate) > 0) {
        return 3;
    }
}

////////////////////////////////////////////////////////////////////////////
//给所有SELECT ORDER
function sortAllSelect(objfrm){
    objfrm.reset();
    var selectObj = objfrm.getElementsByTagName("select");
    for (i = 0; i < selectObj.length; i++) {
        sortSelect(selectObj[i]);
    }
}

//INPUT取得焦点
function inputOnFocusEve(inputA){
    if (inputA.getAttribute("className") != "alert") {
        inputA.setAttribute("className", "focus");
    }
}

//INPUT焦点离开
function inputOnBlurEve(inputA){
    if (inputA.getAttribute("className") != "alert") {
        inputA.setAttribute("className", "blur");
    }
}

// SELECT 排序
function sortSelect(oSel){
    var ln = oSel.options.length;
    var arr = new Array(); // 排序容器
    if (ln < 2) {
        // alert("sortSelect "+ln);
        return;
    }
    var selectno = oSel.selectedIndex;
    var selectvalue = oSel.options[oSel.selectedIndex].value;
    var selecttext = oSel.options[oSel.selectedIndex].text;
    // var inforstr=oSel.options[0].text;
    // 将select中的所有option的value值将保存在Array中
    var realint = 0;
    for (var i = 0; i < ln; i++) {
    
        // 如果需要对option中的文本排序，可以改为arr[i] = oSel.options[i].;
        if (selectno != i) {
            arr[realint] = oSel.options[i].text + "#@#" + oSel.options[i].value;
            realint++;
        }
    }
    arr.sort(); // 开始排序
    // 清空Select中全部Option
    while (ln--) {
        oSel.options[ln] = null;
    }
    
    // 将排序后的数组重新添加到Select中
    // oSel.add(new Option(inforstr,""));
    // var afterselectno;
    oSel.add(new Option(selecttext, selectvalue));
    oSel.selectedIndex = 0;
    for (i = 0; i < arr.length; i++) {
        var infor_str = arr[i].split("#@#");
        // if(selectvalue==infor_str[1]){
        // afterselectno=i;
        // }
        oSel.add(new Option(infor_str[0], infor_str[1]));
    }
    // oSel.selectedIndex=afterselectno;
}

/////////////////////////////
function check_uploadfile(upfile, att){
    //检查上传文件
    if(upfile==null||att==null){
    	return true;
    }
    var attVal = att.value;
    var ext = "*.zip,*.ZIP";
    if (attVal == null || attVal == ""||1==1){ 
        return true;
     }
    if (attVal.indexOf(".") == -1) {
        alert("只允许上传与zip文件!");
        upfile.value = "";
        att.value = "";
        return false;
    }
    attVal = attVal.substr(attVal.lastIndexOf("."));
    if (ext.indexOf("*" + attVal) == -1) {
        alert("只允许上传与zip文件!");
        upfile.value = "";
        att.value = "";
        return false;
    }
}

////////////////////////
//约束检查
function constraint_checktype2(inputObj, minVal, maxVal){
    //检查值范围约束
    if (inputObj.value != "") {
        var inputVal = inputObj.value;
        var strHint = inputObj.getAttribute("hint");
        if (check_isDigital(inputObj.value)) {
            if (minVal > inputVal || maxVal < inputVal) {
                alert(strHint + " 需要在" + minVal + "至" + maxVal + "之间的值");
                inputObj.value = "";
                inputObj.focus();
            }
        }
        else {
            alert(strHint + " 需要是一个数值。");
            inputObj.value = "";
            inputObj.focus();
        }
    }
}

function constraint_checktype3(inputObj, regStr){
    //检查格式
    if (inputObj.value != "") {
        var regs = regStr;
        if (regs.test(inputObj.value)) {
        
        }
        else {
            var strHint = inputObj.getAttribute("hint");
            alert(strHint + "格式不正确!");
            inputObj.value = "";
            inputObj.focus();
        }
    }
}
///2009-12-1
//控制附件属性不可以被输入，但可以使用退格
//onkeydown="return attachmentInputCtrl(this,event)" onpaste="return false" ondragenter="return false" style="ime-mode:Disabled" 
function attachmentInputCtrl(attInput,e){

 //backspace key input only  IE OR FIREFOX
 var key = window.event ? e.keyCode:e.which;
 
 if(key==8){
 	//when input backspace key need clear attrib value
 	if(attInput.type=="file"){
 		//clear input type=file value
 		attInput.select();   
		document.selection.clear(); 
 	}else{
		attInput.value="";
	}
 }
 return false;
}
function createRequestObject() {
							//ajax 导步通信
	var ro;
	var browser = navigator.appName;
	if (browser == "Microsoft Internet Explorer") {
		ro = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		ro = new XMLHttpRequest();
	}
	return ro;
}
function closeSelectDiv(selectDivID, inputDataID) {
	var selectDiv = document.getElementById(selectDivID);
	var inputData = document.getElementById(inputDataID);
	if (document.activeElement.id.indexOf(inputData.getAttribute("valueName"))==0) {
		var index = document.activeElement.getAttribute("index");
		pointSelectEml(index, inputDataID, selectDivID);
	}
	selectDiv.style.display = "none";
	selectDiv.innerHTML = "";
	selectDiv.setAttribute("selectMax", "0");
	selectDiv.setAttribute("selectIndex", "-1");
	if (inputData.getAttribute("isClearVal") == "true") {
		inputData.value = "";
		document.getElementById(inputData.getAttribute("valueName")).value = "";
	}
}
function setDefaultSelectInputDiv(inputData,selectDiv){
		var corrected_value=0;
		if(document.getElementById("mune_div")!=null){
			corrected_value=document.getElementById("mune_div").offsetHeight+4;
		}else{
			corrected_value=4;
		}
		var divh = inputData.offsetHeight;
		var divw = inputData.offsetWidth;
		var divx = inputData.offsetLeft;
		var divy = inputData.offsetTop;
		var my_array=ShowPos(inputData);
		selectDiv.style.height = divh + "px";
		selectDiv.style.width = divw + "px";
		selectDiv.style.top = (my_array[0]-corrected_value)+ "px";
		selectDiv.style.left = my_array[1] + "px";
		selectDiv.style.height = "200px";
		selectDiv.style.display = "block";
		selectDiv.style.background = "#f9f9f9";
}
function changesDivText(inputData, e, classTitle,basePath) {
	var key = window.event ? e.keyCode : e.which;
					//39 37,13
	if (key == 39 || key == 37) {
		return;
	}
	
	var selectDiv = document.getElementById(inputData.getAttribute("valueName") + "_selectDataDiv");
	if (selectDiv.style.display == "none"&&(inputData.value!=null&&inputData.value!="")) {
		setDefaultSelectInputDiv(inputData,selectDiv);
	}
	
	if (key == 40) {
		var currSelIndex = selectDiv.getAttribute("selectIndex");
		var maxCouter = selectDiv.getAttribute("selectMax");
		if ((currSelIndex - 1 + 2) >= maxCouter) {
			currSelIndex = -1;
		}
		pointSelectEml(currSelIndex - 1 + 2, inputData.id, selectDiv.id);
	} else {
		if (key == 38) {
			var currSelIndex = selectDiv.getAttribute("selectIndex");
			if (currSelIndex == "0") {
				currSelIndex = selectDiv.getAttribute("selectMax");
			}
			pointSelectEml(currSelIndex - 1, inputData.id, selectDiv.id);
		} else {
			if (key == 13) {
				closeSelectDiv(selectDiv.id, inputData.id);
			} else {
				inputData.setAttribute("isClearVal", "true");
				uqtData(selectDiv.id, inputData.id, classTitle,basePath);
			}
		}
	}
}
function uqtData(selectDivID, inputDataID, classTitle,basePath) {
	var selectDiv = document.getElementById(selectDivID);
	var inputData = document.getElementById(inputDataID);
	//document.getElementById("testDiv").innerHTML = "test......";
	if (inputData.value != null && inputData.value != "") {
		var http = createRequestObject();
		http.open("get", basePath+"pagedata/inputorselect.jsp?classTitle=" + classTitle + "&key=" + inputData.value);
		http.onreadystatechange = function handleResponse() {
			if (http.readyState == 4) {
				var infor = http.responseText;
				//document.getElementById("testDiv").innerHTML = infor;
				if (infor != null && infor != "") {
					var reqData = infor.split("###");
					selectDiv.innerHTML = "";
					for (var i = 0; i < reqData.length; i++) {
							selectDiv.innerHTML = selectDiv.innerHTML + "<div class=\"divStyle\" id=\""+inputData.getAttribute("valueName")+"_select_span_"+ i + "\" onMouseOver=\"setSelectColor(this,'" + selectDiv.id + "'," + i + ",'#CCCCCC')\" onMouseOut=\"setSelectColor(this,'" + selectDiv.id + "'," + i + ",'')\" index=\""+i+"\" valueText=\""+reqData[i]+"\" align=\"left\">" + reqData[i].split("#@#")[0] + "</div>";
					}
					selectDiv.innerHTML = selectDiv.innerHTML + "<div class=\"closeButton\" onClick=\"closeSelectDiv('" + selectDiv.id + "','"+inputDataID+"')\"><a>关闭</a></div>";
					selectDiv.setAttribute("selectMax", reqData.length);
					selectDiv.setAttribute("selectIndex", "-1");
					selectDiv.style.height = (reqData.length*20+18)+"px";
				} else {
					selectDiv.innerHTML = "";
					closeSelectDiv(selectDivID, inputDataID);
				}
			}
		};
		http.send(null);
	} else {
		selectDiv.innerHTML = "";
		//closeSelectDiv(selectDivID, inputDataID);
	}
}
function pointSelectEml(index, inputDataID, selectDivID) {
	var selectDiv = document.getElementById(selectDivID);
	var inputData = document.getElementById(inputDataID);
	var counter = selectDiv.getAttribute("selectMax");
	for (var i = 0; i < counter; i++) {
		var select_span_var = document.getElementById(inputData.getAttribute("valueName")+"_select_span_" + i);
		if (select_span_var == null) {
			continue;
		}
		if (i == index) {
			inputData.setAttribute("isClearVal", "false");
			select_span_var.style.background = "#6091ff";
			selectDiv.setAttribute("selectIndex", index);
			var inVal = select_span_var.getAttribute("valueText").split("#@#");
			inputData.value = inVal[0];
			document.getElementById(inputData.getAttribute("valueName")).value = inVal[1];
		} else {
			select_span_var.style.background = "#f8f8f8";
		}
	}
}
function setSelectColor(curInput, selectDiv, index, color) {
	var selectDiv = document.getElementById(selectDiv);
	var counter = selectDiv.getAttribute("selectMax");
	for (var i = 0; i < counter; i++) {
		var select_span_var = document.getElementById("select_span_" + i);
		if (select_span_var == null) {
			continue;
		}
		if (i == index) {
			selectDiv.setAttribute("selectIndex", index);
			select_span_var.style.background = "#6091ff";
		} else {
			select_span_var.style.background = "#f8f8f8";
		}
	}
}
 function   ShowPos(e){   
  var   t=e.offsetTop;   
  var   l=e.offsetLeft;   
  while(e=e.offsetParent){   
  t+=e.offsetTop;   
  l+=e.offsetLeft;   
  }
  var my_array = new Array();
   my_array[0]=t;
   my_array[1]=l;  
  //alert("top="+t+"/nleft="+l);
  return my_array;   
  }