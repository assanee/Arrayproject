<?php

require_once '../include/DbHandler.php';
require_once '../include/PassHash.php';
require '.././libs/Slim/Slim.php';

\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

// User id from db - Global Variable
$user_id = NULL;

/**
 * Adding Middle Layer to authenticate every request
 * Checking if the request has valid api key in the 'Authorization' header
 */
function authenticate(\Slim\Route $route) {
    // Getting request headers
    $headers = apache_request_headers();
    $response = array();
    $app = \Slim\Slim::getInstance();

    // Verifying Authorization Header
    if (isset($headers['Authorization'])) {
        $db = new DbHandler();

        // get the api key
        $api_key = $headers['Authorization'];
        // validating api key
        if (!$db->isValidApiKey($api_key)) {
            // api key is not present in users table
            $response["error"] = true;
            $response["message"] = "Access Denied. Invalid Api key";
            echoRespnse(401, $response);
            $app->stop();
        } else {
            global $user_id;
            // get user primary key id
            $user_id = $db->getUserId($api_key);
        }
    } else {
        // api key is missing in header
        $response["error"] = true;
        $response["message"] = "Api key is misssing";
        echoRespnse(400, $response);
        $app->stop();
    }
}

/**
 * Verifying required params posted or not
 */
function verifyRequiredParams($required_fields) {
    $error = false;
    $error_fields = "";
    $request_params = array();
    $request_params = $_REQUEST;
    // Handling PUT request params
    if ($_SERVER['REQUEST_METHOD'] == 'PUT') {
        $app = \Slim\Slim::getInstance();
        parse_str($app->request()->getBody(), $request_params);
    }
    foreach ($required_fields as $field) {
        if (!isset($request_params[$field]) || strlen(trim($request_params[$field])) <= 0) {
            $error = true;
            $error_fields .= $field . ', ';
        }
    }

    if ($error) {
        // Required field(s) are missing or empty
        // echo error json and stop the app
        $response = array();
        $app = \Slim\Slim::getInstance();
        $response["error"] = true;
        $response["message"] = 'Required field(s) ' . substr($error_fields, 0, -2) . ' is missing or empty';
        echoRespnse(400, $response);
        $app->stop();
    }
}


/**
 * Validating email address
 */
function validateEmail($email) {
    $app = \Slim\Slim::getInstance();
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)) {
        $response["error"] = true;
        $response["message"] = 'Email address is not valid';
        echoRespnse(400, $response);
        $app->stop();
    }
}

/**
 * Echoing json response to client
 * @param String $status_code Http response code
 * @param Int $response Json response
 */
function echoRespnse($status_code, $response) {
    $app = \Slim\Slim::getInstance();
    // Http response code
    $app->status($status_code);

    // setting response content type to json
    $app->contentType('application/json');

    echo json_encode($response);
}

/**
 * ----------- METHODS WITHOUT AUTHENTICATION ---------------------------------
 */
/**
 * User Registration
 * url - /register
 * method - POST
 * params - username, first_name, last_name, birthday, gender, email, password, repassword, class
 */
$app->post('/register', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('username', 'first_name', 'last_name', 'birthday', 'gender', 'email', 'password', 'repassword', 'class'));

            $response = array();

            // reading post params
            $username = $app->request->post('username');
            $first_name = $app->request->post('first_name');
            $last_name = $app->request->post('last_name');
            $birthday = $app->request->post('birthday');
            $gender = $app->request->post('gender');
			$email = $app->request->post('email');
			$password = $app->request->post('password');
			$repassword = $app->request->post('repassword');
            $class = $app->request->post('class');

            // validating email address
            validateEmail($email);

            $db = new DbHandler();
            $res = $db->createUser($username, $first_name, $last_name, $birthday, $gender, $email, $password, $repassword, $class);

            if ($res == USER_CREATED_SUCCESSFULLY) {
                $response["error"] = false;
                $response["message"] = "You are successfully registered"; 
				
				echoRespnse(201, $response);
            } else if ($res == USER_CREATE_FAILED) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while registereing";
				
				echoRespnse(200, $response);
            } else if ($res == USER_ALREADY_EXISTED_EMAIL) {
                $response["error"] = true;
                $response["message"] = "Sorry, this email already existed";
				
				echoRespnse(200, $response);
            } else if ($res == USER_ALREADY_EXISTED_USERNAME) {
                $response["error"] = true;
                $response["message"] = "Sorry, this username already existed";
				
				echoRespnse(200, $response);
            } else if ($res == USER_PASSWORD_NOT_MATCH) {
                $response["error"] = true;
                $response["message"] = "Sorry, this password not match";
				
				echoRespnse(200, $response);
            }
           
        });


