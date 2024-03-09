<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Classement</title>
</head>
<body>
    <h1>Classement</h1>
    <table>
        <thead>
            <tr>
                <th>Position</th>
                <th>Nom</th>
                <th>Score</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($classement as $position => $joueur) : ?>
                <tr>
                    <td><?= $position + 1 ?></td>
                    <td><?= htmlspecialchars($joueur['nom']) ?></td>
                    <td><?= htmlspecialchars($joueur['score']) ?></td>
                </tr>
            <?php endforeach; ?>
        </tbody>
    </table>
    <!-- Formulaire pour ajouter au classement, si nécessaire -->
</body>
</html>
