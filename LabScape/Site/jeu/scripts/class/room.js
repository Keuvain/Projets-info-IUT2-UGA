// Classe des salles
import { Ground } from './ground.js';

class Room {
    // Attributs de la classe listeItems
    constructor(name, ground, listWall, listItems) {
        this.name = name;
        this.ground = ground;
        this.listWall = listWall;
        this.listItems = listItems;
    }

    // Fonction pour générer une salle
    loadRoom(scene) {
        // Créer un sol 
        this.ground.loadGround();

        // Créer les murs
        this.listWall.forEach(wall => {
            wall.loadWall();
        });

        // Créer les items
        this.listItems.forEach(item => {
            switch (item.name) {
                case 'door':
                    item.generateItem(scene, "door.jpg");
                    break;
                case 'board':
                    item.generateItem(scene, "board.png");
                    break;
                case 'periodique':
                    item.generateItem(scene, "periodique.png");
                    break;
                default:
                    item.loadItem(scene).then(({ item, hitbox}) => {
                        console.log('Item et hitbox chargés avec succès', item,     hitbox);
                    })
                    .catch((error) => {
                        console.error('Erreur lors du chargement de l\'item', error);
                    });
                    break;
            }
        });
    }

    // Fonction pour récupérer la hitbox d'un item par son nom
    getHitboxByName(itemName) {
        const foundItem = this.listItems.find(item => item.name === itemName);
        return foundItem ? foundItem.hitbox : null;
    }

    

}

// Exporter la classe
export { Room };