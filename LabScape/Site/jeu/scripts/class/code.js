import Swal from '../../node_modules/sweetalert2/src/sweetalert2.js';
// Classe représentant un code composé de 4 chiffres
class Code {
    constructor() {
        this.value = this.generateCode(5969);
    }

    // Méthode pour générer un code aléatoire de 4 chiffres
    generateRandomCode() {
        const code = Math.floor(1000 + Math.random() * 9000);
        return code.toString();
    }

    // Méthode pour génére le code avec une valeur spécifique
    generateCode(value) {
        const code = value;
        return code.toString();
    }

    getCode() {
        return this.value;
    }

    // Méthode pour afficher la boîte de dialogue et récupérer le code entré par l'utilisateur
    async promptForCode() {
        const { value: enteredCode } = await Swal.fire({
            title: 'Veuillez saisir le code pour ouvrir la porte',
            input: 'text',
            inputAttributes: {
                maxLength: 4,
                autocapitalize: 'off',
                autocorrect: 'off',
            },
            showCancelButton: true,
            confirmButtonText: 'Valider',
            cancelButtonText: 'Annuler',
            inputValidator: (value) => {
                if (!value) {
                    return 'Le code ne peut pas être vide';
                }
                if (!/^\d{4}$/.test(value)) {
                    return 'Le code doit être composé de 4 chiffres';
                }
            },
        });

        return enteredCode;
    }

    // Méthode pour vérifier si le code entré est correct
    checkEnteredCode(enteredCode) {
        return enteredCode === this.getCode();
    }

    startCode() {
        // Utiliser une fonction asynchrone pour pouvoir utiliser await
        const runCodeVerification = async () => {
            // Attendre que la promesse soit résolue
            const enteredCode = await this.promptForCode();
    
            if (enteredCode !== null) {
                if (this.checkEnteredCode(enteredCode)) {
                    Swal.fire('Code correct. La porte est déverrouillée !', '', 'success');
                } else {
                    Swal.fire('Code incorrect. Accès refusé.', '', 'error');
                }
            } else {
                Swal.fire('Opération annulée. Veuillez saisir le code à nouveau.', '', 'info');
            }
        };
    
        // Appeler la fonction asynchrone
        runCodeVerification();
    }
    
}


// Exporter la classe
export { Code };