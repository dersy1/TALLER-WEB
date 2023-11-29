// Datos de ejemplo para los productos
const products = [
    { category: 'personajes', name: 'Personaje 1', image: 'images/JUGADOR.jpg', description: 'Jugador fuerte', price: 10 },
    { category: 'personajes', name: 'Personaje 2', image: 'images/JUGADOR.jpg', description: 'Jugador rapido', price: 15 },
    { category: 'balones', name: 'Balon doble', image: 'images/BALON.jpg', description: 'Doble puntaje', price: 20 },
    { category: 'potenciadores', name: 'Potenciador 1', image: 'images/ITEM.png', description: 'Tiro extra', price: 5 },
    { category: 'potenciadores', name: 'Potenciador 2', image: 'images/ITEM.png', description: 'Otra chance', price: 8 }
];

const productList = document.getElementById('product-list');
const navLinks = document.querySelectorAll('.nav-link');

// Función para mostrar los productos según la categoría seleccionada
function showProducts(category) {
    productList.innerHTML = '';
    const filteredProducts = products.filter(product => product.category === category);

    filteredProducts.forEach(product => {
        const card = document.createElement('div');
        card.classList.add('col-md-4', 'mb-4');
        card.innerHTML = `
                    <div class="card h-100" style="width: 12rem; ">
                        <img src="${product.image}" class="card-img-top w-50" alt="${product.name}">
                        <div class="card-body">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">${product.description}</p>
                            <p class="card-text"><strong>Valor: <img src="images/MONEDA.png" class="w-25"> ${product.price}</strong></p>
                            <button href="#">Comprar</button></a>
                        </div>
                    </div>
                `;
        productList.appendChild(card);
    });
}

// Manejo de eventos para mostrar productos al hacer clic en una categoría
navLinks.forEach(link => {
    link.addEventListener('click', () => {
        const category = link.getAttribute('data-category');
        showProducts(category);
    });
});

// Mostrar productos de la categoría "personajes" por defecto
showProducts('personajes');