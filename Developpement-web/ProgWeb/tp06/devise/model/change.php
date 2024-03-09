<?php

// Classe chargée de réaliser un change entre deux monnaies
Class Change {
    private array $rates = array();
    private array $devises = array();

    function __construct(string $filename) {
        $this->load($filename);
    }

    private function load(string $filename) {
        if (!file_exists($filename)) {
            throw new Exception("Fichier $filename introuvable.");
        }

        $lines = file($filename, FILE_IGNORE_NEW_LINES);
        if ($lines === false) {
            throw new Exception("Erreur lors de la lecture du fichier $filename.");
        }

        array_shift($lines); // Suppression de la première ligne (en-têtes)

        foreach ($lines as $line) {
            list($from, $to, $rate) = explode(",", $line);

            $this->rates[$from.' '.$to] = $rate;

            if (!in_array($from, $this->devises)) {
                $this->devises[] = $from;
            }
            if (!in_array($to, $this->devises)) {
                $this->devises[] = $to;
            }
        }
    }

   function getRate(string $from, string $to) : float {
    if ($from == $to) {
        return 1.0;
    }

    $key = $from.' '.$to;
    $inverseKey = $to.' '.$from;

    if (isset($this->rates[$key])) {
        return floatval($this->rates[$key]);
    } elseif (isset($this->rates[$inverseKey])) {
        // Si le taux inverse existe, nous l'invertons
        return 1.0 / floatval($this->rates[$inverseKey]);
    } else {
        throw new Exception("ERREUR : taux de $from vers $to inconnu");
    }
}


    function getDevises() : array {
        return $this->devises;
    }

    function change(string $from, string $to, float $amount) : float {
        $rate = $this->getRate($from, $to);
        return round($amount * $rate, 2);
    }
}


?>