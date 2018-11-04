

function verSubmit()
{
    var uname = $.trim($("#username").val());
    var passwd = $.trim($("#password").val());
    if(uname == "" || uname == null || uname == "您的用户名")
    {

        alert("用户名不能为空,请输入用户名!");
        document.getElementById("username").focus();
        return false;
    }
    if(passwd == "" || passwd == null || passwd == "登录密码")
    {
        alert("密码不能为空,请输入密码!");
        $("#password").focus();
        return false;
    }

//    $.ajax( {     
//        type: "POST",
//        url: "http://localhost:8080/login",   
//        dataType:"json",  
//        data: {username:'user',password:'1111'},  
//        success: function(result) {  
//
//         
//           		alert(JSON.stringify(result));
//          
//         
//        }     
//     });   
    
    $.post("http://localhost:8081/login", { username: "admin", password: "123456" },
    		function(data){
				alert(JSON.stringify(data));
	});

   // $("#loginForm").submit();

}



function tologin(){
	window.location.href="/";
}

