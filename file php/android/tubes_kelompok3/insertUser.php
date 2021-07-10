<?php  
include 'koneksi.php'; 
 
$hasil         = mysqli_query($conn, "INSERT INTO users VALUES('', '".$_GET['username']."', '".$_GET['password']."', '".$_GET['nama']."', '".$_GET['alamat']."', '0')") or die(mysql_error());
$json_response = array();

if($hasil){
	$json_response[] = "true";
}else{
	$json_response[] = "false";
}
 
echo json_encode(array('result' => $json_response));
?>