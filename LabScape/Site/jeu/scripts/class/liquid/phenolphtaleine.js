import { Liquid } from './liquid.js';
import { showName } from '../../utilitaire.js';

class Phenolphtaleine extends Liquid {
    constructor() {
        super('phenolphtaleine', 'becher', [1, 1, 1], 0);
    }

    // Fonction pour augmenter le ph du liquide (6 acides différents)
    changePh(nameAcide, scene) {
        if (nameAcide === 'Hydroxide') {
            this.ph += 11; // Ajout d'une valeur cohérente pour l'hydroxyde de sodium.
        } else if (nameAcide === 'Acetic') {
            this.ph += 0.5; // Augmentation du pH pour l'acide acétique.
        } else if (nameAcide === 'Carbonate') {
            this.ph += 1.5; // Ajout d'une valeur cohérente pour le carbonate.
        } else if (nameAcide === 'Chloridryque') {
            this.ph -= 0.5; // Réduction du pH pour l'acide chlorhydrique.
        } else if (nameAcide === 'Sulfurique') {
            this.ph -= 2; // Réduction du pH pour l'acide sulfurique.
        } else if (nameAcide === 'Bicarmonate') {
            this.ph += 3; // Ajout d'une valeur cohérente pour le bicarbonate.
        }
        // Vérifie que le ph reste dans une plage valide (entre 0 et 14).
        this.ph = Math.max(0, Math.min(this.ph, 14));
        this.reaction(scene);

    }

    reaction(scene, code) {
        const OrangeColor = [1, 0.5, 0];
        const PinkColor = [1, 0, 1];
        const WhiteColor = [1, 1, 1];
        const music = new BABYLON.Sound("Music", "../asset/sound/succes.mp3", scene, null, { loop: false, autoplay: true });

        // Condition de changement de couleur
        if (this.ph >= 1 && this.ph <= 2) {
            // Couleur orange
            this.changeLiquidColor(OrangeColor);
            console.log("La couleur est orange");
            showName("9", "40%", "1%", 46);
            music.play();

        } else if (this.ph > 2 && this.ph <= 8.2) {
            // Incolore 
            this.changeLiquidColor(WhiteColor);
            console.log("La couleur est incolore");
            showName("6", "40%", "10%", 46);
            music.play();

        } else if (this.ph > 8.2 && this.ph <= 12) {
            // Couleur rose 
            this.changeLiquidColor(PinkColor);
            console.log("La couleur est rose");
            // Mettre un son pour la réussite
            music.play();
            // Afficher le code
            showName("5", "40%", "-10%", 46); 
        } else if (this.ph > 12){
            // Incolore 
            this.changeLiquidColor(WhiteColor);
            music.play();

            console.log("La couleur est incolore");
            showName("9", "40%", "15%", 46);

        }
    }

}

// Exporter la classe
export { Phenolphtaleine };