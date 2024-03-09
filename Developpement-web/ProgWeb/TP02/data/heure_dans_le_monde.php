<?php
$villes = array('America/Anchorage','America/Los_Angeles','America/Guadeloupe',
'Europe/Paris', 'Africa/Kigali',
'Asia/Singapore','Australia/Sydney','Pacific/Auckland');
?>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="utf-8">
  <title>L'heure dans le monde</title>
  <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
  <h1>L'heure dans le monde</h1>
  <table>
    <tr>
      <?php
        foreach($villes as $villes){
          date_default_timezone_set($villes);
          $heure_actuelle = date("H:i d/m/Y");
          echo"<tr>";
            echo"<td>$villes<\td>";
            echo"<td>$heure_actuelle<\td>";
          echo"<\tr>";
        }
      ?>
    </tr>
  </table>
</body>
</html>
