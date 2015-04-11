
<html>
    <head>
        <meta charset="UTF-8">
        <title>NEW WORDS</title>
    </head>
    <ul>
<h2>NEW WORD</h2>
<br><br>

<form action="" method="POST">
<label for="engWord">English word: </label>
<input type="TEXT" NAME="engWord" placeholder="New English word" required="" >
<br><br><br>
<label for="arabWord">Arab word: </label>
<input type="TEXT" NAME="arabWord" placeholder="New Arabic word" required="">
<br><br><br>
<label for="englishpron">English pronunciation: </label>
<input type="TEXT" NAME="englishpron"  placeholder="New English pron" required="" >
<br><br><br>
<label for="arabpron">Arab pronunciation: </label>
<input type="TEXT" NAME="arabpron"  placeholder="New Arabic word" required="">
<br><br><br>
<label for="category">Category: </label>
    <input type="RADIO" name="a" value="O1">dsf
     <input type="RADIO" name="a" value="O1">dsf
      <input type="RADIO" name="a" value="O1">sd
    
          
<br><br><br>
<button class="submit" type="SUBMIT">Save</button>
<br><br>
<button class="submit" type="SUBMIT">Show All</button>
</form>
</ul>


    <body>
        
        <?php
 if(!empty($_POST)) {
     
 $servername = "localhost";
$username = "root";
$password = "";
$db = "newwords";

// Create connection
$conn = new mysqli($servername, $username, $password, $db);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

echo "Connected successfuly";

  
    $englishWord = mysqli_real_escape_string($conn,$_POST['engWord']);
    $arabicWord = mysqli_real_escape_string($conn,$_POST['arabWord']);
    $englishpron = mysqli_real_escape_string($conn,$_POST['englishpron']);
    $arabicpron = mysqli_real_escape_string($conn,$_POST['arabpron']);
    
     $englishWord = mysqli_real_escape_string($conn,$_GET['engWord']);
    $arabicWord = mysqli_real_escape_string($conn,$_GET['arabWord']);
    $englishpron = mysqli_real_escape_string($conn,$_GET['englishpron']);
    $arabicpron = mysqli_real_escape_string($conn,$_GET['arabpron']);
   
    
   // echo ($englishWord);
    
  $query = sprintf("INSERT INTO word " ." (id, engWord, arabWord, englishpron, arabpron) " ." VALUES (NULL, '%s', '%s', '%s', '%s');" , $englishWord, $arabicWord, $englishpron, $arabicpron );
 //echo $query;
  $conn -> query($query);
    
   $queryShow = sprintf("SELECT * FROM word " ." (id, engWord, arabWord, englishpron, arabpron) " ." VALUES (NULL, '%s', '%s', '%s', '%s');" , $englishWord, $arabicWord, $englishpron, $arabicpron );
 //echo $query;
  $conn -> query($queryShow);
 }
   
        ?>
        
        
    </body>
</html>
