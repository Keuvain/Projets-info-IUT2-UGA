<?php
function bonjour() {
          if (isset($nom)) {
            echo "Bonjour $nom</br>";
          } else {
            echo "Mais qui êtes vous ?</br>";
          }
        }

        function hello() {
          global $nom;
          if (isset($nom)) {
            echo "Hello $nom</br>";
          } else {
            echo "Mais qui êtes vous ?</br>";
          }
        }

        function salut() {
          static $nom;
          if (isset($nom)) {
            echo "Salut $nom</br>";
          } else {
            echo "Mais qui êtes vous ?</br>";
          }
          $nom = "Cyprien";
        }

        bonjour();
        $nom="Arthur";
        bonjour();

        hello();
        $nom="Marcel";
        hello();

        salut();
        $nom="Mohamed";
        salut();
?>