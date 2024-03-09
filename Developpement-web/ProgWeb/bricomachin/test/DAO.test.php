<?php
// Test de la classe DAO
require_once(__DIR__.'/../model/DAO.class.php');

try {
  print("Création d'un objet DAO : ");
  $dao = DAO::get();
  print("OK\n");

  print("Les références d'une page : ");
  $value = $dao->getPageRef(5,6);
  $expected = array(63351631,63351645,63351652,63472395,63635355,63771260);
  if ( $value != $expected) {
    var_dump($value);
    print("Attendu : \n");
    var_dump($expected);
    throw new Exception("liste de reférences incorrecte'");
  }
  print("OK\n");

  print("Les références d'une page d'une catégorie : ");
  $categorie = new Categorie(18,"Garage et carport",3);
  $value = $dao->getPageRefCategorie(5,6,$categorie);
  $expected = array(68408991,68486873,68486894,68486922,68486950,68487013);
  if ( $value != $expected) {
    var_dump($value);
    print("Attendu : \n");
    var_dump($expected);
    throw new Exception("liste de reférences incorrecte'");
  }
  print("OK\n");

} catch (Exception $e) {
  exit("\nErreur sur DAO : ".$e->getMessage()."\n");
}


?>
