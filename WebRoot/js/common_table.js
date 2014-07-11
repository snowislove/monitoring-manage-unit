
/*******************************************************************************
 * author:baiyanwei date:2009-6-16 project:ULTRA-CMDB by ultrapower about: table
 * util js
 ******************************************************************************/
// tab sort method
function sortTable(iCol, sDataType, tabNameID) {
	var oTable = document.getElementById(tabNameID);
	if (oTable == null) {
		return;
	}
	var oTBody = oTable.tBodies[0];
	var colDataRows = oTBody.rows;
	var aTRs = new Array;
	for (var i = 0; i < colDataRows.length; i++) {
		aTRs[i] = colDataRows[i];
	}
	if (oTable.sortCol == iCol) {
		aTRs.reverse();
	} else {
		aTRs.sort(generateCompareTRs(iCol, sDataType));
	}
	var oFragment = document.createDocumentFragment();
	for (var i = 0; i < aTRs.length; i++) {
		oFragment.appendChild(aTRs[i]);
	}
	oTBody.appendChild(oFragment);
	oTable.sortCol = iCol;
}
function convert(sValue, sDataType) {
	if (sDataType == null) {
		return null;
	}
	switch (sDataType) {
	  case "int":
		return parseInt(sValue);
	  case "float":
		return parseFloat(sValue);
	  case "date":
		return new Date(Date.parse(sValue));
	  case "url":
		return sValue.toString();
	  case "string":
		return sValue.toString();
	  default:
		return sValue.toString();
	}
}
function generateCompareTRs(iCol, sDataType) {
	return function compareTRs(oTR1, oTR2) {
		if (oTR1.cells[iCol].firstChild == null && oTR2.cells[iCol].firstChild == null) {
			return 0;
		}
		if (oTR1.cells[iCol].firstChild == null && oTR2.cells[iCol].firstChild != null) {
			return -1;
		}
		if (oTR1.cells[iCol].firstChild != null && oTR2.cells[iCol].firstChild == null) {
			return 1;
		}
		var vValue1 = convert(oTR1.cells[iCol].firstChild.nodeValue, sDataType);
		var vValue2 = convert(oTR2.cells[iCol].firstChild.nodeValue, sDataType);
		if (vValue1 < vValue2) {
			return -1;
		} else {
			if (vValue1 > vValue2) {
				return 1;
			} else {
				return 0;
			}
		}
	};
}
function displaypageno(pageNo,pageSize){
	var pageCoutner=document.getElementById("pageCounter").value;
	if(pageNo=='gopage'){
		if(checktext(document.getElementById("gopage"))){
			var gopage = document.getElementById("gopage");
			var currpage_tmp;
			if(gopage.value>pageCoutner){
				currpage_tmp=pageCoutner;
			}else{
				currpage_tmp=gopage.value;
			}
			document.getElementById("pageno").value=currpage_tmp;
		}else{
			return false;
		}
	}else{
	// 分页显示
	//
	document.getElementById("pagesize").value=pageSize;
	document.getElementById("pageno").value=pageNo;}
	//
	var form=document.getElementById("univeiwform");
	form.action='<%=basePath%>jsp/content/uniview.jsp';  
		form.method='POST';  
		form.target='_self';
		form.submit(); 
	
}
function checktext(valem){
	var strHint=valem.getAttribute("hint");
	if (valem.value != "") {
		if (isNaN(valem.value)) {
			alert(strHint + "需要一个整数!");
			valem.focus();
			return false;
		}
		if (valem.value.indexOf(".") >= 0) {
			alert(strHint + "需要一个整数!");
			valem.focus();
			return false;
		}
		if (valem.value<0) {
			alert(strHint + "不能为负数!");
			valem.focus();
			return false;
		}
		if (valem.value== 0) {
			alert(strHint + "不能为0!");
			valem.focus();
			return false;
		}
	}else{
		alert(strHint + "不能为空!");
			valem.focus();
			return false;
	}
	return true;
}	
