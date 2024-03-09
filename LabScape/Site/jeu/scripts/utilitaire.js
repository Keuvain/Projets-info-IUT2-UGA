import { createScene } from "./enigme1.js";

/**
 * Affiche un texte stylisé sur une interface graphique en utilisant Babylon.js.
 * @param {string} name - Le texte à afficher.
 * @param {number} top - Position verticale du texte.
 * @param {number} left - Position horizontale du texte.
 * @function
 */
function showName(name, top, left, fontSize) {
    // Créer une interface graphique Babylon.js en plein écran
    let advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI");

    // Créer un bloc de texte pour afficher le nom
    let text1 = new BABYLON.GUI.TextBlock();
    text1.text = name; // Définir le texte
    text1.color = "black"; // Définir la couleur du texte
    text1.fontSize = fontSize; // Définir la taille de la police
    text1.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_LEFT; // Alignement horizontal à gauche
    text1.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP; // Alignement vertical en haut

    // Positionner le texte sous son "bécher" en utilisant les paramètres top et left
    text1.top = top;
    text1.left = left;
    

    // Ajouter le bloc de texte à l'interface graphique
    advancedTexture.addControl(text1); 
}

/**
 * Affiche un message de retour sur une interface graphique en utilisant Babylon.js.
 * @function
 */
function showReturnMessage() {
    // Créer une interface graphique Babylon.js en plein écran
    let advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI");

    // Créer un bloc de texte pour afficher le nom
    let text1 = new BABYLON.GUI.TextBlock();
    text1.text = "Appuyez sur Échap pour revenir dans la salle";
    text1.color = "black";
    text1.fontSize = 24;
    text1.horizontalAlignment = BABYLON.GUI.Control.HORIZONTAL_ALIGNMENT_LEFT;
    text1.verticalAlignment = BABYLON.GUI.Control.VERTICAL_ALIGNMENT_TOP;

    // Positionner le texte sous son "bécher" en utilisant les paramètres top et left
    text1.top = "-48%";
    text1.left = "-30%";

    // Ajouter le bloc de texte à l'interface graphique
    advancedTexture.addControl(text1); 
}

/**
 * Ouvre un overlay avec une image et une croix de fermeture.
 * @param {string} imageSrc - La source de l'image à afficher dans l'overlay.
 * @function
 */
function openImageOverlay(imageSrc) {
    // Créer un élément div pour l'overlay
    const overlay = document.createElement('div');
    overlay.style.position = 'fixed';
    overlay.style.top = '0';
    overlay.style.left = '0';
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.backgroundColor = 'rgba(0, 0, 0, 0.7)';
    overlay.style.display = 'flex';
    overlay.style.alignItems = 'center';
    overlay.style.justifyContent = 'center';
    overlay.style.zIndex = '1000';

    // Créer un élément div pour contenir l'image et appliquer un style
    const imageContainer = document.createElement('div');
    imageContainer.style.width = '80%';  // Ajustez la largeur selon vos besoins
    imageContainer.style.height = '80%'; // Ajustez la hauteur selon vos besoins

    // Créer un élément img pour afficher l'image
    const imageElement = document.createElement('img');
    imageElement.src = imageSrc;
    imageElement.style.width = '90%';
    imageElement.style.height = '90%';

    // Créer un conteneur pour la croix et positionner la croix en haut à droite
    const closeButtonContainer = document.createElement('div');
    closeButtonContainer.style.position = 'absolute';
    closeButtonContainer.style.top = '10px';  // Ajustez la position de la croix selon vos besoins
    closeButtonContainer.style.right = '10px'; // Ajustez la position de la croix selon vos besoins
    closeButtonContainer.style.cursor = 'pointer';

    // Créer un élément div pour la croix de fermeture
    const closeButton = document.createElement('div');
    closeButton.innerHTML = 'X';
    closeButton.style.fontSize = '20px';
    closeButton.style.color = '#fff';

    // Ajouter un gestionnaire d'événements pour fermer l'overlay en cliquant sur la croix
    closeButton.addEventListener('click', function () {
        document.body.removeChild(overlay);
    });

    // Ajouter la croix de fermeture au conteneur
    closeButtonContainer.appendChild(closeButton);

    // Ajouter l'élément img dans le conteneur d'image
    imageContainer.appendChild(imageElement);

    // Ajouter le conteneur d'image et le conteneur de croix à l'overlay
    overlay.appendChild(imageContainer);
    overlay.appendChild(closeButtonContainer);

    // Ajouter l'overlay à la page
    document.body.appendChild(overlay);

    // Pouvoir zoomer sur l'image avec la molette de la souris
    imageElement.addEventListener('wheel', function (event) {
        // Empêcher le comportement par défaut
        event.preventDefault();

        // Récupérer la valeur du zoom
        const scale = 1 + event.deltaY * 0.01;

        // Définir un seuil minimum pour le zoom
        const minScale = 0.5;

        // Appliquer le zoom avec la vérification du seuil minimum
        imageElement.style.transform = 'scale(' + Math.max(minScale, scale) + ')';
    });
}

/**
 * Renvoie à la scène principale 
 * @param {BABYLON.scene} scene 
 * @param {BABYLON.canvas} canvas 
 * @param {Camera} camera 
 */
function returnToMainScene(scene, canvas, camera) {
    // Créer un gestionnaire d'événements pour revenir à la scène principale
    const returnToMainScene = new BABYLON.ExecuteCodeAction(BABYLON.ActionManager.OnKeyUpTrigger, (evt) => {
        if (evt.sourceEvent.key === 'Escape') {
            // Détacher la caméra du canvas
            camera.detachControl(canvas);
            // Détacher le contrôle de la caméra
            camera.inputs.clear();
            // Libérer les ressources de la scène
            disposeScene(scene);
            // Revenir à la scène principale
            scene.activeCamera = scene.getCameraByName('mainCamera');
            // Réattacher le contrôle de la caméra
            scene.activeCamera.attachControl(canvas, true);
            scene = createScene();
        }
    });
}

/**
 * Libérer les ressources de la scène
 * @param {BABYLON.scene} scene 
 */
function disposeScene(scene) {
    // Libérer les ressources de la scène
    scene.meshes.forEach(mesh => {
        mesh.material.dispose(); // Libérer le matériau du mesh
        if (mesh.material.albedoTexture) {
            mesh.material.albedoTexture.dispose(); // Libérer la texture du matériau
        }
        mesh.dispose(); // Libérer le mesh lui-même
    });
}



// Exporter la fonction
export { showName, showReturnMessage, openImageOverlay, returnToMainScene, disposeScene};