/**
 * User Login
 * url - /login
 * method - POST
 * params - email, password
 */
$app->post('/login', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('email', 'password'));

            // reading post params
            $email = $app->request()->post('email');
            $password = $app->request()->post('password');
            $response = array();

            $db = new DbHandler();
            // check for correct email and password
            if ($db->checkLogin($email, $password)) {
                // get the user by email
                $user = $db->getUserByEmail($email);

                if ($user != NULL) {
                    $response["error"] = false;
                    $response['username'] = $user['username'];
                    $response['first_name'] = $user['first_name'];
                    $response['last_name'] = $user['last_name'];
                    $response['username'] = $user['username'];
                    $response['email'] = $user['email'];
                    $response['apiKey'] = $user['api_key'];
                    $response['class'] = $user['class'];
                    $response['createdAt'] = $user['created_at'];
                } else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "An error occurred. Please try again";
                }
            } else {
                // user credentials are wrong
                $response['error'] = true;
                $response['message'] = 'Login failed. Incorrect credentials';
            }

            echoRespnse(200, $response);
        });


/**
 * User Login Web
 * url - /login
 * method - POST
 * params - email, password
 */
$app->post('/loginweb', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('email', 'password'));

            // reading post params
            $email = $app->request()->post('email');
            $password = $app->request()->post('password');
            $response = array();

            $db = new DbHandler();
            // check for correct email and password
            if ($db->checkLogin($email, $password)) {
                // get the user by email
                $user = $db->getUserByEmail($email);

                if ($user != NULL) {
					session_start();
					
                    $response["error"] = false;
                    $response['username'] = $user['username'];
                    $response['first_name'] = $user['first_name'];
                    $response['last_name'] = $user['last_name'];
                    $response['username'] = $user['username'];
                    $response['email'] = $user['email'];
                    $response['apikey'] = $user['api_key'];
                    $response['class'] = $user['class'];
                    $response['createdAt'] = $user['created_at'];
					
                    $_SESSION["error"] = false;
                    $_SESSION['username'] = $user['username'];
                    $_SESSION['first_name'] = $user['first_name'];
                    $_SESSION['last_name'] = $user['last_name'];
                    $_SESSION['username'] = $user['username'];
                    $_SESSION['email'] = $user['email'];
                    $_SESSION['apiKey'] = $user['api_key'];
                    $_SESSION['class'] = $user['class'];
                    $_SESSION['createdAt'] = $user['created_at'];
					
					
					
                } else {
                    // unknown error occurred
                    $response['error'] = true;
                    $response['message'] = "An error occurred. Please try again";
                }
            } else {
                // user credentials are wrong
                $response['error'] = true;
                $response['message'] = 'Login failed. Incorrect credentials';
            }

            echoRespnse(200, $response);
        });

		
$app->post('/createshopweb', function() use ($app) {
	
			session_start();
			
            // check for required params
            verifyRequiredParams(array('name', 'mobile_number', 'phone_number'));

            // reading post params
            $api_key = $_SESSION['apiKey'];
            $name = $app->request()->post('name');
            $mobile_number = $app->request()->post('mobile_number');
            $phone_number = $app->request()->post('phone_number');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			} 
			else {
				global $user_id;
				// get user primary key id
				$user_id = $db->getUserId($api_key);
			}
			
			$res = $db->createShop($user_id, $name, $mobile_number, $phone_number);
			
			if ($res == USER_SHOP_SUCCESSFULLY) {
                $response["error"] = false;
                $response["message"] = "You are successfully create"; 
				
				echoRespnse(201, $response);
            } else if ($res == USER_SHOP_FAILED) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while create";
				
				echoRespnse(200, $response);
            } 

           
        });
		
