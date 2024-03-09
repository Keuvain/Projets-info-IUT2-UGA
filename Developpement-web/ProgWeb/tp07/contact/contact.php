<?php
// récupère les informations de la query string
$city = $_GET['city'];
$dataSourceName = 'sqlite:' . __DIR__ . '/data/contact.db';

$db = new PDO($dataSourceName);
$pdoSth = $db->query("SELECT * FROM contact WHERE city = '$city'");
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
    <h1>My contacts from <?= $city ?></h1>
    <table>
      <?php foreach ($data as $city): ?>
      <tr>
          <td><?= $city['name']?></td>
          <td><?= $city['phone']?></td>
        </tr>
        <?php endforeach; ?>
    </table>
  </body>
</html>
