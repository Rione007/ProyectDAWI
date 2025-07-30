
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




