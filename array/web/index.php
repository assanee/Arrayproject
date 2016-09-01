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

  <title>Login Page</title>

  <!-- Favicons-->
  <link rel="icon" href="images/favicon/favicon-32x32.png" sizes="32x32">
  <!-- Favicons-->
  <link rel="apple-touch-icon-precomposed" href="images/favicon/apple-touch-icon-152x152.png">
  <!-- For iPhone -->
  <meta name="msapplication-TileColor" content="#00bcd4">
  <meta name="msapplication-TileImage" content="images/favicon/mstile-144x144.png">
  <!-- For Windows Phone -->

   <!-- Sweetalert -->
   <script src="sweetalert/dist/sweetalert-dev.js"></script>
   <link rel="stylesheet" href="sweetalert/dist/sweetalert.css">

  <!-- CORE CSS-->
  
  <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="css/style.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="css/page-center.css" type="text/css" rel="stylesheet" media="screen,projection">

  <!-- INCLUDED PLUGIN CSS ON THIS PAGE -->
  <link href="css/prism.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="js/plugins/perfect-scrollbar/perfect-scrollbar.css" type="text/css" rel="stylesheet" media="screen,projection">
  
</head>

<body class="cyan">
  <!-- Start Page Loading -->
 
  <!-- End Page Loading -->



  <div id="login-page" class="row">
    <div class="col s12 z-depth-4 card-panel">
      <form class="login-form" id="form_login" name="form_login" >
        <div class="row">
          <div class="input-field col s12 center">
            <img src="images/login-logo.png" alt="" class="circle responsive-img valign profile-image-login">
            <p class="center login-form-text">Array System</p>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-social-person-outline prefix"></i>
            <input id="username_email" name="username_email" type="text">
            <label for="username_email" class="center-align">E-mail or Username</label>
          </div>
        </div>
        <div class="row margin">
          <div class="input-field col s12">
            <i class="mdi-action-lock-outline prefix"></i>
            <input id="password" name="password" type="password">
            <label for="password">Password</label>
          </div>
        </div>
        
      </form>
	    <div class="row">
          <div class="margin center input-field col s12">
            <button class="btn waves-effect waves-light" onclick="checkLogin()">Login</button>
          </div>
        </div>
        <div class="row">
          <div class="input-field col s6 m6 l6">
            <p class="margin medium-small"><a href="register.php">Register!</a></p>
          </div>
                  
        </div>

    </div>
  </div>

<script type="text/javascript">   
 function checkLogin()
{
	
var username_email = document.forms["form_login"]["username_email"].value;
var password = document.forms["form_login"]["password"].value;



	if (!checkEmpty(username_email))
	{
		swal({title: "Please input e-mail or username",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else if(!checkEmpty(password))
	{
		swal({title: "Please input password",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
	}
	else 
	{
		$.post("../api/v1/loginweb", {email: username_email, password: password}, 
		function(result){
			
			if(!result.error)
			{
				console.log(result.error);
				console.log(result.class);
				
				window.location = "loadbalance.php";
			}
			else
			{
				console.log(result.error);
			}
				
		});
	
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



</body>

</html>