
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


// Scripts para Gestion de usuario:

// Ocultar automáticamente la alerta
setTimeout(function () {
    var alerta = document.getElementById('alerta-exito');
    if (alerta) {
        alerta.classList.remove('show');
        alerta.classList.add('fade');
        alerta.style.display = 'none';
    }
}, 4000);

// Confirmación al eliminar
document.querySelectorAll('.btn-eliminar').forEach(function(btn) {
    btn.addEventListener('click', function(event) {
        var username = btn.getAttribute('data-username');
        if (!confirm('¿Estás seguro que deseas eliminar al usuario: ' + username + '?')) {
            event.preventDefault();
        }
    });
});

// Confirmar  contraseña y nueva contraseña


 document.getElementById("guardarPasswordBtn").addEventListener("click", function(event) {
        const newPassword = document.getElementById("newPassword").value;
        const confirmPassword = document.getElementById("confirmPassword").value;
        const errorDiv = document.getElementById("passwordError");

        if (newPassword !== confirmPassword) {
            event.preventDefault();
            errorDiv.classList.remove("d-none");
        } else {
            errorDiv.classList.add("d-none");
        }
    });

    //JS PARA VENTA:

    //filtrar datos en ventas

document.addEventListener("DOMContentLoaded", () => {
    // --- Filtro dinámico para CLIENTES ---
    const clientSearch = document.getElementById("buscarCliente");
    const clientSelect = document.getElementById("cliente");

    if (clientSearch) {
        clientSearch.addEventListener("input", () => {
            const nombre = clientSearch.value.trim();

            if (nombre.length >= 2) { // Buscar desde 2 letras
                fetch(`/clientes/buscar?nombre=${encodeURIComponent(nombre)}`)
                    .then(response => response.json())
                    .then(data => {
                        clientSelect.innerHTML = '<option value="">Seleccione un cliente</option>';
                        data.forEach(c => {
                            clientSelect.innerHTML += `<option value="${c.id}">${c.name}</option>`;
                        });
                    })
                    .catch(error => console.error("Error al buscar clientes:", error));
            } else {
                clientSelect.innerHTML = '<option value="">Seleccione un cliente</option>';
            }
        });
    }

    // --- Filtro dinámico para PRODUCTOS ---
    const productSearch = document.getElementById("buscarProducto");
    const productSelect = document.getElementById("producto");

    if (productSearch) {
        productSearch.addEventListener("input", () => {
            const nombre = productSearch.value.trim();

            if (nombre.length >= 2) {
                fetch(`/productos/buscar?nombre=${encodeURIComponent(nombre)}`)
                    .then(response => response.json())
                    .then(data => {
                        productSelect.innerHTML = '<option value="">Seleccione un producto</option>';
                        data.forEach(p => {
                            productSelect.innerHTML += `<option value="${p.id}" data-precio="${p.price}">${p.name}</option>`;
                        });
                    })
                    .catch(error => console.error("Error al buscar productos:", error));
            } else {
                productSelect.innerHTML = '<option value="">Seleccione un producto</option>';
            }
        });
    }
});

//manejar carrito
let total = 0;

document.addEventListener("DOMContentLoaded", () => {
    const btnAgregar = document.getElementById("btnAgregar");
    const tablaDetalle = document.querySelector("#tablaDetalle tbody");
    const totalVenta = document.getElementById("totalVenta");

    btnAgregar.addEventListener("click", () => {
        const productoSelect = document.getElementById("producto");
        const cantidadInput = document.getElementById("cantidad");

        const productoId = productoSelect.value;
        const productoNombre = productoSelect.options[productoSelect.selectedIndex].text;
        const precio = parseFloat(productoSelect.options[productoSelect.selectedIndex].dataset.precio);
        const cantidad = parseInt(cantidadInput.value);

        if (!productoId) {
            alert("Seleccione un producto");
            return;
        }

        const subtotal = precio * cantidad;
        total += subtotal;

        const fila = document.createElement("tr");
        fila.dataset.productId = productoId;
        fila.innerHTML = `
            <td>${productoNombre}</td>
            <td class="cantidad">${cantidad}</td>
            <td class="precio">S/ ${precio}</td>
            <td>S/ ${subtotal}</td>
            <td><button class="btn btn-danger btn-sm eliminar">X</button></td>
        `;

        tablaDetalle.appendChild(fila);
        totalVenta.textContent = total;

        fila.querySelector(".eliminar").addEventListener("click", () => {
            total -= subtotal;
            totalVenta.textContent = total;
            fila.remove();
        });

        // reset campos
        productoSelect.selectedIndex = 0;
        cantidadInput.value = 1;
    });

    // Confirmar venta
    document.getElementById("btnConfirmarVenta").addEventListener("click", () => {
        const clienteId = document.getElementById("cliente").value;
        const fecha = document.getElementById("fecha").value;

        if (!clienteId || tablaDetalle.children.length === 0) {
            alert("Debe seleccionar cliente y al menos un producto");
            return;
        }

        const productIds = [];
        const quantities = [];
        const prices = [];

        document.querySelectorAll("#tablaDetalle tbody tr").forEach(row => {
            productIds.push(row.dataset.productId);
            quantities.push(row.querySelector(".cantidad").textContent);
            prices.push(row.querySelector(".precio").textContent.replace("S/ ", ""));
        });

        const formData = new URLSearchParams();
        formData.append("clientId", clienteId);
        formData.append("date", fecha);
        productIds.forEach(id => formData.append("productIds", id));
        quantities.forEach(q => formData.append("quantities", q));
        prices.forEach(p => formData.append("prices", p));

        fetch("/ventas/guardar", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: formData.toString()
        })
        .then(res => res.text())
        .then(msg => {
            alert(msg);
            window.location.href = "/ventas/listado";
        });
    });
});
//fin de venta


//JS cliente

setTimeout(function () {
    var alerta = document.getElementById('alerta-exito2');
    if (alerta) {
        alerta.classList.remove('show');
        alerta.classList.add('fade');
        alerta.style.display = 'none';
    }
}, 4000);

    document.addEventListener("DOMContentLoaded", () => {
        document.querySelectorAll(".btn-eliminar-client").forEach(btn => {
            btn.addEventListener("click", e => {
                const nombre = btn.getAttribute("data-nombre");
                if (!confirm(`¿Estás seguro de eliminar al cliente "${nombre}"?`)) {
                    e.preventDefault();
                }
            });
        });
    });



    function actualizarKPIs() {
        fetch('/dashboard/data')
            .then(response => response.json())
            .then(data => {
                document.querySelector('#ventasHoy').textContent = 'Ventas de Hoy: S/ ' + data.ventasHoy.toFixed(2);
                document.querySelector('#stockBajo').textContent = 'Stock Bajo: ' + data.stockBajo + ' Productos';
                document.querySelector('#totalClientes').textContent = 'Clientes Registrados: ' + data.totalClientes;
                document.querySelector('#ventasMes').textContent = 'Ventas del Mes: S/ ' + data.ventasMes.toFixed(2);
            });
    }

    // Actualizar cada 10 segundos (10000 ms)
    setInterval(actualizarKPIs, 10000);

    // Cargar al inicio
    actualizarKPIs();

