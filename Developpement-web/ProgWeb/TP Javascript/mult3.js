function afficherTableMultiplication(nombre) {
    let resultat = "Table de " + nombre + "\n";
    for (let i = 1; i <= 10; i++) {
      resultat += (nombre + " x " + i + " = " + nombre * i + "\n");
    }
    return resultat;
  }
  
  let nombre = prompt("Entrez un nombre pour voir sa table de multiplication:");
  let tableau = afficherTableMultiplication(nombre);
  
  document.getElementById("resultat").textContent = tableau;
  