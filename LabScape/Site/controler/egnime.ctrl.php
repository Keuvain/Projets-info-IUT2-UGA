<?php

include_once(__DIR__."/../framework/view.class.php");

$view = new View();
$outgoing = "view/enigme1.html";

$view->display($outgoing, true);

?>