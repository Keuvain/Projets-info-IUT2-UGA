<?php
require_once(dirname(__FILE__) . '/model/change.php');
$change = new Change("data/exchangeRate.csv");

$sourceCurrency = isset($_GET['source']) ? $_GET['source'] : '';
$targetCurrency = isset($_GET['target']) ? $_GET['target'] : '';
$amount = isset($_GET['montant']) ? $_GET['montant'] : '';
$result = '';

if ($sourceCurrency && $targetCurrency && $amount) {
    try {
        $result = $change->change($sourceCurrency, $targetCurrency, (float)$amount);
    } catch (Exception $e) {
        $result = $e->getMessage();
    }
}

$currencies = $change->getDevises();
?>

<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
  <meta charset="utf-8">
  <link rel="stylesheet" href="design/w3.css">
  <link rel="stylesheet" href="design/style.css">
  <title>Outil de conversion de devises</title>
</head>
<body>

  <header class="w3-container w3-teal">
    <h1>Outil de conversion de devises</h1>
  </header>

  <main class="w3-container">
    <p>
      Boursomachin met à votre disposition ce convertisseur de devises, qui vous permet de convertir des monnaies instantanément et gratuitement.
      Vous pouvez convertir entre elles les devises les plus populaires comme l'Euro (EUR), le Dollar US (USD), le Yen japonais (JPY), la Livre Sterling (GBP).
    </p>
    <p>
      Usage : vous entrez dans le convertisseur le montant que vous souhaitez convertir, indiquez la devise d'origine et la devise qui vous intéresse. Et vous obtenez instantanément le montant dans la devise souhaitée, avec le taux de change entre les 2 monnaies.
    </p>

    <h2>Convertisseur</h2>
    <div>
      <form id="convertisseur" action="indexV1.php" method="get" class="w3-panel w3-card-2">
        <input id="montant" type="number" name="montant" placeholder="Montant" value="<?php echo $amount; ?>" >
        <select id="source" name="source">
          <?php foreach ($currencies as $currency) { ?>
            <option value="<?php echo $currency; ?>" <?php if ($sourceCurrency === $currency) echo 'selected'; ?>><?php echo $currency; ?></option>
          <?php } ?>
        </select>
        <img src="design/arrow-icon.png" alt="">
        <select id="target" name="target">
          <?php foreach ($currencies as $currency) { ?>
            <option value="<?php echo $currency; ?>" <?php if ($targetCurrency === $currency) echo 'selected'; ?>><?php echo $currency; ?></option>
          <?php } ?>
        </select>
        <button name="button" type="submit" class="w3-button w3-teal">Convertir</button>
      </form>

      <output for="montant source cible" form="convertisseur" name="target_amount" class="w3-container w3-teal w3-xlarge">
        <?php if ($result !== '') { ?>
          Le change de <?php echo number_format($amount, 2); ?> <?php echo $sourceCurrency; ?> en <?php echo $targetCurrency; ?> fait <?php echo number_format($result, 2); ?>
        <?php } ?>
      </output>
    </div>
  </main>

</body>
</html>


