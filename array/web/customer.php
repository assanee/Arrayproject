<?php

		session_start();

		if($_SESSION['class'] == "customer")
		{
			
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
  <title>Calendar | Materialize - Material Design Admin Template</title>

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
            <li class="bold"><a href="user.php" class="waves-effect waves-cyan"><i class="mdi-editor-insert-invitation"></i>ตารางการนัดตรวจ</a>
            </li>
           
        </ul>
        <a href="#" data-activates="slide-out" class="sidebar-collapse btn-floating btn-medium waves-effect waves-light hide-on-large-only darken-2"><i class="mdi-navigation-menu" ></i></a>
      </aside>
      <!-- END LEFT SIDEBAR NAV-->

      <!-- //////////////////////////////////////////////////////////////////////////// -->

      <!-- START CONTENT -->
      <section id="content">
        
       ssss
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

    <!-- Calendar Script -->
    <script type="text/javascript" src="js/plugins/fullcalendar/lib/jquery-ui.custom.min.js"></script>
    <script type="text/javascript" src="js/plugins/fullcalendar/lib/moment.min.js"></script>
    <script type="text/javascript" src="js/plugins/fullcalendar/js/fullcalendar.min.js"></script>


    <!--plugins.js - Some Specific JS codes for Plugin Settings-->
    <script type="text/javascript" src="js/plugins.js"></script>

 <script type="text/javascript">   
  $(document).ready(function() {
    

    /* initialize the external events
    -----------------------------------------------------------------*/
    $('#external-events .fc-event').each(function() {

      // store data so the calendar knows to render an event upon drop
      $(this).data('event', {
        title: $.trim($(this).text()), // use the element's text as the event title
        stick: true, // maintain when user navigates (see docs on the renderEvent method)
        color: '#00bcd4'
      });

      // make the event draggable using jQuery UI
      $(this).draggable({
        zIndex: 999,
        revert: true,      // will cause the event to go back to its
        revertDuration: 0  //  original position after the drag
      });

    });


    /* initialize the calendar
    -----------------------------------------------------------------*/


    $('#calendar').fullCalendar({
		dayClick: function(date, allDay, jsEvent, view) {

		//$('#calendar').fullCalendar('renderEvent', { title: 'YOUR TITLE', start: date, allDay: true }, true );
		
		 //$('#calendar').fullCalendar( 'removeEvents', function(e){
			//		console.log("delete");
			//	return true;
			//	});
		
		
		
    },
	eventClick: function(calEvent, jsEvent, view) {

	
	  $.getJSON('api/v1/get_assign_id?id='+calEvent.id, function(result) {
			 
			if(!result.error)
			{
				swal({   
				title: "รายละเอียดใบนัดตรวจ ",   
				text: 
				
				"<div class='row left-align'><div class='card-panel green '><span class='white-text'>แพทย์ผู้นัด : "+result.data[0].ranks+""+result.data[0].firstnamedoctor+" "+result.data[0].lastnamedoctor
					+"</span><br><span class='white-text'>แผนก : "+result.data[0].department
					+"</span></div><div class='card-panel red '>"
					+"<span class='white-text'>ชื่อ : "+result.data[0].firstnameUser+" "+result.data[0].lastnameUser
					+"</span><br><span class='white-text'>เบอร์โทรติดต่อ : "+result.data[0].phonenumber
					+"</span><br><span class='white-text'>รายละเอียดของอาการ : "+result.data[0].detail
					+"</span><br><span class='white-text'>การประพฤติตน : "+result.data[0].behave
					+"</span><br><span class='white-text'>วันนัดตรวจ : "+result.data[0].datetimes
					+"</span><br><span class='white-text'>เวลา : "+result.data[0].times
					+" น.</span></div></div>"
					
					
					,   
				html: true });
				
				//swal('รายละเอียดใบนัด', result.data[0].detail);
				//for(var i = 0 ; i < result.data.length ; i++)
				//{
				//	var dates = result.data[i].datetimes+"T"+result.data[i].times;
				//	var event={id: result.data[i].id , title: result.data[i].detail, start:  dates ,color : '#ff5252'};
				//	$('#calendar').fullCalendar( 'renderEvent', event, true);
					
				//}
				
			}
				
        }); 
	
		//
		//console.log("id "+calEvent.id);
        //alert('Event: ' + calEvent.title);
      
    },
      header: {
        left: 'prev,next today',
        center: 'title',
        right: 'month,basicWeek,basicDay'
      },
      defaultDate: new Date(),
      editable: true,	// don't edit
      droppable: false, // this allows things to be dropped onto the calendar
      eventLimit: true, // allow "more" link when too many events
    
	  
    });
	
	
	GetAssign();

    
  });
  
  function GetAssign()
  {
	  var input_search = '<?php echo $_SESSION['identificationnumber'];?>';
	  $.getJSON('api/v1/get_Assign?input_search='+input_search, function(result) {
			 
			if(!result.error)
			{
				
				for(var i = 0 ; i < result.data.length ; i++)
				{
					var dates = result.data[i].datetimes+"T"+result.data[i].times;
					var event={id: result.data[i].id , title: result.data[i].detail, start:  dates ,color : '#ff5252'};
					$('#calendar').fullCalendar( 'renderEvent', event, true);
					
					//$('#calendar').fullCalendar('renderEvent', { title: result.data[i].detail , start: result.data[i].datetimes+"T"+result.data[i].times, allDay: true }, true );
				
				}
				
				
			}
			
			
			
				
				
				
        }); 
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