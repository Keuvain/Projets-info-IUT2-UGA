import { Phenolphtaleine} from "./liquid/phenolphtaleine.js"
import { Bromothymol } from "./liquid/bromothymol.js";
import { MethylRed } from "./liquid/methylRed.js";
import { MethylOrange } from "./liquid/methylOrange.js";
import { Light } from "./light.js";
import { Ground } from "./ground.js";
import { Burette } from './burette.js';
import { Items } from "./items.js";
import { showName, showReturnMessage, returnToMainScene} from "../utilitaire.js";
import { Code } from "./code.js";

class Titrage {
    constructor() {
        this.centeredBecher = null;
        // Current liquid est un objet de type Liquid
        this.currentLiquid = new Phenolphtaleine();
        this.ph = 0;
        this.code = new Code();
    }

    getCurrentLiquid() {
        return this.currentLiquid;
    }

    // Fonction pour set le liquide actuel
    setCurrentLiquid(liquid) {
        this.currentLiquid = liquid;
    }

    moveToCenter(scene, hitboxName, mesh, listBurette) {
        // Supprimer le bécher actuel s'il existe
        if (this.centeredBecher) {
            this.centeredBecher.dispose();
        }

        // Supprimer le liquide actuel s'il existe
        console.log('Current liquid:', this.currentLiquid);
        if (this.currentLiquid && typeof this.currentLiquid.dispose === 'function') {
            console.log('Disposing current liquid');
            this.currentLiquid.dispose();
        } else {
            console.log('No current liquid to dispose');
        }

        // Cloner le bécher actuel et le placer au centre
        const centeredBecher = mesh.clone("centeredBecher");
        centeredBecher.position = new BABYLON.Vector3(0, 0, -3.5);
        this.centeredBecher = centeredBecher;

        // Générer le nouveau liquide en fonction de la hitbox cliquée
        let newLiquid;
        const liquidPosition = new BABYLON.Vector3(
            centeredBecher.position.x - 0.25, centeredBecher.position.y + 2, centeredBecher.position.z + 3.4
        );

        if (hitboxName === 'liquidPHitbox') {
            newLiquid = new Phenolphtaleine();
        } else if (hitboxName === 'liquidBHitbox') {
            newLiquid = new Bromothymol();
        } else if (hitboxName === 'liquidMRHitbox') {
            newLiquid = new MethylRed();
        } else if (hitboxName === 'liquidMOHitbox') {
            newLiquid = new MethylOrange();
        }
        // Si aucune hitbox n'est cliquée, ne rien faire
        if (!newLiquid) {
            return;
        }

        this.setCurrentLiquid(newLiquid); // Mise à jour du liquide actuel

        if (newLiquid) {
            
            newLiquid.generateLiquid(1, 2, 64, liquidPosition);
            newLiquid.reaction(scene, this.code);
            console.log("Current liquid:", this.currentLiquid);
            console.log("Ph du liquide:", this.currentLiquid.ph);
            this.showPhCounter();
        }

        return newLiquid;
    }

    launchMiniGame() {
        // Récupère canva
        const canvas = document.getElementById('renderCanvas');
        // Créer le moteur de rendu
        const engine = new BABYLON.Engine(canvas);

        // Arrêter la boucle de rendu de la scène principale
        engine.stopRenderLoop();
        // Créer une nouvelle scène pour le mini-jeu
        const miniGameScene = new BABYLON.Scene(engine);
        this.setupMiniGameScene(miniGameScene, canvas);

        // Passer la scène du mini-jeu au moteur de rendu
        engine.runRenderLoop(() => {
            miniGameScene.render();
        });

        window.addEventListener('keydown', (event) => {
            if (event.key === 'Escape') {
                console.log("Touche Échap pressée, tentative de masquer le compteur de pH");
                this.hidePhCounter();
                miniGameScene.dispose();
                returnToMainScene(engine, canvas);
            }
        });
    }

