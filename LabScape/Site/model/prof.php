<?php

// Inclusion des fichiers nécessaires
require_once(__DIR__ . '/DAO.class.php');
require_once(__DIR__ . '/utilisateur.php');

// Classe représentant un enseignant, héritant de la classe abstraite User
class Teacher extends User {

    const ROLE_ID = 2; // Type de l'utilisateur dans la base de données

    // Méthode pour créer un nouvel enseignant dans la base de données
    public function create(): void {
        // Vérifie si l'objet enseignant n'est pas déjà dans la base avec un identifiant existant
        if ($this->id !== -1) {
            throw new Exception("Impossible de créer : déjà présent dans la base avec l'identifiant=" . $this->id);
        }

        // Données à insérer dans la base
        $data = [];
        $data[] = $this->nom;
        $data[] = $this->prenom;
        $data[] = $this->login;
        $data[] = $this->password;
        $data[] = Teacher::ROLE_ID;

        // Requête SQL pour insérer un nouvel enseignant dans la base
        $query = "INSERT INTO Utilisateur (nom, prenom, login, password, roleID) VALUES (?, ?, ?, ?, ?)";
        $dao = DAO::get();
        $res = $dao->exec($query, $data);

        // Vérifie si l'opération d'insertion a réussi
        if ($res === false) {
            throw new Exception("Le professeur n'a pas été ajouté");
        }

        // Met à jour l'identifiant de l'objet enseignant avec celui généré par la base de données
        $this->id = $dao->lastInsertId();
    }

    // Méthode statique pour lire un enseignant à partir de son identifiant
    public static function readTeacher($id): Teacher {
        // Récupère l'instance de la DAO
        $dao = DAO::get();

        // Requête SQL pour sélectionner un enseignant par son identifiant
        $query = "SELECT * FROM Utilisateur WHERE id = ?";
        $data = [$id];
        $table = $dao->query($query, $data);

        // Vérifie si l'enseignant a été trouvé
        if (count($table) == 0) {
            throw new Exception("Enseignant non trouvé avec l'identifiant $id");
        }

        // Récupère la première ligne de résultat de la requête
        $row = $table[0];

        // Crée une nouvelle instance de la classe Teacher avec les informations récupérées
        $teacher = new Teacher($row['nom'], $row['prenom'], $row['login'], $row['password']);
        $teacher->id = $row['id'];
        return $teacher;
    }
}

?>
