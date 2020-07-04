<?php
//文件上传函数封装
/*
 * @param1 array $file,要上传的文件信息，包含5个元素
 *@param2 string $path,存储位置
 * @param3 $string error,错误信息
 * @param4 array $type=array(),MIME类型限定
 * @param5 int $size=2000000,默认2M
 *  @return mixed, 成功返回文件名，失败返回false
 */
function fileUpload($file,$path,&$error,$type=array(),$size=2000000 ){
//判断本身文件是否有效
    if(!isset($file['error'])){
        $error='File have some faults,please check it';
        return $error;
    }
    //有效路径的判断
    if(!is_dir($path)){
        $error='Failure To Submit Please Try It Again';
        return $error;
    }
    //判断文件本身上传是否成功
    switch ($file['error']){
        case 1:
        case 2:
            $error="The File Size Is Too Large To Uploaded";
            return $error;
        case 3:
            $error='File name have some faults,please check it';
            return $error;
        case 4:
            $error='Null File Is Not Allowed!';
            return $error;
        case 6:
        case 7:
            $error='Cannot Link To The Server';
            return $error;
    }
//    //判断类型是否符合
//    if (!empty($type) && !in_array($file['type'],$type)){
//        $error='当前上传的文件类型不符合';
//        return false;
//    }
    //转存，移动文件
    $newfilename=$file['name'];
    if(@move_uploaded_file($file['tmp_name'],$path.'/'.$newfilename)){
        return "successfully";
    }
    else{
        $error='Your uploads have some faults,please check it!';
        return $error;
    }

}