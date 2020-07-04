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

$conn  = link_to_daoyun();
$sql_delete_role = "delete from role where id='$id';";
//$sql_delete_role = "delete from user_role where user_id='$user_id';";
//$result = $conn->query($sql_delete_user);
$result = $conn->query($sql_delete_role);
header("location:role_list.php")
?>