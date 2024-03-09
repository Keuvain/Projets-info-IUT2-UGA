// Définition des paramètres de la vue 
const view = {
    //  La zone de sortie
    output: document.getElementsByTagName("output")[0],
    button: document.getElementById("bjr"),
    input: document.getElementsByTagName("input")[0],

    // Fonction qui affiche une date dans la balise output
    show: function (out) {
        this.output.textContent = out;
    }
}
