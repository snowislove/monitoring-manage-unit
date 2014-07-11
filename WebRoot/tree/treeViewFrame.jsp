<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page session="false"%>
<%@ page import = "com.secpro.platform.monitoring.manage.entity.SysUserInfo" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.vandagroup.common.includes.*" %>
<%@ page import = "com.secpro.platform.monitoring.manage.tree.*" %>
<%@ page import = "com.vandagroup.common.po.*" %>
<%
	response.setHeader("Expires","0");
	response.setHeader("Pragma","no-cache");
	response.setHeader("Catch-Control","no-cache");
	String _contexPath=request.getContextPath().equals("/")?"":request.getContextPath();
	SysUserInfo user=(SysUserInfo)request.getSession().getAttribute("user");
	Map app=user.getApp();
%>
<script>
  _contexPath='<%=_contexPath%>';
</script>

<script language=javascript src='<%=_contexPath%>/js/util/map.js'></script>

<meta http-equiv='content-type' content='text/html; charset=GBK'>
<meta http-equiv='Content-Style-Type' content='text/css'>
<link rel="stylesheet" media="all" type="text/css" href="<%=_contexPath%>/style/tree/css/restree.css" />
<script type='text/javascript'
			src='<%=_contexPath%>/dwr/interface/TreeAjax.js'></script>
		<script type='text/javascript' src='<%=_contexPath%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=_contexPath%>/dwr/util.js'></script>
		
<%
	try {
%>

<script language="JavaScript">

//if(moz) {
//	extendEventObject();
//	extendElementModel();
//	emulateAttachEvent();
//}
function _clickEvent( currID ) {
	
	_currTr = eval( "document.all( \"_~tr_" + trim(currID) + "\" )" );
    _currTd = eval( "document.all( \"_~td_" + trim(currID) + "\" )" );
    _currImg = eval( "document.all( \"_~img_" + trim(currID) + "\" )" );
    
    //alert( "currID::" + currID );
    
    var parentLinkList = new Array();
    var i=0;    
    var tempNodeID = currID;
    parentLink = _getParent( tempNodeID );
    while( parentLink!=null && parentLink != _initValue ) {
        //alert("parentLink============::"+parentLink);
        tempNodeID=parentLink;
        parentLinkList[i++] = parentLink;
        parentLink = _getParent( tempNodeID );
    }
    parentLinkList[i++]=currID;
    
    
    for( i=0; i<parentLinkList.length; i++ ) {
    
        _currTr = eval( "document.all( \"_~tr_" + trim(parentLinkList[i]) + "\" )" );
        _currTd = eval( "document.all( \"_~td_" + trim(parentLinkList[i]) + "\" )" );
        _currImg = eval( "document.all( \"_~img_" + trim(parentLinkList[i]) + "\" )" );

	    if ( _currTr.style.display == "none" ) {
		    _doOpenFolder( parentLinkList[i] );
	    } else {
            if( parentLinkList[i]==currID){
            //alert("close");
            _doCloseFolder( currID );
            break;
            }
	        
	        
	    }
        
    }
    
	changeTreeNodeStatus();
	
}
function addCover() {
    _treeViewCover.style.display = "block";
}

function clickRoot() {
    
}
function clickFolder() {
    try {
   		 
        var subcode = arguments[0];
       	_clickEvent(subcode);
       	changeTreeNodeStatus();
    } catch ( e ) {
    	alert(e);
    }
}


 function clickLeaf() {
    try {
        var subcode = arguments[0];

        if(arguments[0].indexOf("_")>0){
        <% if(app.get("查看防火墙信息")!=null){%>
        	window.parent.frames["contextMain"].location.href="<%=_contexPath%>/toViewSysObj.action?resid="+subcode+"&&operation=查看资源明细";
       	<%}%>
		}
    } catch ( e ) {
        alert( "查找成员单位数据时出错，请等待右侧表格出现后再选择成员单位。" );
    }
}

//-->
</script>

<script language="javascript" src='<%=_contexPath%>/js/restree/TreeView.js'></script>
<%
		String title= "城市列表"; 							//树状菜单标题
		String tableName= "res_tree";				//表名
		String colName= "rtrim(CITY_CODE)"; 						//主字段名称
		String textColName= "rtrim(CITY_NAME)"; 		//主字段显示文字字段
		String parentColName= "rtrim(PARENT_CODE)"; 				//父字段名称 
		String parentColValue = "0";		//"00000001";//;		//父字段初始值
		com.secpro.platform.monitoring.manage.tree.TreeView treeView = new com.secpro.platform.monitoring.manage.tree.TreeView(
				title, 
				tableName,
				colName, 
				textColName,
				parentColName, 
				parentColValue);			//0是父字段初始值
		treeView.setWhereClause("SELECT rtrim(CITY_CODE) FROM res_tree WHERE  1=1 ");		
		treeView.setOrderBy("CITY_SORT,CITY_NAME");
		treeView.setFilterWhere(" AND PARENT_CODE is not null "); //
		treeView.setImgTreeHead(_contexPath
		+ "/style/tree/images/tv1_treeview.gif");
		treeView.setImgPlus(_contexPath
		+ "/style/tree/images/tv1_plus.gif");
		treeView.setImgMinus(_contexPath
		+ "/style/tree/images/tv1_minus.gif");
		treeView.setImgFolder(_contexPath
		+ "/style/tree/images/alert_ok.png");
		treeView.setImgNode(_contexPath
		+ "/style/tree/images/alert_ok.png");

		treeView.create();
		treeView.display(out);
		treeView.readTreeNode(request, out);
%>


<div name="_treeViewCover" id="_treeViewCover"
	style="position: absolute; left: 0px; top: 0px; width:114%; height: 100%; 
    background-color: #000000; 
    filter: Alpha( Opacity = 20 ); 
    z-index: 2; 
    display: none">
</div>
<%
		} catch (Throwable t) {
			t.printStackTrace();
	}
