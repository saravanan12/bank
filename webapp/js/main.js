/*function callMyAction() {

	$.ajax({
		type: "POST",
		url: "hello",
		data: { field1: "hello", field2: "hello2" },
		success: function(response) {
			console.log(response)
		},
		error: function(response) {
			alert("No values found..!!");
		}
	});
	console.log("called my function")
}
	
function callPost(){
	console.log("called post function")
	$.post('login', { "first": "hello", "second": "hello2" },
		function(mapResonse) {
			console.log("map response "+mapResonse);
			console.log("map response key1 "+mapResonse["key1"]);
		});
}*/
/*function login(){
	
	$.ajax({
        url: 'login',
        type: 'POST',
        data: '{ "userName": "saro", "password": "123"}' ,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
           console.log("map response "+response);
          // console.log("map response key "+response["maps"]["key1"]);
        },
        error: function () {
            alert("error");
        }
    }); 
    
}

function login2(cId,pwd){
	
	$.ajax({
        url: 'login',
        type: 'POST',
        data: '{ "customerId": '+cId+', "password": "'+pwd+'"}' ,
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
           console.log("map response -> ",response["maps"]);
          // console.log("map response key "+response["maps"]["key1"]);
        },
        error: function () {
            alert("error");
        }
    }); 
    
}

function signUpRequest(){
	
	var params = {"firstName":"saro","lastName":"guna","emailId":"saroguna@gmail.com","password":"Test@12","phone":"9876543234","gender":"Male"}
	$.ajax({
        url: 'signUp',
        type: 'POST',
        data: JSON.stringify(params),
        contentType: 'application/json; charset=utf-8',
        success: function (response) {
           console.log("signup response "+response);
          // console.log("map response key "+response["maps"]["key1"]);
        },
        error: function () {
            alert("error");
        }
    });
}*/

function checkLoggedStatus(){
	var bankCookie = document.cookie;
	var url ="";
	var path = window.location.pathname;
	var refresh = false
	console.log("cookie ->"+bankCookie);
	if(bankCookie.indexOf("bankKey")>=0 && path.indexOf("create")<0){ // already logged
		url = "create.html"	
		refresh = true;
	}else if(bankCookie.indexOf("bankKey")<0 && path.indexOf("login")<0){ //User has no login status -> redirect to login page
		url = "login.html";
		refresh = true;
		
	}
	console.log("url ->"+url+" , refresh :"+refresh);
	if(refresh){
		setTimeout(function(){document.location.href = url},100);
	}
	disableNav()
}
function disableNav(){
	//window.history.pushState(null, null, window.location.href); 
	window.history.pushState(null, null, window.location.href);
	window.onpopstate = function () {
    	window.history.go(1);
	}; 
}

var Signup ={
	
	validation:function(){
		
		var parent = $("#signupTable");
		var fName =  parent.find("[name='fName']").val()
		var lName =  parent.find("[name='lName']").val()
		var email =  parent.find("[name='mail']").val()
		var password =  parent.find("[name='password']").val()
		var phone =  parent.find("[name='phone']").val()
		console.log(fName,lName,email,password,phone)
		var params = {"firstName":fName,"lastName":lName,"emailId":email,"password":password,"phone":phone,"gender":"Male"}
		if(fName!="" && lName!="" && email!="" && password!=""){
			this.submit(params);
		}else{
			alert("Please submit the datas")
		}
	},
	submit:function(params){
		
		$.ajax({
	        url: 'signUp',
	        type: 'POST',
	        data: JSON.stringify(params),
	        contentType: 'application/json; charset=utf-8',
	        success: function (response) {
			 	var response  = response["data"]["response"];
			 	var status  = response["status"];
			 	var message = response["message"];
			 	if(status==1){ // success
			 		var customerId = response["customerId"];
					alert("customer id "+customerId+", "+message);
					Navigation.navigate("login");
				}else{
					alert(message);
				}
			   console.log("signup error ",error);
	           console.log("signup response ",response);
	        },
	        error: function () {
	            alert("Something went wrong!");
	        }
	    });
		
	}
}
var customerId1 = "";
var Login ={
	validation:function(){
		var parent = $("#loginTable");
		var customerId =  parent.find("[name='customerId']").val()
		var password =  parent.find("[name='password']").val()
		console.log("customerId ",customerId,", password ,",password);
		if(customerId!="" && password!=""){
			var params = {"customerId":customerId,"password":password}
			this.submit(params);
		}else{
			alert("Please submit the datas")
		}
	},
	submit:function(params){
		$.ajax({
	        url: 'login',
	        type: 'POST',
	        data: JSON.stringify(params) ,
	        contentType: 'application/json; charset=utf-8',
	        success: function (response) {
				localStorage.setItem("customerId",params["customerId"])
				var response = response["data"]["response"]
				var status = response["status"];
				var message  = response["message"];
	            console.log("map response ",response);
	            console.log("message -> ",message)
	            if(status==1){ //Success
					Navigation.navigate("balance");
				}else{
					
				}
				alert(message)
	        },
	        error: function () {
	            alert("Something went wrong!");
	        }
 	    }); 
	},
	signOut:function(){
		localStorage.setItem("customerId","")
		localStorage.setItem("accountId","")
        localStorage.setItem("firstName","")
        localStorage.setItem("lastName","")
		Navigation.navigate("home");
	}
}

