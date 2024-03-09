<html>
<head>
  <title>Bricomachin</title>
  <meta charset="UTF-8"/>
  <meta http-equiv="content-type" content="text/html;" />
  <meta name="author" content="Jean-Pierre Chevallet" />
  <link rel="stylesheet" type="text/css" href="../view/design/style.css">
</head>

<body>
  <!--  -->
  <header>
    <h1>Bricomachin, le bricolage malin </h1>
    <p><img src="../view/design/pub.png"/></p>
  </header>
  <nav>
    <!-- Bouton de retour au début de la liste -->
    <a href="afficherArticles.ctrl.php"><img src="../view/design/home.png"/></a>
    <form action="choisirCategorie.ctrl.php" method="get">
      <button type="submit">
        Choisir une catégorie
      </button>
    </form>
    <!-- Bouton de retour à la page précédente -->
    <a href="afficherArticles.ctrl.php?page=<?=$pagePrec?>&idCategorie=<?=$idCategorie?>">&lt; </a>
    <!-- Informations sur le No de page -->
    <?= $page ?>
    <!-- Bouton pour passer à la page suivante -->
    <a href="afficherArticles.ctrl.php?page=<?=$pageSuiv?>&idCategorie=<?=$idCategorie?>">></a>
  </nav>


  <h2><?=$nomCategorie?></h2>
  <?php
  // 
  ///////////////////////////////////////////////////////
  //  PLACER ICI L'AFFICHAGE DES ARTICLES
  ///////////////////////////////////////////////
  // Affichage des articles 
?>
<footer>
  <p>Site fictif, issus de données réelles du Web</p>
</footer>
</body>
</html>
