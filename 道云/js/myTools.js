function checkForm(){
	// 校验用户名:
	// 获得用户名文本框的值:
	var username = document.getElementById("username").value;
	if(username == null || username == ''){
		alert("用户名不能为空!");
		return false;
	}
	// 校验密码:
	// 获得密码框的值:
	var password = document.getElementById("password").value;
	if(password == null || password == ''){
		alert("密码不能为空!");
		return false;
	}
	// 校验确认密码:
	var repassword = document.getElementById("repassword").value;
	if(repassword != password){
		alert("两次密码输入不一致!");
		return false;
	}
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

function change(){
	var img1 = document.getElementById("checkImg");
	img1.src="checkImgAction.action?"+new Date().getTime();
}

function removeCart(cart_id,product_id){
	//获取页面标签
	var page = document.getElementById("pageTag").title;
	// 1.创建异步交互对象
	var xhr = createXmlHttp();
	// 2.设置监听
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				alert("删除成功！");
				if(page=="index"){
					var url = "pageIndex.action?time="+new Date().getTime();
				}else if(page=="shop"){
					var url = "pageShop.action?time="+new Date().getTime();
				}else if(page=="detail"){
					var url = "pageDetail.action?product_id="+product_id;
				}
				location.replace(url);
			}
		}
	}
	// 3.打开连接
	xhr.open("GET","removeCartAction.action?time="+new Date().getTime()+"&cart_id="+cart_id+"&page="+page,true);
	// 4.发送
	xhr.send(null);
}

function purchar(){
	var r = comfirm("确认购买该资源？");
	if (r == true) {
		window.location.href="登录页面.jsp";
		} else {
			
		}

}