$app->post('/createshop', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('api_key', 'name', 'mobile_number', 'phone_number'));

            // reading post params
            $api_key = $app->request()->post('api_key');
            $name = $app->request()->post('name');
            $mobile_number = $app->request()->post('mobile_number');
            $phone_number = $app->request()->post('phone_number');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			} 
			else {
				global $user_id;
				// get user primary key id
				$user_id = $db->getUserId($api_key);
			}
			
			$res = $db->createShop($user_id, $name, $mobile_number, $phone_number);
			
			if ($res == USER_SHOP_SUCCESSFULLY) {
                $response["error"] = false;
                $response["message"] = "You are successfully create"; 
				
				echoRespnse(201, $response);
            } else if ($res == USER_SHOP_FAILED) {
                $response["error"] = true;
                $response["message"] = "Oops! An error occurred while create";
				
				echoRespnse(200, $response);
            } 

           
        });

		
$app->post('/editshopweb', function() use ($app) {
	
			session_start();
			
            // check for required params
            verifyRequiredParams(array('name', 'mobile_number', 'phone_number'));

            // reading post params
            $api_key = $_SESSION['apiKey'];
            $name = $app->request()->post('name');
            $mobile_number = $app->request()->post('mobile_number');
            $phone_number = $app->request()->post('phone_number');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			} 
			else {
				
				global $id_shop;
				// get user primary key id
				$id_shop = $db->getUserIdShop($api_key);
				
				$res = $db->editShop($id_shop, $name, $mobile_number, $phone_number);
			
				if ($res == USER_SHOP_SUCCESSFULLY) {
					$response["error"] = false;
					$response["message"] = "You are successfully update"; 
					
					echoRespnse(201, $response);
				} else if ($res == USER_SHOP_FAILED) {
					$response["error"] = true;
					$response["message"] = "Oops! An error occurred while update";
					
					echoRespnse(200, $response);
				}
			}
			
			 

           
        });
		
$app->post('/createbranchweb', function() use ($app) {
	
			session_start();
			
            // check for required params
            verifyRequiredParams(array('name', 'mobile_number', 'phone_number', 'latitude', 'longitude'));

            // reading post params
            $api_key = $_SESSION['apiKey'];
            $name = $app->request()->post('name');
            $mobile_number = $app->request()->post('mobile_number');
            $phone_number = $app->request()->post('phone_number');
            $latitude = $app->request()->post('latitude');
            $longitude = $app->request()->post('longitude');
			
            $response = array();

            $db = new DbHandler();
			
			$id_shop = $db->getUserIdShop($_SESSION['apiKey']);
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				// validating id shop
				if (!$db->isValidIdShop($id_shop)) {
					
					$response["error"] = true;
					$response["message"] = "Access Denied. Invalid Id Shop";
					echoRespnse(401, $response);
					$app->stop();
				} 
				else
				{
					$res = $db->createBranch($id_shop, $name, $mobile_number, $phone_number, $latitude, $longitude);
					
					if ($res == USER_BRANCH_SUCCESSFULLY) {
						$response["error"] = false;
						$response["message"] = "You are successfully create"; 
						
						echoRespnse(201, $response);
					} else if ($res == USER_BRANCH_FAILED) {
						$response["error"] = true;
						$response["message"] = "Oops! An error occurred while create";
						
						echoRespnse(200, $response);
					} 

				}
			}	
			

           
        });
		