var Navigation = {
	
	navigate:function(tabKey){
		console.log("tabname",tabKey)
		var path = window.location.pathname;
		var page ="";
		if(tabKey=="signup"){
			page = "signup.html";
		}else if(tabKey == "login"){
			page = "login.html";
		}else if(tabKey=="home"){
			page ="index1.html";
		}else if(tabKey == "balance"){
			page ="balance.jsp";
		}else if(tabKey == "deposit"){
			page ="deposit.jsp";
		}else if(tabKey == "withdraw"){
			page ="withdraw.jsp";
		}
		if(page!="" && path.indexOf(page)<0){
			setTimeout(function(){document.location.href = page},100);
		}
	}
}

var Balance ={
	getBalanceInfo:function(){
		var params = {"customerId":localStorage.getItem("customerId")}
		$.ajax({
	        url: 'balance',
	        type: 'POST',
	        data: JSON.stringify(params) ,
	        contentType: 'application/json; charset=utf-8',
	        success: function (response) {
				var response = response["data"]["response"]
				var parent = $("#balanceTable");
			    parent.find("[name='customerId']").html(response["customerId"])
		        parent.find("[name='username']").html(response["firstName"] +" "+response["lastName"])
		        parent.find("[name='balance']").html(response["balance"])
		        localStorage.setItem("accountId",response["accountId"])
		        localStorage.setItem("firstName",response["firstName"])
		        localStorage.setItem("lastName",response["lastName"])
	           console.log("map response ",response);
	        },
	        error: function () {
	            alert("Something went wrong!");
	        }
 	    }); 
	}
}

var Transactions = {
	
	depositInit:function(){
		var parent = $("#depositTable");
	    parent.find("[name='customerId']").html(localStorage.getItem("customerId"))
        parent.find("[name='username']").html(localStorage.getItem("firstName") +" "+localStorage.getItem("lastName"));
	},
	deposit:function(){
		var parent = $("#depositTable");
		var amountInputBox = parent.find("[name='amount']")
		var amount =  amountInputBox.val()
		var mode = "deposit";
		var params = {"amount":amount,"action":mode,"accountId":localStorage.getItem("accountId")}
		this.execute(params,this.depositCallback);
		
	},
	depositCallback:function(){
		var parent = $("#depositTable");
		var amountInputBox = parent.find("[name='amount']")
		amountInputBox.val(0);
	},
	withdrawInit:function(){
		var parent = $("#withdrawTable");
	    parent.find("[name='customerId']").html(localStorage.getItem("customerId"))
        parent.find("[name='username']").html(localStorage.getItem("firstName") +" "+localStorage.getItem("lastName"));
        var params = {"customerId":localStorage.getItem("customerId")}
        $.ajax({
	        url: 'balance',
	        type: 'POST',
	        data: JSON.stringify(params) ,
	        contentType: 'application/json; charset=utf-8',
	        success: function (response) {
			    var response = response["data"]["response"]
				parent.find("[name='currentBalance']").html(response["balance"])
			}
		}); 
	},
	withdraw:function(){
		var parent = $("#withdrawTable");
		var amountInputBox = parent.find("[name='amount']")
		var benificiaryInputBox = parent.find("[name='benificiary']")
		var amount =  amountInputBox.val()
		var benificiaryAccount = benificiaryInputBox.val()
		var mode = "withdraw";
		var params = {"amount":amount,"action":mode,"accountId":localStorage.getItem("accountId"),"beneficiary":benificiaryAccount}
		this.execute(params,this.transferCallback);
		
		
	},
	transferCallback:function(response){
		var parent = $("#withdrawTable");
		var amountInputBox = parent.find("[name='amount']")
		var benificiaryInputBox = parent.find("[name='benificiary']")
		var currentBalance =  parent.find("[name=currentBalance]")
		amountInputBox.val(0);
		benificiaryInputBox.val("")
		currentBalance.html(response["balance"])
		
	},
	execute:function(params,callback){
		$.ajax({
	        url: 'transfer',
	        type: 'POST',
	        data: JSON.stringify(params) ,
	        contentType: 'application/json; charset=utf-8',
	        success: function (response) {
				var response = response["data"]["response"]
				alert(response["message"]);
 	            callback(response)
	        },
	        error: function () {
	            alert("Something went wrong!");
	        }
 	    });
	}
	
}

      
        
