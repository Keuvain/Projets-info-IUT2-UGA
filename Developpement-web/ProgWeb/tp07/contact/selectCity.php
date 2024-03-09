<?php
// récupère les informations de la query string
$dataSourceName = 'sqlite:' . __DIR__ . '/data/contact.db';

$db = new PDO($dataSourceName);
$pdoSth = $db->query("SELECT distinct city FROM contact");
$data = $pdoSth->fetchall();

 ?>
<!DOCTYPE html>
<html lang="fr" dir="ltr">
  <head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="design/style.css">
    <title>My contacts</title>
  </head>
  <body>
    <h1>My contacts from : </h1>
    <form action="contact.php" method="get">
    <table>
      <?php foreach ($data as $data): ?>
      <tr>
          <td><label><?= $data['city']?></td>
        <td><input type="radio" name = "city" value = "<?= $data['city']?>"></label></td>
        </tr>
        <?php endforeach; ?>
    </table>
    <button type="submit">Envoyer</button>
  </body>
</html>
