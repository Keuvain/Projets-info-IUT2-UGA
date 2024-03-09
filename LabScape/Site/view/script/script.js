var sonCoupe = false;

function gererSon() {
    var elementsAudio = document.getElementsByTagName('audio');

    for (var i = 0; i < elementsAudio.length; i++) {
        if (sonCoupe) {
            elementsAudio[i].volume = 1;
        } else {
            elementsAudio[i].volume = 0;
        }
    }

    sonCoupe = !sonCoupe;
}

function changerTexte() {
    var elementTexte = document.getElementById("mode");
    if (elementTexte.innerHTML === "HISTOIRE") {
        elementTexte.innerHTML = "ARCADE";
    } else {
        elementTexte.innerHTML = "HISTOIRE";
    }
}

function lancerPage() {
    var elementTexte = document.getElementById("mode");
    if (elementTexte.innerHTML === "HISTOIRE") {
        window.location.href = "../controler/egnime.ctrl.php";  
    }
    else if (elementTexte.innerHTML === "ARCADE") {
        window.location.href = "../controler/ChoixMiniJeu.ctrl.php";  
    }
}
document.addEventListener("DOMContentLoaded", function () {
    var brightnessRange = document.getElementById("brightnessRange");

    // Appliquer la luminosité enregistrée
    if(localStorage.getItem("brightness")) {
        applyBrightness(localStorage.getItem("brightness"));
        brightnessRange.value = localStorage.getItem("brightness");
    }

    brightnessRange.addEventListener("input", function () {
        var brightnessPercentage = brightnessRange.value;
        localStorage.setItem("brightness", brightnessPercentage); // Enregistrer la valeur
        applyBrightness(brightnessPercentage);
    });
});

function applyBrightness(brightnessPercentage) {
    var brightness = brightnessPercentage / 100;
    if (brightnessPercentage < 10) {
        brightness = 0.1;
    } else if (brightnessPercentage > 170) {
        brightness = 1.5;
    }
    document.body.style.filter = "brightness(" + brightness + ")";
}

