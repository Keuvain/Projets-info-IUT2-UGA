document.addEventListener("DOMContentLoaded", function () {
    var brightnessRange = document.getElementById("brightnessRange");
    var brightnessValue = document.getElementById("brightnessValue");

    // Appliquer la luminosité enregistrée
    if(localStorage.getItem("brightness")) {
        applyBrightness(localStorage.getItem("brightness"));
    }

    brightnessRange.addEventListener("input", function () {
        var brightnessPercentage = brightnessRange.value;
        localStorage.setItem("brightness", brightnessPercentage); // Enregistrer la valeur
        applyBrightness(brightnessPercentage);
    });

    var sonRange = document.getElementById("sonRange");
    var myAudio = document.getElementById("myAudio");

    sonRange.addEventListener("input", function () {
        var volumePercentage = sonRange.value;
        var volume = volumePercentage / 200;
        myAudio.volume = volume;
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

function changerPage() {
    window.location.href = "../view/Menu.php";
}
