<?php

// Classe abstraite User, la mère de Eleve et Professeur

abstract class User {

    // Propriétés protégées pour être accessibles dans les classes dérivées
    protected int $id;
    protected string $nom;
    protected string $prenom;
    protected string $login;
    protected string $password;

    // Constructeur de la classe User
    public function __construct($nom, $prenom, $login, $password) {
        $this->id = -1; // L'objet n'est pas encore créé dans la base de données
        $this->nom = $nom;
        $this->prenom = $prenom;
        $this->login = $login;
        $this->password = $password;
    }

    // Méthode abstraite pour créer un utilisateur (implémentée dans les classes dérivées)
    abstract public function create();

    // Méthode pour comparer deux utilisateurs
    public function compareTo(User $user2) {
        if ($this->getNom() == $user2->getNom() && $this->getPrenom() == $user2->getPrenom() && $this->getLogin() == $user2->getLogin() && $this->getPassword() == $user2->getPassword()) {
            return 0;
        }
        return -1;
    }

    // Méthodes pour récupérer les informations de l'utilisateur
    public function getNom() {
        return $this->nom;
    }

    public function getPrenom() {
        return $this->prenom;
    }

    public function getLogin() {
        return $this->login;
    }

    public function getPassword() {
        return $this->password;
    }

    public function getId() {
        return $this->id;
    }

    public function setId($id) {
        $this->id = $id;
    }
}

?>
