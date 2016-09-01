<?php

/**
 * Class to handle all db operations
 * This class will have CRUD methods for database tables
 *
 * @author Assanee Saksiritantawan
 * @link URL Tutorial link
 */
 require_once '../include/Distance.php';
 
class DbHandler {

    private $conn;

    function __construct() {
        require_once dirname(__FILE__) . '/DbConnect.php';
        // opening db connection
        $db = new DbConnect();
        $this->conn = $db->connect();
    }

    /* ------------- `users` table method ------------------ */

	
    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
    public function getUserId($api_key) {
        $stmt = $this->conn->prepare("SELECT id_user FROM users WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $stmt->bind_result($user_id);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user_id;
        } else {
            return NULL;
        }
    }
	
    /**
     * Fetching user id by api key
     * @param String $api_key user api key
     */
    public function getUserIdShop($api_key) {
        $stmt = $this->conn->prepare("SELECT shop.id_shop FROM shop WHERE shop.id_user = (SELECT users.id_user FROM users  WHERE users.api_key = ? )");
        $stmt->bind_param("s", $api_key);
        if ($stmt->execute()) {
            $stmt->bind_result($shop_id);
            $stmt->fetch();
            // TODO
            // $user_id = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $shop_id;
        } else {
            return NULL;
        }
    }


