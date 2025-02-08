 function redirectToLogin() {
                            window.location.href = "http://localhost:8080/login";
  }

                  function fetchUserInfo() {
                      fetch("http://localhost:8084/clients/user-info")
                      .then(response => response.json())
                      .then(data => {
                          if (data.nom && data.prenom) {

                              document.getElementById('user-name').textContent = data.nom + " " + data.prenom;
                              sessionStorage.setItem('clientId', data.id);
                          }
                      })
                      .catch(error => {
                          console.error("Erreur lors de la récupération des informations utilisateur:", error);
                      });
                  }

                  window.onload = fetchUserInfo;



$(document).ready(function() {
            function fetchProducts() {
                $.get("http://localhost:8081/produits", function(data) {
                    if (!Array.isArray(data)) {
                        console.error("Les données reçues ne sont pas un tableau :", data);
                        return;
                    }

                    let latestProducts = data.slice(-4);

                    let productHtml = '';
latestProducts.forEach(function(produit) {
    console.log(produit.nom + "_" + produit.prix);
    if (produit.id && produit.nom && produit.prix && produit.image) {
        productHtml +=
            '<div class="col-sm-6 col-md-4 col-lg-3">' +
                '<div class="box">' +
                    '<a href="#">' +
                        '<div class="img-box">' +

                            '<img src="http://localhost:8081' + produit.image + '" alt="' + produit.nom + '">' +
                        '</div>' +
                        '<div class="detail-box">' +
                            '<h6>' + produit.nom + '</h6>' +
                            '<h6>Price <span>$' + produit.prix + '</span></h6>' +
                        '</div>' +
                        '<div class="new">' +
                            '<span>New</span>' +
                        '</div>' +
                    '</a>' +
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