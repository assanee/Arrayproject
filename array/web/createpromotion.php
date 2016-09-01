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
                    <h4 class="header2">Create Promotion</h4>
					<form id="form_data" method="post" action="" enctype="multipart/form-data">
                    <div class="row">
                      <div class="col s12">
                        <div class="row">
                          <div class="input-field col s12">
                            <select id="id_branch" name="id_branch">
								<option value="" disabled selected>Select branch</option>
									<?php
										$id = 0;
										while($BranchData = $BranchQuery->fetch_object())
										{
											
											echo "<option value=\"".$BranchData->id_branch."\">".$BranchData->name."</option>";
											
										}
									?>
								
							</select>
								<label for="id_branch" >Branch</label>
                          </div>
                        </div>
                        <div class="row">
                          <div class="input-field col s12">
                            <input id="name_promotion" name="name_promotion" type="text">
                            <label for="name_promotion ">Promotion Name</label>
                          </div>
                        </div>
                        <div class="row">
							<div class="input-field col s12">
								<div class="file-field input-field">
									<div class="btn">
										<span>File</span>
										<input id="logo_mini" name="logo_mini" type="file" accept="image/x-png, image/jpeg">
									</div>
									<div class="file-path-wrapper">
										<input class="file-path validate" type="text" placeholder="Image promotion full">
									</div>
								</div>
							</div>
                        </div>
                        <div class="row">
							<div class="input-field col s12">
								<div class="file-field input-field">
									<div class="btn">
										<span>File</span>
										<input id="logo_full" name="logo_full" type="file" accept="image/x-png, image/jpeg">
									</div>
									<div class="file-path-wrapper">
										<input class="file-path validate" type="text" placeholder="Image promotion mini">
									</div>
								</div>
							</div>
                        </div>
                        <div class="row">
                          <div class="input-field col s12">
                            <textarea id="detail" name="detail" rows="4" class="materialize-textarea"></textarea>
                            <label for="detail">Detail</label>
                          </div>
                        </div>
                       <div class="row">
                          <div class="input-field col s12">
                            <input id="start_promotion" name="start_promotion" class="datepicker" type="date" >
							<label for="start_promotion">Start promotion</label>
                          </div>
                        </div>
                        <div class="row">
                          <div class="input-field col s12">
                            <input id="end_promotion" name="end_promotion" class="datepicker" type="date" >
							<label for="end_promotion">End promotion</label>
                          </div>
                        </div>
                        
                      </div>
                    </div>
					<form>
					<div class="row">
                            <div class="input-field col s12">
                              <button class="btn cyan waves-effect waves-light right"  name="action"  >Submit
                                <i class="mdi-content-send right"></i>
                              </button>
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
  
		$(document).ready(function (e) {
			$("#form_data").on('submit',(function(e) {
				
				

			e.preventDefault();
			
				var id_branch = document.getElementById("id_branch").value;
				var name_promotion = document.getElementById("name_promotion").value;
				var logo_mini = document.getElementById("logo_mini").value;
				var logo_full = document.getElementById("logo_full").value;
				var detail = document.getElementById("detail").value;
				var start_promotion = document.getElementById("start_promotion").value;
				var end_promotion = document.getElementById("end_promotion").value;
				
			
				if (!checkEmpty(id_branch))
				{
					swal({title: "Please select branch",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
					return false;
				}
				else if(!checkEmpty(name_promotion))
				{
					swal({title: "Please input promotion name",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
					return false;
				}
				else if(!checkEmpty(detail))
				{
					swal({title: "Please input detail",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
					return false;
				}
				else if(!checkEmpty(start_promotion))
				{
					swal({title: "Please start promotion",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
					return false;
				}
				else if(!checkEmpty(end_promotion))
				{
					swal({title: "Please input end promotion",      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
					
				}
				else 
				{
					
					$.ajax({
					url: "../api/v1/createpromotionweb", // Url to which the request is send
					type: "POST",             // Type of request to be send, called as method
					data: new FormData(this), // Data sent to server, a set of key/value pairs (i.e. form fields and values)
					contentType: false,       // The content type used when sending data to the server.
					cache: false,             // To unable request pages to be cached
					processData:false,        // To send DOMDocument or non processed data file it is set to false
					success: function(result)   // A function to be called if request succeeds
					{
						
						if(!result.error)
						{
							console.log(result.error);
							
							swal(result.message, " ", "success");
							
							
						}
						else
						{
							swal({title: result.message,      type: "warning",confirmButtonColor: "#F44336",   confirmButtonText: "OK",   closeOnConfirm: false });
						}
						
					}
					
					});
				
				}

			
			
			}));


		});

		

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