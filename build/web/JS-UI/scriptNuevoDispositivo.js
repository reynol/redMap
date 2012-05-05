
function insertarDispositivo(imagen, nombre,id, direccion, puerto, radio, nuevo){
	//Crea nuevo dispositivo
	var element=svg.Imagen(imagen, 'move',mouseDownX, mouseDownY, 48,48,id, "device");
	nuevoElemento(element);
	//Coloca la direccion[:puerto]
	
	if(nuevo){
		$(element.node).addClass('nuevo')
	}
	
	if(radio=='puerto'){
		element.txt_ip.textContent =  (puerto)?direccion+":"+puerto:direccion;
		element.b_puerto=true;
		element.puerto=puerto;
	}else{
		element.txt_ip.textContent = direccion;
		element.b_puerto=false;
	}
	//coloca el nombre
	element.txt_nombre.textContent = nombre;
	element.nombre=nombre;
	element.ip=direccion;
	//alert(element.txt_nombre);
	
						//nuevoElemento(svg.Imagen(value,'move',200, 200, 48,48));
	var form=element.node;
						//alert("ok1");
						//alert(form);
						//form=
						//nueva forma dragable
	
	
				
						$(form).draggable({
							start: function(evt){
							 
							},
							drag: function(e){
								//si hay conector central se borra
								if(conector.b_conectorCentral){
									grup = conector.DOM_itm_cnct.parentNode;
									grup.parentNode.removeChild(grup);
									conector.b_conectorCentral=false;
								}
								//Mueve el Dispositivo/nombre/direccion
								 
								 //console.log(e.pageX);
								 //console.log(e.layerX);
								// var pageX=e.layerX;
								 //var pageY=e.layerY;
								  var pageX=e.originalEvent.pageX;
								 var pageY=e.originalEvent.pageY;
								
								 $(this)[0].setAttributeNS(null,'x',pageX-24);	
								 $(this)[0].setAttributeNS(null,'y',pageY-24);
								 
								 //obtengo el elemento para poder manipular los text
								 
								 var el=elementos[formas.indexOf($(this)[0])];
								 el.txt_nombre.setAttributeNS(null,'x',pageX-24);
								 el.txt_nombre.setAttributeNS(null,'y',pageY-24);
								 
								 el.txt_ip.setAttributeNS(null,'x',pageX-24);
								 el.txt_ip.setAttributeNS(null,'y',pageY+34);
								 
								 el.select.setAttributeNS(null,'x',pageX-24);
								 el.select.setAttributeNS(null,'y',pageY-24);
								 
							/*	  
								  //console.log($(this)[0].getAttribute("x")+"+"+$(this)[0].getAttribute("y"));
								  state = $(this)[0].parentNode.getCTM();
								 // console.log(state.e+"+"+state.f);
								  // console.log(e.pageX+"-"+e.pageY);
								 var dx = (e.pageX)-(state.e);
								 var dy = (e.pageY)-(state.f);  
								   
								//console.log(dx+","+dy);
								
								  
								 $(this)[0].parentNode.setAttribute('transform',"translate("+dx+","+dy+")");
								 
								 
								 */
								 
								 
								 //obtengo el objeto DOM del dispositivo
								 el=elementos[formas.indexOf($(this)[0])];
									//actualizo paths
									if(el.paths.length>0){
										for (var i = el.paths.length; i--;) {
											//conexion(el, el.paths[i].destino,el.paths[i].path);
											conexion(el,elementos[formas.indexOf($('#'+el.paths[i].destino).get(0))],
											$('#'+el.paths[i].path).get(0));
										}
									}
									
									
								//Modifico el tamaño del contenedor SVG si sobrepasa su tamaño.
								//Ancho
								if(pageX>=($('#universe').attr("width")-24)){
									//console.log('la anchura es :' + (($('#universe').attr("width")-0)+24));
									$('#universe').attr('width',(($('#universe').attr("width")-0)+24));
									
									var ancho=(($(window).width()*.10)/pageX)*$(window).width();
									$("#shiftMap_drag").css("width",ancho);  


									//$("#shiftMap_drag").css('left',(left2-0)+($("#shiftMap").width()-ancho));  
									 									
								}
								//Alto
								if(pageY>=($('#universe').attr("height")-24)){
								//console.log('la aaltura es :' + (($('#universe').attr("height")-0)+24));
									$('#universe').attr('height',(($('#universe').attr("height")-0)+24));
									
									var alto=(($(window).height()*.10)/pageY)*$(window).height();
									$("#shiftMap_drag").css("height",alto);  

								//$("#shiftMap_drag").css('top',(top2-0)+($("#shiftMap").height()-alto));  

								}
								
							
								

								
							} ,
							stop: function(e){
							console.log($(e.target).position().top);
							
							//Al dejar de mover el dispositivo se actualizan los paths en el servidor
							actualizaPosDispositivo($(this).attr('id'),$(this).attr('x'),$(this).attr('y'));
							
							
							}
							
						});
						/*
						//Add a las forma su Menu Contextual
						 $(form).bind("contextmenu", function(e){
								e.stopPropagation();
								
								addMenuContextualDispositivo(e,$(this)[0]);
								return false; 
						  });
						*/  
						var menu1 = [
						  {'Conectar':function(menuItem,menu) {
									
									conector.DOM_dispositivoTarget1=menu.target;
									
									console.log("Conector1 "+conector.DOM_dispositivoTarget1);
								        conectarDispositivos(menu);  } },
						$.contextMenu.separator,				
							{'Eliminar':function(menuItem,menu) {
									removeDispositivo(elementos[formas.indexOf(menu.target)]);
									} },
						  $.contextMenu.separator,
						  {'Configurar':function(menuItem,menu) { 
						     //indico que sera actualizara el dispositivo
							nuevoDevice=false;
						      //indico el target, el DOM del elemento a modificar.
						      conector.DOM_dispositivoTarget1=menu.target;
						      
						      
						      //coloco los datos previos en el dialog.
						      $("#tipo").val($(menu.target).attr("href"));
						      var element=elementos[formas.indexOf(menu.target)];
		//				      console.log('puerto'+element.b_puerto);
						      if(element.b_puerto){
							$('#opc_puerto').prop('checked', true);
							
						      }else{
							$('#opc_ping').prop('checked', true);
						      }
							$("#direccion" ).val(element.ip);
							$("#nombre" ).val(element.nombre);
							$("#puerto").val(element.puerto);
						      
						      //abro el dialogo
						      $( "#dialog-form" ).dialog( "open" );
						  }
						  }];
						$(function() {
						  $(form).contextMenu(menu1,{theme:'osx'});
						});



// Menu contextual para los seleccionados
						menu1 = [
						  {'Eliminar Seleccionados':function(menuItem,menu) {
									
									removeSeleccionados() ;				
									
									
										} },
						  $.contextMenu.separator];
						  var elem = elementos[formas.indexOf(form)];
						$(function() {
						  $(elem.select).contextMenu(menu1,{theme:'osx'});
						});


 
						  
						  
						  
						  // Anadir mouse over img conector central solo si se pasa sobre un device
						  
						  //$(form).parent().mouseenter(
						$(form).mouseenter(
						  function(e){
							//evita que se cree mas de un conector central, cada vez que se salga del conector 
							// y entre nuevamente al la forma que tiene el eventor mouseenter.
							//si conector.b_conectorCentral=false se crea. si es True ya existe y no se vuelve a crear.
							if(!conector.b_conectorCentral){
									console.log("mouse enter");
									var box=getCentro(e.target);
									
	//							conector.DOM_itm_cnct=svg.Imagen('imagenes/canvas/conct.png','pointer', box.x-8,box.y-8, 16,16).node;
								conector.DOM_itm_cnct=svg.Imagen('imagenes/canvas/conct_arrow_c.png','pointer', box.x-8,box.y-8, 16,16,"id","conector").node;
								grup = conector.DOM_itm_cnct.parentNode;
								
								//muevo ese grupo de imagen dentro del grupo de la imagen que se dio click
								$(form).parent().get(0).appendChild(grup);
								
								
								// habilita que el conector pueda ser arrastrado
								//alert(conector.DOM_itm_cnct);
								$(conector.DOM_itm_cnct).draggable({
								//appendTo: 'body',
								helper: 'clone', 
								start: 
								function(e){
								// primer dispositivo (g --> g  >> primer hijo(img) )
								conector.DOM_dispositivoTarget1=$(e.target).parent().parent().get(0).firstChild;
								//alert(conector.DOM_dispositivoTarget1);
								// Inicia la conexion
								conectarDispositivos();
								
								},
								stop: 
								function(event, ui) {
								   
								}}
								);

								
								
								// indica que el Dispositivo si tiene conector "activo"
								conector.b_conectorCentral=true;
								
							}
								
						  }
						  );
						  
						$(form).parent().mouseleave(
						function(){
							console.log("mouse leave");
							grup = conector.DOM_itm_cnct.parentNode;
								if(conector.b_conectorCentral){
									grup.parentNode.removeChild(grup);
									conector.b_conectorCentral=false;
								}
							}
						);
						console.log("Fin inserta dispo");
}	