    /**
     * Validating user api key
     * If the api key is there in db, it is a valid key
     * @param String $api_key user api key
     * @return boolean
     */
    public function isValidApiKey($api_key) {
        $stmt = $this->conn->prepare("SELECT id_user from users WHERE api_key = ?");
        $stmt->bind_param("s", $api_key);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
	

    /**
     * Validating user id shop
     * If the id shop is there in db, it is a valid key
     * @param String $id_shop user id shop
     * @return boolean
     */
    public function isValidIdShop($id_shop) {
        $stmt = $this->conn->prepare("SELECT id_shop from shop WHERE id_shop = ?");
        $stmt->bind_param("s", $id_shop);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    /**
     * Validating user id shop
     * If the id shop is there in db, it is a valid key
     * @param String $id_shop user id shop
     * @return boolean
     */
    public function isValidFavirite($user_id, $id_branch) {
        $stmt = $this->conn->prepare("SELECT * FROM `favorite` WHERE id_user = ? AND id_branch = ? ");
        $stmt->bind_param("ss", $user_id, $id_branch);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    /**
     * Validating user id branch
     * If the id branch is there in db, it is a valid key
     * @param String $id_branch user id branch
     * @return boolean
     */
    public function isValidIdBranch($id_branch) {
        $stmt = $this->conn->prepare("SELECT id_branch from branch WHERE id_branch = ?");
        $stmt->bind_param("s", $id_branch);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
	

    /**
     * Checking for duplicate user by email address
     * @param String $email email to check in db
     * @return boolean
     */
    private function isUserExistsEmail($email) {
        $stmt = $this->conn->prepare("SELECT id_user from users WHERE email = ?");
        $stmt->bind_param("s", $email);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }

    /**
     * Checking for duplicate user by username address
     * @param String $username username to check in db
     * @return boolean
     */
    private function isUserExistsUsername($username) {
        $stmt = $this->conn->prepare("SELECT id_user from users WHERE username = ?");
        $stmt->bind_param("s", $username);
        $stmt->execute();
        $stmt->store_result();
        $num_rows = $stmt->num_rows;
        $stmt->close();
        return $num_rows > 0;
    }
	
	
    /**
     * Generating random Unique MD5 String for user Api key
     */
    private function generateApiKey() {
        return md5(uniqid(rand(), true));
    }

    /**
     * Generating random Unique MD5 String for user Api key
     */
    private function checkMatch($password,$repassword) {
		if( strpos($password,$repassword) !== false ) 
		{	
			return true;
		}
        else
		{
			return false;
		}
    }

    /**
     * Creating new user
     * @param String $username User login username id
     * @param String $email User login email id
     * @param String $password User login password
     */
    public function createUser($username, $first_name, $last_name, $birthday, $gender, $email, $password, $repassword, $class) {
        require_once 'PassHash.php';
        $response = array();

        // First check if user already existed in db
        if (!$this->isUserExistsEmail($email)) 
		{
			if(!$this->isUserExistsUsername($username))
			{
				if($this->checkMatch($password,$repassword))
				{
						// Generating password hash
				$password_hash = PassHash::hash($password);

				// Generating API key
				$api_key = $this->generateApiKey();

				// insert query
				$birthday = date('Y-m-d', strtotime($birthday));
				
				$stmt = $this->conn->prepare("INSERT INTO users(username, first_name, last_name, birthday, gender, email, password, class, api_key) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				$stmt->bind_param("sssssssss", $username, $first_name, $last_name, $birthday, $gender, $email, $password_hash, $class, $api_key);

				$result = $stmt->execute();

				$stmt->close();

				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_CREATED_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_CREATE_FAILED;
				}
				}
				else
				{
					
					return USER_PASSWORD_NOT_MATCH;
				}
				
			}
			else
			{
				// User with same username already existed in the db
				return USER_ALREADY_EXISTED_USERNAME;
			}
            
        } else {
            // User with same email already existed in the db
            return USER_ALREADY_EXISTED_EMAIL;
        }

        return $response;
    }

    /**
     * Checking user login
     * @param String $email User login email id
     * @param String $password User login password
     * @return boolean User login status success/fail
     */
    public function checkLogin($username_email, $password) {
        // fetching user by username or email
        $stmt = $this->conn->prepare("SELECT password FROM users WHERE username = ? OR email = ?");

        $stmt->bind_param("ss", $username_email, $username_email);

        $stmt->execute();

        $stmt->bind_result($password_hash);

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // Found user with the email
            // Now verify the password

            $stmt->fetch();

            $stmt->close();

            if (PassHash::check_password($password_hash, $password)) {
                // User password is correct
                return TRUE;
            } else {
                // user password is incorrect
                return FALSE;
            }
        } else {
            $stmt->close();

            // user not existed with the email
            return FALSE;
        }
    }

    /**
     * Fetching user by email
     * @param String $email User email id
     */
    public function getUserByEmail($email) {
        $stmt = $this->conn->prepare("SELECT username, first_name, last_name, email, api_key, class, created_at FROM users WHERE username = ? OR email = ?");
        $stmt->bind_param("ss", $email, $email);
        if ($stmt->execute()) {
            // $user = $stmt->get_result()->fetch_assoc();
            $stmt->bind_result($username, $first_name, $last_name, $email, $api_key, $class, $created_at);
            $stmt->fetch();
            $user = array();
            $user["username"] = $username;
            $user["first_name"] = $first_name;
            $user["last_name"] = $last_name;
            $user["email"] = $email;
            $user["api_key"] = $api_key;
            $user["class"] = $class;
            $user["created_at"] = $created_at;
            $stmt->close();
            return $user;
        } else {
            return NULL;
        }
    }

    public function createShop($user_id, $name, $mobile_number, $phone_number) {
		
        $stmt = $this->conn->prepare("INSERT INTO `array`.`shop` (`id_shop`, `id_user`, `name`, `logo`, `mobile_number`, `phone_number`, `created_at`) VALUES (NULL, ?, ?, 'default.png', ?, ?, CURRENT_TIMESTAMP)");
				$stmt->bind_param("ssss", $user_id, $name, $mobile_number, $phone_number);

				$result = $stmt->execute();

				$stmt->close();

				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_SHOP_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_SHOP_FAILED;
				}
    }

    public function editShop($id_shop, $name, $mobile_number, $phone_number) {
		
        $stmt = $this->conn->prepare("UPDATE `array`.`shop` SET `name` = ?, `mobile_number` = ?, `phone_number` = ? WHERE `shop`.`id_shop` = ? ");
				$stmt->bind_param("ssss", $name, $mobile_number, $phone_number,$id_shop);

				$result = $stmt->execute();

				$stmt->close();

				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_SHOP_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_SHOP_FAILED;
				}
    }

    public function updateLogo($id_shop, $logo_name) {
		
        $stmt = $this->conn->prepare("UPDATE `array`.`shop` SET `logo` = ? WHERE `shop`.`id_shop` = ? ");
				$stmt->bind_param("ss", $logo_name, $id_shop);

				$result = $stmt->execute();

				$stmt->close();

				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_SHOP_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_SHOP_FAILED;
				}
    }

