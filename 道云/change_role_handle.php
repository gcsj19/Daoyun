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
$id = $_GET["id"];
$name = $_POST["name"];




$conn  = link_to_daoyun();
$sql_update_role = "update role set name='$name'where id='$id';";

$result = $conn->query($sql_update_role);
//
//$sql_update_user_role = "update user_role set user_id='$phone',role_id=$role where user_id='$phone';";
//$result = $conn->query($sql_update_user_role);
//
header("location:role_list.php")


?>