    // Fonction pour créer la scène du mini-jeu
    setupMiniGameScene(miniGameScene, canvas) {
        // Nettoyer la scène
        miniGameScene.clearColor = new BABYLON.Color3(1, 1, 1); // Couleur de fond = blanc

        /*----------------------------- Sol + caméra  -----------------------------*/
        // Créer un sol noir
        const ground = new Ground('ground', 10, 20, 4);
        ground.loadGround(miniGameScene);

        // Position de la caméra
        const cameraPosition = new BABYLON.Vector3(1, 20, 0);
        const cameraTarget = new BABYLON.Vector3(0, 0, 0);
        // Créer et configurer une caméra fixe pour le mini-jeu
        const camera = new BABYLON.FreeCamera("miniGameCamera", cameraPosition, miniGameScene);
        camera.setTarget(cameraTarget);
        camera.attachControl(canvas, false); // Retirer les contrôles du canvas
        // Caméra fixe sans rotation
        camera.inputs.clear();

                /*----------------------------- Phenolphtaleine  -----------------------------*/
        // Créer le becher de phénolphtaléine
        const becherPhénolphtaléine = new Items('becher', 4, 4, 4, new BABYLON.Vector3(2, 0, -8));
        becherPhénolphtaléine.loadItem(miniGameScene);
        becherPhénolphtaléine.generateHitBox(miniGameScene, 'liquidPHitbox', 1.5, 5, 1.5, new BABYLON.Vector3(1.9, 0, -5));
        becherPhénolphtaléine.interactWith(miniGameScene);

        // Créer le liquide de phénolphtaléine
        const phenolphtaleine = new Phenolphtaleine();
        // Position du liquide
        const phenolphtaleinePosition = new BABYLON.Vector3(
            becherPhénolphtaléine.position.x - 0.25, becherPhénolphtaléine.position.y + 2, becherPhénolphtaléine.position.z + 3.3);
        phenolphtaleine.generateLiquid(1, 2, 64, phenolphtaleinePosition);
        // Associer le liquide au bécher
        becherPhénolphtaléine.liquidMesh = phenolphtaleine;
        
        // Afficher le nom du liquide
        showName("Phénolphtaleine", "18%", "-13%", 14);

                /*----------------------------- Bromothymol  -----------------------------*/
        // Créer le becher de bromothymol
        const becherBromothymol = new Items('becher', 4, 4, 4, new BABYLON.Vector3(2, 0, -5));
        becherBromothymol.loadItem(miniGameScene);
        becherBromothymol.generateHitBox(miniGameScene, 'liquidBHitbox', 1.5, 5, 1.5, new BABYLON.Vector3(1.9, 0, -1.8));
        becherBromothymol.interactWith(miniGameScene);

        // Créer le liquide de bromothymol
        const bromothymol = new Bromothymol();
        const bromothymolPosition = new BABYLON.Vector3(
            becherBromothymol.position.x - 0.25, becherBromothymol.position.y + 2, becherBromothymol.position.z + 3.3);
        bromothymol.generateLiquid(1, 2, 64, bromothymolPosition);
        // Associer le liquide au bécher
        becherBromothymol.liquidMesh = bromothymol;

        // Afficher le nom du liquide
        showName("Bromothymol", "18%", "-4%", 14);

                /*----------------------------- Rouge de Methyl  -----------------------------*/
        // Créer le becher de rouge de methyl
        const becherMetylRed = new Items('becher', 4, 4, 4, new BABYLON.Vector3(2, 0, -2));
        becherMetylRed.loadItem(miniGameScene);
        becherMetylRed.generateHitBox(miniGameScene, 'liquidMRHitbox', 1.5, 5, 1.5, new BABYLON.Vector3(1.9, 0, 1.6));
        becherMetylRed.interactWith(miniGameScene);

        // Créer le liquide de rouge de methyl
        const methylRed = new MethylRed();
        const methylRedPosition = new BABYLON.Vector3(
            becherMetylRed.position.x - 0.25, becherMetylRed.position.y + 2, becherMetylRed.position.z + 3.5);
        methylRed.generateLiquid(1, 2, 64, methylRedPosition);
        // Associer le liquide au bécher
        becherMetylRed.liquidMesh = methylRed;

        // Afficher le nom du liquide
        showName("Rouge de Methyle", "18%", "5%", 14);

                /*----------------------------- Orange de Methyl  -----------------------------*/
        // Créer le becher de orange de methyl
        const becherMetylOrange = new Items('becher', 4, 4, 4, new BABYLON.Vector3(2, 0, 1));
        becherMetylOrange.loadItem(miniGameScene);
        becherMetylOrange.generateHitBox(miniGameScene, 'liquidMOHitbox', 1.5, 5, 1.5, new BABYLON.Vector3(1.9, 0, 4.7));
        becherMetylOrange.interactWith(miniGameScene);

        // Créer le liquide de orange de methyl
        const methylOrange = new MethylOrange();
        const methylOrangePosition = new BABYLON.Vector3(
            becherMetylOrange.position.x - 0.25, becherMetylOrange.position.y + 2, becherMetylOrange.position.z + 3.5);
        methylOrange.generateLiquid(1, 2, 64, methylOrangePosition);
        // Associer le liquide au bécher
        becherMetylOrange.liquidMesh = methylOrange;

        // Afficher le nom du liquide
        showName("Orange de Methyle", "18%", "15%", 14);


                /*----------------------------- Burettes  -----------------------------*/
        
        // Création de la burette acide et base en utilisant la classe Burette
        const listBurette = this.generateListBurette();
        for (let i = 0; i < listBurette.length; i++) {
            listBurette[i].createBurette();
            listBurette[i].addGraduations();
            this.interactWithBurette(miniGameScene, listBurette[i]);
        }

        // Afficher le nom pour chaque burette
        showName("Chloridryque", "-10%", "-20%", 14);
        showName("Acetic", "-10%", "-14%", 14);
        showName("Carbonate", "-10%", "-8%", 14);
        showName("Sulfurique", "-10%", "8%", 14);
        showName("Bicarmonate", "-10%", "14%", 14);
        showName("NaOH", "-10%", "20%", 14);

        // Ajouter un éclairage directionnel pour mieux éclairer la burette
        const directionalLight = new Light('directionalLight', new BABYLON.Vector3(0, -2, 0), 0.5, 'DirectionalLight');
        directionalLight.generateDirectionalLight(miniGameScene);
 

        // Afficher un message pour revenir à la scène principale
        showReturnMessage();
    }

