function cargaBase(){
reiniciarArrays();
console.log("entro carga base");
//Aqui se decide si carga o regresa al login
			cargaMapaInicial();
console.log("salio carga mapa");			
//Carga cvonfiguraciones (scriptEventosBase)
cargaConfiguraciones();			

//Se cargan los eventos base (munus, acciones sobre el area de trabajo)
			cargaMenuToolBar();
			//cargaMenuSuperior();
			eventosBase();	
			//cargar menu Tool Bar	
			//cargaMenuToolBar();
			//carga el shift Map
			loadShiftMap();



}

function cargaMapaInicial(){

//var isLogOn=false;
	$('#container2').removeClass('hasSVG'); 
$.ajax(
		{
        url: 'probandoSesion',
		async:false,
        type: "POST",
        success: function(data){
/*$.getJSON("http://localhost:8084/ProbandoJSON_WEB/probandoSesion", {
                random: random
            }, function(data){*/
                //aqui empieza nuestro codigo (Callback json)
                //llenamos el select con los dias
                 
				if(!data){
					console.log("No hay sesion");
					console.log(data);
					window.location="./index.jsp";
                }else{
				console.log("Si hay sesion");
				/*
					data=eval(data);
				
					console.log("Dispositivos: "+ data.length);
					
					for(var i=0; i< data.length;i++){
						
						var source = data[i];  
						var head = document.getElementsByTagName("head")[0];  
						var script = document.createElement("script");  
						script.type = "text/javascript";  
						script.src =  source  ;
						head.appendChild(script);  

					}
				*/
				console.log(data);
					 data = eval(data);
				console.log(data);
					if(data[0].tipomapa==1)
						cargaMapa1(data[0].urlmapa)
					else
						cargaMapa2();
					
				}
				
				
				
}
});

}

function reiniciarArrays(){
			conexiones=[];
			formas=[];
			elementos=[];
			 lineas=[];
			 lineasDOM=[]; 
			 svg=null;
			Root=null;
			band=false;
			nuevoDevice = false;  // indica si se crea un nuevo dispositivo(true) o se mudifica un existente (false)
			b_dragMap=false;  // indica si se esta arrastrando el miniMapa
			conector={
					b_conectorCentral: false,  		// indica si los dispositivos tendran imagen de conector central
					DOM_itm_cnct:null,			//contiene el elemento dom del conectorCentral
					DOM_dispositivoTarget1:null,		//contiene el elemento dom del dispositivo origen del conector
					DOM_dispositivoTarget2:null		//contiene el elemento dom del dispositivo destino del conector
			};
}