%>

<script>
	function setPausedTreeNode(){
		var pausedTreeNode;
		DWREngine.setAsync(false);
      	DWREngine.setTimeout(10000);
      	DWREngine.setErrorHandler(function(){return;});
		TreeAjax.getpausedTreeNode(function(data){
				pausedTreeNode = data;
		});
		for(var i=0;i<pausedTreeNode.length;i++){
			var nodeImg=document.getElementById("_img_"+pausedTreeNode[i]);
			if(nodeImg!=null){
				nodeImg.src=_contexPath+"/style/tree/images/paused_res.png"
			}
		}
	
	}
	function setImgSrc(nodeImgs){

		nodeImgs.each(function(key,value,index){     
        	var nodeImg= document.getElementById(key);
        	if(nodeImg!=null){
        	
        		if(value=="1"){
        			nodeImg.src=_contexPath+"/style/tree/images/alert_inform.png";
        		}else if(value=="2"){
					nodeImg.src=_contexPath+"/style/tree/images/alert_minor.png";
				}else if(value=="3"){
					nodeImg.src=_contexPath+"/style/tree/images/alert_major.png";
				}else if(value=="4"){
					nodeImg.src=_contexPath+"/style/tree/images/alert_critical.png";
				}else{
        			nodeImg.src=_contexPath+"/style/tree/images/tree/alert_ok.png";
        		}
        	}
        	
   		 }); 
		 var imgs=document.getElementsByName("res_img");
        	for(var i=0;i<imgs.length;i++){
        		if(imgs[i]!=null){
        			if(nodeImgs.get(imgs[i].id)==null){
        				imgs[i].src=_contexPath+"/style/tree/images/alert_ok.png"
        			}
        		}
        	}
	}
	function changeTreeNodeStatus(){
		var nodes;
		DWREngine.setAsync(false);
      	DWREngine.setTimeout(10000);
      	DWREngine.setErrorHandler(function(){return;});
		TreeAjax.getAllTreeNodeStatus(function(data){
				nodes = data;
			});
		var nodeMap=new Map();
		if(nodes.length!=0){
			for(var i=0;i<nodes.length;i++){
				var nodeStatus=nodes[i].split("&");
				var nodeImg=document.getElementById("_img_"+nodeStatus[0]);
				
				if(nodeImg!=null){
		
					//setImgSrc(nodeImg,nodeStatus[1]);	
					if(nodeMap.get("_img_"+nodeStatus[0])==null){
						nodeMap.put("_img_"+nodeStatus[0],nodeStatus[1]);
					}else{
						if(parseInt(nodeMap.get("_img_"+nodeStatus[0]))<parseInt(nodeStatus[1])){
							nodeMap.put("_img_"+nodeStatus[0],nodeStatus[1]);	
						}
					}
				
					var pid=_getParent( nodeStatus[0] );
					
					while(pid!=null){
						
						nodeImg=document.getElementById("_img_"+pid);
						if(nodeImg!=null){
							
							if(nodeMap.get("_img_"+pid)==null){
								nodeMap.put("_img_"+pid,nodeStatus[1]);
							}else{
								if(parseInt(nodeMap.get("_img_"+pid))<parseInt(nodeStatus[1])){
									nodeMap.put("_img_"+pid,nodeStatus[1]);	
								}
							}
						}
						pid=_getParent(pid);
					}
				}else{
					var pid=_getParent( nodeStatus[0] );
					
					while(pid!=null){		
				
							nodeImg=document.getElementById("_img_"+pid);
							
							if(nodeImg!=null){
								
								if(nodeMap.get("_img_"+pid)==null){
									nodeMap.put("_img_"+pid,nodeStatus[1]);
								}else{
									if(parseInt(nodeMap.get("_img_"+pid))<parseInt(nodeStatus[1])){
										nodeMap.put("_img_"+pid,nodeStatus[1]);	
									}
								}
							}
						
						pid=_getParent(pid);
					}
				}
			}
			
		}
		setImgSrc(nodeMap);
		setPausedTreeNode();
	}
	changeTreeNodeStatus();
	setInterval('changeTreeNodeStatus()',60000);