    interactWithBurette(scene, burette, hitboxName) {
        if (!burette.buretteMesh) {
            console.error('Burette mesh not defined');
            return;
        }
    
        // Supprimer l'ancien gestionnaire d'actions s'il existe
        if (burette.buretteMesh.actionManager) {
            burette.buretteMesh.actionManager.dispose();
        }
    
        // Cliquer sur la burette
        burette.buretteMesh.actionManager = new BABYLON.ActionManager(scene);
        burette.buretteMesh.actionManager.registerAction(
            new BABYLON.ExecuteCodeAction(BABYLON.ActionManager.OnPickTrigger, () => {
                this.showPhCounter();
                console.log('Before pH change - Current liquid:', this.currentLiquid);
                this.currentLiquid.changePh(burette.name, scene);
                console.log('After pH change - Current liquid:', this.currentLiquid);
                console.log('New pH:', this.currentLiquid.ph);
                this.currentLiquid.reaction();
                
            })
        );
    }
    


    generateListBurette(miniGameScene) {
        const buretteMaterial = new BABYLON.StandardMaterial("buretteMaterial", miniGameScene);
        buretteMaterial.alpha = 0.7;
        buretteMaterial.diffuseColor = new BABYLON.Color3(0.8, 0.8, 0.8);
        // Création de la burette acide et base en utilisant la classe Burette
        const buretteChloridryque = new Burette(miniGameScene, "Chloridryque", buretteMaterial, new BABYLON.Vector3(-3, 0, -7));
        const buretteAcetic = new Burette(miniGameScene, "Acetic", buretteMaterial, new BABYLON.Vector3(-3, 0, -5));
        const buretteCarbonate = new Burette(miniGameScene, "Carbonate", buretteMaterial, new BABYLON.Vector3(-3, 0, -3));
        const buretteSulfurique = new Burette(miniGameScene, "Sulfurique", buretteMaterial, new BABYLON.Vector3(-3, 0, 3));
        const buretteHydroxydeSodium = new Burette(miniGameScene, "Hydroxide", buretteMaterial, new BABYLON.Vector3(-3, 0, 7));
        const buretteBicarbonateSodium = new Burette(miniGameScene, "Bicarmonate", buretteMaterial, new BABYLON.Vector3(-3, 0, 5));
        const listBurette = [
            buretteChloridryque ,buretteAcetic, buretteCarbonate, buretteSulfurique, buretteHydroxydeSodium, 
            buretteBicarbonateSodium];
        return listBurette;
    }

    // Fonction pour afficher un compteur de pH (div)
    showPhCounter() {
        // Créer le compteur de pH s'il n'existe pas
        if (!this.phCounter) {
            this.phCounter = document.createElement('div');
            this.phCounter.id = 'phCounter';
            this.phCounter.style.position = 'absolute';
            // Position du compteur (en %)
            this.phCounter.style.top = '10%';
            this.phCounter.style.left = '42%';
            this.phCounter.style.width = '15%';
            this.phCounter.style.height = '5%';
            this.phCounter.style.backgroundColor = 'white';
            this.phCounter.style.borderRadius = '10px';
            this.phCounter.style.border = '1px solid black';
            this.phCounter.style.padding = '10px';
            this.phCounter.style.textAlign = 'center';
            this.phCounter.style.fontSize = '20px';
            this.phCounter.style.fontWeight = 'bold';
            this.phCounter.style.zIndex = '10';
            document.body.appendChild(this.phCounter);
        }
        // Mettre à jour le contenu du compteur avec la nouvelle valeur de pH
        this.phCounter.innerHTML = 'pH: ' + this.currentLiquid.ph;
        console.log('Updated pH counter :' + this.currentLiquid.ph);
    }
    
     // Fonction pour cacher le compteur de pH
    hidePhCounter() {
        const phCounter = document.getElementById('phCounter');
        if (phCounter) {
            phCounter.remove();
        }
    }

    
}

// Exporter la classe
export { Titrage };
