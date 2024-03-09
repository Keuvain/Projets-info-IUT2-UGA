import { Liquid } from './liquid.js';

class MethylRed extends Liquid {
    constructor() {
        super('MethylRed', 'becher', [1, 0, 0], 1);
    }

    // Fonction pour augmenter le pH du liquide avec rouge de méthyle (pH initial = 1)
    changePh(nameAcide) {
        if (nameAcide === 'Hydroxide') {
            this.ph += 12; // Ajout d'une valeur cohérente pour l'hydroxyde de sodium.
        } else if (nameAcide === 'Acetic') {
            this.ph += 1; // Augmentation du pH pour l'acide acétique.
        } else if (nameAcide === 'Carbonate') {
            this.ph += 2; // Ajout d'une valeur cohérente pour le carbonate.
        } else if (nameAcide === 'Chloridryque') {
            this.ph -= 1; // Réduction du pH pour l'acide chlorhydrique.
        } else if (nameAcide === 'Sulfurique') {
            this.ph -= 3; // Réduction du pH pour l'acide sulfurique.
        } else if (nameAcide === 'Bicarbonate') {
            this.ph += 5; // Ajout d'une valeur cohérente pour le bicarbonate.
        }

        // Vérifie que le ph reste dans une plage valide (entre 0 et 14).
        this.ph = Math.max(0, Math.min(this.ph, 14));
    }


    reaction() {
        const RedColor = [1, 0, 0];
        const OrangeColor = [1, 0.5, 0];
        const YellowColor = [1, 1, 0];
        // Condition de changement de couleur
        if (this.ph < 4.4) {
            this.changeLiquidColor(RedColor);
            console.log("La couleur est rouge");
        } else if (this.ph >= 4.4 && this.ph <= 6.2) {
            this.changeLiquidColor(OrangeColor);
            console.log("La couleur est orange");
        } else if (this.ph > 6.2) {
            this.changeLiquidColor(YellowColor);
            console.log("La couleur est jaune");
            // Mettre un son pour la réussite
            const music = new BABYLON.Sound("Music", "../asset/sound/succes.mp3", scene, null, { loop: false, autoplay: true });
            music.play();
        }
    }
}

// Exporter la classe
export { MethylRed };