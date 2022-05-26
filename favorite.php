<?php 

    error_reporting(E_ALL); 
    ini_set('display_errors',1); 

    include('dbcon.php');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        // 안드로이드 코드의 postParameters 변수에 적어준 이름을 가지고 값을 전달 받는다.
        $name=$_POST['name'];
        $content=$_POST['content'];
        
        if(empty($name)){
            $errMSG = "이름을 입력하세요.";
        }
        else if(empty($content)){
            $errMSG = "내용을 입력하세요.";
        }

        if(!isset($errMSG)) // 이름과 내용 모두 입력이 되었다면 
        {
            if ($content  != "" ){ 
                $sql="select * from favorite where content='$content' and name='$name'";
                $stmt = $con->prepare($sql);
                $stmt->execute();
             
                if ($stmt->rowCount() != 0){
                    try{
                        $sql = "delete from favorite where name='$name'";
                        $stmt = $con->prepare($sql);
                        $stmt->execute();
                       
                        //SQL 실행결과를 위한 메시지를 생성한다.
                        if($stmt->execute()){
                            $successMSG = "즐겨찾기를 삭제하였습니다.";
                        }
                        else{
                            $errMSG = "즐겨찾기 에러가 발생하였습니다.";
                        }
                    } catch(PDOException $e) {
                        die("Database error: " . $e->getMessage()); 
                    }
                }
                else{
                    try{
                        // SQL문을 실행하여 데이터를 MySQL 서버의 favorite 테이블에 저장한다. 
                        $stmt = $con->prepare('INSERT INTO favorite(name, content) VALUES(:name, :content)');
                        $stmt->bindParam(':name', $name);
                        $stmt->bindParam(':content', $content);

                        //SQL 실행 결과를 위한 메시지를 생성한다.
                        if($stmt->execute()){
                            $successMSG = "즐겨찾기 추가 완료";
                        }
                        else{
                            $errMSG = "즐겨찾기 추가 실패";
                        }
                    } catch(PDOException $e) {
                        die("Database error: " . $e->getMessage()); 
                    }
                }

            }
        }
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
                Name: <input type = "text" name = "name" />
                Content: <input type = "text" name = "content" />
                <input type = "submit" name = "submit" />
            </form>  
       </body>
    </html>

<?php 
    }
?>