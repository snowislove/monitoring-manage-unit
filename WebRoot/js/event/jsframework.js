/*---------------------------------------------------------------------------*\
|  Subject: JavaScript Framework
|  Author:  meizz
|  Created: 2005-02-27
|  Version: 2007-08-31
|-----------------------------------
|  MSN:huangfr@msn.com QQ:112889082 Copyright(c) meizz
|  http://www.meizz.com/jsframework/ MIT-style license
|  The above copyright notice and this permission notice shall be
|  included in all copies or substantial portions of the Software
\*---------------------------------------------------------------------------*/

if(typeof(System)!="function"&&typeof(Using)!="function"){(function(){var S=
window.System=function()
{
  System._instances[(this.hashCode=System.getUniqueId())]=this;
}
S.extend=function(d,s){for(var i in s)d[i]=s[i];return d};
S.extend(System,{

debug: false, //false
currentVersion:"20080422",

ie:navigator.userAgent.indexOf("MSIE")>0&&!window.opera,
NOT_XHR:"Your browser do not support XMLHttp",
_codebase:{},_instances:{},_hashCounter:0,
_prototypes:{"System":S,"System.Object":S},

alert:function(msg){if(this.debug)alert(msg)},
getUniqueId:function(){return "mz_"+(this._hashCounter++).toString(36)},

_getPrototype:function(ns,argu)
{
  if(typeof(this._prototypes[ns])=="undefined")return new System();
  for(var a=[],i=0;i<argu.length;i++)a[i]="argu["+i+"]";
  return eval("new (System._prototypes['"+ns+"'])("+a.join(",")+")");
},

supportsXmlHttp:function()
{
  return "object"==typeof(this.xhr||(this.xhr=new XMLHttpRequest()));
},

load:function(ns, op)
{
  var x,S=System,path=S._mapPath(ns, op);
  if(S._userdata&&/\.js$/i.test(path)&&(x=S.loadUserData(ns)))
    return (S._codebase[ns]="//from userdata\r\n"+ x);
  try
  {
    if(S.supportsXmlHttp()){
      x=S.xhr; x.open("GET",path,false); x.send(null);
    if (x.readyState==4){
    if(x.status==0||x.status==200||/^file\:/i.test(path))
      return S._parseResponse(ns,x.responseText,op.expires);
    else if(x.status==404)S.alert(ns+"\nFile not found");
    else throw new Error(x.status +": "+ x.statusText);}}
    else S.alert(S.NOT_XHR);
  }
  catch(ex){S.alert(ns+"\n"+ex.message);}return "";
},
_parseResponse:function(ns,s,expires)
{
  if(s==null||s.charAt(0)=="\uFFFD"){
    this.alert("Maybe file encoding isn't ANSI or UTF-8");return ""}
  if(s.charAt(0)=="\xef")s=s.substr(3); //for firefox
  s=s.replace(/(^|\n)\s*\/\/+\s*((Using|Import|Include)\((\"|\'))/g,"$1$2");
  if(this._userdata)this.saveUserData(ns,s,expires);return s;
},
_exist:function(ns,path)
{
  if(typeof(this._existences[ns])=="undefined") return false;
  return this._existences[ns]==this._mapPath(ns,{"path":path});
},
_mapPath:function(ns,op)
{
  op=op||{};
  if(typeof(op.path)=="string"&&op.path.length>3)return op.path;
  op.path=this.path+"/"+ns.replace(/\./g,"/")+".js";
  return op.path +(op.nocache?"?t="+Math.random():"");
}});
S._userdata=S.ie&&!S.debug;

var t=document.getElementsByTagName("SCRIPT");t=(
S.scriptElement=t[t.length-1]).src.replace(/\\/g,"/");
S.path=(t.lastIndexOf("/")<0?".":t.substring(0,t.lastIndexOf("/")));
S._existences=
{
  "System":S._mapPath("System"),
  "System.Event":S._mapPath("System.Event"),
  "System.Object":S._mapPath("System.Object")
};

try{if(window!=parent&&parent.System&&parent.System._codebase)
  S._codebase=parent.System._codebase;
else if(typeof(opener)!="undefined"&&opener.System&&opener.System._codebase)
  S._codebase=opener.System._codebase;
else if(typeof(dialogArguments)!="undefined"&&dialogArguments.System)
  S._codebase=dialogArguments.System._codebase;}catch(ex){}

if(!window.XMLHttpRequest&&window.ActiveXObject)
{
  window.XMLHttpRequest=function(){
  for(var i=0,a=['MSXML3','MSXML2','Microsoft'];i<a.length;i++)
    try{return new ActiveXObject(a[i]+'.XMLHTTP')}catch(ex){}
  System.xhr="mz";throw new Error(System.NOT_XHR);}
}
Function.prototype.Extends=function(SuperClass,ClassName)
{
  var op=this.prototype, i, p=this.prototype=new SuperClass();
  if(ClassName)p._className=ClassName; for(i in op)p[i]=op[i];
  if(p.hashCode)delete System._instances[p.hashCode];return p;
};

window.Using = function(namespace,op)//op{path,rename,nocache,expires}
{
  var N=namespace,C=N.substr(N.lastIndexOf(".")+1);
  var S=System,i=S._prototypes;op=op||{};
  if(S._exist(N,op.path)){if(window[C]!=i[N])window[C]=i[N];return}
  if(!/((^|\.)[\w_\$]+)+$/.test(N))throw new Error(N+" nonstandard namespace")

  for(var s,e,c=N+".",i=c.indexOf(".");i>-1;i=c.indexOf(".",i+1)){
  e=c.substring(0,i);s=(e.indexOf(".")==-1)?"window['"+e+"']":e;
  if(e&&typeof(eval(s))=="undefined")
  eval(s+"=function(){return System._getPrototype('"+e+"',arguments)}");}

  if(typeof(S._codebase[N])!="string"&&(s=S.load(N,op))) S._codebase[N]=
    s+";\r\nSystem._prototypes['"+N+"']=window['"+(op.rename||C)+"']="+C;
  S._existences[N]=S._mapPath(N, op);

  if(typeof(s=S._codebase[N])=="string")try{(new Function(s))()}
  catch(e){S.alert("Syntax error on load "+ N +"\n"+ e.message)}
};
window.Include=function(namespace, op)//op{path,nocache,expires}
{
  var N=namespace,c,S=System,X=S.supportsXmlHttp();op=op||{};
  if(S._exist(N,op.path))return;
  if(!/((^|\.)[\w_\$]+)+$/.test(N))throw new Error(N+" nonstandard namespace")

  if(typeof(S._codebase[N])!="string")if(X&&(c=S.load(N,op)))
  S._codebase[N]=c;S._existences[N]=S._mapPath(N,op);

  var B=(typeof(S._codebase[N])=="string");try{
  if(window.execScript&&B)window.execScript(S._codebase[N]);else
  {
    var s=document.createElement("SCRIPT");s.type="text/javascript";
    if(B)s.innerHTML="eval(System._codebase['"+N+"']);";
    else{s.src=S._existences[N]=S._mapPath(N,op); System.xhr="mz";}
    document.getElementsByTagName("HEAD")[0].appendChild(s);s=null}
  }catch(B){S.alert("Syntax error on include "+N+"\n"+ B.message);}
};
window.Import=function(ns,op){Using(ns,op)};
window.Instance=function(hashCode){return System._instances[hashCode]};

if(S.ie&&S._userdata){try{
S.scriptElement.addBehavior("#default#userdata")}catch(ex){S._userdata=false}
S.saveUserData=function(key,value,expires)
{
  try{var d=new Date();
  if(typeof(expires)=="number")d.setTime(d.getTime()+expires);
  else d.setDate(d.getDate()+30);//30 day
  var t=this.scriptElement;t.load(key.replace(/\W/g,"_"));
  t.setAttribute("code",value);
  t.setAttribute("version",this.currentVersion);
  t.expires=d.toUTCString();t.save(key.replace(/\W/g,"_"));
  return t.getAttribute("code");}catch(ex){this._userdata=false}
}
S.loadUserData=function(key)
{
  try{var t=this.scriptElement;t.load(key.replace(/\W/g,"_"));
  if(this.currentVersion!=t.getAttribute("version")){
  if(t.getAttribute("code"))this.deleteUserData(key);return ""}
  return t.getAttribute("code");}catch(ex){this._userdata=false;return ""}
}
S.deleteUserData=function(key)
{
  try{var t=this.scriptElement;t.load(key.replace(/\W/g,"_"));
  t.expires=new Date(315532799000).toUTCString();
  t.save(key.replace(/\W/g, "_"));}catch(ex){this._userdata=false}}
} S=t=null;

Include("System.Global");

})()}