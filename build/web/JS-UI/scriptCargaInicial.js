	
function cargaDevices(){
var start = new Date();
var random = Math.random() + start.getTime();


 
$.ajax(
		{
        url: 'jsonSrv',
		async:false,
        type: "POST",
        success: function(data){


/*
$.getJSON("http://localhost:8084/ProbandoJSON_WEB/jsonSrv", {
                random: random
            }, function(data){*/
                //aqui empieza nuestro codigo (Callback json)
                //llenamos el select con los dias
         var       devices = eval(data);
                
                
 /*        
devices=[
{id:'1', posX: 100, posY: 600, img:'imagenes/canvas/workstation.png', nombre:"PC", direccion:"127.0.0.01", puerto: "",
enlaces:[
{ destino:'4', id:'1-4'},
{ destino:'3', id:'1-3'},
{ destino:'2', id:'1-2'}
]
},
{id:'2', posX: 400, posY: 200, img:'imagenes/canvas/laptop.png', nombre:"Laptop", direccion:"127.0.0.01",  puerto: "",
enlaces:[
{ destino:'3', id:'2-3'},
{ destino:'4', id:'2-4'},
{ destino:'1', id:'1-2'}]
},
{id:'3', posX: 700, posY: 200, img:'imagenes/canvas/firewall.png', nombre:"Firewall", direccion:"127.0.0.01", puerto: "",
enlaces:[
{ destino:'4', id:'3-4'},
{ destino:'1', id:'1-3'},
{ destino:'2', id:'2-3'}]
},
{id:'4', posX: 500, posY: 500, img:'imagenes/canvas/switch.png', nombre:"Switch",direccion:"127.0.0.01", puerto: "",
enlaces:[
{ destino:'1', id:'1-4'},
{ destino:'2', id:'2-4'},
{ destino:'3', id:'3-4'}
]
}
];
*/
//inserto los dispositivos
console.log("Dispositivos: "+ devices.length);
for(i=0; i< devices.length;i++){
	mouseDownX=devices[i].posX;
	mouseDownY=devices[i].posY;
	console.log(devices[i].img+','+devices[i].id);
	insertarDispositivo(devices[i].img, devices[i].nombre, devices[i].id , devices[i].direccion , devices[i].puerto, (devices[i].puerto!=0)?'puerto':'' );
	
}
console.log("__ENLACES_");
//coloco sus enlaces

for(i=0; i< devices.length;i++){
	for(j=0; j< devices[i].enlaces.length;j++){
		//verifico si el enlace existe
		 temporal='#'+devices[i].enlaces[j].id;
		
		if($(temporal).length==0){
		console.log($(temporal).length);
		        console.log('Entro: '+'\'#'+devices[i].enlaces[j].id+'\'');
				
				//creo la linea 
				console.log($('#'+devices[i].id).get(0));
				var centro=getCentro($('#'+devices[i].id).get(0));
				
				console.log(centro.x);
				console.log(centro.y);
				
				nuevaLinea(svg.Linea(devices[i].posX+24, devices[i].posY+24, devices[i].posX+24, devices[i].posX+24, 5 ,"black",devices[i].enlaces[j].id));
				
				//obtengo la linea recien creada
				var lin=lineas[lineas.length-1].node;
				//añoso eventos click y menucontextual
				addEventosLinea(lin);
				
				dispositivo1=elementos[formas.indexOf($('#'+devices[i].id).get(0))];				
				
				dispositivo2=elementos[formas.indexOf($('#'+devices[i].enlaces[j].destino).get(0))];

			//	console.log(dispositivo1.node);
			//	console.log(dispositivo2.node);
				
			//	console.log($('#'+devices[i].id).get(0));
			//	console.log($('#'+devices[i].enlaces[j].destino).get(0));
				// disp 1 disp 2 false false
				conexion(dispositivo1,dispositivo2, false, true);
				console.log("termino enlace");
		}
	
	}
}


 }
 }
 );


}

	
function cargaMenuToolBar(){

		tools_attr=[
		{title: 'Herramienta Mover',img:'imagenes/toolbar/mover_tool.png'},
		{title: 'Herramienta Seleccion',img:'imagenes/toolbar/seleccion_tool.png'},
		{title: 'Herramienta Mover',img:'imagenes/toolbar/conector_tool.png'},
		];	
		for(i=0;i<tools_attr.length;i++){
		div=document.createElement("div");
		$(div).addClass("toolbar_item");

		a=document.createElement("a");
		$(a).attr('title','p');
		//a.title=tools_attr[i].title;
		$(a).addClass("item");
		div.appendChild(a);

		img=document.createElement("img");
		//img.src=tools_attr[i].img;
		$(img).attr('src',tools_attr[i].img);
		a.appendChild(img);


		$('#toolbar_iner').get(0).appendChild(div);
		}

		tools_attr=[
		{title: 'Desktop',img:'imagenes/canvas/workstation.png'},
		{title: 'Laptop',img:'imagenes/canvas/laptop.png'},
		{title: 'servidor',img:'imagenes/canvas/server.png'},
		{title: 'Impresora',img:'imagenes/canvas/impresora.png'},
		{title: 'Wireless',img:'imagenes/canvas/wireless.png'},
		{title: 'Firewall',img:'imagenes/canvas/firewall.png'},
		{title: 'Hub',img:'imagenes/canvas/router.png'},
		{title: 'Switch',img:'imagenes/canvas/switch.png'},
		{title: 'Router',img:'imagenes/canvas/network-router-blue.png'},
		{title: 'Monitor de red',img:'imagenes/canvas/network-monitor.png'},
		{title: 'Network Cloud',img:'imagenes/canvas/network-cloud.png'}
		];


		for(i=0;i<tools_attr.length;i++){
		div=document.createElement("div");
		$(div).addClass("toolbar_item");

		a=document.createElement("a");
		//a.title=tools_attr[i].title;
		$(a).attr('title','p');
		$(a).addClass("item");
		$(a).addClass("network");
		div.appendChild(a);

		img=document.createElement("img");
		//img.width='24';
		$(img).attr('width',24);
		$(img).attr('src',tools_attr[i].img);
		//img.src=tools_attr[i].img;
		a.appendChild(img);


		$('#toolbar_iner').get(0).appendChild(div);
		}


		//Clona los item menu que pueden ser arrastrado
		$('.network').draggable({
		appendTo: 'body',
		helper: 'clone', 
		stop: 
		function(event, ui) {
			//quito todos los seleccionados
			$('.toolbar_item').removeClass('item_selected');
			//Actualizo el lugar donde se solto el click
			var pageX=event.originalEvent.pageX;
			var pageY=event.originalEvent.pageY;
			
			mouseDownY=pageY-24;
			mouseDownX=pageX-24;
			/*
			console.log("CLIENT");
			console.log(clientY-24);
			console.log(clientX-24);
			console.log("PAGE");
			console.log(pageY-24);
			console.log(pageX-24);
			*/
			crearDispositivo($(item_menu_temp).attr('src'),"",0,"127.0.0.1");
		}}
		);






		$('.item').mousedown(seleccion_tool_item);


		 function seleccion_tool_item(evt){
				//obtengo el elemento imagen del menu item seleccionado
				item_menu_temp=evt.target;
				//alert(evt.target);
				//quito todos los seleccionados
				$('.toolbar_item').removeClass('item_selected');
				// Al precionar la img coloco al div como seleccionado (img -> a -> div) 
				$(evt.target).parent().parent().addClass('item_selected');
		 }


			$('#minimize img').toggle(
			function() {
				$('#minimize img').attr('src', 'imagenes/toolbar/expand.png');
				//$('#toolbar_iner').css('display', 'none');
				$('#toolbar_iner').animate({height:'0px', width: '38px', opacity: 1}, 300);
				$('#toolbar').animate({height:'15px', width: '46px', opacity: 1}, 300);
				
			}, 
			function() {
				$('#minimize img').attr('src', 'imagenes/toolbar/collapse.png');
				//$('#toolbar_iner').css('display', 'inline');
				$('#toolbar_iner').animate({height:'430px', width: '38px', opacity: 1}, 300);
				$('#toolbar').animate({height:'456px', width:'46px', opacity: 1}, 300);
			});


			
			
			cargaMenuSuperior();

			


}	

