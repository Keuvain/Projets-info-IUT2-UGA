<?php
    if (isset($_GET['a'])) {
        $a = $_GET['a'];
      } else {
        $a = "inconnu";
      }

      if (isset($_GET['b'])) {
        $b = $_GET['b'];
      } else {
        $b = "inconnu";
      }

      if (isset($_GET['op'])) {
        $op = $_GET['op'];
      } else {
        $op = "inconnu";
      }
      $resultat = 0;

      switch($op){
        case'+':
            $resultat = $a + $b;
            break;
        case'-':
            $resultat = $a - $b;
            break;
        case'*': 
            $resultat = $a * $b;
            break;
        case'/': 
            $resultat = $a / $b;
            break; 
        default:
        echo "Opérateur non valable ou manquant"; 
        exit;            
      }

      echo "$a $op $b = $resultat";

?>