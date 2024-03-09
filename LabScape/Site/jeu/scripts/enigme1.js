// Import la classe Items
import { Items } from './class/items.js';
import { Wall } from './class/wall.js';
import { Ground } from './class/ground.js';
import { Room } from './class/room.js';
import { Character } from './class/character.js';
import { Camera } from './class/camera.js';
import { Light } from './class/light.js';
import { createHintBook, createHintButton } from './class/hint.js';
import { Timer } from './class/timer.js';
import { Code } from './class/code.js';


/*********************************************************************************************************
 *                                         VARIABLES                                                   *
 * *******************************************************************************************************/

// Récupère canva
const canvas = document.getElementById('renderCanvas');
// Créer le moteur de rendu
const engine = new BABYLON.Engine(canvas);

let timerElement = new Timer();
let character = new Character('character', canvas);
/*----------------------------- ROOM -----------------------------*/
let ground = new Ground('ground', 10, 10, 4);

let wall1 = new Wall('wall1', 10, 2, 0.2, new BABYLON.Vector3(0, 1, -5));
let wall2 = new Wall('wall2', 10, 2, 0.2, new BABYLON.Vector3(0, 1, 5));
let wall3 = new Wall('wall3', 0.2, 2, 10, new BABYLON.Vector3(-5, 1, 0));
let wall4 = new Wall('wall4', 0.2, 2, 10, new BABYLON.Vector3(5, 1, 0));
const listWall = [wall1, wall2, wall3, wall4];

let table = new Items('table', 0.45, 0.45, 0.45, new BABYLON.Vector3(0, 0, 0));
let table2 = new Items('table2', 0.45, 0.45, 0.45, new BABYLON.Vector3(4, 0, 0));
let rocket = new Items('rocket', 0.1, 0.1, 0.1, new BABYLON.Vector3(-3.3, 0.7, -4.3));
let plant = new Items('plant', 1, 1, 1, new BABYLON.Vector3(-4.1, 0, 4.5));
let paint = new Items('paint', 0.3, 0.3, 0.3, new BABYLON.Vector3(2, 1, 4.87));
let globe = new Items('globe', 0.5, 0.5, 0.5, new BABYLON.Vector3(0, 1.35, 0));
let chest = new Items('chest', 0.2, 0.2, 0.2, new BABYLON.Vector3(4.5, 0, 4.3));
let desk = new Items('desk', 1, 1, 1, new BABYLON.Vector3(-3, 0, -4.8));
let box = new Items('box', 0.3, 0.3, 0.3, new BABYLON.Vector3(-4.5, 0, 0));
let box2 = new Items('box', 0.3, 0.3, 0.3, new BABYLON.Vector3(-4.5, 0.5, 0));
let box3 = new Items('box', 0.3, 0.3, 0.3, new BABYLON.Vector3(-4.5, 0, 0.6));
let bookshelf = new Items('bookshelf', 0.8, 0.8, 0.8, new BABYLON.Vector3(4, 0, -4.8));
let bookcase = new Items('bookcase', 1, 1, 1, new BABYLON.Vector3(4.5, 0, -3));
let door = new Items('door', 1, 2, 0.1, new BABYLON.Vector3(-2, 1, 4.9));
let board = new Items('board', 2, 1, 0.1, new BABYLON.Vector3(4.9, 1.4, 0.5));
let periodique = new Items('periodique', 2, 1, 0.1, new BABYLON.Vector3(0, 1, 4.9));
let becher = new Items('becher', 0.5, 0.5, 0.5, new BABYLON.Vector3(3.5, 0.8, -0.5));
const listItems = [table, table2, rocket, plant, paint, globe, chest, desk, box, box2, box3, bookshelf, bookcase, door, board, 
    periodique, becher];

// Créer la salle
const room = new Room('titrage', ground, listWall, listItems);

/*----------------------------- CAMERA -----------------------------*/
let mainCamera = new Camera('camera', new BABYLON.Vector3(0, 2.5, -4), new BABYLON.Vector3(0, 0, 0));
let light = new Light('light', new BABYLON.Vector3(0, 1, 0), 1, 'HemisphericLight');