$app->post('/createbranch', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('api_key', 'id_shop', 'name', 'mobile_number', 'phone_number', 'latitude', 'longitude'));

            // reading post params
            $api_key = $app->request()->post('api_key');
            $id_shop = $app->request()->post('id_shop');
            $name = $app->request()->post('name');
            $mobile_number = $app->request()->post('mobile_number');
            $phone_number = $app->request()->post('phone_number');
            $latitude = $app->request()->post('latitude');
            $longitude = $app->request()->post('longitude');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				// validating id shop
				if (!$db->isValidIdShop($id_shop)) {
					
					$response["error"] = true;
					$response["message"] = "Access Denied. Invalid Id Shop";
					echoRespnse(401, $response);
					$app->stop();
				} 
				else
				{
					$res = $db->createBranch($id_shop, $name, $mobile_number, $phone_number, $latitude, $longitude);
					
					if ($res == USER_BRANCH_SUCCESSFULLY) {
						$response["error"] = false;
						$response["message"] = "You are successfully create"; 
						
						echoRespnse(201, $response);
					} else if ($res == USER_BRANCH_FAILED) {
						$response["error"] = true;
						$response["message"] = "Oops! An error occurred while create";
						
						echoRespnse(200, $response);
					} 

				}
			}	
			

           
        });

		
$app->post('/createtable', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('api_key', 'id_branch', 'table_type'));

            // reading post params
            $api_key = $app->request()->post('api_key');
            $id_branch = $app->request()->post('id_branch');
            $table_type = $app->request()->post('table_type');
            
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				// validating id shop
				if (!$db->isValidIdBranch($id_branch)) {
					
					$response["error"] = true;
					$response["message"] = "Access Denied. Invalid Id Branch";
					echoRespnse(401, $response);
					$app->stop();
				} 
				else
				{
					$res = $db->createTable($id_branch, $table_type);
					
					if ($res == USER_TABLE_SUCCESSFULLY) {
						$response["error"] = false;
						$response["message"] = "You are successfully create"; 
						
						echoRespnse(201, $response);
					} else if ($res == USER_TABLE_FAILED) {
						$response["error"] = true;
						$response["message"] = "Oops! An error occurred while create";
						
						echoRespnse(200, $response);
					} 

				}
			}	
			

           
        });

		
$app->post('/createqueue', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('api_key', 'id_branch', 'table_type', 'number'));

            // reading post params
            $api_key = $app->request()->post('api_key');
            $id_branch = $app->request()->post('id_branch');
            $table_type = $app->request()->post('table_type');
            $number = $app->request()->post('number');
            
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				// validating id shop
				if (!$db->isValidIdBranch($id_branch)) {
					
					$response["error"] = true;
					$response["message"] = "Access Denied. Invalid Id Branch";
					echoRespnse(401, $response);
					$app->stop();
				} 
				else
				{
					
					global $user_id;
					// get user primary key id
					$user_id = $db->getUserId($api_key);
					
					$code = $db->getCode($id_branch);
					
					if( strlen($code) == 1 )
					{
						$code = "A00".$code;
					}
					else if( strlen($code) == 2 )
					{
						$code = "A0".$code;
					}
					else
					{
						$code = "A".$code;
					}
					
					//$code = 999;
					
					$status = "book";
					
					$res = $db->createQueue($id_branch, $user_id, $table_type, $number, $code, $status);
					
					
					if ($res == USER_QUEUE_SUCCESSFULLY) {
						
						$id_queue = $db->getIdLastQueue();
						
						$detail = "book";
						
						$db->createQueueStatus($id_queue, $id_branch, $detail);
						
						$response["error"] = false;
						$response["message"] = "You are successfully book"; 
						
						echoRespnse(201, $response);
						
					} else if ($res == USER_QUEUE_FAILED) {
						
						$response["error"] = true;
						$response["message"] = "Oops! An error occurred while book";
						
						echoRespnse(200, $response);
					} 

				}
			}	
			

           
        });

		
