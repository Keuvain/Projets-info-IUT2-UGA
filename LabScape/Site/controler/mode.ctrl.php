<?php

// Inclusion du fichier de classe de vue
include_once(__DIR__."/../framework/view.class.php");

// Création d'une instance de la classe View
$view = new View();

// Définition du chemin de sortie pour la vue
$outgoing = "../view/Mode.php";

// Affichage de la vue en utilisant le chemin spécifié
$view->display($outgoing);

?>
