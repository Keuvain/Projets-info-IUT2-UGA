// Classe des liquides
class Liquid {
    // Modéliser les différents liquides avec différentes classes

    constructor(name, item, color, ph, scene) {
        this.name = name;
        this.item = item;
        this.color = color;
        this.ph = ph;
        this.scene = scene;
        this.liquidMesh = null;
    }

    getColor() {
        return this.color;
    }

    // Fonction pour générer un liquide dans l'item
    generateLiquid(diameter, height, tessellation, position) { 
        // Créer un liquide
        const liquid = BABYLON.MeshBuilder.CreateCylinder(this.name, 
            { diameter: diameter, height: height, tessellation: tessellation }, this.scene);
        // Positionner le liquide
        liquid.position = position;
        liquid.position.y = 0.25;
        // Appliquer les collisions au liquide
        liquid.checkCollisions = true;
        // Appliquer la couleur au liquide
        liquid.material = new BABYLON.StandardMaterial('liquidMat');
        liquid.material.diffuseColor = new BABYLON.Color3(this.color[0], this.color[1], this.color[2]);
        this.liquidMesh = liquid;
        // Retourner le liquide
        return liquid;
    }

    dispose() {
        // Logique de suppression du liquide
        console.log('Disposing liquid');
        if (this.liquidMesh) {
            this.liquidMesh.dispose();
        }
    }

    // Méthode abstraite pour interagir avec le liquide
    changePh() {
        console.log('Abstract method changePh() called');
    }

    // Méthode pour changer la couleur du liquide après sa création
    changeLiquidColor(newColor) {
        // Disposer de l'ancien liquide
        if (this.liquidMesh) {
            this.liquidMesh.dispose();
        }

        // Mettre à jour la couleur du liquide
        this.color = newColor;

        // Assurez-vous de fournir les paramètres appropriés (diameter, height, tessellation, position)
        this.generateLiquid(1, 2, 64, new BABYLON.Vector3(-0.27, 0, -0.10));
    }
}

// Exporter la classe
export { Liquid };
