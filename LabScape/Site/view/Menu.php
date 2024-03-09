<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="author" content="" />
    <link href="../view/style/Menu.css" rel="stylesheet" type="text/css" />
    <title>LAB'SCAPE-menu</title>
    <script src="../view/script/scriptMenu.js" defer></script>
</head>

<body>
    <?php
    // Démarrez la session
    session_start();
    ?>

    <div class="background-container">
        <img src="../view/assets/Menu.png" alt="Menu" class="menu-image">
    </div>

    <a href="../controler/mode.ctrl.php" class="com">COMMENCER</a>
    <a href="PARAMETRES.html" class="cre">CRÉDIT</a>
    <a href="../controler/parametre.ctrl.php" class="para">⚙️</a>
    <a href="../controler/regles.ctrl.php" class="reg">REGLES</a>
    <button onclick="toggleAudio()" class="bo">🔇</button>
    <a href="PARAMETRES.html" class="sc">SCORE</a>
    <audio id="audioPlayer" controls>
        <source src="../view/song/imperfect.mp3" type="audio/mp3">
    </audio>

    
</body>

</html>