$app->post('/favorite', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('api_key', 'id_branch'));

            // reading post params
            $api_key = $app->request()->post('api_key');
            $id_branch = $app->request()->post('id_branch');
            
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				// validating id shop
				if (!$db->isValidIdBranch($id_branch)) {
					
					$response["error"] = true;
					$response["message"] = "Access Denied. Invalid Id Branch";
					echoRespnse(401, $response);
					$app->stop();
				} 
				else
				{
					
					global $user_id;
					// get user primary key id
					$user_id = $db->getUserId($api_key);
					
					if(!$db->isValidFavirite($user_id, $id_branch))
					{
						$res = $db->setFavorite($user_id, $id_branch);
						
						if ($res == USER_FAVORITE_SUCCESSFULLY) {
						
						$response["error"] = false;
						$response["message"] = "You are successfully favorite"; 
						
						echoRespnse(201, $response);
						
						} else if ($res == USER_FAVORITE_FAILED) {
							
							$response["error"] = true;
							$response["message"] = "Oops! An error occurred while favorite";
							
							echoRespnse(200, $response);
						} 
					}
					else
					{
						$res = $db->UnsetFavorite($user_id, $id_branch);
						
						if ($res == USER_FAVORITE_SUCCESSFULLY) {
						
						$response["error"] = false;
						$response["message"] = "You are successfully un favorite"; 
						
						echoRespnse(201, $response);
						
						} else if ($res == USER_FAVORITE_FAILED) {
							
							$response["error"] = true;
							$response["message"] = "Oops! An error occurred while un favorite";
							
							echoRespnse(200, $response);
						} 
					}
					
					

				}
			}	
			

           
        });

		
$app->get('/favorite', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				
					global $user_id;
					// get user primary key id
					$user_id = $db->getUserId($api_key);
					
					$res = $db->getFavorite($user_id);
					
					$response["error"] = false;
					$response["data"] = $res; 
						
					echoRespnse(200, $response);
					

				
			}	
			

           
        });
 
 
$app->get('/history', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				
					global $user_id;
					// get user primary key id
					$user_id = $db->getUserId($api_key);
					
					$res = $db->getHistory($user_id);
					
					$response["error"] = false;
					$response["data"] = $res; 
						
					echoRespnse(200, $response);
					

				
			}	
			

           
        });
           
    
$app->get('/promotion', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
					$res = $db->getPromotion();
					//$dis = $db->haversineGreatCircleDistance(,,,);
					
					$response["error"] = false;
					$response["data"] = $res; 
						
					echoRespnse(200, $response);
					

				
			}	
			
        });

		
$app->get('/distance', function() use ($app) {
	
	       // check for required params
            verifyRequiredParams(array('latitudeFrom', 'longitudeFrom', 'latitudeTo', 'longitudeTo'));
            
            // reading post params
            $latitudeFrom = $app->request()->get('latitudeFrom');
            $longitudeFrom = $app->request()->get('longitudeFrom');
            $latitudeTo = $app->request()->get('latitudeTo');
            $longitudeTo = $app->request()->get('longitudeTo');
			
            $response = array();

            $db = new DbHandler();
			
			$dis = $db->haversineGreatCircleDistance($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo);
					
			$response["error"] = false;
			$response["data"] = $dis; 
						
			echoRespnse(200, $response);
				
			
        });
		
    
$app->get('/queue', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				
					global $user_id;
					// get user primary key id
					$user_id = $db->getUserId($api_key);
					
					$res = $db->getQueue($user_id);
					$resST = $db->getQueueStatus($res);
					
					$response["error"] = false;
					$response["data"] = $res; 
					$response["status"] = $resST; 
						
					echoRespnse(200, $response);
					

				
			}	
			
        });
			
    
$app->get('/queuebyid', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
            $id_queue = $app->request()->get('id_queue');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				
					global $user_id;
					// get user primary key id
					$user_id = $db->getUserId($api_key);
					
					$res = $db->getQueueById($user_id,$id_queue);
					//$dis = $db->haversineGreatCircleDistance(,,,);
					
					$response["error"] = false;
					$response["data"] = $res; 
						
					echoRespnse(200, $response);
					

				
			}	
			
        });
			
		
