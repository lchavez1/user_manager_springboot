// Call the dataTables jQuery plugin
$(document).ready(function() {
  updateTopName();
  cargarUsuarios();
  $('#usuarios').DataTable();
});

function updateTopName() {
    document.getElementById("txtNombreUsuario").outerHTML = localStorage.email;
}

function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization' : localStorage.token
    }
}

async function cargarUsuarios() {
      const request = await fetch('api/usuarios', {
        method: 'GET',
        headers: getHeaders(),
      });
      const usuarios = await request.json();

      let listadoContent = '';

      for(let usuario of usuarios){
        let botonElimiar = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i> </a>';
        let usuarioContent = '<tr> <td>' + usuario.id + '</td> <td>'+ usuario.nombre + ' ' + usuario.apellido + '</td> <td>' + usuario.email + '</td> <td>' + usuario.telefono + '</td> <td>' + botonElimiar +  '</td> </tr>';
        listadoContent += usuarioContent;
      }

      document.querySelector('#usuarios tbody').outerHTML = listadoContent;
}

async function eliminarUsuario(id){
    if(!confirm('¿Deseas eliminar el usuario con id: ' + id))
        return;
    const request = await fetch('api/usuarios/' + id, {
            method: 'DELETE',
            headers: getHeaders(),
          });
    location.reload();
}
