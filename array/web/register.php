<?php
session_start();

	if(isset($_SESSION['class']) && $_SESSION['error'] == false)
	{
		header("location:loadbalance.php");
	}
			


?>

<!DOCTYPE html>
<html lang="en">

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="msapplication-tap-highlight" content="no">
 
   <title>Register Page </title>

  <!-- Favicons-->
  <link rel="icon" href="images/favicon/favicon-32x32.png" sizes="32x32">
  <!-- Favicons-->
  <link rel="apple-touch-icon-precomposed" href="images/favicon/apple-touch-icon-152x152.png">
  <!-- For iPhone -->
  <meta name="msapplication-TileColor" content="#00bcd4">
  <meta name="msapplication-TileImage" content="images/favicon/mstile-144x144.png">
  <!-- For Windows Phone -->


  <!-- CORE CSS-->
  
  <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="css/style.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection">
  
    <!-- Sweetalert -->
   <script src="sweetalert/dist/sweetalert-dev.js"></script>
   <link rel="stylesheet" href="sweetalert/dist/sweetalert.css">

  <!-- INCLUDED PLUGIN CSS ON THIS PAGE -->
  <link href="css/prism.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="js/plugins/perfect-scrollbar/perfect-scrollbar.css" type="text/css" rel="stylesheet" media="screen,projection">
  

</head>

<body class="cyan">
  <!-- Start Page Loading -->
 
  <!-- End Page Loading -->



  <div id="login-page" class="row">
    <div class="col s12 z-depth-4 card-panel">
      <form class="login-form" id="form_register" name="form_register"  >
        <div class="row">
          <div class="input-field col s12 center">
            <h4>Register</h4>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="username" name="username" type="text">
            <label for="username" class="center-align">Username</label>
          </div>
        </div>
		<div class="row margin">
          <div class="input-field col s12">
            <input id="first_name" name="first_name" type="text">
            <label for="first_name" class="center-align">First name</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="last_name" name="last_name" type="text">
            <label for="last_name" class="center-align">Last name</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="birthday" name="birthday" class="datepicker"  type="date" >
            
          </div>
        </div>
        <div class="row margin">
            <div class="input-field col s12">
                <select id="gender" name="gender">
					<option value="" disabled selected>Select gender</option>
					<option value="male">Male</option>
					<option value="famale">Famale</option>
                </select>
					<label for="gender" >Gender</label>
            </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="email" name="email" type="email">
            <label for="email" class="center-align">E-mail</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="password" name="password" type="password">
            <label for="password">Password</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <input id="repassword" name="repassword" type="password">
            <label for="repassword">Re-password</label>
          </div>
        </div>
      </form>
	    <div class="row">
          <div class="margin center input-field col s12">
            <button class="btn waves-effect waves-light " onclick="checkRegister()">Register</button>
          </div>
          <div class="input-field col s12">
            <p class="margin center medium-small sign-up">I'm have a username? <a href="index.php">Login</a></p>
          </div>
        </div>
    </div>
  </div>

<script type="text/javascript">   
 function checkRegister()
{
	
var username = document.forms["form_register"]["username"].value;
var first_name = document.forms["form_register"]["first_name"].value;
var last_name = document.forms["form_register"]["last_name"].value;
var birthday = document.forms["form_register"]["birthday"].value;
var gender = document.forms["form_register"]["gender"].value;
var email = document.forms["form_register"]["email"].value;
var password = document.forms["form_register"]["password"].value;
var repassword = document.forms["form_register"]["repassword"].value;
var class_data = "customer";

	if (!checkEmpty(username))
	{
		swal({title: "Please input Username",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else if(!checkEmpty(first_name))
	{
		swal({title: "Please input First name ",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else if(!checkEmpty(last_name))
	{
		swal({title: "Please input Last name ",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else if(!checkEmpty(birthday))
	{
		swal({title: "Please input Birthday ",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else if(!checkEmpty(gender))
	{
		swal({title: "Please input Gender ",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else if(!checkEmpty(email))
	{
		swal({title: "Please input E-mail",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else if(!checkPassword(password,repassword))
	{
		swal({title: "Please input not match",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else 
	{
		birthday = splitedate(birthday);
		
		$.post("../api/v1/register", {username: username, first_name: first_name, last_name: last_name, birthday: birthday, gender: gender
		, email: email, password: password, repassword: repassword, class : class_data}, 
		function(result){
			
			//console.log(result.error);
			
			if(!result.error)
			{
				//window.location = "loadbalance.php";
				swal("Register success!", " ", "success");
				
			}
			else
			{
				swal({title: ""+result.message,      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
			}
       
		});
	
	}
	
	
}


function splitedate(assign)
{
	var res = assign.split(' ');
    var res2 = res[1].split(',');
    
	if(res[0] < 10 )
	{
		res[0] = "0"+res[0];
	}
	else
	{
		var year = res[2];
	    var day = res[0];
	
		if(res2[0] == "January")
		{
			return year+"-"+"01"+"-"+day;
		}
		else if(res2[0] == "February")
		{
			return year+"-"+"02"+"-"+day;
		}
		else if(res2[0] == "March")
		{
			return year+"-"+"03"+"-"+day;
		}
		else if(res2[0] == "April")
		{
			return year+"-"+"04"+"-"+day;
		}
		else if(res2[0] == "May")
		{
			return year+"-"+"05"+"-"+day;
		}
		else if(res2[0] == "June")
		{
			return year+"-"+"06"+"-"+day;
		}
		else if(res2[0] == "July")
		{
			return year+"-"+"07"+"-"+day;
		}
		else if(res2[0] == "August")
		{
			return year+"-"+"08"+"-"+day;
		}
		else if(res2[0] == "September")
		{
			return year+"-"+"09"+"-"+day;
		}
		else if(res2[0] == "October")
		{
			return year+"-"+"10"+"-"+day;
		}
		else if(res2[0] == "November")
		{
			return year+"-"+"11"+"-"+day;
		}
		else
		{
			return year+"-"+"12"+"-"+day;
		}
	}
	
	
	
}


function checkPassword(pass,repass) {
    if(pass == "" || repass == "")
	{
		return false;
	}
	else if(pass != repass)
	{
		return false;
	}
	else
	{
		return true;
	}
}

function checkEmpty(str) {
    if(str == "" )
	{
		return false;
	}
	else
	{
		return true;
	}
}

</script>

  <!-- ================================================
    Scripts
    ================================================ -->

  <!-- jQuery Library -->
  <script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
  <!--materialize js-->
  <script type="text/javascript" src="js/materialize.js"></script>
  <!--prism-->
  <script type="text/javascript" src="js/prism.js"></script>
  <!--scrollbar-->
  <script type="text/javascript" src="js/plugins/perfect-scrollbar/perfect-scrollbar.min.js"></script>
 <!--plugins.js - Some Specific JS codes for Plugin Settings-->
    <script type="text/javascript" src="js/plugins.js"></script>
    


</body>

</html>