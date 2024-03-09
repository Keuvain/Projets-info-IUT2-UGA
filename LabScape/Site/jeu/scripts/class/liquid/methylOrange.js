import { Liquid } from './liquid.js';

class MethylOrange extends Liquid {
    constructor() {
        super('MethylOrange', 'becher', [1, 0.5, 0], 6.2);
    }

    // Fonction pour augmenter le ph du liquide (6 acides différents)
    changePh(nameAcide) {
        if (nameAcide === 'Hydroxide') {
            this.ph += 4; // Ajout d'une valeur cohérente pour l'hydroxyde de sodium.
        } else if (nameAcide === 'Acetic') {
            this.ph += 0.2; // Augmentation du pH pour l'acide acétique.
        } else if (nameAcide === 'Carbonate') {
            this.ph += 0.5; // Ajout d'une valeur cohérente pour le carbonate.
        } else if (nameAcide === 'Chloridryque') {
            this.ph -= 0.2; // Réduction du pH pour l'acide chlorhydrique.
        } else if (nameAcide === 'Sulfurique') {
            this.ph -= 0.8; // Réduction du pH pour l'acide sulfurique.
        } else if (nameAcide === 'Bicarbonate') {
            this.ph += 1.5; // Ajout d'une valeur cohérente pour le bicarbonate.
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
            // Mettre un son pour la réussite
            const music = new BABYLON.Sound("Music", "../asset/sound/succes.mp3", scene, null, { loop: false, autoplay: true });
            music.play();
        } else if (this.ph >= 4.4 && this.ph <= 6.2) {
            this.changeLiquidColor(OrangeColor);
            console.log("La couleur est orange");
        } else if (this.ph > 6.2) {
            this.changeLiquidColor(YellowColor);
            console.log("La couleur est jaune");
        }
    }
}

// Exporter la classe
export { MethylOrange };