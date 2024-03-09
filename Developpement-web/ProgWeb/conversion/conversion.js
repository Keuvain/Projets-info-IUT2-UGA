//////////////// Partie Modèle //////////////////////
function convertCelsiusToFahrenheit(celsius) {
    return 1.8 * celsius + 32;
}

//////////////// Partie Contrôleur /////////////////
function calcul() {
    let result = convertCelsiusToFahrenheit();
    view.res.textContent = result;
  }
  
  view.button.onclick = calcul;

//////////////// Partie Vue  ///////////////////////
let view = {
    inputCelsius: document.querySelector("input[type='number']"),
    outputFahrenheit: document.querySelector("output"),
    convertButton: document.querySelector("button")
};
