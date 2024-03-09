// Action de dempander l'heure au serveur 
function onSaluer() {
    const nom = view.input.value;
    const queryString = 'nom = ' + encodeURIComponent('nom');
    xhttp = new XMLHttpRequest();
    xhttp.onload = function () {
        view.show(this.responseText);
    }
    xhttp.open("GET", "/api/hello.api.php?" + queryString);
                xhttp.send();
}

// Attache le controleur au bouton
view.button.onclick = onSaluer;