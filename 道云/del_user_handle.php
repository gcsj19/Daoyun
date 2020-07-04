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
//$role = $_POST["role"];
//$major = $_POST["major"];
//$name = $_POST["name"];
//$phone = $_POST["phone"];
//$mail = $_POST["mail"];
//
//$conn  = link_to_daoyun();
//$sql_insert_user = "insert into user(user_name,phone,email,major) values ('$name','$phone','$mail','$major');";
//$result = $conn->query($sql_insert_user);
//
//$sql_insert_user_role = "insert into user_role(user_id,role_id)values ('$phone',$role);";
//$result = $conn->query($sql_insert_user_role);

$user_id = $_GET["user_id"];
$conn  = link_to_daoyun();
$sql_delete_user = "delete from user where phone='$user_id';";
$sql_delete_role = "delete from user_role where user_id='$user_id';";
$result = $conn->query($sql_delete_user);
$result = $conn->query($sql_delete_role);
header("location:user_list.php")
?>