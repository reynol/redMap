	function getCentro(el){
	
				var	obj1 = el;
					
				var bb1 = obj1.getBBox();
				var centro={
					x:	bb1.x + bb1.width / 2,
					y:	bb1.y + bb1.height / 2
				};
				
				return centro;
				
	}
//Radio indica si se debe activar el checkbox puerto, y si tiene un puerto	
function crearDispositivo(imagen, nombre,id, direccion, puerto, radio){
if(puerto=="")puerto=0;
	$.ajax(
		{
        url: 'nuevoItemSrv',
        type: "POST",
        success: function(data){
		 var id=data;
		 if(id){
			
			console.log('datos enviados correctamente'); console.log(data);
			insertarDispositivo(imagen, nombre, id , direccion , puerto, radio);
			
		 }
		
		},   
		data:{item: $.toJSON({id:id,posX: mouseDownX, posY: mouseDownY, img: imagen, nombre:nombre, direccion:direccion, puerto: puerto})}
//        contentType: "application/json"
    });

}

function actualizaInfoDispositivo(nombre, direccion, puerto,radio, nueva_imagen){
//obtener el elemento apartir del DOM
var element=elementos[formas.indexOf($(conector.DOM_dispositivoTarget1).get(0))];
//actualizar datos
	$.ajax(
		{
        url: 'ActualizaInfoItemSrv',
        type: "POST",
        success: function(data){
		 var ok=data;
		 if(ok){
			
			console.log('Dispositivo actualizado correctamente'); 
		
				if(radio=='puerto'){
						element.txt_ip.textContent =  (puerto)?direccion+":"+puerto:direccion;
						element.b_puerto=true;
						element.puerto=puerto;
					}else{
						element.txt_ip.textContent = direccion;
						element.b_puerto=false;
					}

					//actualiza nueva imagen

					element.node.setAttributeNS("http://www.w3.org/1999/xlink", "href", nueva_imagen);
					element.txt_nombre.textContent = nombre;
					element.nombre=nombre;
					element.ip=direccion;
			
		 }
		
		},   
		data:{item: $.toJSON({id:$(element.node).attr('id'), img: nueva_imagen, nombre:nombre, direccion:direccion, puerto: ((radio=='puerto')?puerto:0)})}

    });


}

function actualizaPosDispositivo(id,X, Y){

$.ajax(
		{
        url: 'ActualizaPosItemSrv',
        type: "POST",
        success: function(data){
		 var id=data;
		 if(id){
			
			console.log('Disp actualizado correctamente'); console.log(data);
			
			
		 }
		
		},   
		data:{id:id,posX: X, posY: Y}
//        contentType: "application/json"
    });

}


function conectarDispositivos(){
 //alert(dispositivo1.id); 
            //conector(e,temp);
			document.getElementById("menuv").style.display="none";
			var centro=getCentro(conector.DOM_dispositivoTarget1);
			nuevaLinea(svg.Linea(centro.x, centro.y, centro.x, centro.x, 5,"black",Color()));
				
			//temp=elementos[formas.indexOf(el)];
			
			//Obtengo la linea creada
			elemento=lineas[lineas.length-1].node;
			
			addEventosLinea(elemento);
				
			

			$(Root).bind("mousemove.conectaDispositivos", function(e){
								
								elemento.setAttributeNS(null, "x2", e.originalEvent.pageX);
								elemento.setAttributeNS(null, "y2", e.originalEvent.pageY);
		
									 
								 
							}); 
			$(Root).bind("mouseup.conectaDispositivos", function(e){
									
									dispositivo1=elementos[formas.indexOf(conector.DOM_dispositivoTarget1)];
									dispositivo2=elementos[formas.indexOf(e.target)];
									//alert(temp);
									//alert(obj2);
									//Actualizo el ID del path una vez conocido el origen y el destino
									
									elemento.setAttributeNS(null, "id", $(dispositivo1.node).attr('id')+'-'+$(dispositivo2.node).attr('id'));
			
									console.log($(dispositivo1.node).attr('id'));
									console.log($(dispositivo2.node).attr('id'));
									
									// se crea la conexion
									conexion(dispositivo1,dispositivo2);
									
									//valoresoriginales para futuras ligas y no inferir en onmouseup
									//svg.conector.linea=null;
									//svg.conector.funcion=null;
									$(this).unbind(".conectaDispositivos");
									
									
									
							
							
							
						});
						//nue
			
}	


	