<?php
require "php_tools/php_mysql.php";
$conn = link_to_daoyun();
$sql_all_student = "select id,phone,user_name,email,major,is_permission from user where user_name<>'' ;";
$result = $conn->query($sql_all_student);

$all_user = $result->fetch_all();
$index = 0;
foreach ($all_user as $user){
    $phone = $user[1];
    $sql_role = "select role_id from user_role where user_id = $phone";
    $result = $conn->query($sql_role);
    $roles = $result->fetch_all();
    $roles = $roles[0][0];

    echo "-";
    echo $roles;
    echo "-";
    $sql_role_name = "select name from role where id = $roles";
    $result = $conn->query($sql_role_name);
    $roles = $result->fetch_all();
    $roles = $roles[0][0];
    array_push($all_user[$index],$roles);
    $index=$index+1;
}

foreach ($all_user as $user){
    echo $user[6];
}
$conn->close();
?>


<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>用户列表</title>
<link rel="icon" href="favicon.ico" type="image/ico">
<meta name="keywords" content="LightYear,光年,后台模板,后台管理系统,光年HTML模板">
<meta name="description" content="LightYear是一个基于Bootstrap v3.3.7的后台管理系统的HTML模板。">
<meta name="author" content="yinqi">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/materialdesignicons.min.css" rel="stylesheet">
<link href="css/style.min.css" rel="stylesheet">
</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <?php include_once "sider_bar.php"?>
    <!--End 左侧导航-->
    
    <!--头部信息-->
    <?php include_once "header.php"?>
    <!--End 头部信息-->
    
    <!--页面主要内容-->
    <main class="lyear-layout-content">
      
      <div class="container-fluid">
        
        <div class="row">
          <div class="col-lg-12">
            <div class="card">
              <div class="card-toolbar clearfix">
                <form class="pull-right search-bar" method="get" action="#!" role="form">
                  <div class="input-group">
                    <div class="input-group-btn">
                        <a class="btn btn-primary m-r-5" href="add_user.php"><i class="mdi mdi-plus"></i> 新增</a>
                    </div>

                  </div>
                </form>
                <div class="toolbar-btn-action">
                  <p>用户列表</p>
                </div>
              </div>
              <div class="card-body">
                
                <div class="table-responsive">
                  <table class="table table-bordered">
                    <thead>
                      <tr>

                        <th>编号</th>
                        <th>用户名</th>
                          <th>角色</th>
                          <th>电话</th>
                        <th>邮箱</th>
                        <th>专业</th>
                        <th>操作</th>
                      </tr>
                    </thead>
                    <tbody>
                        <?php
                            foreach ($all_user as $user){
                                echo "<tr>";
                                echo "<td>$user[0]</td>";
                                echo "<td>$user[2]</td>";
                                echo "<td>$user[6]</td>";
                                echo "<td>$user[1]</td>";
                                echo "<td>$user[3]</td>";
                                echo "<td>$user[4]</td>";
                                echo "<td>
                          <div class=\"btn-group\">
                            <a class=\"btn btn-xs btn-default\" href=\"change_user.php?user_id=$user[1]&user_name=$user[2]&user_mail=$user[3]\" title=\"编辑\" data-toggle=\"tooltip\"><i class=\"mdi mdi-pencil\"></i></a>
                            <a class=\"btn btn-xs btn-default\" href=\"del_user_handle.php?user_id=$user[1]\" title=\"删除\" data-toggle=\"tooltip\"><i class=\"mdi mdi-window-close\"></i></a>
                          </div>
                        </td>";
                                echo "</tr>";
                            }

                        ?>

                    </tbody>
                  </table>
                </div>
       
              </div>
            </div>
          </div>
          
        </div>
        
      </div>
      
    </main>
    <!--End 页面主要内容-->
  </div>
</div>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/perfect-scrollbar.min.js"></script>
<script type="text/javascript" src="js/main.min.js"></script>
<script type="text/javascript">
$(function(){
    $('.search-bar .dropdown-menu a').click(function() {
        var field = $(this).data('field') || '';
        $('#search-field').val(field);
        $('#search-btn').html($(this).text() + ' <span class="caret"></span>');
    });
});
</script>
</body>
</html>