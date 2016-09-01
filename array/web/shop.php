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
  <title>Shop Dashboard</title>

  <!-- Favicons-->
  <link rel="icon" href="images/favicon/favicon-32x32.png" sizes="32x32">
  <!-- Favicons-->
  <link rel="apple-touch-icon-precomposed" href="images/favicon/apple-touch-icon-152x152.png">
  <!-- For iPhone -->
  <meta name="msapplication-TileColor" content="#00bcd4">
  <meta name="msapplication-TileImage" content="images/favicon/mstile-144x144.png">
  <!-- For Windows Phone -->


  <!-- Table -->
  
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
				<a href="editshop.php" class="waves-effect waves-cyan"><i class="mdi-editor-border-color"></i>Edit shop</a>
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
                    <div id="card-stats">
                        <div class="row">
                            <div class="col s12 m6 l3">
                                <div class="card">
                                    <div class="card-content  red white-text">
                                        <p class="card-stats-title"><i class="mdi-action-store"></i><?php echo $shopDetail->name?></p>
                                        
                                    </div>
                                   
                                </div>
                            </div>
							
							<div class="col s12">
                                <div class="card">
                                    <div class="card-content black-text">
										     
										<div class="row">
											 <div class="col s12 m6 l3">
												<div class="input-field col s12">
													<input id="searchInput" name="search" type="text">
													<label for="searchInput">Searching...</label>
												</div>
											</div>
										</div>
										<div class="row">
											<table class="table">
												<thead>
												  <tr>
													  <th data-field="id">ID </th>
													  <th data-field="name">Name Branch</th>
													  <th data-field="price">Phone Number</th>
													  <th data-field="price">Mobile Number</th>
												  </tr>
												</thead>

												<tbody>
												<?php
												$id = 0;
												while($BranchData = $BranchQuery->fetch_object())
												{
													$id++;
													echo "<tr>";
													echo "<td>".$id."</td>";
													echo "<td>".$BranchData->name."</td>";
													echo "<td>".$BranchData->mobile_number."</td>";
													echo "<td>".$BranchData->phone_number."</td>";
													echo "</tr>";
														//echo $BranchQuery->id_doctor;
												}
												?>
												 
												</tbody>
											  </table>
												
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

	<!-- data-tables -->
   <script src="js/filterForTable.js"></script>
    <script>
        $('table').filterForTable();
    </script>
    <!--plugins.js - Some Specific JS codes for Plugin Settings-->
    <script type="text/javascript" src="js/plugins.js"></script>
	


</body>

</html>