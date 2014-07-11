/**
 * @author baiyanwei 2012-9-9
 */
//select department with sub window 
function selectDepartmentDialog(ppName, disPpName,roleName) {
	try {
		var subParemeter = new Array();
		subParemeter[0] = window;
		subParemeter[1] = document;
		subParemeter[2] = document.getElementById(ppName);
		subParemeter[3] = document.getElementById(disPpName);
		var winSetStr = "status:no;dialogWidth:800px;dialogHeight:600px,resizable:yes,scroll:yes,help:no,unadorned:yes";
		//
		var pageurl = "../util/department_tree.jsp?roleName="+roleName;
		showModalDialog(pageurl, subParemeter, winSetStr);
	} catch (e) {
		alert(e);
	}
}
function selectUserByDepartmentDialog(ppName, disPpName,department) {
	try {
		var departmentInput=document.getElementById(department)
		if(departmentInput.value==""||departmentInput.value==null){
			alert("请选择"+departmentInput.getAttribute("hint"));
			return;
		}
		var subParemeter = new Array();
		subParemeter[0] = window;
		subParemeter[1] = document;
		subParemeter[2] = document.getElementById(ppName);
		subParemeter[3] = document.getElementById(disPpName);
		var winSetStr = "status:no;dialogWidth:800px;dialogHeight:600px,resizable:yes,scroll:yes,help:no,unadorned:yes";
		//
		var pageurl = "../util/user_selector.jsp?departmentID="+departmentInput.value;
		showModalDialog(pageurl, subParemeter, winSetStr);
	} catch (e) {
		alert(e);
	}
}
function constraintDimSubSelectDialog(ppName, disPpName, dimClassName,
		consPpName, parentPpName, reqpath) {
	// 处理代约束列表类型属性的对象选择
	var subParemeter = new Array();
	subParemeter[0] = window;
	subParemeter[1] = document;
	subParemeter[2] = document.getElementById(ppName);
	subParemeter[3] = document.getElementById(disPpName);
	var userStr = "";
	if (document.getElementById("subPageUserAccount") != null) {
		userStr = "&userAccount="
				+ document.getElementById("subPageUserAccount").value;
	}
	var winSetStr = "status:no;dialogWidth:800px;dialogHeight:600px,resizable:yes,scroll:yes,help:no,unadorned:yes";
	if (document.getElementById(consPpName) == null) {
		alert("约束定义有问题，没有发现" + consPpName);
		return;
	}
	var consPpValue = document.getElementById(consPpName).value;
	if (consPpValue == null || consPpValue == "" || consPpValue == "0"
			|| consPpValue == "null") {
		alert("\u8bf7\u5148\u9009\u62e9 "
				+ document.getElementById(consPpName + "_display")
						.getAttribute("hint") + " \u5c5e\u6027!");
		return;
	}
	showModalDialog(reqpath
			+ "jsp/content/subpage/dimsubselect.jsp?dimClassName="
			+ dimClassName + "&dimPpName=" + parentPpName + "&dimPpValue="
			+ consPpValue + userStr, subParemeter, winSetStr);
}
function subPageInputArea(ppName, ppDescr, isReadOnly, isAllowNull, reqpath) {
	// 用来处理字符串属性太长提供用户弹出窗口输入
	var subParemeter = new Array();
	subParemeter[0] = window;
	subParemeter[1] = document;
	subParemeter[2] = document.getElementById(ppName);
	subParemeter[3] = isReadOnly;
	subParemeter[4] = isAllowNull;
	var winSetStr = "status:no;dialogWidth:400px;dialogHeight:300px,resizable:yes,scroll:yes,help:no,unadorned:yes";
	// var pageurl = reqpath + "jsp/content/subpage/inputarea.jsp?ppDescr=" +
	// ppDescr;alert(ppDescr);//修改乱码标题
	var pageurl = reqpath + "jsp/content/subpage/inputarea.jsp?ppDescr="
			+ encodeURI(encodeURI(ppDescr));
	showModalDialog(pageurl, subParemeter, winSetStr);
}