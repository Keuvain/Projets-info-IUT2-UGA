// hint.js
var book; // Déclaration de 'book' pour le rendre accessible en dehors des fonctions
var bookText; // Déclaration de 'bookText'
var hintCount = 0; // Initialisez le compteur d'indices utilisés à zéro
var isBookOpen = false; // Ajoutez une variable pour suivre l'état du livre (ouvert/fermé)

function createHintButton(advancedTexture) {
    var hintButton = BABYLON.GUI.Button.CreateSimpleButton("hintButton", "!");
    hintButton.width = "40px";
    hintButton.height = "40px";
    hintButton.color = "white";
    hintButton.cornerRadius = 20;
    hintButton.background = "black";
    hintButton.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_RIGHT;
    hintButton.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP;
    hintButton.top = "10px";
    hintButton.right = "10px";
    advancedTexture.addControl(hintButton);

    hintButton.onPointerUpObservable.add(function() {
        if (hintCount < 3) {
            isBookOpen = !isBookOpen;
            book.isVisible = isBookOpen;
            hintButton.textBlock.text = isBookOpen ? "X" : "!";
            
            if (isBookOpen) {
                hintCount++;
                bookText.text = "Indicateur 1 : Phénolphtaléine\nCherchez des indices autour d'un chiffre entre 8 et 9, et du concept de \"transition colorée\" pour découvrir quand la phénolphtaléine montre une légère teinte.\n\nIndicateur 2 : Bleu de Bromothymol\nExplorez les indices évoquant un chiffre entre 7 et 8, ainsi que la couleur associée au ciel par une journée ensoleillée pour comprendre quand le bleu de bromothymol réagit.\n\nIndicateur 3 : Rouge de Méthyle\nRecherchez des indices faisant référence à un chiffre entre 4 et 5, ainsi qu'à une couleur associée aux roses pour découvrir les conditions où le rouge de méthyle devient rouge.\n\nIndicateur 4 : Vert de Bromocrésol\nTrouvez des indices évoquant un chiffre entre 3 et 4, ainsi qu'une couleur rappelant le citron mûr pour comprendre quand le vert de bromocrésol change de couleur.(" + hintCount + "/3 utilisés)";
            }
        } else {
            if (isBookOpen) {
                // Fermez le livre si ouvert et informez l'utilisateur qu'il n'y a plus d'indices
                isBookOpen = false;
                book.isVisible = false;
                hintButton.textBlock.text = "!";
                alert("Vous avez déjà utilisé tous les indices disponibles.");
            } else {
                // Informez l'utilisateur qu'il n'y a plus d'indices sans ouvrir le livre
                alert("Vous avez déjà utilisé tous les indices disponibles.");
            }
        }
    });

    return hintButton;
};


function createHintBook(advancedTexture) {
    book = new BABYLON.GUI.Rectangle("hintBook"); // Utilisation de la variable déclarée en dehors
    book.width = "600px"; // Taille fixe en pixels
    book.height = "700px"; // Taille fixe en pixels
    book.cornerRadius = 10;
    book.color = "black";
    book.thickness = 4;
    book.background = "white";
    book.isVisible = false;
    advancedTexture.addControl(book);


    // Ajouter une image comme fond pour le livre d'indices
    var bookBackground = new BABYLON.GUI.Image("hintBookBg", "lab-scape/asset/images/hintbook.png");
    bookBackground.width = "400px";
    bookBackground.height = "300px";
    bookBackground.stretch = BABYLON.GUI.Image.STRETCH_FILL;
    book.addControl(bookBackground);

    // Ajouter du texte au livre
    bookText = new BABYLON.GUI.TextBlock();
    bookText.text = "VIndicateur 1 : Phénolphtaléine\nCherchez des indices autour d'un chiffre entre 8 et 9, et du concept de \"transition colorée\" pour découvrir quand la phénolphtaléine montre une légère teinte.\n\nIndicateur 2 : Bleu de Bromothymol\nExplorez les indices évoquant un chiffre entre 7 et 8, ainsi que la couleur associée au ciel par une journée ensoleillée pour comprendre quand le bleu de bromothymol réagit.\n\nIndicateur 3 : Rouge de Méthyle\nRecherchez des indices faisant référence à un chiffre entre 4 et 5, ainsi qu'à une couleur associée aux roses pour découvrir les conditions où le rouge de méthyle devient rouge.\n\nIndicateur 4 : Vert de Bromocrésol\nTrouvez des indices évoquant un chiffre entre 3 et 4, ainsi qu'une couleur rappelant le citron mûr pour comprendre quand le vert de bromocrésol change de couleur.";
    bookText.color = "black";
    bookText.textWrapping = true;
    bookText.width = "500px"; // Un peu moins que la largeur du livre pour la marge
    bookText.height = "650px"; // Un peu moins que la hauteur du livre pour la marge
    bookText.paddingTop = "5px";
    bookText.paddingLeft = "5px";
    bookText.textHorizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_LEFT;
    bookText.textVerticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP;
    book.addControl(bookText);

    return { book, bookText };
};

function resetHintCount() {
    hintCount = 0;
    bookText.text = "Indicateur 1 : Phénolphtaléine\nCherchez des indices autour d'un chiffre entre 8 et 9, et du concept de \"transition colorée\" pour découvrir quand la phénolphtaléine montre une légère teinte.\n\nIndicateur 2 : Bleu de Bromothymol\nExplorez les indices évoquant un chiffre entre 7 et 8, ainsi que la couleur associée au ciel par une journée ensoleillée pour comprendre quand le bleu de bromothymol réagit.\n\nIndicateur 3 : Rouge de Méthyle\nRecherchez des indices faisant référence à un chiffre entre 4 et 5, ainsi qu'à une couleur associée aux roses pour découvrir les conditions où le rouge de méthyle devient rouge.\n\nIndicateur 4 : Vert de Bromocrésol\nTrouvez des indices évoquant un chiffre entre 3 et 4, ainsi qu'une couleur rappelant le citron mûr pour comprendre quand le vert de bromocrésol change de couleur.\n\n(" + hintCount + "/3 utilisés)";
}


// Exporter les fonctions
export { createHintButton, createHintBook, resetHintCount };

