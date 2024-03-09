class Burette {
    constructor(scene, name, material, position, height = 10, diameter = 1, tessellation = 24, becher) {
        this.scene = scene;
        this.name = name;
        this.material = material;
        this.position = position;
        this.height = height;
        this.diameter = diameter;
        this.tessellation = tessellation;
        this.becher = becher;
    }

    
    createBurette() {
        this.buretteMesh = BABYLON.MeshBuilder.CreateCylinder(this.name, {
            diameterTop: this.diameter,
            diameterBottom: this.diameter,
            height: this.height,
            tessellation: this.tessellation
        }, this.scene);
        this.buretteMesh.material = this.material;
        this.buretteMesh.position = this.position;

        this.addGraduations();
    }

    addGraduations() {
        for (let i = 0; i <= this.height; i++) {
            let graduation = BABYLON.MeshBuilder.CreateBox("graduation", { width: 0.05, height: 0.1, depth: 1 }, this.scene);
            graduation.parent = this.buretteMesh;
            graduation.position.y = i - this.height / 2;
            graduation.position.z = 0.5;
        }
    }
}

// Exporter la classe
export { Burette };
