
window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

});


document.addEventListener("DOMContentLoaded", function() {
    const ctx = document.getElementById('salesChart');
    if (ctx) {
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun'],
                datasets: [{
                    label: 'Ventas',
                    data: [1200, 1900, 3000, 2500, 2200, 2800],
                    borderColor: 'rgba(75, 192, 192, 1)',
                    tension: 0.3,
                    fill: false
                }]
            },
            options: { responsive: true }
        });
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const btnAgregar = document.getElementById("btnAgregar");
    const productoSelect = document.getElementById("producto");
    const cantidadInput = document.getElementById("cantidad");
    const tablaDetalle = document.querySelector("#tablaDetalle tbody");
    const totalVenta = document.getElementById("totalVenta");

    let total = 0;

    btnAgregar.addEventListener("click", function () {
        const producto = productoSelect.options[productoSelect.selectedIndex];
        const cantidad = parseInt(cantidadInput.value);
        const precio = parseFloat(producto.dataset.precio);

        if (!producto.value) {
            alert("Seleccione un producto");
            return;
        }

        const subtotal = precio * cantidad;
        total += subtotal;

        // Agregar fila
        const fila = document.createElement("tr");
        fila.innerHTML = `
            <td>${producto.text}</td>
            <td>${cantidad}</td>
            <td>S/ ${precio}</td>
            <td>S/ ${subtotal}</td>
            <td><button class="btn btn-danger btn-sm eliminar">X</button></td>
        `;
        tablaDetalle.appendChild(fila);

        // Actualizar total
        totalVenta.textContent = total;

        // Reset campos
        productoSelect.selectedIndex = 0;
        cantidadInput.value = 1;

        // Botón eliminar
        fila.querySelector(".eliminar").addEventListener("click", function () {
            total -= subtotal;
            totalVenta.textContent = total;
            fila.remove();
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const ctx = document.getElementById('ventasFechaChart');
    if (ctx) {
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['15/07', '16/07', '17/07', '18/07', '19/07'],
                datasets: [{
                    label: 'Ventas (S/)',
                    data: [120, 250, 180, 300, 200],
                    backgroundColor: 'rgba(54, 162, 235, 0.6)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
});


document.addEventListener("DOMContentLoaded", function () {
    const ctx = document.getElementById('ventasGeneralChart');
    if (ctx) {
        new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
                datasets: [{
                    label: 'Ventas Mensuales (S/)',
                    data: [8000, 9000, 7500, 11000, 9500, 12000, 15000, 14000, 10000, 9000, 8500, 13000],
                    backgroundColor: 'rgba(75, 192, 192, 0.6)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }
});