    public function createBranch($id_shop, $name, $mobile_number, $phone_number, $latitude, $longitude) {
		
        $stmt = $this->conn->prepare("INSERT INTO `array`.`branch` (`id_branch`, `id_shop`, `name`, `mobile_number`, `phone_number`, `latitude`, `longitude`, `created_at`) VALUES (NULL, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
				$stmt->bind_param("ssssss", $id_shop, $name, $mobile_number, $phone_number, $latitude, $longitude);

				$result = $stmt->execute();

				$stmt->close();

				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_BRANCH_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_BRANCH_FAILED;
				}
    }

    public function createTable($id_branch, $table_type) {
		
        $stmt = $this->conn->prepare("INSERT INTO `array`.`tables` (`id_table`, `id_branch`, `table_type`, `created_at`) VALUES (NULL, ?, ?, CURRENT_TIMESTAMP)");
				$stmt->bind_param("ss", $id_branch, $table_type);

				$result = $stmt->execute();

				$stmt->close();

				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_TABLE_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_TABLE_FAILED;
				}
    }

    public function createQueue($id_branch, $user_id, $table_type, $number, $code, $status) {
		
        $stmt = $this->conn->prepare("INSERT INTO `array`.`queue` (`id_queue`, `id_branch`, `id_user`, `table_type`, `number`, `code`, `status`, `created_at`) VALUES (NULL, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
				$stmt->bind_param("ssssss", $id_branch, $user_id, $table_type, $number, $code, $status);

				$result = $stmt->execute();

				$stmt->close();

				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_QUEUE_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_QUEUE_FAILED;
				}
    }

    public function createQueueStatus($id_queue, $id_branch, $detail) {
		
        $stmt = $this->conn->prepare("INSERT INTO `array`.`queue_status` (`id_queue_status`, `id_branch`, `id_queue`, `detail`, `created_at`) VALUES (NULL, ?, ?, ?, CURRENT_TIMESTAMP)");
				$stmt->bind_param("sss", $id_branch, $id_queue,  $detail);

				$result = $stmt->execute();

				$stmt->close();
				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_QUEUE_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_QUEUE_FAILED;
				}
				
    }

    public function getIdLastQueue() {
		
		$stmt = $this->conn->prepare("SELECT id_queue FROM `queue` ORDER BY id_queue DESC LIMIT 1");
		$stmt->execute(); 
		$stmt->bind_result($id_queue);
		$stmt->fetch();
		$stmt->close();
		
		return $id_queue;
    }
	

    public function setFavorite($user_id, $id_branch) {
		
        $stmt = $this->conn->prepare("INSERT INTO `array`.`favorite` (`id_favorite`, `id_user`, `id_branch`, `created_at`) VALUES (NULL, ?, ?, CURRENT_TIMESTAMP)");
				$stmt->bind_param("ss", $user_id, $id_branch);

				$result = $stmt->execute();

				$stmt->close();
				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_FAVORITE_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_FAVORITE_FAILED;
				}
				
    }

    public function UnsetFavorite($user_id, $id_branch) {
		
        $stmt = $this->conn->prepare("DELETE FROM `array`.`favorite` WHERE `favorite`.`id_user` = ? AND `favorite`.`id_favorite` = ?");
				$stmt->bind_param("ss", $user_id, $id_branch);

				$result = $stmt->execute();

				$stmt->close();
				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_FAVORITE_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_FAVORITE_FAILED;
				}
				
    }

    
    public function getFavorite($user_id) {
		
		$response = array();
		
        $stmt = $this->conn->prepare("SELECT favorite.id_favorite, shop.logo, shop.name, branch.name, branch.id_branch
										FROM favorite INNER JOIN branch ON favorite.id_branch = branch.id_branch  
										JOIN shop ON shop.id_shop = branch.id_shop WHERE  favorite.	id_user = ? ");
				$stmt->bind_param("s", $user_id);
				$result = $stmt->execute();
				$stmt->bind_result($id, $logo, $brand_name, $branch_name, $id_branch);
				while($stmt->fetch())
				{
					$temp = array($id, $logo, $brand_name, $branch_name, $id_branch);
						
				   array_push($response,$temp);
				   
				}
				
				$stmt->close();
				
				return $response;
				
				
    }
	
    public function getHistory($user_id) {
		
		$response = array();
		
        $stmt = $this->conn->prepare("SELECT queue.id_queue, shop.logo, shop.name, branch.name, branch.id_branch
										FROM queue INNER JOIN branch ON queue.id_branch = branch.id_branch  
										JOIN shop ON shop.id_shop = branch.id_shop WHERE  queue.id_user = ? GROUP BY  queue.id_branch");
				$stmt->bind_param("s", $user_id);
				$result = $stmt->execute();
				$stmt->bind_result($id, $logo, $brand_name, $branch_name, $id_branch);
				while($stmt->fetch())
				{
					$temp = array($id, $logo, $brand_name, $branch_name, $id_branch);
						
				   array_push($response,$temp);
				   
				}
				
				$stmt->close();
				
				return $response;
				
				
    }
	
    public function getPromotion() {
		
		$response = array();
		
		$today = getdate();
		
		$temp_today = $today['year']."-".$today['mon']."-".$today['mday'];
		
		$stmt = $this->conn->prepare("SELECT * FROM promotion WHERE ( start_promotion <= ? AND end_promotion >= ? ) ");
				$stmt->bind_param("ss", $temp_today, $temp_today);
				$result = $stmt->execute();
				$stmt->bind_result($id_promotion, $id_branch, $name_promotion, $logo_mini, $logo_full, $detail, $start_promotion, $end_promotion, $created_at);
				while($stmt->fetch())
				{
					$temp = array($id_promotion, $id_branch, $name_promotion, $logo_mini, $logo_full, $start_promotion, $end_promotion, $created_at);
						
				   array_push($response,$temp);
				   
				}
				
				//array_push($response,$temp_today);
				
				$stmt->close();
				
				return $response;
				
				
    }
	
	public function haversineGreatCircleDistance($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo){
		
	  $earthRadius = 6371000;
	  
	  // convert from degrees to radians
	  $latFrom = deg2rad($latitudeFrom);
	  $lonFrom = deg2rad($longitudeFrom);
	  $latTo = deg2rad($latitudeTo);
	  $lonTo = deg2rad($longitudeTo);

	  $latDelta = $latTo - $latFrom;
	  $lonDelta = $lonTo - $lonFrom;

	  $angle = 2 * asin(sqrt(pow(sin($latDelta / 2), 2) +
		cos($latFrom) * cos($latTo) * pow(sin($lonDelta / 2), 2)));
	  return $angle * $earthRadius;
	  
	}
		
    public function getCode($id_branch) {
		
		$response = array();
		
		$today = getdate();
		
        $stmt = $this->conn->prepare("SELECT * FROM queue WHERE (EXTRACT(YEAR FROM created_at)  <= ? AND EXTRACT(MONTH  FROM created_at)  <= ?  AND EXTRACT(DAY  FROM created_at) <= ?) AND id_branch = ? ");
				$stmt->bind_param("ssss", $today['year'], $today['mon'], $today['mday'], $id_branch);
				$stmt->execute();
				$stmt->store_result();
				$num_rows = $stmt->num_rows;
				$stmt->close();
				
				return $num_rows;
				
				
    }
	
	
    public function getQueue($user_id) {
		
		$response = array();
		
        
        $stmt = $this->conn->prepare("SELECT queue.id_queue, queue.table_type, queue.number, queue.code, 
										queue.status, queue.created_at, shop.logo, shop.name, branch.name
										FROM queue INNER JOIN branch ON queue.id_branch = branch.id_branch  
										JOIN shop ON shop.id_shop = branch.id_shop WHERE  queue.id_user = ? AND queue.status = 'book'  ");
				$stmt->bind_param("s", $user_id );
				$result = $stmt->execute();
				$stmt->bind_result($id_queue, $table_type, $number, $code, $status, $created_at, $logo, $sname, $bname);
				
				while($stmt->fetch())
				{
								
					$temp = array($id_queue, $table_type, $number, $code, $status, Date('d/m/Y', strtotime($created_at)), Date('H:i:s', strtotime($created_at)), $logo, $sname, $bname);
						
					array_push($response,$temp);
				   
				}
				
				$stmt->close();
						
				
				return $response;
				
				
    }
	
	public function getQueueStatus($res)
	{
		$response = array();
		
		for($i = 0 ; $i < sizeof($res) ; $i++)
		{
					$stmt = $this->conn->prepare("SELECT detail, created_at FROM queue_status WHERE id_queue = ? ");
							$stmt->bind_param("s", $res[$i][0] );
							$result = $stmt->execute();
							$stmt->bind_result($detail, $created_at);
					
					$temp = array();
					
					while($stmt->fetch())
					{
						array_push($temp,date('d/m/Y H:i:s', strtotime($created_at))." ".$detail);
					}
					
					array_push($response,$temp);		
						
					$stmt->close();
		}
		
		return $response;
				
	}

	
    public function getQueueById($user_id,$id_queue) {
		
		$response = array();
		
        $stmt = $this->conn->prepare("SELECT * FROM queue WHERE id_queue = ? AND id_user = ? AND status = 'book' ");
				$stmt->bind_param("ss", $id_queue , $user_id);
				$result = $stmt->execute();
				$stmt->bind_result($id_queue, $id_branch, $id_user, $table_type, $number, $code, $status, $created_at);
				while($stmt->fetch())
				{
					$temp = array($id_queue, $id_branch, $id_user, $table_type, $number, $code, $status, $created_at);
						
				   array_push($response,$temp);
				   
				}
				
				$stmt->close();
				
				return $response;
				
				
    }

	
    public function deleteQueue($user_id, $id_queue) {
		
        $stmt = $this->conn->prepare("DELETE FROM queue WHERE id_queue = ? AND id_user = ?");
				$stmt->bind_param("ss", $id_queue, $user_id);

				$result = $stmt->execute();

				$stmt->close();
				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return USER_FAVORITE_SUCCESSFULLY;
				} else {
					// Failed to create user
					return USER_FAVORITE_FAILED;
				}
				
    }

		
    public function getBranch($id_branch) {
		
		$response = array();
		
        $stmt = $this->conn->prepare("SELECT shop.id_shop, branch.id_branch, shop.name, branch.name, branch.mobile_number, branch.phone_number, branch.latitude,
									branch.longitude, shop.logo  FROM branch INNER JOIN shop ON shop.id_shop = branch.id_shop WHERE id_branch = ? ");
				$stmt->bind_param("s", $id_branch);
				$result = $stmt->execute();
				$stmt->bind_result($id_shop, $id_branch, $name_shop, $name_branch, $mobile_number, $phone_number, $latitude, $longitude, $logo);
				while($stmt->fetch())
				{
					$temp = array($id_shop, $id_branch, $name_shop, $name_branch, $mobile_number, $phone_number, $latitude, $longitude, $logo);
						
				   array_push($response,$temp);
				   
				}
				
				$stmt->close();
				
				return $response;
				
				
    }
	
		
    public function getNear($user_id, $latitudeFrom, $longitudeFrom) {
		
		$response = array();
		
        $stmt = $this->conn->prepare("SELECT branch.id_branch, branch.id_shop, shop.name, branch.name, branch.mobile_number, branch.phone_number, branch.latitude, branch.longitude, shop.logo, branch.created_at FROM branch INNER JOIN shop ON shop.id_shop = branch.id_shop");
				$result = $stmt->execute();
				$stmt->bind_result($id_branch, $id_shop, $shop_name, $branch_name, $mobile_number, $phone_number, $latitudeTo, $longitudeTo, $logo, $created_at);
				while($stmt->fetch())
				{
					//$temp = array($id_branch, $id_shop, $name, $mobile_number	, $phone_number, $latitude, $longitude, $created_at);
						
				   //array_push($response,$temp);
				   
				    //$temp_distance = haversineGreatCircleDistance($latitudeFrom, $longitudeFrom, $latitudeTo, $longitudeTo);
					$DT = new Distance();
					
					$temp_distance = $DT->haversineGreatCircleDistance($latitudeFrom+0.00, $longitudeFrom+0.00, $latitudeTo+0.00, $longitudeTo+0.00);
				   if($temp_distance < 30000.00)
				   {
						$temp = array( $id_shop, $id_branch, $shop_name, $branch_name, $mobile_number, $phone_number, $latitudeTo, $longitudeTo, $temp_distance, $logo, $created_at);
						
						array_push($response,$temp);
				   }
				   
				}
				
				$stmt->close();
				
				return $response;
				
    }
			
    public function getPromotionByIdBranch($id_branch) {
		
		$response = array();
		
		$today = getdate();
		
		$temp_today = $today['year']."-".$today['mon']."-".$today['mday'];
		
        $stmt = $this->conn->prepare("SELECT * FROM promotion WHERE ( start_promotion <= ? AND end_promotion >= ? ) AND id_branch = ? ");
				$stmt->bind_param("sss", $temp_today, $temp_today, $id_branch);
				$result = $stmt->execute();
				$stmt->bind_result($id_promotion, $id_branch, $name_promotion, $logo_mini, $logo_full, $detail, $start_promotion, $end_promotion, $created_at);
				while($stmt->fetch())
				{
					$temp = array($id_promotion, $id_branch, $name_promotion, $logo_full, $start_promotion, $end_promotion, $created_at);
						
				   array_push($response,$temp);
				   
				}
				
				$stmt->close();
				
				return $response;
				
    }
			
    public function getPromotionDetail($id_promotion) {
		
		$response = array();
		
		
        $stmt = $this->conn->prepare("SELECT * FROM promotion WHERE id_promotion = ? ");
				$stmt->bind_param("s", $id_promotion);
				$result = $stmt->execute();
				$stmt->bind_result($id_promotion, $id_branch, $name_promotion, $logo_mini, $logo_full, $detail, $start_promotion, $end_promotion, $created_at);
				while($stmt->fetch())
				{
					$temp = array($id_promotion, $id_branch, $name_promotion, $logo_mini, $logo_full, $detail, $start_promotion, $end_promotion, $created_at);
						
				   array_push($response,$temp);
				   
				}
				
				$stmt->close();
				
				return $response;
				
		
				
				
    }
			
    public function createStoreImage($temp_file) {
		
			$response = array();
		
			$allowedExts = array("png", "jpg", "jpeg");
			$extension = end(explode(".", $temp_file["name"]));
			
			if ((($temp_file["type"] == "image/png") || ($temp_file["type"] == "image/jpg") || ($temp_file["type"] == "image/jpeg")) && in_array($extension, $allowedExts))
			{
				if ($temp_file["error"] > 0)
				{
					$response["error"] = true;
					$response["status"] = "Return Code: " . $temp_file["error"] . "";;
					return $response;
				
				}
				else
				{
					if($temp_file["type"] == "image/png")
					{
						$file_new = $this->generateApiKey().".png";
					}
					else
					{
						$file_new = $this->generateApiKey().".jpg";
					}
					
					  move_uploaded_file($temp_file["tmp_name"],"../img_promotion/" .$file_new);
					  
					  $response["error"] = false;
					  $response["name"] = $file_new;
					return $response;
					 
				
				}
          } 
		  else
		  {
			  $response["error"] = true;
			  $response["status"] = "Invalid file";
			  return $response;
		  }
		
				
				
    }
			
    public function createStoreLogo($temp_file) {
		
			$response = array();
		
			$allowedExts = array("png", "jpg", "jpeg");
			$extension = end(explode(".", $temp_file["name"]));
			
			if ((($temp_file["type"] == "image/png") || ($temp_file["type"] == "image/jpg") || ($temp_file["type"] == "image/jpeg")) && in_array($extension, $allowedExts))
			{
				if ($temp_file["error"] > 0)
				{
					$response["error"] = true;
					$response["status"] = "Return Code: " . $temp_file["error"] . "";;
					return $response;
				
				}
				else
				{
					if($temp_file["type"] == "image/png")
					{
						$file_new = $this->generateApiKey().".png";
					}
					else
					{
						$file_new = $this->generateApiKey().".jpg";
					}
					
					  move_uploaded_file($temp_file["tmp_name"],"../logo_brand/" .$file_new);
					  
					  $response["error"] = false;
					  $response["name"] = $file_new;
					return $response;
					 
				
				}
          } 
		  else
		  {
			  $response["error"] = true;
			  $response["status"] = "Invalid file";
			  return $response;
		  }
		
				
				
    }
			
    public function createPromotion($id_branch, $name_promotion, $logo_mini, $logo_full, $detail, $start_promotion, $end_promotion) {
		
				$start_promotion = date('Y-m-d', strtotime($start_promotion));
				$end_promotion = date('Y-m-d', strtotime($end_promotion));
				
				$stmt = $this->conn->prepare("INSERT INTO `array`.`promotion` (`id_promotion`, `id_branch`, `name_promotion`, `logo_mini`, `logo_full`, `detail`, `start_promotion`, `end_promotion`, `created_at`) 
				VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP);");
				$stmt->bind_param("sssssss", $id_branch, $name_promotion, $logo_mini, $logo_full, $detail, $start_promotion, $end_promotion);

				$result = $stmt->execute();

				$stmt->close();
				// Check for successful insertion
				if ($result) {
					// User successfully inserted
					return STORE_SUCCESSFULLY;
				} else {
					// Failed to create user
					return STORE_FAILED;
				}
		
				
				
    }

	
}
  

?>
