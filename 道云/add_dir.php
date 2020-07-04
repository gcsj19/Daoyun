<?php
require "php_tools/php_mysql.php";

$conn = link_to_daoyun();
$sql_get_name = "select user_name from user;";
$result = $conn->query($sql_get_name);

$all_name = $result->fetch_all();


?>


<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>新增字典</title>
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

                                <form action="add_dir_handle.php" method="post" class="row">

                                    <div class="form-group col-md-12">
                                        <label for="para_name">参数名</label>
                                        <input type="text" class="form-control" id="para_name" name="para_name" value="" placeholder="参数名" />
                                    </div>
                                    <div class="form-group col-md-12">
                                        <label for="code">code</label>
                                        <input type="text" class="form-control" id="code" name="code" value="" placeholder="code" />
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