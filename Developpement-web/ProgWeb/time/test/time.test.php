<?php
// Test de la classe Article
require_once(__DIR__ . '/../model/time.class.php');
// Fonctions d'aide
require_once(__DIR__ . '/../test/helper.php');

try {

  print("Création d'un objet Time "); 
  $time = new Time();
  print($time->dateToString().' ');
  OK();

  print("Création d'un format JSON ");
  $time = new Time();
  $json = json_encode($time);
  print($json.' ');
  OK();

} catch (Exception $e) {
  printCol("\nLe test produit l'erreur suivante :\n");
  print($e->getMessage() . "\n");
}



?>