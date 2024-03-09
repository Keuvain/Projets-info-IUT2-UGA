<?php
$tempc ="";
$tempf ="";
if(isset($_GET['temp_in']) && is_numeric($_GET['temp_in'])){
  $tempc = $_GET['temp_in'];
  $tempf = (1.8 * $tempc) +32.0;
}

?>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Conversion Celcius/Fahrenheit</title>
    <link rel="stylesheet" href="design/style.css">
  </head>
  <body>
    <h1>Conversion de températures</h1>
    <form  action="conversion1.php" method="get">
      <input id="in" type="number" step="any" name="temp_in" value="<?php echo $tempc;?>">
      <label for="in">Celcius</label>
      égal
      <output id="out" for="in" name="temp_out"><?php echo $tempf;?></output>
      <label for="out">fahrenheit</label>
      <button type="submit" name="action" value="convertir">Convertir</button>
    </form>
  </body>
</html>
<!--question 3: il y a la valeur à convertir et le valeur de l'action du bouton-->
<!--question 4: car il faut mettre : php echo $tempc;-->