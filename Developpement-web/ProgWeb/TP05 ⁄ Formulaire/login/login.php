<?php
$nom ="";
$passwordLenght = 0;
if(isset($_POST['nom'])){
  $nom =htmlspecialchars($_POST['nom']);
}
if(isset($_POST['password'])){
  $passwordLenght = strlen($_POST['password']);
}

?>
<!DOCTYPE html>
<html lang="fr" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Login</title>
    <link rel="stylesheet" href="style.css">
  </head>
  <body>
    <h1>Sur le site</h1>
    <p>
      WSH! <?= $nom ?><br>
      taille <?=$passwordLenght?>

    </p>
  </body>
</html>
