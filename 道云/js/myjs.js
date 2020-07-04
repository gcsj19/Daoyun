function createXmlHttp(){
    var xmlHttp;
    try{ // Firefox, Opera 8.0+, Safari
        xmlHttp=new XMLHttpRequest();
    }
    catch (e){
        try{// Internet Explorer
            xmlHttp=new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (e){
            try{
                xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch (e){}
        }
    }

    return xmlHttp;
}


function checkUsername(){
    // 获得文件框值:
    var username = document.getElementById("username").value;
    // 1.创建异步交互对象
    var xhr = createXmlHttp();
    // 2.设置监听
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4){
            if(xhr.status == 200){
                document.getElementById("span1").innerHTML = xhr.responseText;
            }
        }
    }
    // 3.打开连接
    xhr.open("GET","${pageContext.request.contextPath}/checkNameAction.action?time="+new Date().getTime()+"&username="+username,true);
    // 4.发送
    xhr.send(null);
}