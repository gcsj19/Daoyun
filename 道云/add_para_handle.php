<?php
/**
 * Created by PhpStorm.
 * User: cxm
 * Date: 2020/7/1
 * Time: 15:20
 *
 */

function link_to_daoyun(){
    $host='123.56.164.172:3306';

    $user='root';

    $password='735139542';

    $dbName='daoyun';

    $link=new mysqli($host,$user,$password,$dbName);

    if ($link->connect_error){

        die("连接失败：".$link->connect_error);

    }

    return $link;
}

$para_name = $_POST["para_name"];
$para_value = $_POST["para_value"];
$code = $_POST["code"];



$conn  = link_to_daoyun();
$sql_insert_para = "insert into sys_parameter(para_name,para_value,code) values ('$para_name','$para_value','$code');";
$result = $conn->query($sql_insert_para);
//$id = $result->fetch_all();
//
//$id = $id[0][0];
//
//
//
//
//$sql_insert_user = "insert into course(user_id,name) values ('$id','$course_name');";
//$result = $conn->query($sql_insert_user);
////
////$sql_insert_user_role = "insert into user_role(user_id,role_id)values ('$phone',$role);";
////$result = $conn->query($sql_insert_user_role);
header("location:para_list.php")


?>