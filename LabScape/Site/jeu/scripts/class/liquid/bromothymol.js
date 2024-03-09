import { Liquid } from './liquid.js';

class Bromothymol extends Liquid {
    constructor() {
        super('Bromothymol', 'becher', [1, 1, 0.5], 2);
    }

    // Fonction pour augmenter le ph du liquide (6 acides différents)
    changePh(nameAcide) {
        if (nameAcide === 'Hydroxide') {
            this.ph += 11; // Ajout d'une valeur cohérente pour l'hydroxyde de sodium.
        } else if (nameAcide === 'Acetic') {
            this.ph -= 0.5; // Réduction du pH pour l'acide acétique.
        } else if (nameAcide === 'Carbonate') {
            this.ph += 1; // Ajout d'une valeur cohérente pour le carbonate.
        } else if (nameAcide === 'Chloridryque') {
            this.ph -= 0.5; // Réduction du pH pour l'acide chlorhydrique.
        } else if (nameAcide === 'Sulfurique') {
            this.ph -= 1; // Réduction du pH pour l'acide sulfurique.
        } else if (nameAcide === 'Bicarbonate') {
            this.ph += 3; // Ajout d'une valeur cohérente pour le bicarbonate.
        }
    
        // Vérifie que le ph reste dans une plage valide (entre 0 et 14).
        this.ph = Math.max(0, Math.min(this.ph, 14));
    }

    reaction() {
        const PinkColor = [1, 0, 1];
        const beigeColor = [1, 1, 0.5];
        const YellowColor = [1, 1, 0];
        const GreenColor = [0, 1, 0];
        const BlueColor = [0, 0, 1];
        // Condition de changement de couleur
        if (this.ph === 1) {
            this.changeLiquidColor(PinkColor);
            console.log("La couleur est rose");
        } else if (this.ph > 1 && this.ph <= 2) {
            this.changeLiquidColor(beigeColor);
            console.log("La couleur est beige");
        } else if (this.ph > 2 && this.ph <= 6) {
            this.changeLiquidColor(YellowColor);
            console.log("La couleur est jaune");
        } else if (this.ph > 6 && this.ph <= 7.6) {
            this.changeLiquidColor(GreenColor);
            console.log("La couleur est verte");
            // Mettre un son pour la réussite
        } else if (this.ph > 7.6) {
            this.changeLiquidColor(BlueColor);
            console.log("La couleur est bleue");
        }
    }

    /**
     * 
     * @param {Code} code 
     */
    solution(code) {
        // Afficher une partie du code de la solution
        const codeDigits = code.getCodeDigits();
        // Afficher 

    }
}

// Exporter la classe
export { Bromothymol };