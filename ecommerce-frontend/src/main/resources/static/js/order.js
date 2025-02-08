    async function fetchProductName(productId) {
        const productApiUrl = `http://localhost:8081/produits/${productId}`;

        try {
            const response = await fetch(productApiUrl);
            if (!response.ok) {
                throw new Error('Erreur de récupération du produit');
            }
            const product = await response.json();
            return product.nom || `Produit inconnu (ID: ${productId})`;
        } catch (error) {
            console.error(`Erreur lors de la récupération du produit avec ID ${productId}:`, error);
            return `Produit inconnu (ID: ${productId})`;
        }
    }

    async function fetchOrders() {
        const clientId = sessionStorage.getItem('clientId');
        const ordersApiUrl = `http://localhost:8083/orders/${clientId}`;

        try {
            const response = await fetch(ordersApiUrl);
            if (!response.ok) {
                throw new Error('Erreur de récupération des commandes');
            }
            const orders = await response.json();
            if (!orders || orders.length === 0) {
                console.error("Erreur : Liste de commandes vide ou mal formée");
                return;
            }

            let ordersHtml = '';

            for (let order of orders) {
                let formattedDate = new Date(order.date).toLocaleString();

                let productList = await Promise.all(order.products.map(async (product) => {
                    const productName = await fetchProductName(product.id);
                    return `${productName} (x${product.quantite})`;
                }));

                productList = productList.join(', ');

                ordersHtml += `
                    <tr style="color: black;">
                        <td>${order.id}</td>
                        <td>${order.total} €</td>
                        <td>${formattedDate}</td>
                        <td>${productList}</td>
                    </tr>`;
            }

            document.querySelector('#myTable').innerHTML += ordersHtml;

        } catch (error) {
            console.error("Erreur lors de la récupération des commandes :", error);
        }
    }

    window.onload = fetchOrders;