</script>
<script src="<%=_contexPath%>/js/jquery/jquery.min.js" type="text/javascript"></script>
<script language=javascript src='<%=_contexPath%>/js/jquery/jquery.contextmenu.r2-min.js'></script>

<div class="contextMenu" id="citymenu" style="display :none;">
      <ul>
       <% if(app.get("创建防火墙")!=null){ %>
        <li id="add"><img src="<%=_contexPath%>/style/app/images/dynamic_group_add.png" /> 添加资源</li>
       <%} %>
        <li id="refresh"><img src="<%=_contexPath%>/style/app/images/refresh.png" /> 树刷新</li>
      </ul>
</div>

<div class="contextMenu" id="headmenu" style="display :none;">
      <ul>
        <li id="refresh"><img src="<%=_contexPath%>/style/app/images/refresh.png" /> 树刷新</li>
      </ul>
</div>

<div class="contextMenu" id="resmenu" style="display :none;">
      <ul>
      <% if(app.get("删除防火墙")!=null){ %>
        <li id="delete"><img src="<%=_contexPath%>/style/app/images/delete.png" /> 删除资源</li>
     <% }if(app.get("查看防火墙告警")!=null){ %>  
        <li id="catevent"><img src="<%=_contexPath%>/style/app/images/deregulation_affair.png" /> 查看告警</li>
       <%}if(app.get("查看防火墙任务")!=null){  %>
        <li id="taskview"><img src="<%=_contexPath%>/style/app/images/workspace.png" /> 查看任务</li>
       <%} %>
        <li id="refresh"><img src="<%=_contexPath%>/style/app/images/refresh.png" /> 树刷新</li>
      </ul>
</div>
