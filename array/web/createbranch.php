<?php

		session_start();

		if($_SESSION['class'] == "shop")
		{
			include_once dirname(__FILE__) . '/../api/include/Config.php';

			// Connecting to mysql database
			$conn = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
			$conn->set_charset('utf8');
			
			$shopData = $conn->query("SELECT * FROM shop WHERE shop.id_user = (SELECT users.id_user FROM users  WHERE users.api_key = '".$_SESSION['apiKey']."' )");
			if($shopData->num_rows == 0)
			{
				header("location:createshop.php");	
			}
			else
			{
				$shopDetail = $shopData->fetch_object();
				
				$BranchQuery = $conn->query("SELECT * FROM branch WHERE branch.id_shop = (SELECT shop.id_shop FROM shop WHERE shop.id_user = (SELECT users.id_user FROM users  WHERE users.api_key = '".$_SESSION['apiKey']."' ))");
				
			}
			
		}
		else
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
  <meta name="description" content="Materialize is a Material Design Admin Template,It's modern, responsive and based on Material Design by Google. ">
  <meta name="keywords" content="materialize, admin template, dashboard template, flat admin template, responsive admin template,">
  <title>Create Branch</title>

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

   <!-- Sweetalert -->
   <script src="sweetalert/dist/sweetalert-dev.js"></script>
   <link rel="stylesheet" href="sweetalert/dist/sweetalert.css">

  <!-- INCLUDED PLUGIN CSS ON THIS PAGE -->
  <link href="css/prism.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="js/plugins/chartist-js/chartist.min.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="js/plugins/perfect-scrollbar/perfect-scrollbar.css" type="text/css" rel="stylesheet" media="screen,projection">
  <link href="js/plugins/fullcalendar/css/fullcalendar.min.css" type="text/css" rel="stylesheet" media="screen,projection">
  
  
</head>

