<!DOCTYPE html>
<html>
<head>
    <link href="../view/style/Mode.css" rel="stylesheet" type="text/css" />
    <title>LAB'SCAPE-mode</title>
    <script src="../view/script/script.js" defer></script>

    
</head>
<body>
    <img src="../view/assets/Mode.png" alt="Paramètres" id="fond">
    <h1 class="ph">Physique</h1><h1 class="ch">Chimie</h1>
    <a href="../view/Menu.php "class="back">BACK</a>
    <button onclick="gererSon()" class="bo">🔊</button>
    <button onclick="lancerPage()" class="st">START</button>
    <p id="mode" onclick="changerTexte()">HISTOIRE</p>
    <button onclick="changerTexte()" class="charger">
    <img src="../view/assets/1f504.png" alt="charger" id="charger"></button>
</body>
</html>
