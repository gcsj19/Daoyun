<?php
require "php_tools/php_mysql.php";

$conn = link_to_daoyun();
$sql_get_jiaose = "select id,name from role;";
$result = $conn->query($sql_get_jiaose);

$all_jiaose = $result->fetch_all();


$sql_get_major = "select id,major from institutional_framework;";
$result = $conn->query($sql_get_major);
$all_major = $result->fetch_all();

?>


<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>新增用户</title>
<link rel="icon" href="favicon.ico" type="image/ico">
<meta name="keywords" content="LightYear,光年,后台模板,后台管理系统,光年HTML模板">
<meta name="description" content="LightYear是一个基于Bootstrap v3.3.7的后台管理系统的HTML模板。">
<meta name="author" content="yinqi">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/materialdesignicons.min.css" rel="stylesheet">
<!--标签插件-->
<link rel="stylesheet" href="js/jquery-tags-input/jquery.tagsinput.min.css">
<link href="css/style.min.css" rel="stylesheet">
</head>
  
<body>
<div class="lyear-layout-web">
  <div class="lyear-layout-container">
    <!--左侧导航-->
    <?php include_once "sider_bar.php" ?>
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
              <div class="card-body">
                
                <form action="add_user_handle.php" method="post" class="row">
                  <div class="form-group col-md-12">
                    <label for="role">角色</label>
                    <div class="form-controls">
                      <select name="role" class="form-control" id="type">
                          <?php
                          foreach ($all_jiaose as $jiaose){
                              echo "<option value=\"$jiaose[0]\">$jiaose[1]</option>";
                          }
                          ?>
                      </select>
                    </div>
                  </div>
                    <div class="form-group col-md-12">
                        <label for="major">专业</label>
                        <div class="form-controls">
                            <select name="major" class="form-control" id="major">
                                <?php
                                    foreach ($all_major as $major){
                                        echo "<option value=\"$major[0]\">$major[1]</option>";
                                    }
                                ?>
                            </select>
                        </div>
                    </div>

                  <div class="form-group col-md-12">
                    <label for="name">用户名</label>
                    <input type="text" class="form-control" id="name" name="name" value="" placeholder="用户名" />
                  </div>
                  <div class="form-group col-md-12">
                    <label for="phone">电话</label>
                    <input type="text" class="form-control" id="phone" name="phone" value="" placeholder="电话" />
                  </div>
                <div class="form-group col-md-12">
                    <label for="mail">邮箱</label>
                    <input type="text" class="form-control" id="mail" name="mail" value="" placeholder="邮箱" />
                </div>




                  <div class="form-group col-md-12">
                    <button type="submit" class="btn btn-primary ajax-post" target-form="add-form">确 定</button>
                    <button type="button" class="btn btn-default" onclick="javascript:history.back(-1);return false;">返 回</button>
                  </div>
                </form>
       
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
<!--标签插件-->
<script src="js/jquery-tags-input/jquery.tagsinput.min.js"></script>
<script type="text/javascript" src="js/main.min.js"></script>
</body>
</html>