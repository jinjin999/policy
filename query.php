<?php  

error_reporting(E_ALL); 
ini_set('display_errors',1); 

include('dbcon.php');

//POST 값을 읽어온다.
$content=isset($_POST['content']) ? $_POST['content'] : '';
$name = isset($_POST['name']) ? $_POST['name'] : '';
$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

$stmt = $con->prepare('select * FROM favorite');
$stmt->execute();

$data = array(); 

if ($stmt->rowCount() == 0){
    //echo "'";
    //echo $name,",";
    //echo $content,",";
    echo "즐겨찾기를 추가해주세요.";
}
else{
    while($row=$stmt->fetch(PDO::FETCH_ASSOC)){

        extract($row);
                array_push($data, 
                    array('id'=>$row["id"],
                    'name'=>$row["name"],
                    'content'=>$row["content"]
                ));
            }

            //안드로이드에 전달하기 위해 JSON 포맷으로 변경 후 에코한다.
            if (!$android) {
                echo "<pre>";
                print_r($data);
                echo '</pre>';
            }
            else{
                header('Content-Type: application/json; charset=utf8');
                $json = json_encode(array("favorite_result"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
                echo $json;
            }
    }

?>

<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
<body>  
  <form action="<?php $_PHP_SELF ?>" method="POST">
     내용: <input type = "text" name = "content" />
     이름: <input type = "text" name = "name" />
     <input type = "submit" />
  </form>  
</body>
</html>
<?php
}

?>