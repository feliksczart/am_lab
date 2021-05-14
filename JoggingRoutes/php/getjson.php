<?php

require "init.php";

$u_name=$_POST["name"];
//$u_name = "1";

$sql_query="select login, password from users where login like '$u_name';";
$result = mysqli_query($connection,$sql_query);
$response = array();

while($row=mysqli_fetch_array($result))
{
    array_push($response,array("name"=>$row[0],"password"=>$row[1]));
}

echo json_encode(array("server_response"=>$response));
mysqli_close($connection);
?>