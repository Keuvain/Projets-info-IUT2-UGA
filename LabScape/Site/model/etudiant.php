<?php

// Inclusion des fichiers nécessaires
require_once(__DIR__ . '/DAO.class.php');
require_once(__DIR__ . '/utilisateur.php');

// Classe représentant un étudiant, héritant de la classe abstraite User
class Student extends User {

    const ROLE_ID = 1; // Type de l'utilisateur dans la base de données

    // Méthode pour créer un nouvel étudiant dans la base de données
    public function create(): void {
        // Vérifie si l'objet étudiant n'est pas déjà dans la base avec un identifiant existant
        if ($this->id !== -1) {
            throw new Exception("Impossible de créer : déjà présent dans la base avec l'identifiant=" . $this->id);
        }

        // Données à insérer dans la base
        $data = [];
        $data[] = $this->nom;
        $data[] = $this->prenom;
        $data[] = $this->login;
        $data[] = $this->password;
        $data[] = Student::ROLE_ID;

        // Requête SQL pour insérer un nouvel étudiant dans la base
        $query = "INSERT INTO Utilisateur (nom, prenom, login, password, roleid) VALUES (?, ?, ?, ?, ?)";
        $dao = DAO::get();
        $res = $dao->exec($query, $data);

        // Vérifie si l'opération d'insertion a réussi
        if ($res === false) {
            throw new Exception("L'élève n'a pas été ajouté");
        }

        // Met à jour l'identifiant de l'objet étudiant avec celui généré par la base de données
        $this->id = $dao->lastInsertId();
    }

    // Méthode statique pour lire un étudiant à partir de son identifiant
    public static function readStudent($id): Student {
        // Récupère l'instance de la DAO
        $dao = DAO::get();

        // Requête SQL pour sélectionner un étudiant par son identifiant
        $query = "SELECT * FROM Utilisateur WHERE id = ?";
        $data = [$id];
        $table = $dao->query($query, $data);

        // Vérifie si l'étudiant a été trouvé
        if (count($table) == 0) {
            throw new Exception("Élève non trouvé avec l'identifiant $id");
        }

        // Récupère la première ligne de résultat de la requête
        $row = $table[0];

        // Crée une nouvelle instance de la classe Student avec les informations récupérées
        $student = new Student($row['nom'], $row['prenom'], $row['login'], $row['password']);
        $student->id = $row['id'];
        return $student;
    }
}

?>
