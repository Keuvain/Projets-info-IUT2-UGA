// Classe du modèle 
class Time {
  timeZone;  // Chaine de la time zone
  date;      // Chaîne qui indique la date et l'heure à cette time zone

  // Constructeur
  constructor(timeZone = '', date = '') {
    this.timeZone = timeZone;
    this.date = date;
  }

  static read(timeZone, onAnswser) {
    // Demande au DAO une lecture
    const params = {
      'action': 'read',
      'timeZone': timeZone
    }
    dao.query(params,
      function (answer) {
        // 
        ///////////////////////////////////////////////////////
        // A COMPLETER
        ///////////////////////////////////////////////////////

        //  

      }
    );
  }
}