$app->post('/deletequeue', function() use ($app) {
            // check for required params
            verifyRequiredParams(array('api_key', 'id_queue'));

            // reading post params
            $api_key = $app->request()->post('api_key');
            $id_queue = $app->request()->post('id_queue');
            
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				// validating id shop
				
					global $user_id;
					// get user primary key id
					$user_id = $db->getUserId($api_key);
					
					$res = $db->deleteQueue($user_id, $id_queue);
					
					if ($res == USER_FAVORITE_SUCCESSFULLY) {
						
						
						$response["error"] = false;
						$response["message"] = "You are successfully delete"; 
						
						echoRespnse(201, $response);
						
					} else if ($res == USER_FAVORITE_FAILED) {
						
						$response["error"] = true;
						$response["message"] = "Oops! An error occurred while delete";
						
						echoRespnse(200, $response);
					} 

				
			}	
			

           
        });

			
$app->get('/branch', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
            $id_branch = $app->request()->get('id_branch');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				// validating id shop
				if (!$db->isValidIdBranch($id_branch)) {
					
					$response["error"] = true;
					$response["message"] = "Access Denied. Invalid Id Branch";
					echoRespnse(401, $response);
					$app->stop();
				} 
				else
				{
				
					
					$res = $db->getBranch($id_branch);
					
					$response["error"] = false;
					$response["data"] = $res; 
						
					echoRespnse(200, $response);
					
				}
				
			}	
			

           
        });
 
     
$app->get('/near', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
            $latitude = $app->request()->get('latitude');
            $longitude = $app->request()->get('longitude');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				
					global $user_id;
					// get user primary key id
					$user_id = $db->getUserId($api_key);
					
					$res = $db->getNear($user_id, $latitude, $longitude);
					//$dis = $db->haversineGreatCircleDistance(,,,);
					
					$response["error"] = false;
					$response["data"] = $res; 
						
					echoRespnse(200, $response);
					

				
			}	
			
        });
 
			
$app->get('/promotionbyidbranch', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
            $id_branch = $app->request()->get('id_branch');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				// validating id shop
				if (!$db->isValidIdBranch($id_branch)) {
					
					$response["error"] = true;
					$response["message"] = "Access Denied. Invalid Id Branch";
					echoRespnse(401, $response);
					$app->stop();
				} 
				else
				{
				
					
					$res = $db->getPromotionByIdBranch($id_branch);
					
					$response["error"] = false;
					$response["data"] = $res; 
						
					echoRespnse(200, $response);
					
				}
				
			}	
			

           
        });
		
$app->get('/promotiondetail', function() use ($app) {
            
            // reading post params
            $api_key = $app->request()->get('api_key');
            $id_promotion = $app->request()->get('id_promotion');
			
            $response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				
					$res = $db->getPromotionDetail($id_promotion);
					
					$response["error"] = false;
					$response["data"] = $res; 
						
					echoRespnse(200, $response);
					
			}	
			

           
        });
      
		
$app->post('/createpromotion', function() use ($app) {
	
			//session_start();
			
            // check for required params
            verifyRequiredParams(array('api_key', 'id_branch', 'name_promotion', 'detail', 'start_promotion', 'end_promotion'));

            // reading post params
            $api_key = $app->request()->post('api_key');
            $id_branch = $app->request()->post('id_branch');
            $name_promotion = $app->request()->post('name_promotion');
			$logo_mini = $_FILES["logo_mini"];
			$logo_full = $_FILES["logo_full"];
            $detail = $app->request()->post('detail');
            $start_promotion = $app->request()->post('start_promotion');
            $end_promotion = $app->request()->post('end_promotion');
			
			$response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				
				$res_logo_mini = $db->createStoreImage($logo_mini);
				$res_logo_full = $db->createStoreImage($logo_full);
				
				if($res_logo_mini["error"] == false && $res_logo_full["error"] == false)
				{
					$resCP = $db->createPromotion($id_branch, $name_promotion, $res_logo_mini["name"], $res_logo_full["name"], $detail, $start_promotion, $end_promotion);
					
					if($resCP == STORE_SUCCESSFULLY)
					{
						$response["error"] = false;
						$response["message"] = "create promotion successfully"; 
						$response["logo_mini"] = $res_logo_mini["name"]; 
						$response["logo_full"] = $res_logo_full["name"]; 
						echoRespnse(201, $response);
					}
					else
					{
						$response["error"] = true;
						$response["message"] = "create promotion failed"; 
						echoRespnse(200, $response);
					}
					
				}
				else
				{
					$response["error"] = true;
					$response["message"] = "create promotion failed"; 
					echoRespnse(200, $response);
				}
			}		
			
        });
		
