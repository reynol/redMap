function eventosBase(){	
$("#guardar").bind('click', function(){
/*
	var xSel = $('.nuevo');
			var el;
			console.log("numero de seleccionados"+xSel.length);
			

			for(i=0;i<xSel.length;i++){
			
			    	el=elementos[formas.indexOf(xSel.get(i))];
					console.log("Direccion"+el.ip);
					if(puerto=="")puerto=0;
					$.ajax(
						{
						url: 'http://localhost:8084/ProbandoJSON_WEB/nuevoItemSrv',
						type: "POST",
						success: function(data){
						 var id=data;
						 if(id){
							
							console.log('datos enviados correctamente'); console.log(data);
							el.node.setAttributeNS(null, "id", id);
							
						 }
						
						},   
						data:{item: $.toJSON({id:0,
						posX: $(el.node).attr('x'), 
						posY: $(el.node).attr('y'), 
						img: $(el.node).attr('href'), 
						nombre:el.nombre, direccion:el.ip, puerto: el.puerto})}
				//        contentType: "application/json"
					});
					
					
					
					
					
					
					
			}
			
			*/

			
			
			//Sube al mapa superior si es que este existe (script carga mapas)
			//1= Existe mapa  , 2= Existe pero no esta cargado, 0= no existe
			
			var existePapa= subeParentMap();
			if( existePapa==1 ){
			
				//Se borra el mapa de devices
				$('#universe').remove();

				// reinica los arrays de DOM (script carga base)
				reiniciarArrays();
			
			
			}else{
				if( existePapa==0 ){
					alert("Este mapa no tiene un mapa superior asignado");
				}else{
					alert("El mapa superior no esta cargado, seleccionelo desde el panel de opciones.");
				
				}
				
			}
			
			
			
				//		$('#container2').show();
						/*
			$.ajax(
				{
				url: 'http://localhost:8084/ProbandoJSON_WEB/SetParentMAp',
				async:true,
				type: "POST",
				success: function(data){
					if(data){
						$('#container2').show();
					}

				}
			});
			*/
			
			
			
			
			
	});
	
	
	
	$("#chk_status").bind('click', function(){
	
		if($(this).hasClass('play')){
					console.log("INICIA TIMER");
					$(this).removeClass('play');
					$(this).addClass('stop');
					refreshInterval = setInterval(refreshStatus, $("#intervalo").val() * 1000);
					refreshStatus();
		  }else{
		  
		  if($(this).hasClass('stop')){
					console.log("STOP TIMER");
					$(this).removeClass('stop');
					$(this).addClass('play');
					 clearInterval(refreshInterval);
		  }
		  }
		  
	});	


	
	$("#chart").bind('click', function(){
		document.location.href = "CreaReporteWebSrv";
//		console.log("TIMER STOP");
//		 clearInterval(refreshInterval);
	/*				$.ajax({
							type: "POST",
							url: "CreaReporteWebSrv",
							async: false,
							success: function(data) {
									if(data){
										document.location.href = data;
									}

							   }
						}
					);
*/


	});	
	
	
	
	
	
	$("#m_tab_opc").bind('click', function(){
		console.log("opciones");
		
		
		
		
		//cierro el menu desplegable del menu superior
		
		$('.m_Tab').removeClass("active");
		
		
		//Carga valores de las opciones

		cargaConfiguraciones();
			
		
		//Se actualiza el tree
		var rootNode = $("#tree3").dynatree("getRoot");		
		rootNode.tree.reload();
		

		$( "#tabs" ).dialog( "open" );
	});	
	
	
		$( "#nuevoMapa" ).button().click(function (){$( "#tree_form" ).dialog( "open" );});
		$( "#borraMapa" ).button().click(function(){
			var tree = $("#tree3").dynatree("getTree");
			if(tree.getActiveNode()){
				//alert(tree.getActiveNode().data.key);
				//alert(tree.getActiveNode().getParent()); //null si no tiene padre
				//alert(tree.getActiveNode().getLevel());
				if((tree.getActiveNode().getLevel()==1)){
				
					if(tree.getActiveNode().data.key!=$( "#mapa_inicial option:selected" ).val()){
				//	alert(tree.getActiveNode().data.key);
						if (confirm('¿Estas seguro de eliminar el mapa '+tree.getActiveNode().data.title+' ?' )){ 
							
    
							console.log("Mpa a borrar: "+tree.getActiveNode().data.key);
							$.ajax({
									type: "POST",
									url: "BorraMapaSrv",
									async: false,
									data:{idmapa:tree.getActiveNode().data.key},
									success: function(data) {
											var rootNode = $("#tree3").dynatree("getRoot");								
											rootNode.tree.reload();
									   }
								}
								);
						}
					}else{
						alert("No puedes borrar el mapa actual, para borrarlo debes seleccionar otro mapa actual");
					
					}
					

				}else{
					alert("No es posible borrar hijos.");
				}
			}else{
				alert("Seleccione el mapa a borrar.");
				
			}
		});
		$( "#guardarMapa" ).button().click(recorreTree);
		
		$( "#guardarConf" ).button().click(function(){
		$.ajax({
							type: "POST",
							url: "ActualizaSettings",
							async: false,
							data:{email:$("#email").val(), intervalo:$("#intervalo").val(), mapa:$( "#mapa_inicial option:selected" ).val()},
							success: function(data) {
									if(data){
										alert("Los datos se guardaron satisfactoriamente");
										//Borro el contenido de los contenedores de mapas 
										$('#universe').remove();
										$('#container2').html("");
										//cargo mapa nuevo
										 cargaMapaInicial();
										 //clearInterval(refreshInterval);
										 //refreshInterval = setInterval(refreshStatus, $("#intervalo").val() * 1000);
										 
										 if($('#chk_status').hasClass('stop')){
													clearInterval(refreshInterval);
													refreshInterval = setInterval(refreshStatus, $("#intervalo").val() * 1000);
										  }
										 
										 
										 
									}else{
										alert("Hubo un error al intentar guardar");
									}

							   }
						}
						);

		}
		
		);
		$( "#guardarEvento" ).button().click(function(){
		recorreTree();
		$.ajax({
							type: "POST",
							url: "ActualizaSetting1",
							async: false,
							data:{evento:$( "#tipo_evt option:selected" ).val(), accion: $( "#tipo_action option:selected" ).val()},
							success: function(data) {
									if(data){
										alert("Los datos se guardaron satisfactoriamente");
									}else{
										alert("Hubo un error al intentar guardar");
									}

							   }
						}
						);

		}		
		);
			
			
			
//Export

$("#exportIMG").bind('click', function(){
console.log("CLICK MENU EXPORT SUP");


		$("#btn_export").hide();
		$("#msg_export").show();
		
		$("#export").dialog( "open" );	
		
		var svg = $('#mapa').html().replace(/>\s+/g, ">").replace(/\s+</g, "<");
			//alert(svg);
			canvg('canvas_export', svg, {renderCallback: function(){        
				console.log("SE creo la imagen");
				//Se oculta el mensaje de generacion
				$("#msg_export").hide();
				//muestra boton de descarga
				$("#btn_export").show();
				
			} });
				
	});	
	
$("#btn_export").button().click(function (){


		var oCanvas = document.getElementById("canvas_export");  
  
		Canvas2Image.saveAsPNG(oCanvas);
		$("#export").dialog( "close" );	


});

			
	
	
	$("#shiftMap_drag").draggable({
							containment: "#shiftMap",
							start: function(){
								//se esta arrastrando el minimapa.
								b_dragMap=true;
							},
							drag: function(e){
							/*pasos:
							se obtiene la diferencia entre la equina de shiftMap y 
							la esquina de el shiftMap_drag, obtengo cuantos px los separa.
							Mediante una operacion obtengo a cuanto equivale esa separacion
							al desplazaciento del scroll.
							128                          _      totaWidhDocument
							separacion(shift-shiftdrag)  -       X  
							
							*/			
							
					            //Desplazamiento Horizontal

							var left=$("#shiftMap_drag").position().left -$(window).scrollLeft() ;
							var left2= $("#shiftMap").position().left -$(window).scrollLeft() ;

							//la esquina - wid_esquina es el tamaño
							tama=left-left2;
							var des=(tama * ($(document).width()-0))/$("#shiftMap").width();
							//console.log("Esto es el desplazamiento");
							//console.log(des);
							$(window).scrollLeft(des);
							
							//Desplazamiento vertical
							var top=$("#shiftMap_drag").position().top -$(window).scrollTop() ;
							var top2= $("#shiftMap").position().top -$(window).scrollTop() ;

							//la esquina - wid_esquina es el tamaño
							tama=top-top2;
							var des=(tama * ($(document).height()-0))/$("#shiftMap").height();
							//console.log("Esto es el desplazamiento");
							//console.log(des);
							$(window).scrollTop(des);
							
							
							
							},
							stop: function(e, ui){
								//se dejo de usar el minimapa
								b_dragMap=false;
							}
							
							
							});
							
							
	$(window).scroll( function(e){
		// si se esta arrastrando el minimapa no se actualiza el minimapa central(seria redundante)
		if(!b_dragMap){
		console.log("se esta actualizando minimapa");
								/*
								Procedimiento:
								obtengo la equina de el shiftMap y le sumo el desplazamiento del scrool trasformado
								a su equivalente a la distancia entre shiftMap y shiftMap_drag.
								
								128 --totalWidthDocument
								X __  scroll
								
								Ejemplo:
								 La esquina es 1135, a esto le tengo que sumar el escrol transformado a su
								 equivalente a escala en el minimapa, con la regla de 3.
								 
								nota: en el desplazamiento tambien puede usarse, la diferencia entre
								el tamaño del shiftMap_drag y el ShiftMap, esto  es lo que se desplaza el scrool.
								
								*/
		
		
	
								//desplazamiento minimapa horizontal miniMapa
								
								var left2= $("#shiftMap").position().left -$(window).scrollLeft() ;
								var despla= ($(window).scrollLeft() *$("#shiftMap").width()) / $(document).width() ;
								$("#shiftMap_drag").css('left',(left2-0)+despla);  
								
								//desplazamiento minimapa vertical Minimapa
								var top2= $("#shiftMap").position().top -$(window).scrollTop() ;
								despla= ($(window).scrollTop() *$("#shiftMap").height()) / $(document).height() ;
								$("#shiftMap_drag").css('top',(top2-0)+despla);  
		}							
								
								//Desplazamiento en la ventana
	
	
	}	
	
	);						
	
	
	//cambia el tipo de monitorieo
	$('#opc_ping').click(function(){
		$('#puerto').attr("disabled", true); 
		$('#ping').addClass("negritas"); 
		
	});
	
	$('#opc_puerto').click(function(){
		$('#ping').removeClass("negritas"); 
		$('#puerto').removeAttr("disabled"); 
	});


	
	
	
		/*  $('body').bind("mousedown", function(evt){
		  	   $('.m_Tab').removeClass("active");
		  });
		  */
		
	
	}
	
	 function asignaEventosRoot(){
	 
	 
	//////////////////////////////////////////////////////////////
	
	
	//MenuContextual del Root

	var menu1 = [
	  {'Agregar dispositivo':function(menuItem,menu) {   showModalFormDispositivo(menu);   } },
	  $.contextMenu.separator,
	  {'Configuración':function(menuItem,menu) { alert("You clicked Option 2!"); } }
	];
	$(function() {
	  $('#universe').contextMenu(menu1,{theme:'osx'});
	});		 
	 
	 
	 
	 	  $(Root).bind("mousedown.canvas", function(evt){
	  
	  

	  
	  //Oculto menu desplegables del menu superior der
	  $('.m_Tab').removeClass("active");
	  
		if(evt.target==Root){
			console.log("click en canvas");
			console.log("__"+evt.target);
			
			//quito la sombra de los seleccionados
			
			var xSel = $('.selected_device');
			
			console.log("numero de seleccionados"+xSel.length);
			
			
			
			for(i=0;i<xSel.length;i++){
						elemento=elementos[formas.indexOf(xSel.get(i))];
						svg.hide(elemento.select);
						$(xSel.get(i)).removeClass('selected_device');
			}
			
			//$('.selected_device').removeClass('selected_device');
			
			
			//si esta seleccionado algun menu desplegable superior, se oculta
			$('.m_Tab').removeClass("active");
			
			//si esta remarcada una linea se oculta
			svg.hide(svg.select_line.node);
			
			var pageX=evt.originalEvent.pageX;
		    var pageY=evt.originalEvent.pageY;
			
			
		    svg.selector.x=pageX;
			svg.selector.y=pageY;
			svg.selector.funcion(pageX,pageY);
//			svg.show(svg.selector.rec);
			
			$(Root).bind("mousemove.canvas_mousemove", function(evt){
				Root.setAttribute("style", "cursor:auto;");
				
				var pageX=evt.originalEvent.pageX;
				var pageY=evt.originalEvent.pageY;
			
		
				svg.selector.funcion(pageX,pageY);
				//SE muestre el selector solo al primer movimiento
				if(svg.selector.nuevaSeleccion){
					svg.selector.nuevaSeleccion=false;
					svg.show(svg.selector.rec);
				}
				
			});
			
		}	
	
	  });
	 
	 
	 
	 	  $(Root).bind("mouseup.canvas", function(evt){
		$(Root).unbind(".canvas_mousemove");
		
		
		svg.seleccionados();
		svg.hide(svg.selector.rec);
		svg.selector.nuevaSeleccion=true;
		
	  });
	 
	 
	 }
	
	
	function cargaConfiguraciones(){
	
	//Se carga el Tree(scriptTree)
	
	cargaTree();
	
	//Se cargan las demas configuraciones
						$.ajax({
							type: "POST",
							url: "ListaMapasSettings",
							async: false,
							success: function(jsonObj) {
								//alert(jsonObj.d);
								console.log(jsonObj);
									var data = eval(jsonObj);
									console.log(data);
									var j= data;
									var options='<option value="0">Seleccionar</option>';
									console.log("regreso");
								//	j=jsonObj;
									console.log(j);
									console.log(j.length);
								for(var ix=1; ix<j.length;ix++) {
								console.log(j[ix].id);
								console.log(j[ix].nombre);
								console.log(ix);
									options += '<option value="' + j[ix].id + '"' + j[ix].sel +'>' + j[ix].nombre + '</option>';
								  }
								  $("#mapa_inicial").html(options);
								  
								  $("#tipo_evt").val(j[0].evento);
								  $("#tipo_action").val(j[0].accion);
								  $("#intervalo").val(j[0].intervalo);
								  $("#email").val(j[0].email);
								  
								  
								  

							   }
						}
						);

	}