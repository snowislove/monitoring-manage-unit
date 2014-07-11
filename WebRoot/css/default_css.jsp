<%@ page contentType="text/html;charset=GBK" %>
<%
  
String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
%>


#system_head * {
	padding:0;
	margin:0;
}

#system_head_r2 {
	background:transparent url(<%=_contexPath%>/includes/images/top.gif) no-repeat scroll left bottom;
	height:55px;
	position:relative;
}
#system_head_r1 {
	background:transparent url(<%=_contexPath%>/includes/images/top_right.gif) no-repeat scroll right bottom;	
}
#system_head {
	background:transparent url(<%=_contexPath%>/includes/images/top_center.gif) repeat-x scroll center bottom;	
}
#system_head ul li {
	display:inline;
}
#system_head ul {
	position:absolute;
	right:150px;
	top:20px;
}

#system_head p {	
	position:absolute;
	right:30px;
	padding-top:20px;
}

#system_head label {	
	margin:0 5px  0 10px ;
}








body {
    margin-top: 0px;
    margin-right: 3px;
    margin-bottom: 0px;
    margin-left: 5px;

    padding-top: 0px;
    padding-right: 0px;
    padding-bottom: 0px;
    padding-left: 0px;

    font-size: 9pt;
    font-family: simsun;
    background-repeat: no-repeat;
    background-position: center;
 
}

td {
    font-size: 9pt;
    font-family: simsun;
	font-size:12px;
/*	height:20px;*/
	/*color:#164289;*/
}

input_wk {
    font-size: 9pt;
    font-family: simsun;
    border-top: 1px solid #5E5E5E;
    border-right: 1px solid #C9C9C9;
    border-bottom: 1px solid #C9C9C9;
    border-left: 1px solid #5E5E5E;
    padding-top: 2px;
    background-image: url( "<%=_contexPath%>/includes/images/ns_input_bg.gif" );
	behavior:url(<%=_contexPath%>/includes/js/input.htc);
}



 
input.radio {
    border: 0px;
}

/*input{border:1px solid #9ebfe0;#b3c7e0;
	behavior:url(<%=_contexPath%>/includes/js/input.htc);
}*/

.vda_input_class {
	border:1px solid #B5B8C8;
    font-size:9pt;
/*	margin:2;*/
    padding-top:1px;
	behavior:url(<%=_contexPath%>/includes/js/input.htc);
}
.vda_input_class-hover {
	border: 1px solid #000080;
	font-size:9pt;
/*	margin:2;*/
    padding-top:1px;
	behavior:url(<%=_contexPath%>/includes/js/input.htc);
}
.vda_input_class-selected {
	border: 1px solid #000080;
	font-size:9pt;
/*		margin:2;*/
    padding-top:1px;
	behavior:url(<%=_contexPath%>/includes/js/input.htc);
}



input.checkbox {
    border: 0px;
	height:14px;
	width:14px;
}

.vda_checkbox {
	height:13px;
	width:13px;
	margin:1px;
}

.font_red{
padding-left:3px;
}
select {
    font-size: 9pt;
    font-family: simsun;
    background-image: url( "<%=_contexPath%>/includes/images/ns_input_bg.gif" );
	behavior:url(<%=_contexPath%>/includes/js/input.htc);
}

option {
    font-size: 9pt;
    font-family: simsun;
    background-image: url( "<%=_contexPath%>/includes/images/ns_input_bg.gif" );
}

textarea {
    overflow: auto;
    font-size: 9pt;
    font-family: simsun;
   /* border-top: 1px solid #5E5E5E;
    border-right: 1px solid #C9C9C9;
    border-bottom: 1px solid #C9C9C9;
    border-left: 1px solid #5E5E5E;*/
	border:1px solid #9ebfe0;#b3c7e0;
    /*background-image: url( "<%=_contexPath%>/includes/images/ns_input_bg.gif" );*/
	/*behavior:url(<%=_contexPath%>/includes/js/input.htc);*/
}

.spaceFont1 { font-size: 1px }
.spaceFont2 { font-size: 2px }
.spaceFont3 { font-size: 3px }
.spaceFont4 { font-size: 4px }
.spaceFont5 { font-size: 5px }
.spaceFont6 { font-size: 6px }
.spaceFont7 { font-size: 7px }
.spaceFont8 { font-size: 8px }
.spaceFont9 { font-size: 9px }
.spaceFont10 { font-size: 10px }
.spaceFont11 { font-size: 11px }
.spaceFont12 { font-size: 12px }

