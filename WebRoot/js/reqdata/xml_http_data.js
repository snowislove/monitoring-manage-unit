
/**
 * @author baiyanwei
 * 2009-2-17
 */

function createRequestObject(){
    //ajax 导步通信
    var ro;
    var browser = navigator.appName;
    if (browser == "Microsoft Internet Explorer") {
        ro = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else {
        ro = new XMLHttpRequest();
    }
    return ro;
}
//显示指定属性，DIMCLASS，页码的数据DIV内容
function setSelDivDataTextObj(dataDiv,reqpath){
    //设置正在读取数据提示
    dataDiv.innerHTML = "数据读取中....";
    //设置DIV中显示的配置项
    var http = createRequestObject();
    //请求DIMCLASS数据
    http.open("get", reqpath);
    http.onreadystatechange = function handleResponse(){
        if (http.readyState == 4) {
            var infor = http.responseText;
            //判断是否取DIMCLASS数据成功
            if (infor != null && infor != "" && infor != "nofind") {
                dataDiv.innerHTML = infor;
            }
            else {
                //置空页码
    			dataDiv.innerHTML = "提示：没有取得对应的资源配置项。<br>原因：没有对应的资源配置项或系统正忙。";
            }
        }
    };
    http.send(null);
}