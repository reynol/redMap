

	


function nuevaLinea(linea){
//coloco la line al inici del SVG ( para que siempre queden abajo)

			inicio=document.getElementById("selector");
			
			linea.node.parentNode.insertBefore(linea.node,inicio);		

			lineasDOM.push(linea.node);
			lineas.push(linea);
	console.log("inserto linea"+inicio);		
}




function addEventosLinea (linea){

			$(linea).bind('mousedown',
				function(e){
				console.log("clik line");
				console.log(svg.select_line);
					var lin=svg.select_line.node;
					//se muestra la linea remarcada
					svg.show(svg.select_line.node);
					lin.setAttributeNS(null, "x1", e.target.getAttribute("x1"));
					lin.setAttributeNS(null, "y1", e.target.getAttribute("y1"));
					lin.setAttributeNS(null, "x2", e.target.getAttribute("x2"));
					lin.setAttributeNS(null, "y2", e.target.getAttribute("y2"));
					
				}
			);	
			
	var menu1 = [
	  $.contextMenu.separator,
	  {'Eliminar':function(menuItem,menu) { 
	  console.log("LA LINEA");
				console.log($(menu.target).attr('id'));
				console.log($(menu.target).attr('id'));
				console.log(lineas.length);
				removeLinea($(menu.target).attr('id'));
				console.log(lineas.length);
	  
	  } }
	];
	$(function() {
	  $(linea).contextMenu(menu1,{theme:'osx'});
	});		
}

//Actualiza las lineas al arrastre, o crea el vinculo entre 2 dispositivos si es nueva linea
function conexion(forma1, forma2, linea, inicial) {

        var obj1 = forma1.node;
        var obj2 = forma2.node;
		console.log("___");
		console.log(obj1);
			console.log(obj2);

		if(!linea){

				linea=lineas[lineas.length-1].node;
				var centro=getCentro(obj2);

				console.log(24+eval($(obj2).attr('x')));
				linea.setAttributeNS(null, "x2", (inicial)?eval($(obj2).attr('x'))+24:centro.x);
				linea.setAttributeNS(null, "y2", (inicial)?eval($(obj2).attr('y'))+24:centro.y);
			
			console.log($(obj1).attr('id'));
			console.log($(obj2).attr('id'));
			
			
			
			//inserto las rutas en los 2 elementos
				forma1.paths.push(new svg.enlaces($(obj2).attr('id'),$(linea).attr('id')));
				forma2.paths.push(new svg.enlaces($(obj1).attr('id'),$(linea).attr('id')));
				
				if(!inicial){
					$.ajax(
					{
					url: 'nuevoPathSrv',
					type: "POST",
					success: function(data){
					 var id=data;
					 if(id){
						
						console.log('AJAX LINK OK'); console.log(data);
						

					 }
					
					},   
					data:{path: $.toJSON({destino: $(obj2).attr('id') , id: $(linea).attr('id')}),
					item: $(obj1).attr('id')}
			//        contentType: "application/json"
					});
				}
				

		}else{
		//actualizo el path
				var centro1=getCentro(obj1);
				centro2=getCentro(obj2);
				linea.setAttributeNS(null, "x1", centro1.x);
				linea.setAttributeNS(null, "y1", centro1.y);
				linea.setAttributeNS(null, "x2", centro2.x);
				linea.setAttributeNS(null, "y2", centro2.y);
			
		
		
		}
	
}