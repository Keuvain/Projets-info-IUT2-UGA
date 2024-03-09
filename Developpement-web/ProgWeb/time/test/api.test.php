<?php
// Fonctions d'aide
require_once(__DIR__ . '/../test/helper.php');

// Lance un test de l'api
// $data : un tableaux de noms, valeurs qui sera transformé en query string
function runAPI(array $data = [])
{
    // Création de la query string
    $querystring = http_build_query($data);
    // Fait croire à un mode GET
    $_SERVER['REQUEST_METHOD'] = 'GET';
    // Dérivation de toutes les sorties dans un buffer
    $res = ob_start();
    if (!$res) {
        print("Error ob_start\n");
        exit(1);
    }
    // Appel de l'api sans query string
    $_SERVER['QUERY_STRING'] = $querystring;
    // Fabrique le contenu de _GET
    parse_str($querystring, $_GET);
    // Lance le code de l'API
    include(__DIR__ . '/../api/time.api.php');
    // Recupération des sorties
    $value = ob_get_contents();
    ob_end_clean(); // Fin de la dérivation des sorties
    // Retourne ce que l'API a produit
    return $value;
}

try {
    // Test des erreurs
    print_err("Test queryString vide : ");
    $value = runAPI();
    $expected = '{"error":"incorrect action"}';
    if ($value != $expected) {
        print("Retourné : \n");
        var_dump($value);
        print("Attendu : \n");
        var_dump($expected);
        throw new Exception('Mauvaise réponse querystring vide');
    }
    OK();

    print_err("Test read sans timeZone : ");
    $query = array(
        'action' => 'read'
    );
    $value = runAPI($query);
    $expected = '{"error":"timeZone missing for read"}';
    if ($value != $expected) {
        print("Retourné : \n");
        var_dump($value);
        print("Attendu : \n");
        var_dump($expected);
        throw new Exception('Mauvaise réponse à la demande sans timeZone');
    }
    OK();

    print_err("Test read : ");
    $timeZone = 'Europe/Paris';
    $query = array(
        'action' => 'read',
        'timeZone' => $timeZone
    );
    $value = runAPI($query);
    print_err($value.' ');
    OK();


} catch (Exception $e) {
    printCol("\nErreur sur l'API : " . $e->getMessage() . "\n");
}

?>