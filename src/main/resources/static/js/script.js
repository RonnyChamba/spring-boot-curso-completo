// Para verificar que el documento este cargado
$(document).ready(function() {


	autoCompleteBuscarProducto();

	function autoCompleteBuscarProducto() {

		$("#buscar_producto").autocomplete(
			{

				// autoFocus : true,
				minLength: 1,
				source: function(request, response) {

					$.ajax(
						{

							// term es una propieda de request, representa el valor qe se escrbie
							url: "/factura/cargar-productos/" + request.term,
							dataType: "json",
							method: "GET",
							// Procesa los datos devuleto desde el servidor
							success: function(data) {
								// pasaamos los datos obtenidos al autocomplete mediante su objeto response

								response($.map(data, function(item) {
									return {
										value: item.id,
										label: item.nombre,
										precio: item.precio
									}
								}));



							}
						}

					)

				},


				select: function(event, ui) {

					// html() obtiene el contenido del elemento seleccionado
					let lineaTableProdu = $("#plantillaItemsFactura").html();

					let idPro = ui.item.value;
					let nombrePro = ui.item.label;
					let precioPro = ui.item.precio;


					// Cambmiamos los parametros por los valores
					lineaTableProdu = lineaTableProdu.replace(/{ID}/g, idPro);
					lineaTableProdu = lineaTableProdu.replace(/{NOMBRE}/g, nombrePro);
					lineaTableProdu = lineaTableProdu.replace(/{PRECIO}/g, precioPro);


					// agegamos la fila a la tabla
					$("#cargarItemProductos tbody").append(lineaTableProdu);

					itemsHelper.calcularImporte(idPro, precioPro, 1);

					return false; // obligatorio devolver false

				}
			}


		);

	}


	const itemsHelper = {
		calcularImporte: function(id, precio, cantidad) {

			$("#total_importe_"+id).html(parseInt(precio) * parseInt(cantidad));

		},
		eliminarLineaFactura : function(id){
			
			$("#"+id).remove();
			
		}

	}



	$("#cargarItemProductos").click(function(event) {

		if (event.target.matches("input[type='number']")) {

			let elementCantidad = event.target;

			let id = elementCantidad.id +"";
			let cantidad = elementCantidad.value;
			let precioElement = elementCantidad.parentElement.previousElementSibling;
			let precio = precioElement.textContent.trim();
			let idNumero = id.substr (id.length-1, id.length);				
	
			itemsHelper.calcularImporte(idNumero, precio, cantidad);

		}
		
		if (event.target.matches("button")) {

				let rowButton = event.target.parentElement.parentElement;
				itemsHelper.eliminarLineaFactura(rowButton.id);

		}

	});


});


