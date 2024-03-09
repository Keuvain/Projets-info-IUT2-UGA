<?php
require_once(__DIR__ . '/DAO.class.php');

class Classement {

    public static function getClassement(): array {
        $dao = DAO::get();
        $query = "SELECT id, nom, score FROM classement ORDER BY score DESC";
        return $dao->query($query);
    }
}
?>