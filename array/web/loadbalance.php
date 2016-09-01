<?php
session_start();

	if($_SESSION['class'] == "admin")
	{
		header("location:admin.php");
	}
	else if($_SESSION['class'] == "shop")
	{
		header("location:shop.php");
	}
	else if($_SESSION['class'] == "customer")
	{
		header("location:customer.php");
	}	
	else
	{
		session_destroy();
		header("location:index.php");
	}		


?>
