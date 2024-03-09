<?php 
    const ACADEMIC_PATTERN = "as";
    const ACADEMIES = array(
        'ac-aix-marseille' => 'Académie d\'Aix-Marseille',
        'ac-amiens' => 'Académie d\'Amiens',
        'ac-besancon' => 'Académie de Besançon',
        'ac-bordeaux' => 'Académie de Bordeaux',
        'ac-caen' => 'Académie de Caen',
        'ac-clermont-ferrand' => 'Académie de Clermont-Ferrand',
        'ac-corse' => 'Académie de Corse',
        'ac-dijon' => 'Académie de Dijon',
        'ac-grenoble' => 'Académie de Grenoble',
        'ac-guadeloupe' => 'Académie de Guadeloupe',
        'ac-guyane' => 'Académie de Guyane',
        'ac-lille' => 'Académie de Lille',
        'ac-limoges' => 'Académie de Limoges',
        'ac-lyon' => 'Académie de Lyon',
        'ac-martinique' => 'Académie de Martinique',
        'ac-mayotte' => 'Académie de Mayotte',
        'ac-metz-nancy' => 'Académie de Metz-Nancy',
        'ac-montpellier' => 'Académie de Montpellier',
        'ac-nantes' => 'Académie de Nantes',
        'ac-nice' => 'Académie de Nice',
        'ac-normandie' => 'Académie de Normandie',
        'ac-nouvelle-caledonie' => 'Académie de la Nouvelle-Calédonie',
        'ac-orleans-tours' => 'Académie d\'Orléans-Tours',
        'ac-paris' => 'Académie de Paris',
        'ac-poitiers' => 'Académie de Poitiers',
        'ac-reims' => 'Académie de Reims',
        'ac-rennes' => 'Académie de Rennes',
        'ac-reunion' => 'Académie de la Réunion',
        'ac-strasbourg' => 'Académie de Strasbourg',
        'ac-toulouse' => 'Académie de Toulouse',
        'ac-wallis-futuna' => 'Académie de Wallis et Futuna'
    );

/**
 * Fonction permettant de logger des valeurs dans la session:
 * data: Array sous forme clé -> valeur où clé est le nom de la colonne du tableau
 */

 function MailProf(string $email) : bool{
    if (!filter_var($email, FILTER_VALIDATE_EMAIL)){
        return false;
    }

    $site = explode("@", $email)[1];
    foreach (ACADEMIES as $key => $val){
        if (strpos($site, $key) !== false){
            return true;
        }
    }

    return false;
}
function log_session(array $data) : bool{

    $status = session_status();
    if ($status == PHP_SESSION_DISABLED){
        throw new Exception ("Sessions are disabled !");
    }
    //Je crois il l'évite si session none ?
    session_start();

    foreach($data as $key => $val){
        $_SESSION[$key] = $val;
    }
    return true;
    
}

?>