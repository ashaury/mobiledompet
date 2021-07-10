<?php  
include 'koneksi.php'; 
 
$hasil         = mysqli_query($conn, "SELECT * FROM users WHERE username='".$_GET['username']."'") or die(mysql_error());
$json_response = array();
 
if(mysqli_num_rows($hasil)> 0){
	while ($row = mysqli_fetch_array($hasil)) {
		$json_response[] = $row;
	}
	echo json_encode(array('users' => $json_response));
} 
?>