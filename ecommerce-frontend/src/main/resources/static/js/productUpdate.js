
    document.addEventListener("DOMContentLoaded", function () {
    const idProduit = sessionStorage.getItem("produitId");
    const form = document.getElementById("updateProductForm");
    const imagePreview = document.getElementById("imagePreview");

    if (idProduit) {
        chargerProduit(idProduit);
    }

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        sauvegarderProduit();
    });
});


function chargerProduit(id) {
    fetch(`http://localhost:8081/produits/${id}`)
        .then(response => response.json())
        .then(produit => {
            document.getElementById("nom").value = produit.nom;
            document.getElementById("prix").value = produit.prix;
            document.getElementById("stock").value = produit.stock;
            document.getElementById("description").value = produit.description;

            if (produit.image) {
                document.getElementById("imageHidden").value = produit.image;
            }

            if (produit.image) {
                document.getElementById("imagePreview").src = produit.image;
            }
        })
        .catch(error => console.error("Erreur lors du chargement du produit :", error));
}


function sauvegarderProduit() {
    const idProduit = sessionStorage.getItem("produitId");
    if (!idProduit) {
        alert("ID du produit manquant !");
        return;
    }

    const form = document.getElementById("updateProductForm");

    let formData = new FormData();
    formData.append("nom", document.getElementById("nom").value);
    formData.append("prix", document.getElementById("prix").value);
    formData.append("stock", document.getElementById("stock").value);
    formData.append("description", document.getElementById("description").value);

    let imageFile = document.getElementById("fichierInput").files[0];
    let existingImage = document.getElementById("imageHidden").value;

    if (imageFile) {
        formData.append("image", imageFile);
    } else if (existingImage) {
        formData.append("image", existingImage);
    }

    fetch(`http://localhost:8081/produits/${idProduit}`, {
        method: "PUT",
        body: formData
    })
    .then(response => response.text())
    .then(message => {
        alert(message);
        sessionStorage.removeItem("produitId");
        window.location.href = "/listProduct";
    })
    .catch(error => console.error("Erreur :", error));
}

 const produitsApiUrl = 'http://localhost:8081/produits';

    function fetchProduits() {
        fetch(produitsApiUrl)
            .then(response => response.json())
            .then(produits => {
                let productHtml = '';
                produits.forEach(produit => {
                    if (produit.id && produit.nom && produit.prix && produit.stock !== undefined && produit.description) {
                        productHtml +=
                            '<tr style="color: black;">' +
                                '<td>' + produit.nom + '</td>' +
                                '<td>' + produit.prix + '</td>' +
                                '<td>' + produit.stock + '</td>' +
                                '<td>' + produit.description + '</td>' +
                                '<td>' +
                                    '<button onclick="editProduit(' + produit.id + ')" class="btn btn-primary" style="margin: 5px;">' +
                                        '<i class="fas fa-edit"></i> Modifier' +
                                    '</button>' +
                                    '<button onclick="deleteProduit(' + produit.id + ')" class="btn btn-danger" style="margin: 5px;">' +
                                        '<i class="fas fa-trash-alt"></i> Supprimer' +
                                    '</button>' +
                                '</td>' +
                            '</tr>';
                    } else {
                        console.warn("Produit incomplet :", produit);
                    }
                });
                document.querySelector('#myTable').innerHTML += productHtml;
            })
            .catch(error => {
                console.error('Erreur lors de la récupération des produits :', error);
            });
    }

    window.onload = fetchProduits;

    function editProduit(id) {
    sessionStorage.setItem("produitId", id);
    window.location.href = "/updateproduct";
}



     function deleteProduit(productId) {
            if (confirm('Êtes-vous sûr de vouloir supprimer ce produit ?')) {
                fetch(produitsApiUrl + '/' + productId, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                .then(response => response.text())
                .then(message => {
                    if (message.includes("supprimé")) {
                        alert('Produit supprimé avec succès');
                        window.location.href = "/listProduct";
                    } else {
                        alert('Erreur lors de la suppression du produit');
                    }
                })
                .catch(error => {
                    console.error('Erreur lors de la suppression du produit :', error);
                    alert('Erreur de connexion');
                });
            }
     }
