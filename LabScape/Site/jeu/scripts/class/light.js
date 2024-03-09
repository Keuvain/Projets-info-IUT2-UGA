// Classe des lumières de la scene
class Light {
    // Constructeur de la classe
    constructor(name, position, intensity, type) {
        this.name = name;
        this.position = position;
        this.intensity = intensity;
        this.type = type;
    }

    // Fonction pour créer une lumière
    generateLight(scene) {
        // Créer une lumière
        const light = new BABYLON.HemisphericLight(this.name, this.position, scene);
        light.intensity = this.intensity;
        light.type = this.type;

        return light;
    }

    // Fonction pour créer une lumière directionnelle
    generateDirectionalLight(scene) {
        // Créer une lumière
        const light = new BABYLON.DirectionalLight(this.name, this.position, scene);
        light.intensity = this.intensity;
        light.type = this.type;

        return light;
    }
}

// Exporter la classe
export { Light };