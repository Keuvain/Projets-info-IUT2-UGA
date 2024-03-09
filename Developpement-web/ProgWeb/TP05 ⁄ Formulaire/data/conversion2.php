<?php

$sens = $_GET['sens'] ?? 'C2F';
$action = $_GET['action'] ?? '';
$temp_in = floatval($_GET['temp_in'] ?? 0);

$temp_out = "";
if($sens == "C2F") {
  $label_in = 'Celsius';
  $label_out = 'Fahrenheit';
  $temp_out = (1.8 * $temp_in) + 32;
} else if($sens == "F2C") {
  $label_in = 'Fahrenheit';
  $label_out = 'Celsius';
  $temp_out = ($temp_in - 32) / 1.8;
}

if($action == 'inverser') {
  list($temp_in, $temp_out) = [$temp_out, $temp_in];
  list($label_in, $label_out) = [$label_out, $label_in];
  $sens = ($sens == 'C2F') ? 'F2C' : 'C2F';
}
?>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
  <meta charset="utf-8">
  <title>Conversion Celsius/Fahrenheit</title>
  <link rel="stylesheet" href="design/style.css">
</head>
<body>
  <h1>Conversion de températures</h1>
  <form  action="conversion2.php" method="get">
    <button type="submit" name="action" value="inverser">Inverser</button>
    <input type="hidden" name="sens" value="<?=$sens?>">
    <input id="in" type="number" step="any" name="temp_in" value="<?=$temp_in?>">
    <label for="in"><?=$label_in?></label>
    égal
    <output id="out" for="in" name="temp_out"><?=$temp_out?></output>
    <label for="out"><?=$label_out?></label>
    <button type="submit" name="action" value="convertir">Convertir</button>
  </form>
</body>
</html>