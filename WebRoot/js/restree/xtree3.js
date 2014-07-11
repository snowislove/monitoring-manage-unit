/**
  构造表格。来根据表格构造树型结构。
  表格结构回头补上吧。累。
  Author:YuXiaobin
*/
var Class = {   
  create: function() {   
    return function() {   
      this.initialize.apply(this, arguments);   
    }   
  }   
}   
     
  var Node = Class.create();   
  Node.prototype = {   
    initialize: function(link, level, type) {   
      this.name = link.innerText;         
      this.id;   
      this.link = link;   
      this.type = type;   
      this.level = level;      
      this.isOpen = false;   
      this.isClicked = true;   
      this.root;   
      this.img;
  
      this.parent;   
      this.children = new Array();   
      this.getChildren();   
         
    },   
       
    getChildren: function() {   
        if (this.type == "node") {   
            //alert(this.link.innerText);   
        var dataRows = document.getElementById("tabletree").getElementsByTagName("TBODY")[0].getElementsByTagName("TR");   
        var pushFlag = false;   
           
        for(var j=0; j<dataRows.length; j++) {   
            var linkTag = dataRows[j].firstChild.firstChild;   
            var level = linkTag.getAttribute("level");   
            var type = linkTag.getAttribute("type");   
               
            if (!pushFlag) {   
                if (linkTag == this.link) {   
                    pushFlag = true;   
                }   
                continue;   
            }   
            //alert("cur lvl:"+level+"; type:"+type +" ;parentLvl:" +(parseInt(this.level)+1));   
               
            if (level == (parseInt(this.level)+1)) {   
                //alert("push node's lvl:"+level+"; type:"+type);   
                var leaf = new Node(linkTag, level, type);   
                leaf.parent = this;   
                leaf.id = level+"_"+j;   
                this.children.push(leaf);   
            } else if (level == this.level) {   
                break;   
            } else {   
                continue;   
            }   
               
        }   
        }   
        //for (var i=0; i<this.children.length; i++) {   
            //this.children[i].parent = this;   
        //}   
        //alert("childs:"+this.children.length);   
    },   
       
    getNext: function() {   
        var next = null;   
        //alert(this.name);   
           
        if (this.parent) {   
        for (var i=0; i<this.parent.children.length; i++) {   
            if (this.parent.children[i] == this && i < (this.parent.children.length-1)) {   
                next = this.parent.children[i+1];   
                break;   
            }   
        }      
        }   
        /*  
        if (next)  
            alert("next:"+next.name);  
        else   
            alert("current is last");  
        */  
        return next;   
    },   
       
       
    getCurrentRow: function() {   
        return this.link.parentNode.parentNode.parentNode;             
    },   
       
    changeClickImg: function() {   
        if (this.isOpen) {   
            this.img.src = this.img.src.replace("minus", "plus");   
        } else {   
            this.img.src = this.img.src.replace("plus","minus");   
        }   
        this.isOpen = this.isOpen?false:true;   
    },   
       
    getInnerHTML: function() {   
        var oFragment = document.createDocumentFragment();   
           

    for (var lvl = this.level-1; lvl>0; lvl--) {   
        var indentImg = document.createElement("img");   
           

        var parentNode = this.parent;   
        for (var i=1; i<lvl; i++) {   
            parentNode = parentNode.parent;   
        }   
           
        //alert(this.name+":"+parentNode.name);   
        //alert(parentNode.getNext()?parentNode.getNext().name:"null");   
           

        if (parentNode.getNext()) {   
            indentImg.src = "style/tree/images/I.png";   
        } else {   

            indentImg.src = "style/tree/images/blank.png";   
        }   
            indentImg.align = "absbottom";   
            oFragment.appendChild(indentImg);   
        }   
  
           
        //加减图片切换  
        var img = document.createElement('img');   
        var path;   
        if (this.type == "node") {   
            if (this.level == 0) {   
                path = "style/tree/images/plus.gif";   
            } else {   
                if (this.children.length > 0) {   
                    if (this.getNext()) {   
                        path = "style/tree/images/Tplus.png";   
                    } else {   
                        path = "style/tree/images/Lplus.png";   
                    }   
                }   
                else {   
                    if (this.getNext()) {   
                        path = "style/tree/images/T.png";   
                    } else {   
                        path = "style/tree/images/L.png";   
                    }   
                }   
            }                  
        } else {   
                if (this.getNext()) {   
                    path = "style/tree/images/T.png";   
                }   
                else {   
                    path = "style/tree/images/L.png";   
                }    
        }   
        img.src = path;   
      img.align = "absbottom";   
      //改变指针   
      img.style.cursor = "pointer"  
      this.img = img;   
      img.onclick = expand;   
         
      oFragment.appendChild(img);   
      oFragment.appendChild(this.link);   
         
      var div = document.createElement("div");   
      div.setAttribute("id", this.id);   
  

      div.className = (this.type=="node")?"node":"leaf";    
         

      if (this.level > 0) {   
          div.style.marginLeft = "10px";   
      }   
         
        div.appendChild(oFragment);   
        return div;   
           
    }   
  }   
     

  var root;   

  var nodes = new Array();   
     

  function initTableTree() {   

    var dataRows = document.getElementById("tabletree").getElementsByTagName("TBODY")[0].getElementsByTagName("TR");   
       

        for (var i=0; i<dataRows.length; i++) {   
            var linkTag = dataRows[i].firstChild.firstChild;   
            var level = linkTag.getAttribute("level");   
            var type = linkTag.getAttribute("type");   
               
            if (level == 0 && type == "node") {   
                var root = new Node(linkTag, 0, "node");   
                root.parent = null;   
                root.id = "0_0";   
                break;   
            }   
        }   
           

        nodes.push(root);   
		//alert(root.name);
        initNodes(root);   
           

        for (var j = 0; j<nodes.length; j++) {   
            dataRows[j].firstChild.appendChild(nodes[j].getInnerHTML());   
        }   
  }   
     
  function initNodes(node) {   
      for (var j=0; j<node.children.length; j++) {   
            nodes.push(node.children[j]); 
			//alert(node.name);
			/*alert(node.name);
			alert(j);
			if(j!=0) node.getCurrentRow().style.display = "none"; 
			else node.getCurrentRow().style.display = "block";
			alert(node.getCurrentRow().style.display);*/
			//node.children[j].getCurrentRow().style.display = "none";
            if (node.children[j].children.length > 0) {   
                //node.children[j].getCurrentRow().style.display = "none";
                initNodes(node.children[j]);   
            }   
        }   
  }   
     

  function expand() {          
    var currentDivId = event.srcElement.parentNode.id;   
    var currentNode;   

    for(var i=0; i<nodes.length; i++) {   
        if (currentDivId == nodes[i].id) {   
            currentNode = nodes[i];   
            break;   
        }   
    }   
       
  
    expandChild(currentNode);   
       
  
    currentNode.isClicked = currentNode.isClicked?false:true;   
  }   
     
  function expandChild(currentNode) {   
    //alert(currentNode.name);   
    for (var i=0; i<currentNode.children.length; i++) {   
        var child = currentNode.children[i];   
        if (child.type == "node" && !child.isClicked) {   
            expandChild(child);   
        }   
        child.getCurrentRow().style.display = currentNode.isOpen?"none":"block"; 
    }   
    currentNode.changeClickImg();   
  }   

function addEvent(obj, evType, fn) {   
    if (obj.addEventListener) {   
        obj.addEventListener(evType, fn, true);   
        return true;   
    }   
    else if (obj.attachEvent) {   
        var r = obj.attachEvent("on"+evType, fn);   
        return r;   
    }   
    else {   
        return false;   
    }   
}   

addEvent(window, "load", initTableTree);  