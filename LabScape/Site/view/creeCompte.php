<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="../view/style/style.css">
    <title>Labscape- Créer un compte</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <script src="../view/script/scriptCompte.js"></script>
</head>
<body>
    <?php include(__DIR__.'/header.php'); ?>

    <main class="connection">
        <form onsubmit="return validateForm()" action="../controler/compte.ctrl.php" method="post">
            <div>
                <p>
                    <label for="nom">Nom</label>
                    <input id="nom" type="text" name="nom" value="" required>
                </p>
                <span id="nomError" class="error"></span>
            </div>
            <div>
                <p>
                    <label for="prenom">Prénom</label>
                    <input id="prenom" type="text" name="prenom" value="" required>
                </p>
                <span id="prenomError" class="error"></span>
            </div>
            <div>
                <p>
                    <label for="login">Identifiant</label>
                    <input id="login" type="text" name="login" value="<?=$loginError?>" required>
                </p>
                <span id="loginError" class="error"></span>
                <p> <?=$loginError?></p>
            </div>
            <div>
                <p>
                    <label for="password">Mot de passe</label>
                    <input id="password" type="password" name="password" value="" required>
                    <button type="button" id="togglePassword" onclick="togglePasswordVisibility()"><i class="material-symbols-outlined" style="font-size:20px;">visibility</i></button>
                </p>
                <span id="passwordError" class="error"></span>
            </div>
            <div>
                <p>
                    <label for="mail">E-mail</label>
                    <input id="mail" type="email" name="mail" value="" required>
                </p>
                <span id="mailError" class="error"></span>
            </div>
            <div>
                <p>
                    <input type="checkbox" id="termsCheckbox" name="termsCheckbox" required>
                    <label for="termsCheckbox">J'accepte les conditions d'utilisation et </label>
                </p>
            </div>
            <button class="button2" type="submit" name="create">Créer mon compte</button>
        </form>
        <p>Vous avez déjà un compte ? <a href="../controler/login.ctrl.php">Connectez-vous !</a></p>
    </main>
    <footer>
        <p class="footer-link" onclick="window.location.href='../controler/condition.ctrl.php'">Conditions d'utilisation</p>
    </footer>
</body>
</html>
