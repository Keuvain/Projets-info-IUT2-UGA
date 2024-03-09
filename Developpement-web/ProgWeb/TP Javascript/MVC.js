function lanceDe() {
    return Math.floor(Math.random() * 6) + 1;
  }
  
  // Vue
  let view = {
    button: document.getElementsByTagName("button")[0],
    resDe: document.getElementById("resDe")
  }
  
  // Contrôleur
  function auLance() {
    let result = lanceDe();
    view.resDe.textContent = result;
  }
  
  view.button.onclick = auLance;
