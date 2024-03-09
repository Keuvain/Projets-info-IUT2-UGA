<?php

class DAO {
    // Singleton de la classe, accesseur de la base de données et informations d'identification de la base de données
    private static $instance = null;
    private PDO $db;
    
    // Connexion à la base de données (PDO)

    private string $database = "pgsql:host=192.168.14.215;dbname=labscape";
    private string $user = "theo";
    private string $password = "tete";
  
    // Constructeur chargé d'ouvrir la base de données
    private function __construct() {
        try {
            $this->db = new PDO($this->database, $this->user, $this->password);
            
            if (!$this->db) {
                throw new Exception("Impossible d'ouvrir $this->database avec le nom d'utilisateur $this->user");
            }

            // Configure PDO pour lancer des erreurs sous forme d'exceptions
            $this->db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $e) {
            throw new Exception("Erreur PDO : " . $e->getMessage() . ' sur ' . $this->database);
        }
    }
  
    // Méthode statique pour accéder au singleton
    public static function get(): DAO {
        // Si l'objet n'a pas encore été créé, le crée
        if (is_null(self::$instance)) {
            self::$instance = new DAO();
        }
        return self::$instance;
    }
  
    // Exécute une requête sur la base de données et retourne un tableau de résultats
    public function query(string $query, array $data = []): array {
        try {
            // Prépare la requête, produisant un objet PDOStatement
            $statement = $this->db->prepare($query);
            
            // Exécute la requête avec les données fournies
            $statement->execute($data);
        } catch (Exception $e) {
            // Capture l'exception et ajoute la requête pour faciliter le débogage
            throw new PDOException(__METHOD__ . " at " . __LINE__ . ": " . $e->getMessage() . " Query=\"" . $query . '"');
        }

        // Affiche l'erreur PDO en clair si la requête ne peut pas être exécutée
        if ($statement === false) {
            throw new PDOException(__METHOD__ . " at " . __LINE__ . ": " . implode('|', $this->db->errorInfo()) . " Query=\"" . $query . '"');
        }

        // Récupère tous les résultats de la requête sous forme de tableau
        $result = $statement->fetchAll();
        return $result;
    }

    // Exécute une requête sur la base de données sans retour de résultats (INSERT, UPDATE, DELETE, etc.)
    public function exec(string $query, array $data = []): void {
        try {
            // Prépare la requête, produisant un objet PDOStatement
            $statement = $this->db->prepare($query);
            
            // Exécute la requête avec les données fournies
            $result = $statement->execute($data);
        } catch (Exception $e) {
            // Capture l'exception et ajoute la requête pour faciliter le débogage
            throw new PDOException(__METHOD__ . " at " . __LINE__ . ": " . $e->getMessage() . " Query=\"" . $query . '"');
        }

        // Affiche l'erreur PDO en clair si la requête ne peut pas être exécutée
        if ($result === false) {
            throw new PDOException(__METHOD__ . " at " . __LINE__ . ": " . implode('|', $this->db->errorInfo()) . " Query: \"" . $query . '"');
        }
    }

    // Récupère l'identifiant du dernier élément inséré
    public function lastInsertId(): string {
        return $this->db->lastInsertId();
    }
}

?>
