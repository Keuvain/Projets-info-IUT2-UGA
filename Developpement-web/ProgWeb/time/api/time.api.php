<?php
require_once(__DIR__ . "/../model/time.class.php");

// Tableau qui contient la réponse 
$out = [];
// Examine l'action demandée
$action = $_GET['action'] ?? '';

switch ($action) {
    // Lecture de la date sachant la time zone
    case 'read':
        // 
        ///////////////////////////////////////////////////////
        // A COMPLETER
        ///////////////////////////////////////////////////////

        //  

        break;
    default:
        $out['error'] = 'incorrect action';
}

// Change le status en cas d'erreur
// 
///////////////////////////////////////////////////////
// A COMPLETER
///////////////////////////////////////////////////////

//  

// Sort la réponse
// Indique dans le header que l'on sort du JSON
header('Content-Type: application/json; charset=utf-8');
print(json_encode($out));
?>