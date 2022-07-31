// Call the dataTables jQuery plugin
$(document).ready(function() {
    //on ready
});

async function registrarUsuarios() {

      let datos = {};

      datos.nombre = document.getElementById('txtNombre').value;
      datos.apellido = document.getElementById('txtApellido').value;
      datos.email = document.getElementById('txtEmail').value;
      datos.telefono = document.getElementById('txtTelefono').value;
      datos.password = document.getElementById('txtPassword').value;

      console.log(datos);

      let repetirPassword = document.getElementById('txtRepetirPassword').value;
      if(repetirPassword != datos.password){
        alert('Las contrasenas no son iguales');
        return;
      }

      const request = await fetch('api/usuarios', {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(datos)
      });

      alert("La cuenta fue creada con exito!");
      window.location.href = "login.html";
}

