<?php

require "init.php";

$name=$_POST["name"];
$route=$_POST["route"];

$sql_query1="select id_user from users where login like '$name';";

$res = mysqli_query($connection,$sql_query1);
$row=mysqli_fetch_array($res);

if(mysqli_num_rows($res)>0){
    $u_id = $row[0];
}

$sql_query2="select result, date from results where id_user like '$u_id' and route like '$route' order by cast(result as int) limit 1;";
$max_result = mysqli_query($connection,$sql_query2);
$row2=mysqli_fetch_array($max_result);

$best_result = $row2[0];
$best_result_date = $row2[1];

$sql_query3="SELECT result, date FROM results where id_user like '$u_id' and route like '$route' order by STR_TO_DATE(date,'%Y-%m-%d %H:%i:%s') desc limit 1;";
$last_result = mysqli_query($connection,$sql_query3);
$row3=mysqli_fetch_array($last_result);

$recent_result = $row3[0];
$recent_result_date = $row3[1];

$response = "";

if(mysqli_num_rows($max_result)>0){
    $response = $response . $best_result . ',' . $best_result_date . ';';
} else {
    $response = $response . "None;";
}

if(mysqli_num_rows($last_result)>0){
    $response = $response . $recent_result . ',' . $recent_result_date . ';';
} else {
    $response = $response . "None" . ';';
}

$sql_query4="select login, result, date from results join users using(id_user) where route like '$route' order by cast(result as int);";
$all_results = mysqli_query($connection,$sql_query4);
if(mysqli_num_rows($all_results) > 0 ){
    while ($row = mysqli_fetch_array($all_results)) {
        $response = $response . $row[0] . ',' . $row[1] . ',' . $row[2] . '.';
    }
}

echo $response;

mysqli_close($connection);
?>