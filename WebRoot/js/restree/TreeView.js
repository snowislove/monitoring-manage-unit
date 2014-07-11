 
var _currImg = null;
var _currTr = null;
var _currTd = null;
var _currFont = null;

var _initValue = null;
var _treeTitle = null;

var _preloaded = false;

var _imgTreeHead    = _contexPath+"/style/tree/images/tv_treeview.gif";
var _imgPlus        = _contexPath+"/style/tree/images/tv1_plus.gif";
var _imgMinus       = _contexPath+"/style/tree/images/tv1_minus.gif";
var _imgFolder      = _contexPath+"/style/tree/images/tv_singlefolder.gif";
var _imgNode        = _contexPath+"/style/tree/images/tv_singlepaper.gif";
var _imgTrans       = _contexPath+"/style/tree/images/tv_blank.gif";

function TreeView( initValue, treeTitle ) {

    this._displayTree   = _displayTree;

    _initValue = initValue;
    _treeTitle = treeTitle;

    if (!_preloaded) {
        _preLoadImg();
        _preloaded = true;
    }

    _displayTree();

}
function trim(a){
	return a;
}
function _displayTree() {
    var html = "<table id=tabTree border=0 cellspacing=0 cellpadding=0 width='100%'>\n";
    html += "<tr><td nowrap style='font-size: 9pt; '><img src='" 
        + _imgTreeHead + "' align=absmiddle>※<font class='treeLink' onmouseover='_onMouseOver( this )' onmouseout='_onMouseOut( this )' onclick='javascript:_clickRoot( this )'>" 
        + _treeTitle + "</font></td></tr>\n";
    html += _getChildItems( _initValue );
    html += "</table>\n";
    document.write( html );
}

function _getParent( childID ){

    
    for ( var i = 0 ; i < _arrTreeView.length ; i++ ) {
        if ( _arrTreeView[ i ][ 1 ] == childID ) {
            parentID = _arrTreeView[ i ][ 0 ];
            return( parentID );
        }
    }
    return null;
}


function _getChildItems( pID ) {
    var html = "";

    for ( var i = 0 ; i < _arrTreeView.length ; i++ ) {

        if ( _arrTreeView[ i ][ 0 ] == pID ) {

            var hasChild = _hasChild( _arrTreeView[ i ][ 1 ] );
            html += "<tr><td nowrap>" + _getLeft( _arrTreeView[ i ][ 1 ], hasChild );

            if ( hasChild ) {
                html += "<font id='"+_arrTreeView[ i ][ 1 ]+"' class='treeLink' onmouseover='_onMouseOver( this )' onmouseout='_onMouseOut( this )' onmousedown='_onMouseDown(this,event)'  onclick='_clickFolder( this, \"" 
                    + _arrTreeView[ i ][ 1 ] + "\", \"" 
                    + _arrTreeView[ i ][ 2 ] + "\"" 
                    + _getParaColValues( i ) + " )'>" 
                    + _arrTreeView[ i ][ 2 ] + "</font>";
            } else {
                html += "<font id='"+_arrTreeView[ i ][ 1 ]+"' class='treeLink' onmouseover='_onMouseOver( this )' onmouseout='_onMouseOut( this )' onmousedown='_onMouseDown(this,event)' onclick='_clickLeaf( this, \"" 
                    + _arrTreeView[ i ][ 1 ] + "\", \"" 
                    + _arrTreeView[ i ][ 2 ] + "\"" 
                    + _getParaColValues( i ) + " )'>" 
                    + _arrTreeView[ i ][ 2 ] + "</font>";
            }

            html += "</td></tr>\n";

            if ( hasChild )
                html += "<tr id='_~tr_" 
                    + trim( _arrTreeView[ i ][ 1 ] ) + "' style='display: none'><td id='_~td_" 
                    + trim( _arrTreeView[ i ][ 1 ] ) + "' nowrap style='font-size: 9pt; padding-left: 18px;'>" 
                    + _getNullLeft() + "正在读取数据...</td></tr>\n";
        }
    }
    return html;
}

function _addItems( currID ) {
    var html = "<table border=0 cellspacing=0 cellpadding=0 width='100%'>\n";
    html += _getChildItems( currID );
    html += "</table>\n";
    _currImg.loaded = "yes";
    _currTd.innerHTML = html;
}

