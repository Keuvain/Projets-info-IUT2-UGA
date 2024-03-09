// Le Data Access Object 
// Il représente l'acces aux données par l'API
// params : une liste (objet) de nom et valeurs de paramètres à rechercher
// onAnswser : callback qui est déclanchée à la réception du JSON et qui passe en paramètre l'objet issu du JSON
const dao =
{
  query: function (params, onAnswer) {
    // Transforme les paramètres en query sting
    const queryString = new URLSearchParams();
    for (let param in params) {
      queryString.append(param, params[param]);
    }
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
      //Transforme la chaine JSON en objet
      onAnswer(JSON.parse(this.responseText));
    }
    xhttp.open("GET", "/api/time.api.php?" + queryString);
    xhttp.send();
  }
}