<?php

include_once(__DIR__."/../framework/view.class.php");

$view = new View();
$outgoing = "../view/condition.html";

$view->display($outgoing);

?>