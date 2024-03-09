<?php
require_once(__DIR__ . '/../model/classement.dao.php');

class ClassementController {
    public function afficherClassement() {
        $classement = Classement::getClassement();
        require_once(__DIR__ . '/../view/classement.php');
    }
}
?>
