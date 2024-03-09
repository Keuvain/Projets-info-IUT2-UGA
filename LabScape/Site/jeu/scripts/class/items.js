    import { Code } from "./code.js";
    import { openImageOverlay } from "../utilitaire.js";
    import { Titrage } from "./titrage.js";



    // Classe des items de la scene
    class Items {
        // Constructeur de la classe
        constructor(name, width, height, depth, position) {
            this.name = name;
            this.width = width;
            this.height = height;
            this.depth = depth;
            this.position = position;
            this.hitbox = null;
            this.mesh = null;
            this.titrage = new Titrage();
            this.code = new Code();
        }

        // Fonction pour load un item
        loadItem(scene) {
            // Liste des extensions possibles
            const possibleExtensions = ['.babylon', '.obj']; // Ajoutez d'autres extensions au besoin
            // Fonction récursive pour tenter le chargement avec différentes extensions
            const tryLoadItemWithExtension = (extensions) => {
                if (extensions.length === 0) {
                    // Aucune extension n'a réussi, retourner une promesse rejetée
                    return Promise.reject(new Error('Impossible de charger le fichier.'));
                }
                // Prendre la première extension de la liste
                const currentExtension = extensions.shift();
                // Construire le chemin du fichier avec l'extension actuelle
                const filePath = this.name + currentExtension;
                // Tenter de charger le fichier
                return BABYLON.SceneLoader.ImportMeshAsync('', '../asset/Textures/' + this.name + '/', filePath, scene)
                    .then((result) => {
                        // Succès, retourner l'instance du mesh
                        // Si l'item existe déjà dans la scène, le supprimer
                        if (this.mesh) {
                            this.mesh.dispose();
                        }
                        const item = result.meshes[0];
                        item.scaling = new BABYLON.Vector3(this.width, this.height, this.depth);
                        item.position = this.position;
                        item.checkCollisions = true;
                        item.ellipsoid = new BABYLON.Vector3(1, 1, 1);
                        this.mesh = item;

                        return item;
                    })
                    .catch((error) => {
                        // Échec, réessayer avec les extensions restantes
                        return tryLoadItemWithExtension(extensions);
                    });
            };
            // Appeler la fonction récursive avec la liste des extensions possibles
            return tryLoadItemWithExtension([...possibleExtensions]);
        }

        // Fonction pour créer un item
        generateItem(scene, image) {
            // Créer un item
            const item = BABYLON.MeshBuilder.CreateBox(this.name, { width: this.width, height: this.height, depth: this.depth });
            // Positionner l'item
            item.position = this.position;
            // Rotationner l'item seulement si c'est un tableau
            if (this.name === 'board') {
                item.rotation.y = Math.PI / 2;
            }
            // Appliquer les collisions à l'item
            item.checkCollisions = true;
            // Appliquer ellipsoid pour les collisions
            item.ellipsoid = new BABYLON.Vector3(0.5, 1.3, 0.5);
            item.ellipsoidOffset = new BABYLON.Vector3(0, 1.3, 0);
            // Créer une texture pour l'item
            const itemTexture = new BABYLON.StandardMaterial(this.name + 'Texture');
            // Chemin d'accès à l'image
            const filePath = '../../asset/images/' + image;
            itemTexture.diffuseTexture = new BABYLON.Texture(filePath);
            item.material = itemTexture;
            return item;

        }

        /**
         * Interaction avec l'item (ouvrir une image, lancer un mini-jeu, etc.)
         * @param {BABYLON.scene} scene 
         * @param {string} imageSrc 
         * @param {BABYLON.engine} engine 
         * @param {BABYLON.canvas} canvas 
         * @param {Items} door
         * @returns 
         */
        interactWith(scene, imageSrc='', engine='', canvas='', door){
            // Vérifier si la hitbox existe
            if (!this.hitbox) {
                console.error('Hitbox non définie pour ' + this.name);
                return;
            }
            

            // Ajouter un gestionnaire d'événements de clic à la hitbox
            this.hitbox.actionManager = new BABYLON.ActionManager(scene);

            // Définir l'événement de clic sur la hitbox
            this.hitbox.actionManager.registerAction(new BABYLON.ExecuteCodeAction(
                BABYLON.ActionManager.OnPickUpTrigger, // Événement de clic
                () => {
                    switch (this.hitbox.name) {
                        case 'doorHitbox':
                            this.code.startCode();
                            this.titrage.code = this.code;
                            break;
                        case 'becherHitbox':
                            // Mettre la fonction qui permet de lancer le mini-jeu
                            this.titrage.launchMiniGame();
                            break;
                        case 'liquidPHitbox':
                            this.titrage.moveToCenter(scene, 'liquidPHitbox', this.mesh, this.titrage.generateListBurette());
                            break;
                        case 'liquidBHitbox':
                            this.titrage.moveToCenter(scene, 'liquidBHitbox', this.mesh, this.titrage.generateListBurette());
                            break;
                        case 'liquidMRHitbox':
                            this.titrage.moveToCenter(scene, 'liquidMRHitbox', this.mesh, this.titrage.generateListBurette());
                            break;
                        case 'liquidMOHitbox':
                            this.titrage.moveToCenter(scene, 'liquidMOHitbox', this.mesh, this.titrage.generateListBurette());
                            break;
                        default:
                            // Ouvrir l'image associée lorsque la hitbox est cliquée
                            openImageOverlay(imageSrc);
                            break;
                    }
                }
            ));
        }
    
        // Fonction pour générer la hitbox de l'item
        generateHitBox(scene, name, width, height, depth, position) {
            // Vérifier si l'item existe déjà dans la scène
            const existingItem = this.mesh;
            
            if (existingItem) {
                // L'item existe déjà, supprimer l'ancien
                existingItem.dispose();
            }
            // Créer une hitbox
            const hitbox = BABYLON.MeshBuilder.CreateBox(name, { width: width, height: height, depth: depth });
            // Positionner la hitbox
            hitbox.position = position;
            // Appliquer les collisions à la hitbox
            hitbox.checkCollisions = true;
            // Appliquer ellipsoid pour les collisions
            hitbox.ellipsoid = new BABYLON.Vector3(0.2, 1, 0.2);
            hitbox.ellipsoidOffset = new BABYLON.Vector3(0, 1.3, 0);
            // Rendre la hitbox invisible
            hitbox.isVisible = true;   
            hitbox.visibility = 0;    // 0 = invisible, 1 = visible
            // Retourner la hitbox
            this.hitbox = hitbox;
            return hitbox;
        }
    }


    // Exporter la classe
    export { Items };