function _hasChild( pID ) {
    for ( var m = 0 ; m < _arrTreeView.length ; m++ ) {
        if ( _arrTreeView[ m ][ 0 ] == pID ) {
            return true;
        }
    }
    return false;
}

function _doOpenFolder( currID ) {
    _currImg.src = _imgMinus;
    _currTr.style.display = "block";
    if ( _currImg.loaded != "yes" ) {
        _addItems( currID );
    }
}

function _doCloseFolder() {
    _currImg.src = _imgPlus;
    _currTr.style.display = "none";
}



function _getLeft( currID, hasChild ) {
    var rtn = "";
    if ( hasChild )
        rtn += "<img id='_~img_" + trim(currID) + "' src='" + _imgPlus 
            + "' align=absmiddle style='cursor: hand' onClick='_clickEvent( \"" + currID + "\" )'>";
    else
        rtn += "<img src='" + _imgTrans + "' width=18 height=16 align=absmiddle>";
    return rtn + "<img name='res_img' id='_img_" + trim(currID) + "' src='" + ( hasChild?_imgFolder:_imgNode ) + "' align=absmiddle>";
}

function _getNullLeft() {
    var rtn = "";
    return rtn + "<img src='" + _imgTrans + "' width=18 height=16 align=absmiddle><img src='" + _imgNode + "' align=absmiddle>";
}

function _getParaColValues( _arrIndex ) {
    var rtn = "";
    for (var i = 3 ; i < _arrTreeView[ _arrIndex ].length ; i++) {
        rtn += ", \"" + _arrTreeView[ _arrIndex ][ i ] + "\"";
    }
    return rtn;
}

function _getArgumentsString( arg ) {
    var rtn = "";
    for (var i = 1 ; i < arg.length ; i++) {
        rtn += "\"" + arg[ i ] + "\",";
    }
    rtn = rtn == "" ? "" : rtn.substring( 0, rtn.length - 1 );
    return rtn;
}

function _preLoadImg() {
    imgTreeHead = new Image();          imgTreeHead.src = _imgTreeHead;
    imgPlus     = new Image();          imgPlus.src     = _imgPlus;
    imgMinus    = new Image();          imgMinus.src    = _imgMinus;
    imgFolder   = new Image();          imgFolder.src   = _imgFolder;
    imgNode     = new Image();          imgNode.src     = _imgNode;
    imgTrans    = new Image();          imgTrans.src    = _imgTrans;
}

function _clickRoot( obj ) {
    try {
        _onClick( obj );
    	clickRoot( _initValue, _treeTitle );
    } catch ( e ) {
        alert( "页面缺少clickRoot()函数！\n提示：\n" 
            + "当树状菜单根结点（Root）被点击时必须实现clickRoot()函数" );
    }
}

function _clickFolder() {
	
    try {
        _onClick( arguments[ 0 ] );
        var clickFolderFunc = "clickFolder( " + _getArgumentsString( arguments ) + " );";
    	eval( clickFolderFunc );
    } catch ( e ) {
        alert( "页面缺少clickFolder()函数！\n提示：\n" 
            + "当树状菜单枝节点（Folder）被点击时必须实现clickFolder()函数，此函数得到的参数分别为：\n" 
            + "arguments[ 0 ]：此节点代码\n" 
            + "arguments[ 1 ]：此节点显示文字\n" 
            + "arguments[ 2...n ]：此节点对应参数（如果存在）\n" );
    }
}

function _clickLeaf() {
    
    try {
        _onClick( arguments[ 0 ] );		
        var clickLeafFunc = "clickLeaf( " + _getArgumentsString( arguments ) + " );";
    	eval( clickLeafFunc );	
    } catch ( e ) {
        alert( "页面缺少clickLeaf()函数！\n提示：\n" 
            + "当树状菜单叶节点（Leaf）被点击时必须实现clickLeaf()函数，此函数得到的参数分别为：\n" 
            + "arguments[ 0 ]：此节点代码\n" 
            + "arguments[ 1 ]：此节点显示文字\n" 
            + "arguments[ 2...n ]：此节点对应参数（如果存在）\n" );
    }
}

function _onClick( obj ) { 

    if ( _currFont != null ) {
        _currFont.className = "treeLink";
    }
    obj.className = "treeActive";
    _currFont = obj;
}

function _onMouseOver( obj ) {
    if ( obj.className != "treeActive" ) {
        obj.className = "treeHover";
    }
}

function _onMouseOut( obj ) {
    if ( obj.className != "treeActive" ) {
        obj.className = "treeLink";
    }
}

