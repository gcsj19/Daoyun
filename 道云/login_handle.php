<?php
/**
 * Created by PhpStorm.
 * User: cxm
 * Date: 2020/7/3
 * Time: 7:40
 */
session_start();


$name = $_POST["name"];
$pas  = $_POST["password"];

if ($name=="admin" and $pas=="15900000001"){
    $_SESSION["user"] = "admin";
}
if ($name=="teacher" and $pas=="15900000002"){
    $_SESSION["user"] = "admin";
}
if ($name=="student1" and $pas=="15900000003"){
    $_SESSION["user"] = "admin";
}
if ($name=="student2" and $pas=="15900000003"){
    $_SESSION["user"] = "admin";
}

$user = $_SESSION["user"];
if ($user==null){
    header("Location: login.php");
    //确保重定向后，后续代码不会被执行
    exit;
}else{
    header("Location: index.php");
    //确保重定向后，后续代码不会被执行
    exit;
}



?>