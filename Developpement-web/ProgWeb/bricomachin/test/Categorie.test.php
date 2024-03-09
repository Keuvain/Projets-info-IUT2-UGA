<?php
// Acces à la classe
require_once(__DIR__.'/../model/Categorie.class.php');

try {
  echo "Creation d'une categorie vide : ";
  $categorie = new Categorie();
  echo "OK\n";
} catch (Exception $e) {
  exit("\nErreur: ".$e->getMessage()."\n");
}

try {
  echo "Creation d'une categorie : ";
  $categorie = new Categorie(1,"cat",1);
  echo "OK\n";

  // Test de chaque attribut
  echo "Test des attributs : ";
  $value = $categorie->getId();
  $expected = 1;
  if ($value != $expected) {
    throw new Exception("Erreur sur id '$value', attendu '$expected'");
  }

  $value = $categorie->getNom();
  $expected = "cat";
  if ($value != $expected) {
    throw new Exception("Erreur sur nom '$value', attendu '$expected'");
  }

  $value = $categorie->getPere();
  $expected = 1;
  if ($value != $expected) {
    throw new Exception("Erreur sur pere '$value', attendu '$expected'");
  }
  echo "OK\n";

  print("Lecture d'une catégorie de la BD : ");
  $value = Categorie::read(7);
  $expected = new Categorie(7,"Applique murale",6);
  if ( $value != $expected) {
    var_dump($value);
    print("Attendu : \n");
    var_dump($expected);
    throw new Exception("categorie 7 incorrecte");
  }
  echo "OK\n";

  print("La liste des sous categories : ");
  $categorie = new Categorie(15,"Rideau, voilage et vitrage",51);
  $categList = $categorie->readSubCategorie();
  // Transforme la liste de catégories en liste d'id de catégorie
  $value = array();
  foreach ($categList as $categorie) {
    $value[] = $categorie->getId();
  }
  $expected = array(16,30,33,34);
  if ( $value != $expected) {
    var_dump($value);
    print("Attendu : \n");
    var_dump($expected);
    throw new Exception("liste de categorie incorrecte'");
  }
  print("OK\n");


} catch (Exception $e) {
  exit("\nErreur: ".$e->getMessage()."\n");
}
 ?>
