function startGame(gameName) {
    switch (gameName) {
      case 'Mini-Jeu 1':
        window.location.href = '../view/minijeuelectrique.html';
        break;
      case 'Mini-Jeu 2':
        window.location.href = '../view/minijeuelectrique2.html';
        break;
      
      // Ajoutez d'autres cas pour chaque mini-jeu
      default:
        console.log("Mini-jeu non reconnu");
    }
  }
  