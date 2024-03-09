function togglePasswordVisibility() {
    var passwordInput = document.getElementById('password');
    passwordInput.type === 'password' ? passwordInput.type = 'text' : passwordInput.type = 'password'; 
}

function validateForm() {
    var isValid = true;

    // Vérifie la validité du nom 
    var errorMessage = document.getElementById('nomError');
    var input = document.getElementById('nom');
    // Vérifier que le nom ne contient pas des caractères autres que des minuscules, majuscules et accents ou tiret
    if (/[^a-zA-ZÀ-ÖØ-öø-ÿ-]/.test(input.value)) {
        errorMessage.innerHTML = 'Votre nom ne peut contenir des caractères spéciaux ou des chiffres.';
        isValid = false;
    } else {
        errorMessage.innerHTML = '';
    }

    // Vérifie la validité du prénom 
    errorMessage = document.getElementById('prenomError');
    input = document.getElementById('prenom');
    // Vérifier que le prénom ne contient pas des caractères autres que des minuscules, majuscules et accents ou tiret
    if (/[^a-zA-ZÀ-ÖØ-öø-ÿ-]/.test(input.value)) {
        errorMessage.innerHTML = 'Votre prénom ne peut contenir des caractères spéciaux ou des chiffres.';
        isValid = false;
    } else {
        errorMessage.innerHTML = '';
    }

    // Vérifie la validité du login 
    errorMessage = document.getElementById('loginError');
    input = document.getElementById('login');
    // Vérifier que le login ne contient pas des caractères autres que des minuscules, majuscules ou des chiffres
    if (/[^a-zA-Z0-9]/.test(input.value)) {
        errorMessage.innerHTML = 'Votre identifiant ne peut contenir de caractères spéciaux ou des accents.';
        isValid = false;
    } else {
        errorMessage.innerHTML = '';
    }

    // Vérifie la validité du password 
    errorMessage = document.getElementById('passwordError');
    input = document.getElementById('password');
    // Vérifier si le mot de passe contient au moins une minuscule, une majuscule, un chiffre, un caractère spécial et au moins 8 caractères
    if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/.test(input.value)) {
        errorMessage.innerHTML = 'Votre mot de passe doit contenir au minimum 8 caractères dont une minuscule, une majuscule, un chiffre et un caractère spécial.';
        isValid = false;
    } else {
        errorMessage.innerHTML = '';
    }

    // Vérifie la validité du mail 
    errorMessage = document.getElementById('mailError');
    input = document.getElementById('mail');
    // Vérifier si le format de l'adresse mail est valide 
    if (!/^[\w.-]+@[a-zA-Z\d.-]+\.[a-zA-Z]{2,}$/.test(input.value)) {
        errorMessage.innerHTML = 'Votre e-mail doit être de la forme : prenom@exemple.com';
        isValid = false;
    } else {
        errorMessage.innerHTML = '';
    }
    return isValid;
}