<?php
if(session_status()){
    session_start();
}
// Envoit sur le login

if (isset($_SESSION['login'])) {
    include("view/main.php");
    exit();
}else{
    $login = $_POST['login'] ?? '';
    $action = $_POST['submit'] ??  "";

    if(isset($_GET['action']) && $_GET['action'] === 'oublie'){
        include("view/not_implemented.html"); 
        exit();
    }else{
        switch ($action) {
            case 'login':
                if ($_POST['login'] == "zhengke" && $_POST['password'] == "zhengke") {
                    $_SESSION['login'] = $login;
                    include("view/main.php");
                }
                break;
            case 'new':
                include("view/not_implemented.html");
                break;
            default:
            include("view/login.html");
            break;
        }
    }
    
}
exit();


var_dump($_POST);
?>