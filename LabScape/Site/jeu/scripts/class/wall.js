// Classe des murs de la scene
class Wall {
    constructor(name, width, height, depth, position) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.position = position;
    }

    loadWall(name) {
        // Créer un mur
        const wall = BABYLON.MeshBuilder.CreateBox(name, {width: this.width, height: this.height, depth: this.depth});
        // Positionner le mur
        wall.position = this.position;
        // Appliquer les collisions au mur
        wall.checkCollisions = true;
        // Appliquer ellipsoid pour les collisions
        wall.ellipsoid = new BABYLON.Vector3(1, 1, 1);
        // Créer une texture pour le mur
        const wallTexture = new BABYLON.StandardMaterial('wallTexture');
        wallTexture.diffuseTexture = new BABYLON.Texture('../asset/Textures/wall/brick_wall_02_ao_2k.jpg');
        // Modifier les paramètres de répétition de texture pour réduire sa taille apparente
        wallTexture.diffuseTexture.uScale = wallTexture.diffuseTexture.vScale = 3;
        // Appliquer la texture au mur
        wall.material = wallTexture;
    }
}

// Exporter la classe
export { Wall };