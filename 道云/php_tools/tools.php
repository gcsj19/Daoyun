<?php
include_once ("php_mysql.php");

function guid(){
    if (function_exists('com_create_guid')){
        return com_create_guid();
    }else{
        mt_srand((double)microtime()*10000);//optional for php 4.2.0 and up.
        $charid = strtoupper(md5(uniqid(rand(), true)));
        $hyphen = chr(45);// "-"
        $uuid = chr(123)// "{"
            .substr($charid, 0, 8).$hyphen
            .substr($charid, 8, 4).$hyphen
            .substr($charid,12, 4).$hyphen
            .substr($charid,16, 4).$hyphen
            .substr($charid,20,12)
            .chr(125);// "}"
        return $uuid;
    }
}

function create_uuid(){
    $uuid = guid();
    $conn = link_to_DDI();
    $sql = "select * from operation where uuid=$uuid";
    $result = $conn->query($sql);
    while(@mysqli_num_rows($result) >0){
        $uuid = guid();
        $sql = "select * from operation where uuid=$uuid";
        $result = $conn->query($sql);
    }
    $conn->close();
    $len = strlen($uuid);
    $uuid = substr($uuid,1,$len-2);
    return $uuid;
}

function browse_info() {
    if (!empty($_SERVER['HTTP_USER_AGENT'])) {
        $br = $_SERVER['HTTP_USER_AGENT'];
        if (preg_match('/MSIE/i', $br)) {
            $br = 'MSIE';
        } else if (preg_match('/Firefox/i', $br)) {
            $br = 'Firefox';
        } else if (preg_match('/Chrome/i', $br)) {
            $br = 'Chrome';
        } else if (preg_match('/Safari/i', $br)) {
            $br = 'Safari';
        } else if (preg_match('/Opera/i', $br)) {
            $br = 'Opera';
        } else {
            $br = 'Other';
        }
        return $br;
    } else {
        return 'unknow';
    }
}


function get_user_id($user){
    $conn = link_to_sgz();
    $sql = "select id from user where account='$user'";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    $user_id = $result[0][0];
    $conn->close();
    return $user_id;
}

function get_device_all_info($user){
    $user_id = get_user_id($user);
    $conn = link_to_sgz();
    $sql = "select * from device where user_id = $user_id";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    $conn->close();
    return $result;

    
}

function test_if_device_have_qufu($user_id,$device_inner_id,$qufu){
    $conn = link_to_sgz();
    $sql = "select count(*) from device_detail where qufu = $qufu and device_inner_id = $device_inner_id and user_id = $user_id;";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    $conn->close();
    if ($result[0][0] != 0){
        return TRUE;
    }else
    {
        return FALSE;
    }
}

function get_next_inner_id($user_id){
    $conn = link_to_sgz();
    $sql = "select MAX(inner_id) from device where user_id = $user_id;";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    $max = $result[0][0];
    if ($max==NULL){
        $next = 1;
    
    }
    else{
        $next = $max+1;
    }
    
    $conn->close();
    
    return $next;
}

function user_add_device($user_id,$device_name,$device_uuid){
    $next_id = get_next_inner_id($user_id);
    $conn = link_to_sgz();
    $sql = "insert into device(user_id,device_name,device_uuid,inner_id) values($user_id,'$device_name','$device_uuid',$next_id)";
    $q = $conn->query($sql);
    $conn->close();

}


function get_all_device_inner_id($user){
    
    $user_id = get_user_id($user);
    $conn = link_to_sgz();
    $sql = "select inner_id from device where user_id = $user_id";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    return $result;
    
    
}

function get_qufu($user){
    $all_inner_id = get_all_device_inner_id($user);
    $user_id = get_user_id($user);
    $all_qufu = array();
    foreach($all_inner_id as $device_inner_id){
        $device_inner_id = $device_inner_id[0];
        $conn = link_to_sgz();
        $sql = "select distinct qufu from device_detail where device_inner_id = $device_inner_id and user_id = $user_id;";
        $q = $conn->query($sql);
        $result = $q->fetch_all();
        $conn->close();

        foreach($result as $qufu){
            $if_in_all_qufu = in_array($qufu[0],$all_qufu);
            if ($if_in_all_qufu == false){
                array_push($all_qufu,$qufu[0]);
            }
        }
        
        
        
        
    }
    return $all_qufu;

    
}

function get_device_detail($user,$device_inner_id){
    $user_id = get_user_id($user);
    

    $conn = link_to_sgz();
    $sql = "select * from device_detail where device_inner_id = $device_inner_id and user_id = $user_id;";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    $conn->close();
    return $result;
    
    
    
}



function get_qufu_device($all_device_info,$qufu){
    $select_qufu_device_info = array();
    foreach ($all_device_info as $device_info){
        $if_have = test_if_device_have_qufu($device_info[1],$device_info[4],$qufu);
        if ($if_have ==TRUE){
            array_push($select_qufu_device_info,$device_info);
        }
    }
    return $select_qufu_device_info;
    
}




function get_account_refresh_day($device_inner_id,$account_index,$user_id){
    $conn = link_to_sgz();
    $sql = "select time from device_detail where device_inner_id = $device_inner_id and account_index = $account_index and user_id = $user_id;";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    $time = $result[0][0];
    $conn->close();
    $day = substr($time,8,2);
    $now_day = date("d");
    if ($day==$now_day){
        return "today";
    }else{
        
        return "newday";
    }
}

function get_refresh_times($device_id,$account_index,$user_id){
    $conn = link_to_sgz();
    $sql = "select refresh_times from device_detail where device_inner_id = $device_id and account_index = $account_index and user_id = $user_id;";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    $times = $result[0][0];
    $conn->close();
    return $times;
}

function refresh_time($device_id,$account_index,$user_id){
    $which_day =  get_account_refresh_day($device_id,$account_index,$user_id);
    if ($which_day == "today"){
        $refresh_times = get_refresh_times($device_id,$account_index,$user_id);
        if ($refresh_times==NULL){
            return 1;
        }else
        {
            return $refresh_times+1;
        }
    }else{
        return 1;
    }
}

function today_ziyuan_up($new_mucai,$new_shitou,$new_tiekuang,$device_id,$account_index,$user_id)
{
    $which_day =  get_account_refresh_day($device_id,$account_index,$user_id);
    $conn = link_to_sgz();
    $sql = "select mucai,shitou,tiekuang,up_num from device_detail where device_inner_id = $device_id and account_index = $account_index and user_id = $user_id;";
    $q = $conn->query($sql);
    $result = $q->fetch_all();
    $conn->close();
    $mucai = $result[0][0];
    $shitou = $result[0][1];
    $tiekuang = $result[0][2];
    $up_num = $result[0][3];
    $mucai_up = 0;
    $shitou_up = 0;
    $tiekuang_up = 0;
    
    
    if ($new_mucai >$mucai){
        $mucai_up = $new_mucai - $mucai;
    }

    if ($new_tiekuang > $tiekuang){
        $tiekuang_up = $new_tiekuang - $tiekuang;

    }

    if($new_shitou>$shitou){
        $shitou_up = $new_shitou - $shitou;
    }
    
    $all_up = $mucai_up+$shitou_up+$tiekuang_up;

    
    if ($which_day =="today" ){
        return $up_num+$all_up;
    }else
    {
        return $all_up;
    }
    


}





?>





