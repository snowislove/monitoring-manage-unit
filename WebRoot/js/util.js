function AutomateExcel(exportTabId) {
	var elTable = document.getElementById(exportTabId);
	if (elTable==null) {
		alert("ID" + exportTabId + " 对应的表不存在");
		return;
	}
	var oRangeRef = document.body.createTextRange();
	oRangeRef.moveToElementText(elTable);
	oRangeRef.execCommand("Copy");
	try {
		var appExcel = new ActiveXObject("Excel.Application");
	} catch (e) {
		alert("无法调用Office对象，请确保您的机器已安装了Office并已将本系统的站点名加入到IE的信任站点列表中！");
		return;
	}
	appExcel.Visible = true;
	appExcel.Workbooks.Add().Worksheets.Item(1).Paste();
	appExcel = null;
}