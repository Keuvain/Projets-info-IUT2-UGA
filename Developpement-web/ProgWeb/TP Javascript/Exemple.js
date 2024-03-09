do {
    nombre = prompt("Entrez un nombre :");
    if (isNaN(nombre)) {
        alert("Ce n'est pas un nombre valide. Veuillez entrer un nombre.");
    }
} while (isNaN(nombre));

console.log("Table de multiplication pour le nombre: " + nombre);
    for (let i = 1; i <= 10; i++) {
        console.log(nombre + " x " + i + " = " + nombre * i);
    }

