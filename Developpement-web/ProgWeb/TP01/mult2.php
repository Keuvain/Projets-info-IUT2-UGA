<?php
    print("Donnez la table : ");
    $res = fscanf(STDIN, "%d\n", $table);
    if ($res != 1) {
      print("Erreur de lecture\n");
      exit(1);
    }
    print("Table du $table\n");
    for ($i = 1; $i<=10;$i++) {
        printf("$table * $i = %d\n",$i * $table);
    }
?>