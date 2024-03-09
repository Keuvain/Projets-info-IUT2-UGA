<?php
session_start();

if (isset($_SESSION['login'])) {
    $login = $_SESSION['login'];

    // Inclure le fichier DAO
    require_once '../model/DAO.php';

    try {
        // Obtenez une instance du DAO
        $dao = DAO::get();

        // Requête SQL pour récupérer le score associé au login
        $query = "SELECT temps FROM Score INNER JOIN Utilisateur ON Score.id = Utilisateur.id WHERE login = ?";
        $data = [$login];
        $table = $dao->query($query, $data);

        // Afficher le score s'il existe
        if (count($table) > 0) {
            $score = $table[0]['temps'];
            echo "Votre score est : $score";
        } else {
            echo "Aucun score trouvé pour cet utilisateur.";
        }
    } catch (PDOException $e) {
        echo "Erreur PDO : " . $e->getMessage();
    }
} else {
    echo "Vous n'êtes pas connecté.";
}
?>