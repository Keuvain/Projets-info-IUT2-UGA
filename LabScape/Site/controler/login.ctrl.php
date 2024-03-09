<?php

// Inclusion des fichiers nécessaires
include_once(__DIR__."/../framework/view.class.php");
include_once(__DIR__."/../model/DAO.class.php");
include_once(__DIR__."/../model/etudiant.php");
include_once(__DIR__."/utils/Utils.php");

// Initialisation de la vue et démarrage de la session
$view = new View();
session_start();

// Initialisation des variables
$error = "";
$outgoing = "login.view.php";

// Vérification de l'existence de données POST
if(count($_POST)){
    // Récupération des données POST
    $login = $_POST['login'] ?? "";
    $password = $_POST['password'] ?? "";

    // Vérification si les identifiants sont présents
    if (!$login || !$password){
        throw new Exception("Post is filled, but no credentials");
    }

    // Récupération de l'instance de DAO
    $dao = DAO::get();
    $data = [$login];

    // Requête pour récupérer les informations de l'utilisateur
    $query = "SELECT roleid,id,password,login FROM Utilisateur WHERE login = ?";
    $table = $dao->query($query,$data);

    // Vérification si l'utilisateur existe
    if(!count($table)){
        $error = "Le login $login n'existe pas !";
    }
    else {
        // Vérification du mot de passe
        $reussite = false;
        ($reussite = password_verify($password,$table[0]['password'])) ? $outgoing = "../controler/menu.ctrl.php" : $error = "Mauvais mot de passe, réessayer";
        $id = $table[0]['id'];
        $roleid = $table[0]['roleid'];
        $_SESSION['login']=$login;

        // Enregistrement des informations dans les logs en cas de réussite
        $reussite ? log_session(array("id" => $id,"login" => $login, "password" => $table[0]['password'], "roleid" => $roleid)) : "" ;
    }
}

// Attribution de la variable d'erreur à la vue
$view->assign("error",$error);

// Affichage de la vue
$view->display($outgoing);

?>
