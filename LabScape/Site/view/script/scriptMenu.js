var audioPlayer = document.getElementById("audioPlayer");
var boutonPlayer = document.getElementsByClassName("bo")[0];

function toggleAudio() {
    if (audioPlayer.paused) {
        audioPlayer.play();
        boutonPlayer.innerText = "🔊";
    } else {
        audioPlayer.pause();
        boutonPlayer.innerText = "🔇";
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
function quitterPage() {
    window.close();
}
