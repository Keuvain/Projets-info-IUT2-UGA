// Classe du personnage
class Character {
    constructor(name, canvas) {
        this.name = name;
        this.canvas = canvas;
        // Ajouter une propriété de classe pour stocker le personnage (par défaut ne contient rien)
        this.characterMesh = null;
        // Ajouter une propriété pour la vitesse du personnage
        this.heroSpeed = 0.05;
        // Ajouter une propriété pour la vitesse du personnage en marche arrière
        this.heroSpeedBackwards = 0.05;
        // Ajouter une propriété pour la vitesse de rotation du personnage
        this.heroRotationSpeed = 0.2;
        // Ajouter une propriété pour stocker les touches du clavier
        this.inputMap = {};
        // Ajouter une propriété pour gérer les animations
        this.animating = false;
        // Ajouter une propriété pour la position précédente du personnage
        this.previousPosition = new BABYLON.Vector3(0, 0, 3);
    }

    // Fonction pour générer le personnage
    loadCharacter = async (scene) =>{
        // Créer un personnage
        const model = await BABYLON.SceneLoader.ImportMeshAsync(
            '', 
            'https://assets.babylonjs.com/meshes/',
            'HVGirl.glb',
            scene);
        // Récupérer le mesh du personnage
        const character = model.meshes[0];
        // Positionner le personnage
        character.position = new BABYLON.Vector3(0, 0, 3);
        // Dimensionner le personnage
        character.scaling = new BABYLON.Vector3(0.05, 0.05, 0.05);
        // Appliquer les collisions au personnage
        character.checkCollisions = true;
        // Appliquer ellipsoid pour les collisions
        character.ellipsoid = new BABYLON.Vector3(0.1, 0.1, 0.5);
        character.ellipsoidOffset = new BABYLON.Vector3(0, 1, 0);
        // Mettre de la gravité sur le personnage
        character.applyGravity = true;

        // Ajouter une propriété pour la position précédente du personnage
        character.previousPosition = character.position.clone();

        // Ajouter une propriété de classe pour stocker le personnage
        this.characterMesh = character;

        this.sambaAnim = scene.getAnimationGroupByName("Samba");
        this.walkAnim = scene.getAnimationGroupByName("Walking");
        this.walkBackAnim = scene.getAnimationGroupByName("WalkingBack");
    }

    // Fonction pour déplacer le personnage avec les touches ZQSD
    moveWithKeyboard(scene) {
        scene.actionManager = new BABYLON.ActionManager(scene);
        scene.actionManager.registerAction(
            new BABYLON.ExecuteCodeAction(BABYLON.ActionManager.OnKeyDownTrigger, (evt) => {
                this.inputMap[evt.sourceEvent.key] = evt.sourceEvent.type == "keydown";
            }),
        );
        scene.actionManager.registerAction(
            new BABYLON.ExecuteCodeAction(BABYLON.ActionManager.OnKeyUpTrigger, (evt) => {
                this.inputMap[evt.sourceEvent.key] = evt.sourceEvent.type == "keydown";
            }),
        );

        if (this.inputMap["z"]) {
            this.characterMesh.moveWithCollisions(this.characterMesh.forward.scaleInPlace(this.heroSpeed));
            this.animating = true;
            // Play walk animation
            this.walkAnim.start(true, 0.2, this.walkAnim.from, this.walkAnim.to, false);
        } else if (this.inputMap["s"]) {
            this.characterMesh.moveWithCollisions(this.characterMesh.forward.scaleInPlace(-this.heroSpeedBackwards));
            this.animating = true;
            // Play walkBack animation
            this.walkBackAnim.start(true, 0.2, this.walkBackAnim.from, this.walkBackAnim.to, false);
        } else if (this.inputMap["q"]) {
            this.characterMesh.rotate(BABYLON.Vector3.Up(), -this.heroRotationSpeed);
            this.animating = true;
        } else if (this.inputMap["d"]) {
            this.characterMesh.rotate(BABYLON.Vector3.Up(), this.heroRotationSpeed);
            this.animating = true;
        } else if (this.inputMap["b"]) {
            this.animating = true;
            // Play samba animation
            this.sambaAnim.start(true, 1.0, this.sambaAnim.from, this.sambaAnim.to, false);
        } else {
            // Stop all animations if no key is pressed
            this.stopAllAnimations();
            this.animating = false;
        }
    }

    // Fonction pour arrêter toutes les animations
    stopAllAnimations() {
        if (this.sambaAnim) {
            this.sambaAnim.stop();
        }
        if (this.walkAnim) {
            this.walkAnim.stop();
        }
        if (this.walkBackAnim) {
            this.walkBackAnim.stop();
        }
    }
}

// Exporter la classe
export { Character };