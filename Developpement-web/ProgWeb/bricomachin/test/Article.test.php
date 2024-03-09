<?php
// Acces aux classe
require_once(__DIR__.'/../model/Article.class.php');

// Definition d'une categorie pour les tests
$cat = new Categorie(1,"test",1);

try {
  print("Creation d'un article : ");
  $article = new Article(1,'Marteau',$cat,5.0,'image.jpg');
  print("OK\n");

  print("Test des attributs : ");
  $value = $article->getRef();
  $expected = 1;
  if ($value != $expected) {
    throw new Exception("ref incorrecte : '$value', attendu '$expected'");
  }

  $value = $article->getLibelle();
  $expected = "Marteau";
  if ($value != $expected) {
    throw new Exception("libelle incorrect : '$value', attendu '$expected'");
  }

  $value = $article->getCategorie();
  $expected = $cat;
  if ($value != $expected) {
    throw new Exception("categorie incorrecte");
  }

  $value = $article->getPrix();
  $expected = 5.0;
  if ($value != $expected) {
    throw new Exception("prix incorrect : '$value', attendu '$expected'");
  }

  $value = $article->getImage();
  $expected = 'image.jpg';
  if ($value != $expected) {
    throw new Exception("image incorrecte : '$value', attendu '$expected'");
  }

  print("OK\n");


  print("Lecture d'un article : ");
  $categorie = new Categorie(14,"Coussin et housse de coussin",13);
  $value = Article::read(69043744);
  $expected = new Article(69043744,"Coussin Pronto, brun, 60x60 cm",$categorie,19.90,"69043744.jpg");
  if ( $value != $expected) {
    var_dump($value);
    print("Attendu : \n");
    var_dump($expected);
    throw new Exception("article 69043744 incorrect");
  }
  print("OK\n");

  print("Acces à l'URL de l'image d'un article : ");
  $article = $value; // L'article lu précédement
  $value = $article->getImageURL();
  $expected = "http://www-info.iut2.upmf-grenoble.fr/intranet/enseignements/ProgWeb/data/bricomachin/img/69043744.jpg";
  if ( $value != $expected) {
    var_dump($value);
    print("Attendu : \n");
    var_dump($expected);
    throw new Exception("getImageURL de l'article 69043744 incorrect");
  }
  print("OK\n");

  print("Le nombre d'articles : ");
  $value = Article::count();
  $expected = 1522;
  if ( $value != $expected) {
    throw new Exception("nombre d'aricles incorrect '$value', attendu '$expected'");
  }
  print("OK\n");

  print("Lecture d'une page d'articles : ");
  $articles = Article::readPage(1,5);
  // Vérifie le nombre d'articles lus
  $value = count($articles);
  $expected = 5;
  if ($value != $expected) {
    throw new Exception("nombre d'articles incorrect '$value', attendu '$expected'");
  }
  // Vérifie la référence des articles lus
  $value = array();
  foreach ($articles as $article) {
    $value[] = $article->getRef();
  }
  $expected = array(54331060,54331172,54331193,54670434,60040351);
  if ( $value != $expected) {
    throw new Exception("Liste d'articles incorrecte '$value', attendu '$expected'");
  }
  print("OK\n");

}
catch
 (Exception
 $e) {
  exit("\nErreur: ".$e->getMessage()."\n");
}


?>
