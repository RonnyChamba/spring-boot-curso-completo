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


					let idPro = ui.item.value;
					let nombrePro = ui.item.label;
					let precioPro = ui.item.precio;

					// Valida si el producto existe
					if (itemsHelper.productoExist(idPro)) {

						itemsHelper.incrementeCantidadProdu(idPro, precioPro);
						return false;
					}


					// html() obtiene el contenido del elemento seleccionado
					let lineaTableProdu = $("#plantillaItemsFactura").html();



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

	
	// 	Eliminar la linea modelo
	$("form").submit(function() {

		$("#plantillaItemsFactura").remove();

		return;

	});

	const itemsHelper = {
		calcularImporte: function(id, precio, cantidad) {

			$("#total_importe_" + id).html(parseInt(precio) * parseInt(cantidad));
			this.cacularGranTotal();

		},
		eliminarLineaFactura: function(id) {

			$("#" + id).remove();
			this.cacularGranTotal();

		},

		productoExist: function(id) {
			let existe = false;

			$('input[name^="item_id[]"]').each(function(index, item) {

				if (parseInt(id) == parseInt(item.value)) {
					existe = true;

				}


			});
			return existe;


		},
		incrementeCantidadProdu: function(id, precio) {

			let cantiddad = $("#cantidad_" + id).val() ? parseInt($("#cantidad_" + id).val()) : 0;
			$("#cantidad_" + id).val(++cantiddad);
			this.calcularImporte(id, precio, cantiddad)


		},
		cacularGranTotal: function() {

			let total = 0;

			$('span[id^="total_importe_"]').each(function(index, item) {

				let subtotal = parseInt(item.textContent.trim());

				total += subtotal;


			});

			$("#gran_total").html(total);

		}
	}


	$("#cargarItemProductos").click(function(event) {

		if (event.target.matches("input[type='number']")) {

			let elementCantidad = event.target;

			let id = elementCantidad.id + "";
			let cantidad = elementCantidad.value;
			let precioElement = elementCantidad.parentElement.previousElementSibling;
			let precio = precioElement.textContent.trim();
			let idNumero = id.substr(id.length - 1, id.length);

			itemsHelper.calcularImporte(idNumero, precio, cantidad);

		}

		if (event.target.matches("button")) {

			let rowButton = event.target.parentElement.parentElement;
			itemsHelper.eliminarLineaFactura(rowButton.id);

		}

	});


});


