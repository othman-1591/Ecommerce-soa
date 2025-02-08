const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});

document.getElementById('signupForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var lastName = document.getElementById('lastName').value;
    var firstName = document.getElementById('firstName').value;
    var address = document.getElementById('address').value;
    var email = document.getElementById('email').value;
    var numTel = document.getElementById('numTel').value;
    var ville = document.getElementById('ville').value;
    var password = document.getElementById('password').value;

    var clientData = {
        nom: lastName,
        prenom: firstName,
        adresse: address,
        numTel: numTel,
        ville: ville,
        email: email,
        password: password
    };

    if (!lastName || !firstName || !address || !email || !numTel || !ville || !password) {
        alert("Tous les champs doivent être remplis.");
        return;
    }


    fetch("http://localhost:8084/clients", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(clientData)
    })
    .then(response => response.text())
    .then(data => {
        console.log("Réponse du serveur:", data);


        if (data.trim() === "Client créé avec succès !") {
        alert("Client créé avec succès !");
            console.log("Redirection vers l'accueil");
            window.location.href = "/";
        } else {
            console.log("Réponse incorrecte, pas de redirection.");
        }
    })
    .catch(error => {
        console.error("Erreur lors de la création du client:", error);
        alert("Erreur lors de la création du client.");
    });
});


document.getElementById('signinForm').addEventListener('submit', function(event) {
    event.preventDefault();


    var email = document.getElementById('signinEmail').value;
    var password = document.getElementById('signinPassword').value;

    var isValid = true;
    var errorMessageSignin = '';

    if (!email || !password) {
        isValid = false;
        errorMessageSignin = 'Veuillez entrer votre email et mot de passe.';
    }

    if (isValid) {
        fetch("http://localhost:8084/clients/login?email=" + encodeURIComponent(email) + "&password=" + encodeURIComponent(password), {
            method: "POST",
        })
        .then(response => response.text())
        .then(data => {
        console.log(data);

            if (data === "Connexion réussie!") {

                alert(data);
                window.location.href = "http://localhost:8080/";
            } else {
                console.log("Erreur : " + data);
            }
        })
        .catch(error => {
            console.error("Erreur lors de la connexion :", error);
            alert("Une erreur s'est produite.");
        });
    } else {
        document.getElementById('errorMessageSignin').textContent = errorMessageSignin;
    }
});