function saveNodeInfoToSession( nodeID ){
	
	if( window.controller_tree == "[object]"){
	//	window.controller_tree.saveNodeInfo( _treeTitle, nodeID );
	}
    
}

function sortTreeAry(ary,id){
 
  _arrTreeView=ary
	  return;
  var i,j
  for(i=0;i<ary.length;i++){
     if(id==ary[i][0]){
     _arrTreeView[_arrTreeView.length]=new Array();
         for(j=0;j<ary[i].length;j++){
         
          _arrTreeView[_arrTreeView.length-1][j]=ary[i][j]         
         }
        sortTreeAry(ary,ary[i][1]) 
     }
  }  
 
}
function stopDefault( e ) { 
    //阻止默认浏览器动作(W3C) 
    if ( e && e.preventDefault ) 
        e.preventDefault(); 
    //IE中阻止函数器默认动作的方式 
    else
        window.event.returnValue = false; 
    return false; 
}
function _onMouseDown(obj,e){
    var e=window.event||e;
    var value=e.button;    
     if(value==2||value==3){       
    	if((obj.id).indexOf("_")>=0){
    		$('#'+obj.id).contextMenu('resmenu', {
    		      //菜单样式
    		      menuStyle: {
    		        border: '2px solid #000'
    		      },
    		      //菜单项样式
    		      itemStyle: {
    		        fontFamily : 'verdana',
    		        backgroundColor : 'green',
    		        color: 'white',
    		        border: 'none',
    		        padding: '1px'
    		
    		      },
    		      //菜单项鼠标放在上面样式
    		      itemHoverStyle: {
    		        color: 'blue',
    		        backgroundColor: 'red',
    		        border: 'none'
    		      },
    		      //事件    
    		      bindings: 
    	          {
    	            'delete': function(t) {
    	            	if(confirm("资源删除操作。\n\n请注意：此操作不可恢复。确定删除？")){
    	            		window.location.href="ResObjectDelete.action?resId="+obj.id;
    	            	}else
    	            	{
    	            		return false;
    	            	}
      	            },
      	            'catevent': function(t) {
      	            	window.parent.frames["contextMain"].location.href="toViewEvent.action?resId="+obj.id;
      	            },
      	            'taskview': function(t) {
      	            	
    	            	window.parent.frames["contextMain"].location.href="../task/taskView.jsp?resId="+obj.id;
    	            },
      	            'refresh': function(t) {
      	            	window.location.reload();
      	            }
    	          }

    		     
    		    });
    	}else{
    		if(obj.id!="1"&&(obj.id).charAt((obj.id).length-1)!="0"){
    		$('#'+obj.id).contextMenu('citymenu', {
  		      //菜单样式
  		      menuStyle: {
  		        border: '2px solid #000'
  		      },
  		      //菜单项样式
  		      itemStyle: {
  		        fontFamily : 'verdana',
  		        backgroundColor : 'green',
  		        color: 'white',
  		        border: 'none',
  		        padding: '1px'
  		        
  		      },
  		      //菜单项鼠标放在上面样式
  		      itemHoverStyle: {
  		        color: 'blue',
  		        backgroundColor: 'red',
  		        border: 'none'
  		      },
  		      //事件    
  		      bindings: 
  	          {
  	            'add': function(t) {
  	            	window.parent.frames["contextMain"].location.href="toAddSysObj.action?cityCode="+obj.id;
  	            },
  	            'refresh': function(t) {
  	            	window.location.reload();
  	            }
  	          }
  		    });
    	}else{
    		$('#'+obj.id).contextMenu('headmenu', {
    		      //菜单样式
    		      menuStyle: {
    		        border: '2px solid #000'
    		      },
    		      //菜单项样式
    		      itemStyle: {
    		        fontFamily : 'verdana',
    		        backgroundColor : 'green',
    		        color: 'white',
    		        border: 'none',
    		        padding: '1px'
    		
    		      },
    		      //菜单项鼠标放在上面样式
    		      itemHoverStyle: {
    		        color: 'blue',
    		        backgroundColor: 'red',
    		        border: 'none'
    		      },
    		      //事件    
    		      bindings: 
    	          {
    	            'refresh': function(t) {
    	            	window.location.reload();
    	            }
    	          }
    		    });
    	}
    	}
     }else{       
       //   alert('zuojian'); 
     } 
}     




