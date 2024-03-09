// Classe pour le plafond de la scène
class Ceiling {
    constructor(name, width, height, subdivisions) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.subdivisions = subdivisions;
    }

    // Fonction pour générer un plafond
    loadCeiling(name) {
        // Créer un plafond
        const ceiling = BABYLON.MeshBuilder.CreateGround(name, { width: this.width, height: this.height, subdivisions: this.subdivisions });
        // Appliquer une couleur au plafond
        ceiling.material = new BABYLON.StandardMaterial('ceilingMat');
        // Appliquer une couleur au plafond (blanc)
        ceiling.material.diffuseColor = new BABYLON.Color3(1, 1, 1);
    }

}

// Exporter la classe
export { Ceiling };