a:link { font-size: 9pt; color: #2B7291; text-decoration: underline }
a:visited { font-size: 9pt; color: #672C3C; text-decoration: underline }
a:hover { font-size: 9pt; color: #ff3000; text-decoration: none }
a:active { font-size: 9pt; color: #000000; text-decoration: underline }

.operationTitle {
    vertical-align: top;
    height: 40px;
    padding-top: 7px;
    padding-left: 40px;
    background-repeat: no-repeat;
    background-position: left;
}

.currUserInfo {
    text-align: right;
    vertical-align: top;
    font-family: simsun;
    font-size: 9pt;
    height: 40px;
    padding-top: 13px;
    padding-right: 10px;
}

.workDiv {
    width: 100%;
    height: 100%;
    overflow: auto;
}

.copyRight {
    font-size: 10px;
    font-family: arial;
    color: #000000;
}

.btn {
        border:1px double #acc1de;
		background:url(<%=_contexPath%>/includes/images/td_background.gif );
		width:94px;
		height:24px;
		font-size:12px;
		color:#144490;

}

.btn_old {
    height: 20px;
    padding-top: 3px;
    padding-left: 5px;
    padding-right: 5px;
    font-size: 9pt;
    background-image: url( "<%=_contexPath%>/includes/images/ns_btn.gif" );
    border-top: 1px #737373 solid;
    border-right: 1px #C6C6C6 solid;
    border-bottom: 1px #C6C6C6 solid;
    border-left: 1px #737373 solid;
}

.miniBtn {
    height: 15px;
    padding-top: 0px;
    padding-left: 2px;
    padding-right: 2px;
    padding-bottom: 2px;
    font-size: 12px;
    background-image: url( "<%=_contexPath%>/images/ns_btn.gif" );
    border-top: 1px #737373 solid;
    border-right: 1px #C6C6C6 solid;
    border-bottom: 1px #C6C6C6 solid;
    border-left: 1px #737373 solid;
}

/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 列表区域表格样式 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
.ListTable { background-color: #00329B}
.ListTrTitle { background-color: #EBF8FF }
.ListTdTitle { text-align: center; color: #000000; background-color: #EBF8FF; height: 20px }

.ListTr {}
.ListTrJi { background-color: #ffffff; cursor: hand; }
.ListTrOu { background-color: #F0F0F0; cursor: hand; }

.ListTd { padding-left: 9px }
.ListTdDate { text-align: center; font-family: arial }
.ListTdSeq { text-align: center; font-family: arial; width: 5% }
.ListTdFee { text-align: right; font-family: arial; padding-right: 9px }
.ListTdCenter { text-align: center }
.ListTdRight { text-align: right }

.NoResultTr {}
.NoResultTd { text-align: center; background-color: #F7F7F7; height: 45px; font-size: 15pt; font-family: simhei; }
/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 列表区域表格样式 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/




/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 输入表单表格样式 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
.InputTable { border:1px solid #9ebfe0; }
.InputTrJi { height:16px; }
.InputTrOu { height:16px; }
.InputTdTitle { text-align: right;  width: 10% }
.InputTdObj { text-align: left; padding-left: 10px; width: 40%  }
.InputTdExp { text-align: left; padding-left: 10px; width: 30%  }
.input2 {behavior:url(<%=_contexPath%>/includes/js/input.htc; background-color: #F0F0F0)  }

/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 输入表单表格样式 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/


/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 查询框表格样式 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
.SearchTable { background-color: #F7F7F7; border: solid 1px #BBBBBB; margin-bottom: 6px }
.SearchTr {}
.SearchTrJi { background-color: #F7F7F7 }
.SearchTrOu { background-color: #F7F7F7 }
.SearchTd {};
/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 查询框表格样式 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/


/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 备注框表格样式 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
.MemoTable { background-color: #A1A5A9 }
.MemoTr {}
.MemoTrJi { background-color: #E2E2E2 }
.MemoTrOu { background-color: #E2E2E2 }
.MemoTd {};
/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 备注框表格样式 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/


/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 按钮框表格样式 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
.ButtonTable { margin-top: 6px }
.ButtonTr {}
.ButtonTrJi {}
.ButtonTrOu {}
.ButtonTd { text-align: center; height: 32px; padding-right: 16px; background: url( "<%=_contexPath%>/includes/images/ns_btn_bg.gif" ) };
/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 按钮框表格样式 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/


.goToPageText {
    font-family: arial;
    font-size: 10px;
    height: 18px;
    width: 30px;
    text-align: center;
    border-top: 1px #737373 solid;
    border-right: 1px #C6C6C6 solid;
    border-bottom: 1px #C6C6C6 solid;
    border-left: 1px #737373 solid;
}

.msg { font-family: sumhei; font-size: 12pt; text-align: center; font-weight: bold }

/*.readonly { background-color: #D9D9D9; background-image: url( "<%=_contexPath%>/includes/images/ns_input_readonly_bg.gif" ); }*/

.readOnly {border:1px solid #9ebfe0;#b3c7e0;background-color:#E3E3E3;text-align:;font-weight:false;font-size: 9pt;
    font-family: simsun;text-decoration:false;padding-top: 2px;}

.numText { font-size: 8pt; font-family: arial; text-align: left; }

.feeText { font-size: 8pt; font-family: arial; text-align: right; padding-right: 3px; }
.feeTextReadonly { font-size: 8pt; font-family: arial; text-align: right; padding-right: 3px; background-color: #D9D9D9; background-image: url( "<%=_contexPath%>/includes/images/ns_input_readonly_bg.gif" ); }

.timeText { font-size: 8pt; font-family: arial; background-color: #CDCDCD }
.rightText { font-size: 8pt; font-family: arial; text-align: right }
.rightTextReadonly { font-size: 8pt; font-family: arial; text-align: right; background-color: #D9D9D9; background-image: url( "<%=_contexPath%>/includes/images/ns_input_readonly_bg.gif" ); }

.countText { font-size: 8pt; font-family: arial; text-align: center; }
.countTextReadonly { font-size: 8pt; font-family: arial; text-align: center; background-color: #D9D9D9;  background-image: url( "<%=_contexPath%>/includes/images/ns_input_readonly_bg.gif" ); };

.sizeFont { font-family: arial; font-size: 8pt; text-align: center; }


.underline { border-top: 0px; border-right: 0px; border-bottom: 1px #333333 solid; border-left: 0px }
.underlineFee { border-top: 0px; border-right: 0px; border-bottom: 1px #333333 solid; border-left: 0px; text-align: right; font-family: arial }
.underlineReadonly { border-top: 0px; border-right: 0px; border-bottom: 1px #333333 solid; border-left: 0px; background-color: #BCBCBC; background-image: url( "<%=_contexPath%>/includes/images/ns_input_readonly_bg.gif" ); }
.lineBorder { border: 1px solid #666666 }

.baseFrame { border-top: 1px solid #5E5E5E; border-right: 1px solid #C9C9C9; border-bottom: 1px solid #C9C9C9; border-left: 1px solid #5E5E5E; width: 100%; height: 100% }

.sysFrame { width: 100%; height: 100%;border:1px solid #9ebfe0; }

.frameBorder { border-top: 1px solid #5E5E5E; border-right: 1px solid #C9C9C9; border-bottom: 1px solid #C9C9C9; border-left: 1px solid #5E5E5E }
/*.selectTr { background-color: #33CC99; color: #ffffff }*/

.selectTr {background:#DFE8F6!important;border:1px dotted #a3bae9;}


.tabHeadMenu { height: 15px }
.trHeadMenu {}
.tdHeadMenu {
    background-color: #356B97;
    padding-left: 16px;
    padding-right: 4px;
    padding-top: 2px;
    padding-bottom: 0px;
    color: #ffffff;
    height: 15px;
    border-left: 3px solid #ffffff;
    border-right: 3px solid #ffffff;
    background-image: url( "<%=_contexPath%>/images/ns_btn_left.gif" );
    background-repeat: no-repeat;
    background-position: center left;
}
.tdHeadMenuHolder {
    background-repeat: repeat-x;
    background-position: bottom;
    padding-bottom: 1px;
    background-image: url( "<%=_contexPath%>/images/ns_dotted_bg.gif" );
}

a.headMenu:link { font-size: 9pt; color: #ffffff; text-decoration: none }
a.headMenu:visited { font-size: 9pt; color: #ffffff; text-decoration: none }
a.headMenu:hover { font-size: 9pt; color: #ffffff; text-decoration: none }
a.headMenu:active { font-size: 9pt; color: #ffffff; text-decoration: none }

.money {
border:1px solid #B5B8C8;
font-size:9pt;
/*margin:2;*/
padding-right:1px;
padding-top:1px;
behavior:url(<%=_contexPath%>/includes/js/money.htc);
}

.money-hover {
	border: 1px solid #000080;
	font-size:9pt;
/*		margin:2;*/
padding-right:1px;
padding-top:1px;
behavior:url(<%=_contexPath%>/includes/js/money.htc);
}
.money-selected {
	border: 1px solid #000080;
	font-size:9pt;
/*		margin:2;*/
padding-right:1px;
padding-top:1px;
behavior:url(<%=_contexPath%>/includes/js/money.htc);
}


/*↓↓↓↓↓↓↓↓↓↓↓↓↓↓ TreeView样式 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
a.treeview:link { font-size: 9pt; color: #2B7291; text-decoration: none }
a.treeview:visited { font-size: 9pt; color: #2B7291; text-decoration: none }
a.treeview:hover { font-size: 9pt; color: #ff3000; text-decoration: none }
a.treeview:active { font-size: 9pt; color: #ffffff; text-decoration: none; background-color: #0E0088 }

.treeLink { font-size: 9pt; color: #2B7291; text-decoration: none; cursor: hand }
.treeHover { font-size: 9pt; color: #ff3000; text-decoration: none; cursor: hand }
.treeActive { font-size: 9pt; color: #ffffff; text-decoration: none; cursor: hand; background-color: #0E0088 }
/*↑↑↑↑↑↑↑↑↑↑↑↑↑↑ TreeView样式 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/

.add {
	background-image:url("<%=_contexPath%>/includes/ext/icons/add.gif") !important;
}
.option {
	background-image:url("<%=_contexPath%>/includes/ext/icons/plugin.gif") !important;
}
.remove {
	background-image:url("<%=_contexPath%>/includes/ext/icons/delete.gif") !important;
}
.commit {
	background-image:url("<%=_contexPath%>/includes/ext/icons/commit.gif") !important;
}

.select_div {
       display:inline;
       float: left;
       margin: 3px;
}

.input_button {

border:1px double #acc1de;
background:url(<%=_contexPath%>/includes/images/td_background.gif );
width:94px;
height:24px;
font-size:12px;
color:#144490;
/*behavior:url(<%=_contexPath%>/includes/js/button.htc);*/
}
.td_class{
padding-left:3px;
font-size: 9pt;
word-break: keep-all;
white-space:nowrap;
}

.combobox_th_class{
/*font-size:9pt;
nowrap;*/
text-align:center;
font:normal 12px simsun,tahoma,arial,helvetica,sans-serif;padding:2px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;
}

.combobox_th_title{
font:normal 12px simsun,tahoma,arial,helvetica,sans-serif;
text-align:center;
nowrap;
border-right:2px solid #F0F0F0;
}

.combobox_tr_class{
 background-color:#9ebfe0;
}
.comboex-table{

}

 .select-form-field{margin:0;
 font:normal 12px simsun;
/* font-size: 9pt;
 font-family: simsun;*/
 behavior:url(<%=_contexPath%>/includes/js/input.htc);
 }

.ext-ie .x-form-text{ font-size:9pt;line-height:18px;border:1px solid #B5B8C8;text-decoration:false;height:20px;}

.ext-ie .x-form-text-hover{font-size:9pt;line-height:18px;position:relative;top:-1px;border: 1px solid #000080;text-decoration:false;height:20px;}
.ext-ie .x-form-text-selected{font-size:9pt;line-height:18px;position:relative;top:-1px;border: 1px solid #000080;text-decoration:false;height:20px;}

.required{
margin-top:10px;
float:left;
  /*background:url( "<%=_contexPath%>/includes/images/required.gif" ) no-repeat center;*/
}

.comboex-list-td{
 
 font:normal 12px simsun,tahoma,arial,helvetica,sans-serif;padding:2px;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;

}

.comboex-list-td-selected{
	background:#316AC5 none repeat scroll 0 0;
	border:1px dotted #005FBD !important;
	cursor:pointer; padding-left: 3px;color:white;
/*	height:20;*/
}

.comboex_div_title {
	float:left;
    background-color: #9ebfe0;
    font-size:9pt;
	text-align:center;
	nowrap;
	}

.comboex_div_context {
	float:left;
	font:normal 12px simsun,tahoma,arial,helvetica,sans-serif;white-space:nowrap;overflow:hidden;text-overflow:ellipsis;
}


.combo_table_border {
    border-right:1px #F0F0F0 solid; border-bottom:1px #F0F0F0 solid; height:19px; padding-left: 3px; padding-top: 3px
}
.checkboxtree_td {
    font-size: 9pt;
    font-family: simsun;
}

.Wdate_img_required{
    float:left;
    height:18px;
	width:15px;
	background: url(<%=_contexPath%>/includes/js/date/skin/datePicker.gif) no-repeat;
	position:absolute;
	left:-20px;
	top:1.5px;
}

.Wdate_img{
    float:left;
    height:18px;
	width:15px;
	background: url(<%=_contexPath%>/includes/js/date/skin/datePicker.gif) no-repeat;
	position:absolute;
	left:-20px;
	top:1.5px;
}