function cargaMenuSuperior(){

			/////////////////////////////////////////////////////////////
			////////////////////////////////////////////////////////////MAPA SUPERIOR
			////////////////////////////////////////////////////////////
			
			
	
	 $(".menu li").mouseover(function(e) {
	 //agrega el over solo si no esta activo.
		if (!$(this).hasClass('active'))
			$(this).addClass("m_over");
	  }).mouseout(function(e){
	    $(this).removeClass("m_over");
	  });
	  

	//Click sobre todo el menu superior
            $('.menu').click(
		function(e){
			//solo para tabs
			if ($(e.target).parent().hasClass('m_Tab')){
				//solo se activa si el Tab tiene submenu
				
					//si hay un tab activo se quita y es el mismo se deselecciona
					if ($(e.target).parent().hasClass('active')){
						$(e.target).parent().removeClass("active");
						$(e.target).parent().addClass("m_over");
					} else {
						//quito todos los seleccionados y selecciono solo si es un submenu
						$('.m_Tab').removeClass("active");
						if ($(e.target).hasClass('m_sub')){
						//quito el over	
							$(e.target).parent().removeClass("m_over");
						//despliego el submenu	
							$(e.target).parent().addClass("active");
						}	
					
					}
					
				
				
				
				
			}
			
			
						
		}
	    );

}

function loadShiftMap(){
 $("#shiftMap").css("height",$(window).height()*.10).css("width",$(window).width()*.10);  
 $("#shiftMap_drag").css("height",$(window).height()*.10).css("width",$(window).width()*.10);  
}

