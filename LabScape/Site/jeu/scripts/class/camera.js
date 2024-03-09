// Classe de la caméra 
class Camera {
    constructor(name, position, rotation) {
        this.name = name;
        this.position = position;
        this.rotation = rotation;
    }

    // Fonction pour générer une caméra libre
    loadFreeCamera(scene, canvas) {
        // Créer une caméra
        const camera = new BABYLON.FreeCamera(this.name, this.position, scene);
        // Cibler la caméra sur la scène
        camera.setTarget(BABYLON.Vector3.Zero());
        // Appliquer les collisions à la caméra
        camera.checkCollisions = true;
        // Appliquer ellipsoid pour les collisions
        camera.ellipsoid = new BABYLON.Vector3(1, 1, 1);
        // Appliquer les rotations à la caméra
        camera.rotation = this.rotation;
        // Appliquer les mouvements à la caméra
        camera.attachControl(canvas, true);
        // Retourner la caméra
        return camera;
    }

    // Fonction pour générer une caméra qui suit le personnage
    loadFollowCamera(scene, canvas, target) {
        // Créer une caméra
        const camera = new BABYLON.FollowCamera(this.name, this.position, scene, target);
        // Positionner la caméra
        camera.radius = 1; // Distance entre la caméra et le personnage
        camera.heightOffset = 2; // Hauteur de la caméra par rapport au personnage
        camera.rotationOffset = 180; // Rotation de la caméra par rapport au personnage
        camera.cameraAcceleration = 0.1; // Accélération de la caméra
        camera.maxCameraSpeed = 5; // Vitesse maximale de la caméra
        // Cibler la caméra sur le personnage
        camera.lockedTarget = target;
        // Appliquer les collisions à la caméra
        camera.checkCollisions = true;
        // Appliquer ellipsoid pour les collisions
        camera.ellipsoid = new BABYLON.Vector3(1, 1, 1);
        // Appliquer les rotations à la caméra
        camera.rotation = this.rotation;
        // Appliquer les mouvements à la caméra
        camera.attachControl(canvas, true);
        // Retourner la caméra
        return camera;
    }
}

// Exporter la classe
export { Camera };