<body>
  <!-- Start Page Loading -->
    
    <!-- End Page Loading -->

  <!-- //////////////////////////////////////////////////////////////////////////// -->

  <!-- START HEADER -->
  <header id="header" class="page-topbar">
        <!-- start header nav-->
        <div class="navbar-fixed">
            <nav class="cyan">
                <div class="nav-wrapper">
                    <h1 class="logo-wrapper"><a href="index.html" class="brand-logo darken-1"><img src="images/materialize-logo2.png" alt="materialize logo"></a> <span class="logo-text">Materialize</span></h1>
                    <ul class="right hide-on-med-and-down">
                        
						
                       
                    </ul>
                </div>
            </nav>
        </div>
        <!-- end header nav-->
  </header>
  <!-- END HEADER -->

  <!-- //////////////////////////////////////////////////////////////////////////// -->

  <!-- START MAIN -->
  <div id="main">
    <!-- START WRAPPER -->
    <div class="wrapper">

      <!-- START LEFT SIDEBAR NAV-->
      <aside id="left-sidebar-nav">
        <ul id="slide-out" class="side-nav fixed leftside-navigation">
            <li class="user-details cyan darken-2">
                <div class="row">
                    <div class="col col s4 m4 l4">
                         <img src="../api/logo_brand/<?php echo $shopDetail->logo;?>" alt="" class=" responsive-img valign profile-image">
                    </div>
                    <div class="col col s8 m8 l8">
                        <ul id="profile-dropdown" class="dropdown-content">
                            <li><a href="logout.php"><i class="mdi-hardware-keyboard-tab"></i> Logout</a>
                            </li>
                        </ul>
                        <a class="btn-flat dropdown-button waves-effect waves-light white-text profile-btn" href="#" data-activates="profile-dropdown"><?php echo $_SESSION['first_name'];?><i class="mdi-navigation-arrow-drop-down right"></i></a>
                        <p class="user-roal"><?php echo $_SESSION['class'];?></p>
                    </div>
                </div>
            </li>
            <li class="bold">
				<a href="shop.php" class="waves-effect waves-cyan"><i class="mdi-editor-insert-invitation"></i>Dashboard</a>
            </li>
			<li class="bold">
				<a href="createbranch.php" class="waves-effect waves-cyan"><i class="mdi-av-queue"></i>Create branch</a>
            </li>
			<li class="bold">
				<a href="createpromotion.php" class="waves-effect waves-cyan"><i class="mdi-av-queue"></i>Create promotion</a>
            </li>
			
        </ul>
        <a href="#" data-activates="slide-out" class="sidebar-collapse btn-floating btn-medium waves-effect waves-light hide-on-large-only darken-2"><i class="mdi-navigation-menu" ></i></a>
      </aside>
      <!-- END LEFT SIDEBAR NAV-->

      <!-- //////////////////////////////////////////////////////////////////////////// -->

      <!-- START CONTENT -->
      <section id="content">
        
        <!--start container-->
        <div class="container">
          <div class="section">

            
		  
		  <!--card stats start-->
                    <div class="col s12 m12 l6">
                  <div class="card-panel">
                    <h4 class="header2">Create Branch</h4>
                    <div class="row">
                      <div class="col s12">
                        <div class="row">
                          <div class="input-field col s12">
                            <input id="branchname" name="branchname" type="text">
                            <label for="branchname">Branch Name</label>
                          </div>
                        </div>
                        <div class="row">
                          <div class="input-field col s12">
                            <input id="mobile_number" name="mobile_number" type="text">
                            <label for="mobile_number">Mobile number</label>
                          </div>
                        </div>
                        <div class="row">
                          <div class="input-field col s12">
                            <input id="phone_number" name="phone_number" type="text">
                            <label for="phone_number">Phone number</label>
                          </div>
                        </div>
                        <div class="row">
                          <div class="input-field col s12">
                            <input id="latitude" name="latitude" type="text">
                            <label for="latitude">Latitude</label>
                          </div>
                        </div>
                        <div class="row">
                          <div class="input-field col s12">
                            <input id="longitude" name="longitude" type="text">
                            <label for="longitude">Longitude</label>
                          </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                              <button class="btn cyan waves-effect waves-light right"  name="action" onclick="CreateBranch()" >Submit
                                <i class="mdi-content-send right"></i>
                              </button>
                            </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
			<!--card stats end-->
					
           <div class="divider"></div>
           
            </div>
        </div>
        <!--end container-->

	   
      </section>
      <!-- END CONTENT -->

      <!-- //////////////////////////////////////////////////////////////////////////// -->
      <!-- START RIGHT SIDEBAR NAV-->
      
	  <!-- LEFT RIGHT SIDEBAR NAV-->

    </div>
    <!-- END WRAPPER -->

  </div>
  <!-- END MAIN -->



  <!-- //////////////////////////////////////////////////////////////////////////// -->

  <!-- START FOOTER -->
  <footer class="page-footer">
    <div class="footer-copyright">
      <div class="container">
        <span>Copyright © 2016 <a class="grey-text text-lighten-4" href="https://stepsecret.xyz" target="_blank">XeusLab</a> All rights reserved.</span>
        <span class="right"> Design and Developed by <a class="grey-text text-lighten-4" href="https://stepsecret.xyz">XeusLab</a></span>
        </div>
    </div>
  </footer>
    <!-- END FOOTER -->



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
    
    <!-- chartist -->
    <script type="text/javascript" src="js/plugins/chartist-js/chartist.min.js"></script>   

    <!--plugins.js - Some Specific JS codes for Plugin Settings-->
    <script type="text/javascript" src="js/plugins.js"></script>

	<!-- Create Shop function -->
  <script type="text/javascript">  
		function CreateBranch()
		{
			var branchname = document.getElementById("branchname").value;
			var mobile_number = document.getElementById("mobile_number").value;
			var phone_number = document.getElementById("phone_number").value;
			var latitude = document.getElementById("latitude").value;
			var longitude = document.getElementById("longitude").value;
			
			
				if (!checkEmpty(branchname))
				{
					swal({title: "Please input Shop name",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
				}
				else if(!checkEmpty(mobile_number))
				{
					swal({title: "Please input mobile number",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
				}
				else if(!checkEmpty(phone_number))
				{
					swal({title: "Please input phone number",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
				}
				else if(!checkEmpty(latitude))
				{
					swal({title: "Please input latitude",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
				}
				else if(!checkEmpty(longitude))
				{
					swal({title: "Please input longitude",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
				}
				else 
				{
					//phone_number = "";
					
					$.post("../api/v1/createbranchweb", {name: branchname, mobile_number: mobile_number, phone_number: phone_number, latitude: latitude, longitude: longitude}, 
					function(result){
						
						if(!result.error)
						{
							console.log(result.error);
							
							swal(result.message, " ", "success");
							
							//window.location = "loadbalance.php";
							
						}
						else
						{
							swal({title: result.message,      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
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


</body>

</html>