<?php

// Inclusion des fichiers nécessaires
require_once(__DIR__ . '/../framework/view.class.php');
require_once(__DIR__ . '/../model/DAO.class.php');

// Initialisation de la vue
$view = new View();

// Démarrage de la session et initialisation d'une variable d'erreur
session_start();
$error = "";

// Vérification de l'existence de la session
if (isset($_SESSION['id'])) {
    // Récupération de l'ID utilisateur depuis la session
    $userId = $_SESSION['id'];
    
    // Attribution de l'ID utilisateur à la vue
    $view->assign("userId", $userId);
} else {
    // Redirection vers la page de connexion si la session n'existe pas
    header("Location: login.view.php");
    exit();
}

// Initialisation de la devise de l'utilisateur
$userCurrency = 0;

// Vérification à nouveau de l'existence de la session (redondant)
if (isset($_SESSION['id'])) {
    // Récupération de l'ID utilisateur depuis la session (redondant)
    $userId = $_SESSION['id'];
    
    // Récupération de la DAO (Data Access Object)
    $dao = DAO::get();

    // Requête pour récupérer la devise de l'utilisateur à partir de la base de données
    $query = "SELECT currency FROM Person WHERE id = ?";
    $table = $dao->query($query, [$userId]);

    // Vérification si des résultats ont été obtenus
    if (count($table)) {
        // Récupération de la devise de l'utilisateur depuis le résultat de la requête
        $userCurrency = $table[0]['currency'];

        // Affichage de la devise de l'utilisateur (à des fins de débogage)
        echo "User Currency: $userCurrency";
    }
}

// Attribution de la variable d'erreur à la vue
$view->assign("error", $error);

?>
