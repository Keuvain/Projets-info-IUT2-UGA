<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
  <meta charset="utf-8">
  <title>&#x1F399; Mon jukebox static</title>
  <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
  <header>
    <h1>Playing : <?=$_GET['music']?></h1>
  </header>
  <main>
    <nav><a href="staticJukebox.html"> &#8668; Retour</a></nav>
    <section>
        <figure>
            <img src="data/<?=$_GET['music'] ?>.jpeg" />
            <audio src="data/<?= $_GET['music']?>.mp3" controls="" autoplay=""></audio>
        </figure>
    </section>
  </main>
  <footer>
  </footer>
</body>
</html>