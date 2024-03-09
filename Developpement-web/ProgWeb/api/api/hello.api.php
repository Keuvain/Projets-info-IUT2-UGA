<?php
header('Content-Type: text/plain');
// API de type REST qui envoit une salutation à la personne 

if (isset($_GET['nom'])){
    $nom = isset($_GET['nom']);
} else {
    $nom = 'Inconnu';
}

echo "Hello $nom !"; 
?>