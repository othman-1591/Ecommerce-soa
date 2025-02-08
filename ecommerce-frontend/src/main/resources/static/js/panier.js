let cart = JSON.parse(sessionStorage.getItem("cart")) || [];
    let total = 0;
    let itemCount = 0;

    async function getProductDetails(ids) {
        try {
            let response = await fetch('http://localhost:8081/produits/details', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(ids)
            });

            let products = await response.json();
            displayProducts(products);
        } catch (error) {
            console.error('Erreur lors de la récupération des produits:', error);
        }
    }

    function displayProducts(data) {
        let productHtml = '';
        itemCount = 0;
        total = 0;

        data.forEach(function(produit) {
            if (produit.id && produit.nom && produit.prix && produit.image) {
                let quantity = cart.filter(id => id === produit.id).length;
                let productTotal = produit.prix * quantity;
                itemCount += quantity;
                total += productTotal;

                productHtml +=
                    '<div class="row mb-4 d-flex justify-content-between align-items-center">' +
                        '<div class="col-md-2 col-lg-2 col-xl-2">' +
                            '<img src="http://localhost:8081' + produit.image + '" class="img-fluid rounded-3" alt="' + produit.nom + '">' +
                        '</div>' +
                        '<div class="col-md-3 col-lg-3 col-xl-3">' +
                            '<h6 class="text-muted">Produit</h6>' +
                            '<h6 class="mb-0">' + produit.nom + '</h6>' +
                        '</div>' +
                        '<div class="col-md-3 col-lg-3 col-xl-2 d-flex">' +

                            '<input id="form' + produit.id + '" min="0" name="quantity" value="' + quantity + '" type="number" class="form-control form-control-sm" onchange="updateQuantityFromInput(' + produit.id + ')"/>' +

                        '</div>' +
                        '<div class="col-md-3 col-lg-2 col-xl-2 offset-lg-1">' +
                            '<h6 class="mb-0">€ ' + produit.prix + '</h6>' +
                        '</div>' +
                        '<div class="col-md-1 col-lg-1 col-xl-1 text-end">' +
                            '<a href="#!" class="text-muted" onclick="removeProductFromCart(' + produit.id + ')">' +
                                '<i class="fas fa-trash-alt"></i>' +
                            '</a>' +
                        '</div>' +
                    '</div>';
            }
        });

        document.getElementById('product-list').innerHTML = productHtml;
        updateTotal();
    }

    function updateQuantity(productId, change) {
        let productIndex = cart.indexOf(productId);
        if (change === 1) {
            cart.push(productId);
        } else if (productIndex !== -1) {
            cart.splice(productIndex, 1);
        }
        sessionStorage.setItem("cart", JSON.stringify(cart));
        getProductDetails(cart);
    }

    function updateQuantityFromInput(productId) {
        let inputElement = document.getElementById(`form${productId}`);
        let newQuantity = parseInt(inputElement.value) || 0;

        let productIndex = cart.indexOf(productId);
        cart = cart.filter(id => id !== productId);

        for (let i = 0; i < newQuantity; i++) {
            cart.push(productId);
        }
        sessionStorage.setItem("cart", JSON.stringify(cart));
        getProductDetails(cart);
    }

function removeProductFromCart(productId) {
    let index1 = cart.indexOf(Number(productId));
    let index = cart.indexOf(String(productId));
    console.log("Index trouvé : ", index);

    if (index !== -1) {
        cart.splice(index, 1);
        sessionStorage.setItem("cart", JSON.stringify(cart));
        getProductDetails(cart);
    } else {
        console.log("Produit non trouvé dans le panier.");
    }
      if (index1 !== -1) {
        cart.splice(index, 1);
        sessionStorage.setItem("cart", JSON.stringify(cart));
        getProductDetails(cart);
    } else {
        console.log("Produit non trouvé dans le panier.");
    }
}



    function updateTotal() {
        document.getElementById('subtotal').innerText = `€ ${total.toFixed(2)}`;
        document.getElementById('total').innerText = `€ ${(total).toFixed(2)}`;
        document.getElementById('item-count').innerText = itemCount;
    }

    getProductDetails(cart);

function fetchUserInfo() {
                    fetch("http://localhost:8084/clients/user-info")
                    .then(response => response.json())
                    .then(data => {
                        if (data.nom && data.prenom) {
                            return data.id;
                             }
                    })
                    .catch(error => {
                        console.error("Erreur lors de la récupération des informations utilisateur:", error);
                    });
                }


document.getElementById('btn-commander').addEventListener('click', function() {
    var quantities = getQuantities();

    let totalElement = document.getElementById('total');
    let totalValue = parseFloat(totalElement.textContent.replace('€', '').trim());

    let clientId = sessionStorage.getItem('clientId');

    let productIds = [];
    let quantitiesList = [];

    for (let productId in quantities) {
        if (quantities.hasOwnProperty(productId)) {
            productIds.push(productId);
            quantitiesList.push(quantities[productId]);
        }
    }

    let orderUrl = `http://localhost:8083/orders?clientId=${clientId}&total=${totalValue}`;

    productIds.forEach((productId, index) => {
        orderUrl += `&productIds=${productId}&quantities=${quantitiesList[index]}`;
    });

    console.log('URL générée:', orderUrl);

    fetch(orderUrl, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        console.log('Réponse du microservice:', data);
        alert("Commande effectuée avec succès !");
        window.location.href = "/";
    })
    .catch(error => {
        console.error("Erreur lors de l'envoi de la commande:", error);
    });
});


    function getQuantities() {
    var quantities = {};

    var inputs = document.querySelectorAll('input[name="quantity"]');

    inputs.forEach(function(input) {
        var productId = input.id.replace("form", "");

        var quantity = parseInt(input.value);

        if (quantity >= 0) {
            quantities[productId] = quantity;
        } else {
            quantities[productId] = 0;
        }
    });

    console.log(quantities);
    return quantities;
}