	


function nuevoElemento(el){
			el.node.reynol="umm";
			elementos.push(el);
			formas.push(el.node);
			
			
}

function showModalFormDispositivo(cmenu) {
			mouseDownY=cmenu.menu.position().top-24;
			mouseDownX=cmenu.menu.position().left-24;
			
			//indico que sera un nuevo dispositivo
			nuevoDevice=true;
			
				$( "#dialog-form" ).dialog( "open" );
}
//Elemento a borrar, path a borrar

//se usa para remover una linea en particular
function removeLinea(path) {
	var devices=path.split('-');
	var Dev1=elementos[formas.indexOf($('#'+devices[0]).get(0))];
	var Dev2=elementos[formas.indexOf($('#'+devices[1]).get(0))];
	
	

								
	console.log(Dev1.paths.length);							
	console.log(Dev2.paths.length);							
								
								
		$.ajax(
					{
					url: 'BorrarPathSrv',
					async: false,
					type: "POST",
					success: function(data){
					 var ok=data;
					 if(ok){
					 console.log('Path eliminado correctamente'); 
					 
					 
						for(var r=0;r<Dev1.paths.length;r++){

						if(Dev1.paths[r]){

						// y el path(id) del dispositivo destino es igual al path(id) a borar
							if(Dev1.paths[r].path==path){	

											//Borro el path de la lista de arrays del dispositivo destino
											Dev1.paths.splice(r,1);
											//el.paths.splice(j,1);

								break;
							
							}
						}
						}



						for(var r=0;r<Dev2.paths.length;r++){
								console.log(Dev2.paths[r]);
							if(Dev2.paths[r]){

							// y el path(id) del dispositivo destino es igual al path(id) a borar
								if(Dev2.paths[r].path==path){

												//Borro el path de la lista de arrays del dispositivo destino
												Dev2.paths.splice(r,1);
												//el.paths.splice(j,1);

									break;
								
								}
							}
						}
						
						$('#'+path).get(0).parentNode.removeChild($('#'+path).get(0));
						//Borro la linea de los array objetos y DOM
									lineas.splice(lineasDOM.indexOf($('#'+path).get(0)),1);
									lineasDOM.splice(lineasDOM.indexOf($('#'+path).get(0)),1);
									
									
							//Se desselecciona la linea
								svg.hide(svg.select_line.node);
						

						
					 }
					
					},   
					data:{enlace: path}
					

		});								
												
	console.log(Dev1.paths.length);							
	console.log(Dev2.paths.length);							


}

//SE usa para remover todos las lineas de un dispositivo
function removePath(path) {
	var Dev2;
						if(path){
									Dev2=elementos[formas.indexOf($('#'+path.destino).get(0))];
									console.log("dispositivo 2: "+Dev2);
									console.log("leng dev: "+Dev2.paths.length);
									
								for(var r=0;r<Dev2.paths.length;r++){
											console.log(Dev2.paths[r]);
										if(Dev2.paths[r]){

										// y el path(id) del dispositivo destino es igual al path(id) a borar
											if(Dev2.paths[r].path==path.path){
console.log(path.path+" entro antes ajax"+r);	
												$.ajax(
														{
														url: 'BorrarPathSrv',
														async: false,
														type: "POST",
														success: function(data){
														 var ok=data;
														 if(ok){
															console.log('Path eliminado correctamente'); 
															
															//se remueve el path en ambos arreglos
															console.log(path.path+" entro"+r);	
													
															$('#'+path.path).get(0).parentNode.removeChild($('#'+path.path).get(0));
															
															//se remueve el path en ambos arreglos de los devices
															//delete Dev2.paths[r];
															
															//se elimina la informacion del path..el array sigue teniendo el mismo lngth
															delete path;
															
															//Borro el path de la lista de arrays del dispositivo destino
															Dev2.paths.splice(r,1);
															//el.paths.splice(j,1);
															
															//Borro la linea de los array objetos y DOM
															lineas.splice(lineasDOM.indexOf($('#'+path.path).get(0)),1);
															lineasDOM.splice(lineasDOM.indexOf($('#'+path.path).get(0)),1);

															
														 }
														
														},   
														data:{enlace: path.path}
														

													});



											
												
												break;
											
											}
										}
								}
						}


}

function removeDispositivo(el) {
			
			//borro los paths relacionados, a los dispositivos
						
						var total;
						
						//Se remueven todos los paths relacionados
						console.log("numero de paths: "+el.paths.length);
							for(j=0;j<el.paths.length;j++){	 
							//uhm= el.paths.id.split("-");
								console.log(" "+j+"j "+el.paths[j]);
								console.log(el.paths[j]);
								removePath(el.paths[j]) 				;
							
							
							}
							
							
						//Se emina el dispositivo	
							
						$.ajax(
							{
							url: 'BorrarItemSrv',
							type: "POST",
							success: function(data){
							 var ok=data;
							 if(ok){
								console.log('Dispositivo eliminado correctamente'); 
								console.log("papa_"+el.node.parentNode);
								//xSel.get(i).parentNode.removeChild(xSel.get(i));
								el.node.parentNode.removeChild(el.node);

								el.txt_nombre.parentNode.removeChild(el.txt_nombre);
								el.txt_ip.parentNode.removeChild(el.txt_ip);
								el.select.parentNode.removeChild(el.select);	

								// por ultimo se elimina el elemento del array de elementos  Y LA FORMA DOM DEL ARRAY DE FORMAS
								console.log(elementos.length);
								console.log(formas.length);
								elementos.splice(formas.indexOf(el.node),1);
								formas.splice(formas.indexOf(el.node),1);
								console.log(elementos.length);
								console.log(formas.length);
	
							 }
							
							},   
							data:{id: $(el.node).attr('id')}

						});	
			
			
			
			
}

		
function removeSeleccionados() {
			var xSel = $('.selected_device');
			var el;
			console.log("numero de seleccionados"+xSel.length);

			for(i=0;i<xSel.length;i++){
			
			    	el=elementos[formas.indexOf(xSel.get(i))];
					console.log("dispositivo a eliminar:");	
					console.log(el);	
					removeDispositivo(el);
						
						
						
			}
		}		
		
		
		
		
		
		
		
