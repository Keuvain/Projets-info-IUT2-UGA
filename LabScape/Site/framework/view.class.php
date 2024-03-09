<?php

class View {
  // Paramètres de la vue, stockés dans un tableau associatif
  private $param;

  // Constructeur de la classe View
  function __construct() {
    // Initialise un tableau vide pour stocker les paramètres de la vue
    $this->param = array();
  }

  // Méthode pour ajouter une variable à la vue
  function assign(string $varName, $value) {
    $this->param[$varName] = $value;
  }

  // Méthode pour afficher la vue
  function display(string $filename, bool $useAlternativePath = false) {
    // Définition du chemin relatif vers le fichier de la vue
    if ($useAlternativePath) {
        $basePath = "../jeu/";
    } else {
        $basePath = "../view/";

    }
   

    $path = $basePath . $filename;

    // Toutes les variables de l'objet sont rendues accessibles localement
    // à la fonction display. Cela facilite l'expression des valeurs dans la vue.

    // Parcours de toutes les variables de la vue
    foreach ($this->param as $key => $value) {
      // La notation $$ fait référence à une variable dont le nom est contenu dans une autre variable
      $$key = $value;
    }

    // Inclusion du fichier de la vue
    // Cette inclusion se fait dans la portée de la méthode display,
    // donc seules les variables locales à display sont visibles dans le fichier de la vue.
    include($path);
  }

  // Méthode pour afficher toutes les valeurs des paramètres de la vue
  function dump() {
    foreach ($this->param as $key => $value) {
      print("<br/><b>$key: </b>\n");
      var_dump($value);
    }
  }
}

?>
