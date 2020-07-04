<?php
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
function fetch_query($sql,$link){
    $result = $link->query($sql);
    $arr = $result->fetch_all();
    return $arr;
}
?>