//mostrar cuadro de dialogo		
		
$(function() {
		// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
		$( "#dialog:ui-dialog" ).dialog( "destroy" );
		
		
			
			
			
//var selected = select.children( ":selected" ),
//value = selected.val() ? selected.text() : "";

		$('.accordion .head').click(function() {
				$(this).next().toggle();
				return false;
			}).next().hide();


		$( "#dialog-form" ).dialog({
			autoOpen: false,
			height: 330,
			width: 400,
			modal: true,
			buttons: {
				"Acaptar": function() {

					var tipo = $( "#tipo option:selected" ),
					    direccion = $( "#direccion" ),
					    nombre = $( "#nombre" ),
					    puerto=$("#puerto")
					    radio_selected=$("input[@name='grupoRadio']:checked").val();;
					    
					
					var imagen=tipo.val();
					
					
					if ( nuevoDevice ) {	  
						  //crea nuevo sispositivo
						crearDispositivo(imagen,nombre.val(),0,direccion.val(),puerto.val(),radio_selected);
						//insertarDispositivo(imagen,nombre.val(),0,direccion.val(),puerto.val(),radio_selected, true);
						
						$( this ).dialog( "close" );
					}else{
					
						actualizaInfoDispositivo(nombre.val(),direccion.val(),puerto.val(), radio_selected, imagen); // previamente se guardo el target.
						
						$( this ).dialog( "close" );
					}
					
				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				
			}
		});
		
	$( "#user-form" ).dialog({
			autoOpen: false,
			height: 330,
			width: 400,
			modal: true,
			buttons: {
				"Acaptar": function() {

					var tipo = $( "#tipo option:selected" ),
					    direccion = $( "#direccion" ),
					    nombre = $( "#nombre" ),
					    puerto=$("#puerto")
					    radio_selected=$("input[@name='grupoRadio']:checked").val();;
					    
					
					var imagen=tipo.val();
					
					
					if ( nuevoDevice ) {	  
						  //crea nuevo sispositivo
						crearDispositivo(imagen,nombre.val(),0,direccion.val(),puerto.val(),radio_selected);
						//insertarDispositivo(imagen,nombre.val(),0,direccion.val(),puerto.val(),radio_selected, true);
						
						$( this ).dialog( "close" );
					}else{
					
						actualizaInfoDispositivo(nombre.val(),direccion.val(),puerto.val(), radio_selected, imagen); // previamente se guardo el target.
						
						$( this ).dialog( "close" );
					}
					
				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				
			}
		});		
$( "#edo_form" ).dialog({
			autoOpen: false,
			height: 330,
			width: 400,
			modal: true,
			buttons: {
				"Acaptar": function() {

					var tipo = $( "#edo_tipo option:selected" );
					var estado = $( "#edo_nom" ).val();

					var idmap=tipo.val();
					
					console.log(estado);
					console.log(idmap);
	
					$.ajax({
							type: "POST",
							url: "AsignaMapaEdoSrv",
							data: {edo:estado,idmapa: idmap },
							async: false,
							success: function(data) {
									if(!data)
										alert("No se pudo realizar la operacion");
									else
										console.log(data);
								
							   }
						}
						);
					$( this ).dialog( "close" );

				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				
			}
		});

$( "#tree_form" ).dialog({
			autoOpen: false,
			height: 330,
			width: 400,
			modal: true,
			buttons: {
				"Acaptar": function() {

					var tipo = $( "#mapa_tipo option:selected" );
					var nombre = $( "#mapa_nom" ).val();

					var tipo=tipo.val();
					
					//console.log(estado);
					//console.log(idmap);
	
					$.ajax({
							type: "POST",
							url: "nuevoMapaSrv",
							data: {nombre:nombre,tipo: tipo },
							async: false,
							success: function(data) {
									if(!data)
										alert("No se pudo realizar la operacion");
									else{
											console.log(data);
											 var rootNode = $("#tree3").dynatree("getRoot");
										/*	  var childNode = rootNode.addChild({
												title: nombre,
												key: data
											  });
										*/
										
										rootNode.tree.reload();
										//tree.reload();
										}
								
							   }
						}
						);
					$( this ).dialog( "close" );

				},
				Cancelar: function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				
			}
		});			
		
		$( "#tabs" ).dialog({
			autoOpen: false,
			height: 460,
			width: 830,
			modal: true});
			
		$( "#export" ).dialog({
			autoOpen: false,
			height: 100,
			width: 100,
			modal: true});			
			
 
	
	});			
		
		