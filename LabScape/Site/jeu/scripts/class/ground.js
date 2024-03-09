// Classe pour le sol de la scène
class Ground {
    constructor(name, width, height, subdivisions) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.subdivisions = subdivisions;
    }

    loadGround(name) {
        // Créer un sol 
        const ground = BABYLON.MeshBuilder.CreateGround(name, { width: this.width, height: this.height, subdivisions: this.subdivisions });
        ground.material = new BABYLON.StandardMaterial('groundMat');
        ground.material.diffuseColor = new BABYLON.Color3(1, 1, 1);
    

        // Créer une texture pour le sol
        const groundTexture = new BABYLON.StandardMaterial('groundTexture');
        groundTexture.diffuseTexture = new BABYLON.Texture('../asset/Textures/ground/concrete_floor_worn_001_ao_2k.jpg'); 
        groundTexture.displacementTexture = new BABYLON.Texture('../asset/Textures/ground/concrete_floor_worn_001_disp_2k.jpg');
        groundTexture.normalTexture = new BABYLON.Texture('../asset/Textures/ground/concrete_floor_worn_001_nor_gl_2k.jpg');
        groundTexture.roughnessTexture = new BABYLON.Texture('../asset/Textures/ground/concrete_floor_worn_001_rough_2k.jpg');

        // Modifier les paramètres de répétition de texture pour réduire sa taille apparente
        groundTexture.diffuseTexture.uScale = groundTexture.diffuseTexture.vScale = 6;

        // Appliquer la texture au sol
        ground.material = groundTexture;
    }
}


// Exporter la classe
export { Ground };