$app->post('/createpromotionweb', function() use ($app) {
	
			session_start();
			
            // check for required params
            verifyRequiredParams(array('id_branch', 'name_promotion', 'detail', 'start_promotion', 'end_promotion'));
			
			

            // reading post params
            $api_key = $_SESSION['apiKey'];
            $id_branch = $app->request()->post('id_branch');
            $name_promotion = $app->request()->post('name_promotion');
			$logo_mini = $_FILES["logo_mini"];
			$logo_full = $_FILES["logo_full"];
            $detail = $app->request()->post('detail');
            $start_promotion = $app->request()->post('start_promotion');
            $end_promotion = $app->request()->post('end_promotion');
			
			
			
			$response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				
				
				$res_logo_full = $db->createStoreImage($logo_full);
				$res_logo_mini = $db->createStoreImage($logo_mini);
				
				if($res_logo_mini["error"] == false && $res_logo_full["error"] == false)
				{
					$resCP = $db->createPromotion($id_branch, $name_promotion, $res_logo_mini["name"], $res_logo_full["name"], $detail, $start_promotion, $end_promotion);
					
					if($resCP == STORE_SUCCESSFULLY)
					{
						$response["error"] = false;
						$response["message"] = "create promotion successfully"; 
						$response["logo_mini"] = $res_logo_mini["name"]; 
						$response["logo_full"] = $res_logo_full["name"]; 
						echoRespnse(201, $response);
					}
					else
					{
						$response["error"] = true;
						$response["message"] = "create promotion failed"; 
						echoRespnse(200, $response);
					}
					
				}
				else
				{
					$response["error"] = true;
					$response["message"] = "create promotion failed ";
					echoRespnse(200, $response);
				}
			}		
			
        });
      
$app->get('/test', function() use ($app) {
	
            $today = getdate();
		
			$temp_today = $today['year']."-".$today['mon']."-".$today['mday'];
		
            echo date('Y-m-d', strtotime("26 July, 2016"))." : ".strtotime("26 July, 2016");
            echo "\n".json_encode($today);
            echo "\n".$today['year']."-".$today['mon']."-".$today['mday'];
            echo "\n".$temp_today;
			
           
        });

				
$app->post('/updatelogoweb', function() use ($app) {
	
			session_start();
			
            // check for required params
            
            // reading post params
            $api_key = $_SESSION['apiKey'];
			
			$logo_shop = $_FILES["logo_shop"];
            
			$response = array();

            $db = new DbHandler();
			
			// validating api key
			if (!$db->isValidApiKey($api_key)) {
				// api key is not present in users table
				$response["error"] = true;
				$response["message"] = "Access Denied. Invalid Api key";
				echoRespnse(401, $response);
				$app->stop();
			}
			else
			{
				global $id_shop;
				// get user primary key id
				$id_shop = $db->getUserIdShop($api_key);
				
				$res_logo_shop = $db->createStoreLogo($logo_shop);
				
				if($res_logo_shop["error"] == false )
				{
					$resCP = $db->updateLogo($id_shop, $res_logo_shop["name"]);
					
					if($resCP == STORE_SUCCESSFULLY)
					{
						$response["error"] = false;
						$response["message"] = "update logo successfully"; 
						$response["logo shop"] = $res_logo_shop["name"]; 
						echoRespnse(201, $response);
					}
					else
					{
						$response["error"] = true;
						$response["message"] = "update logo failed"; 
						echoRespnse(200, $response);
					}
					
				}
				else
				{
					$response["error"] = true;
					$response["message"] = "update logo failed ";
					echoRespnse(200, $response);
				}
			}		
			
        });

$app->run();

?>