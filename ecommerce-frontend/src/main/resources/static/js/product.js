 document.addEventListener('DOMContentLoaded', function() {
        const form = document.getElementById("productForm");

        form.addEventListener("submit", function(event) {
            event.preventDefault();

            var nom = document.getElementById('nom').value;
            var prix = document.getElementById('prix').value;
            var stock = document.getElementById('stock').value;
            var description = document.getElementById('description').value;
            var image = document.getElementById('fichierInput').files[0];

            var formData = new FormData();
            formData.append("nom", nom);
            formData.append("prix", prix);
            formData.append("stock", stock);
            formData.append("description", description);
            formData.append("image", image);

            fetch("http://localhost:8081/produits", {
                method: "POST",
                body: formData,
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erreur réseau avec le statut ' + response.status);
                }
                return response.text();
            })
            .then(data => {
                alert(data);
                form.reset();
                window.location.href = "/";
            })
            .catch(error => {
                console.error("Erreur:", error);
                alert("Une erreur est survenue lors de l'ajout du produit.");
            });
        });
    });

document.addEventListener("DOMContentLoaded", function () {
    const idProduit = sessionStorage.getItem("produitId");
    const form = document.getElementById("productForm");

    if (idProduit) {
        chargerProduit(idProduit);
    }

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        sauvegarderProduit(idProduit);
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
        })
        .catch(error => console.error("Erreur lors du chargement du produit :", error));
}


function sauvegarderProduit(id) {
    let formData = new FormData();
    formData.append("nom", document.getElementById("nom").value);
    formData.append("prix", document.getElementById("prix").value);
    formData.append("stock", document.getElementById("stock").value);
    formData.append("description", document.getElementById("description").value);
    formData.append("image", document.getElementById("fichierInput").files[0]); // Image

    let url = id ? `http://localhost:8081/produits/modifier/${id}` : "http://localhost:8081/produits/ajouter";
    let method = id ? "PUT" : "POST";

    fetch(url, {
        method: method,
        body: formData
    })
        .then(response => response.text())
        .then(message => {
            alert(message);
            sessionStorage.removeItem("produitId");
            window.location.href = "Product.html";
        })
        .catch(error => console.error("Erreur :", error));
}


document.addEventListener("DOMContentLoaded", function () {
    document.addEventListener("click", function (event) {
        let button = event.target.closest(".add-to-cart");

        if (!button) return;

        event.preventDefault();

        let productId = button.getAttribute("data-product-id");

        if (!productId) {
            console.error("Aucun ID de produit trouvé !");
            return;
        }

        let cart = JSON.parse(sessionStorage.getItem("cart")) || [];

        if (cart.includes(productId)) {
            alert("Ce produit est déjà dans le panier !");
            return;
        }
        cart.push(productId);
        sessionStorage.setItem("cart", JSON.stringify(cart));

        alert("Produit ajouté au panier !");
    });
});

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


 $(document).ready(function() {
        function fetchProducts() {
            $.get("http://localhost:8081/produits", function(data) {
                if (!Array.isArray(data)) {
                    console.error("Les données reçues ne sont pas un tableau :", data);
                    return;
                }

                let productHtml = '';

                data.forEach(function(produit) {
                    console.log(data);
                    console.log(produit.nom + "_" + produit.prix);
                    if (produit.id && produit.nom && produit.prix && produit.image) {
                        productHtml +=
                            '<div class="col-sm-6 col-md-4 col-lg-3 centerblock">' +
                                '<div class="product-card">' +
                                    '<div class="product-tumb">' +
                                         '<img src="http://localhost:8081' + produit.image + '" alt="' + produit.nom + '">' +
                                    '</div>' +
                                    '<div class="product-details">' +
                                        '<span class="product-catagory">Women, bag</span>' +
                                        '<h4><a href="#">' + produit.nom + '</a></h4>' +
                                        '<p>' + produit.description + '</p>' +
                                        '<div class="product-bottom-details">' +
                                            '<div class="product-price">' + produit.prix + '$' + '</div>' +
                                            '<div class="product-links">' +
                                                 '<a href="#" class="add-to-cart" data-product-id="' + produit.id + '"><i class="fa fa-shopping-cart"></i></a>' +
                                            '</div>' +
                                        '</div>' +
                                    '</div>' +
                                '</div>' +
                            '</div>';
                    } else {
                        console.warn("Produit incomplet :", produit);
                    }
                });

                if ($('#productList').length) {
                    $('#productList').html(productHtml);
                } else {
                    console.error('Élément #productList introuvable');
                }
            })
            .fail(function() {
                console.error("Erreur lors de la récupération des produits");
            });
        }

        fetchProducts();
    });


