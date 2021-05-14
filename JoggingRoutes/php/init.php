<?php

$db_name="jogging";
$mysql_user="adam";
$pass="adam2020";
$server_name="localhost";

$connection= mysqli_connect($server_name,$mysql_user,$pass,$db_name);

if(!$connection){
    //echo "Connection error";
}
else{
    //echo "Connection success";
}

?>