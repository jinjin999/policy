<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    //POST 값을 읽어온다.
    $name = isset($_POST['name']) ? $_POST['name'] : '';

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $sql="select * from favorite WHERE name='$name'";
    $stmt = $con->prepare($sql);
    $stmt->execute();

    try{
        $sql = "delete from favorite where name='$name'";
        //sql문 실행하여 데이터를 MYSQL 서버의 favorite 테이블에서 제거
        $stmt = $con->prepare($sql);
        $stmt->execute();
    } catch(PDOException $e) {
        die("Database error: " . $e->getMessage()); 
    }
    
?>

<?php 
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;

 $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
   
    if( !$android )
    {
?>
    <html>
       <body>
            <form action="<?php $_PHP_SELF ?>" method="POST">
                name: <input type = "text" name = "name" />
                content: <input type = "text" name = "content" />
                <input type = "submit" name = "submit" />
            </form>
       
       </body>
    </html>

<?php 
    }
?>