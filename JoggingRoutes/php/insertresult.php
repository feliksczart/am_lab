<?php

require "init.php";

$name=$_POST["name"];
$route=$_POST["route"];
$result=$_POST["result"];
$date=$_POST["date"];

$sql_query1="select id_user from users where login like '$name';";

$res = mysqli_query($connection,$sql_query1);
$row=mysqli_fetch_array($res);

if(mysqli_num_rows($res)>0){
    $u_id = $row[0];
}

$sql_query2="insert into results(id_user,route,result,date) values('$u_id','$route','$result','$date');";

if(mysqli_query($connection,$sql_query2)){
    echo "Data inserted";
}
else{
    echo "Insertion error";
}
mysqli_close($connection);

?>