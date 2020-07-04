<?php
header("Content-Type:text/xml;charset=utf-8");
header("Cache-Control:no-cache");
function link_to_DDI(){
    $host='127.0.0.1';
    $user='root';
    $password='root';
    $dbName='DDI';
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
function spe_replace($name){
    $name_new = str_ireplace("&","",$name);
    $name_new = str_ireplace("<","",$name_new);
    $name_new = str_ireplace("'","",$name_new);
    return$name_new;
}
function compute_runtime($date_str){

    @$submit_time = strtotime($date_str);
    $now_time = time();
    $gap = $now_time - $submit_time;//+28800;//没办法啦  硬编码

    $run_min = round($gap/(60));
    $run_sec = $gap%60;
    $run_time = $run_min.":".$run_sec;
    return $run_time;
}
$uid = $_GET["uid"];
//$uid = "79526D7C-CF7B-4716-BB51-5B269AD9DA5D";
$conn = link_to_DDI();
#先获取op_num下对应的运行状态
$sql = "select * from operation where uuid = '$uid'";
$result_op = fetch_query($sql,$conn);

$status = $result_op[0][9];
$type = $result_op[0][4];

//判断当前程序运行状态
if($status=="running"||$status=="successfully"){
    #请在js代码里给出一个等待跳转时间防止用户过早访问
    #获取当前运行数
    $run_num = $result_op[0][8];
    if ($type=="two_smiles" or $type == "two_name" or $type=="two_files"){
        if ($run_num== 0){
            $progress_num = 30;
        }
        else{
            $progress_num = 90;
        }
    }
    else{
        $progress_num = (int)($run_num/22);
    }
    if ($status=="successfully"){
        $progress_num = 100;
    }
    database_to_xml($result_op[0],$progress_num,$status);
}
else{
    $progress_num = 0;
    database_to_xml($result_op[0],$progress_num,$status);
}



function database_to_xml($result,$progress_num,$status){
    echo '<?xml version="1.0" encoding="ISO-8859-1"?><person>';
    if($result)
    {
        $type = $result[4];
        $name = explode(',',$result[7],2);

        $name1 = $name[0];
        $name2 = $name[1];
        $submit_time = $result[6];
        $submit_time= $submit_time." GMT";
        $run_time = compute_runtime($submit_time);
        if ($type=="one_smiles"||$type=="one_name"||$type=="one_file"){
            $name2 = "No input";
        }
        if ($type=="two_smiles"||$type=="one_smiles"){
            $type_new = "SMILES";
        }
        if ($type=="two_name"||$type=="one_name"){
            $type_new="Drug names";
        }
        if ($type=="two_file"||$type=="one_file"){
            $type_new="Files";
            $name = explode(',',$result[7]);
            $file1 = explode("/",$name[0]);
            $file2 = explode("/",$name[1]);
            $name1 = $file1[count($file1)-1];
            $name2 = $file2[count($file2)-1];
            $name1 = urldecode($name1);
            $name2 = urldecode($name2);
        }

        $name1 = spe_replace($name1);
        $name2 = spe_replace($name2);
        $progress_str = $progress_num."%";
        echo "<UUID>" . $result[1] . "</UUID>";
        echo "<Submit>" . $type_new . "</Submit>";
        echo "<Time>" . $submit_time . "</Time>";
        echo "<file1>".$name1."</file1>";
        echo "<file2>".$name2."</file2>";
        echo "<handled>".$progress_str."</handled>";
        echo "<handled_num>".$progress_num."</handled_num>";
        echo "<run_time>".$run_time."</run_time>";
        echo "<status>".$status."</status>";
        echo "<type>".$type."</type>";
    }
    echo "</person>";

}
$conn->close();
?>