<?php
include 'config.php';
//check whether username of password is set from android

	if(isset($_REQUEST['username']) && isset($_REQUEST['password'])){
		//innitialize variable
		$result = '';
		$username = $_REQUEST['username'];
		$password = $_REQUEST['password'];
		//query databse for row exist or not
		$sql = 'SELECT * FROM users WHERE username = :username AND password= :password';
		$stmt = $conn->prepare($sql);
		$stmt->bindparam(':username', $username, PDO::PARAM_STR);
		$stmt->bindparam(':password', $password, PDO::PARAM_STR);
		$stmt->execute();

			if($stmt->rowCount()){
				$result = "true";
			}elseif(!$stmt->rowCount()){
				$result = "false";
			}
			//send result back to android
			echo $result;
	}

?>
