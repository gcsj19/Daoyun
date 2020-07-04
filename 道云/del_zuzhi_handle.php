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
$sql_delete_ins = "delete from institutional_framework where id='$id';";
$result = $conn->query($sql_delete_ins);
header("location:zuzhi_list.php")
?>