function efectosMapa(){

colorOn = '#396d93';// #bf2e23 --rojo
colorOff = '#5b5b5b';
colorRojo= '#ff0000';

var edos =  $('.EDO');
//console.log(edos.length);

var menu1 = [
			  {'Entrar':function(menuItem,menu) {

						//alert($(menu.target).attr('id'));
						
						

							
							
							
							} 
							
							
							},
			  $.contextMenu.separator,
			  {'Configurar':function(menuItem,menu) { 
			  
				  $.ajax({
							type: "POST",
							url: "ListadoMapas",
							data: {edo:$(menu.target).attr('id')},
							async: false,
							success: function(jsonObj) {
								//alert(jsonObj.d);
									var j=eval(jsonObj);
									var options='<option value="0">Seleccionar</option>';
									console.log("regreso");
								//	j=jsonObj;
									console.log(j);
									console.log(j.length);
								for(var ix=0; ix<j.length;ix++) {
								console.log(j[ix].id);
								console.log(j[ix].nombre);
								console.log(ix);
									options += '<option value="' + j[ix].id + '"' + j[ix].sel +'>' + j[ix].nombre + '</option>';
								  }
								  $("#edo_tipo").html(options);
								   $("#edo_nom").val($(menu.target).attr('id'));
								
								$( "#edo_form" ).dialog( "open" );
								
							   }
						}
						);
				
				 
			  }
			  }];
						 // var elem = elementos[formas.indexOf(form)];
					


for(var i=0; i< edos.length; i++){
console.log($(edos[i]).attr('id'));
	edos[i].style.setProperty("cursor", "pointer", "");
	
	$(edos[i]).bind("mouseover", function(evt){
		//edos[i].style.setProperty("fill", colorOff, "");
		//console.log($(this));
		fillColor($(this).get(0),colorOn);
	});
	
	$(edos[i]).bind("mouseleave", function(evt){
		//edos[i].style.setProperty("fill", colorOff, "");
		//console.log($(this));
		if($(this).hasClass('rojo'))
			fillColor($(this).get(0),colorRojo);
		else
			fillColor($(this).get(0),colorOff);
	});
	
	$(edos[i]).bind("click", function(evt){
	
	 
	//alert('Click sobre el estado de: ['+this.id+']');
	//alert('Click sobre el estado de: ['+$(this).attr('id')+']');
	console.log("envio: "+$(this).attr('id'));
	cargaMapaEdo($(this).attr('id'));
	
	 
	});
	
	$(edos[i]).contextMenu(menu1,{theme:'osx'});
	
}


}
function fillColor(e,c){
console.log(e.tagName);
if(c=='#ff0000')
$(e).addClass('rojo');
else
if(c==colorOff)
$(e).removeClass('rojo');


	if(e.tagName != 'g' ){
		e.style.setProperty("fill", c, "")
		return
	}
	paths = e.getElementsByTagName('path')
	for(j=0; j< paths.length; j++)
		paths[j].style.setProperty("fill", c, "")
}

function loadDone(svg, error){
//    svg.text(10, 20, error || 'Loaded into ' + this.id); 
efectosMapa();
console.log(error || 'Loaded into ' + this.id);
}

function resetSize(svg, width, height) {
	svg.configure({width: width || $(svg._container).width(),
		height: height || $(svg._container).height()});
}

function cargaMapaEdo(nom){
$.ajax(
		{
        url: 'CargaMapaEdoSrv',
		async:false,
        type: "POST",
        success: function(data){
                console.log("REgres: "+data);
				if(data){
					$('#container2').hide();
					cargaMapa2();
				}
				
				
				
		}, data: {edo:nom}
});


}


/**
* 1= Existe mapa, 2 =Existe mapa pero no esta cargado, 0= No existe mapa
*/
function subeParentMap(){
var existeMapaSuperior=0;
$.ajax(
		{
        url: 'SetParentMAp',
		async:false,
        type: "POST",
        success: function(data){
			if(data){
				
				//Verifico que el mapa superior este cargado
				if($('#container2').hasClass('hasSVG')){
				
						existeMapaSuperior= 1;
						$('#container2').show();
						$('#toolbar').hide();
						$('#shiftMap').hide();	
						//Controles no funcionales en mapa superior
						//Controles mapa superior que no funcionan en mapas tipo 1
						$('#guardar').hide();	
						$('#exportIMG').hide();	
						$('#chart').hide();	

						
				}
				else{
					existeMapaSuperior= 2;
				
				}
			}

		}
});

return existeMapaSuperior;
}


function cargaMapa1(urlmapa){
	console.log("que paso: mapa="+urlmapa);
	$('#container2').removeClass('hasSVG');  
	
			$('#container2').svg();  

			var svg = $('#container2').svg('get'); 
			svg.load(urlmapa, {addTo: true, 
				changeSize: true, onLoad: loadDone}); 
			//resetSize(svg,650,442); 
			resetSize(svg); 


			
		 $('#container').addClass("bgBlue");
		 $('#container2').show();
//Se esconde el tool bar el mapshipt
$('#toolbar').hide();
$('#shiftMap').hide();	
//Controles mapa superior que no funcionan en mapas tipo 1
$('#guardar').hide();	
$('#exportIMG').hide();	
$('#chart').hide();	



		 
		$('#carga').hide('slow');		
		$('#container').show();

}

function cargaMapa2(){
		$("#progressbar").progressbar({ value: 50 });  
 
		$("#progressbar").progressbar({ value: 60 });  

			svg= new reynol();
			svg.create('mapa',640,480);

			Root=svg.svgRoot;
			
		$("#progressbar").progressbar({ value: 65 });  	


		// Carga dispositivos en canvas.
			cargaDevices();
			console.log("termino carca devices");
		$("#progressbar").progressbar({ value: 80 });  	
		//Eventos sobre la pagina base
		$("#progressbar").progressbar({ value: 100 });  
			  
			  
			
			root=Root;
			
		//Se asignan los eventos al nuevo canvas creado(scriptEventosBase)
			asignaEventosRoot();
			
		$('#carga').hide('slow');		
		$('#container').show();
	//	$('#m_izq').show();
		$('#toolbar').show();
		$('#shiftMap').show();	
		
		//Funciones solo disponibles en mapas tipo 2
		//Controles mapa superior que no funcionan en mapas tipo 1
		$('#guardar').show();	
		$('#exportIMG').show();	
		$('#chart').show();	

		
}
	