/*********************************************************************************************************
 *                                         SCENE                                                      *
 * *******************************************************************************************************/

// Créer la scène
const createScene = function () {
    const scene = new BABYLON.Scene(engine);
    // Activer les collisions dans la scène
    scene.collisionsEnabled = true;
    // Créer la salle
    room.loadRoom();
    // Créer le personnage
    character.loadCharacter(scene).then(() => {
        console.log(character.characterMesh);
    });
    // Créer la caméra
    mainCamera.loadFreeCamera(scene, canvas);
    // Créer une lumière hemisphérique
    light.generateLight(scene);

    // Créer le code 
    const code = new Code();
    console.log("Le code est : " + code);
    
    /*----------------------------- Hitbox -----------------------------*/
    plant.generateHitBox(scene, "plantHitbox", 0.6, 2, 0.6, plant.position);
    periodique.generateHitBox(scene, "periodiqueHitbox", 2, 1, 0.3, periodique.position);
    globe.generateHitBox(scene, "globeHitbox", 0.5, 0.6, 1, new BABYLON.Vector3(-0.1, 1, 0));
    rocket.generateHitBox(scene, "rocketHitbox", 0.5, 2, 0.5, new BABYLON.Vector3(-3.3, 0.7, -4.3));
    chest.generateHitBox(scene, "chestHitbox", 0.5, 1, 1.5, new BABYLON.Vector3(4.5, 0.5, 4.3));
    box.generateHitBox(scene, "boxHitbox", 1, 2, 1, new BABYLON.Vector3(-4.5, 0, 0.3));
    board.generateHitBox(scene, "boardHitbox", 0.5, 1, 2.3, board.position);
    bookcase.generateHitBox(scene, "bookcaseHitbox", 0.3, 3, 1.7, bookcase.position);
    table.generateHitBox(scene, "tableHitbox", 1, 2, 1, table.position);
    table2.generateHitBox(scene, "table2Hitbox", 1, 1.8, 1.5, table2.position);
    becher.generateHitBox(scene, "becherHitbox", 0.3, 0.3, 0.3, new BABYLON.Vector3(3.5, 1, 0));
    door.generateHitBox(scene, "doorHitbox", 1, 2, 0.3, new BABYLON.Vector3(-2, 1, 4.9));

    /* ---------------------------- Interaction ---------------------------- */
    plant.interactWith(scene, "../asset/images/indice/plantIndice.jpg", code);    
    periodique.interactWith(scene, "../asset/images/periodique.png", code);  
    globe.interactWith(scene, "../asset/images/indice/globeIndice.png", code);    
    rocket.interactWith(scene, "../asset/images/indice/rocketIndice.jpg", code);
    chest.interactWith(scene, "../asset/images/indice/chestIndice.png", code);
    box.interactWith(scene, "../asset/images/indice/boxIndice.png", code);
    board.interactWith(scene, "../asset/images/indice/boardIndice.jpg", code);
    bookcase.interactWith(scene, "../asset/images/indice/bookcaseIndice.png", code);
    becher.interactWith(scene, engine, canvas, code, door);  
    door.interactWith(scene, engine, canvas, code);

    var advancedTexture = BABYLON.GUI.AdvancedDynamicTexture.CreateFullscreenUI("UI");
    createHintButton(advancedTexture);
    createHintBook(advancedTexture);

    return scene;
}



/*********************************************************************************************************
 *                                          FONCTIONS                                                   *
 *********************************************************************************************************/

// Appel de la fonction createScene
const scene = createScene();

// Boucle de rendu
engine.runRenderLoop(function () {
    // Mettre à jour le timer
    timerElement.updateTimer();

    // Déplacer le personnage avec les touches ZQSD
    character.moveWithKeyboard(scene);
    scene.render();
});

/*********************************************************************************************************
 *                                          EVENEMENTS                                                  *
 *********************************************************************************************************/

// Gestion de la taille de la fenêtre
window.addEventListener('resize', function () {
    engine.resize();
});

// Export la scène
export { createScene };