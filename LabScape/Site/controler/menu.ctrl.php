<?php

// Inclusion du fichier de classe de vue
include_once(__DIR__."/../framework/view.class.php");

// Création d'une instance de la classe View
$view = new View();

// Définition du chemin de sortie par défaut pour la vue
$outgoing = "../view/Menu.php";

// Vérification de l'existence de la session et du rôle de l'utilisateur
if(!isset($_SESSION["id"])) {
    // Redirection vers la page de landing (ou page d'accueil) si la session n'existe pas
    $outgoing = "../controler/landing.ctrl.php";
} elseif ($_SESSION['roleid'] == 1) {
    // L'utilisateur a un rôle égal à 1, pas besoin de changer le chemin de sortie
} elseif ($_SESSION['roleid'] == 2) {
    // L'utilisateur a un rôle égal à 2, redirection vers une autre page
    $outgoing = "../controler/menuProf.ctrl.php";
}

// Affichage de la vue en utilisant le chemin déterminé
$view->display($outgoing);

?>