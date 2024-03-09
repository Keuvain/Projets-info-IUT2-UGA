<!DOCTYPE html>
<html>
<style>
table,th,td
{
  border:1px solid black;
  border-collapse:collapse;
}
th,td
{
  padding:5px;
}
</style>
</head>
<body>
  <h1>Tables de multiplication</h1>
  <table>
    <!-- Première ligne : le header -->
    <tr>
      <!-- Première case vide -->
      <th></th>
      <!-- Les autres cases du header de la première ligne -->
      <!-- La syntaxe alternative des blocks est utilisée -->
      <?php for ($i = 1; $i<=10; $i++) : ?>
        <th><?=$i?></th>
      <?php endfor; ?>
    </tr>
    <!--Les autres lignes du tableau -->
    <?php for ($i = 1; $i<=10; $i++) : ?>
      <tr>
        <!--Le header en début de ligne -->
        <th><?=$i?></th>
        <!-- le reste de la ligne -->
        <?php for ($j = 1; $j <= 10; $j++ ) : ?>
          <td><?= $i * $j ?></td>
        <?php endfor; ?>
      </tr>
    <?php endfor; ?>
  </table>
</body>
</html>
