	function refreshStatus(){
		var start = new Date();
var random = Math.random() + start.getTime();

$.getJSON("ChecarStatusSrv", {
                random: random
            }, function(data){
                //aqui empieza nuestro codigo (Callback json)
                //llenamos el select con los dias
				//console.log("Actualizando Status");
				//console.log(data);
				//console.log(data.tipoMapa);
				
                var devices = data;
			//	console.log(datadata.tipoMapa);
							console.log("Numero de Dispositivos: "+ devices.length);
						//	console.log(devices);
							for(var i=0; i< devices.length;i++){					
								
								//si hay tipo de mapa significa que es mapa de nivel superior, si no es de dispositivos
								if(devices[i].tipoMapa){
									//status OK
									if(devices[i].status==1)
										fillColor($('#'+devices[i].nombre).get(0),'#5b5b5b');
									else
										fillColor($('#'+devices[i].nombre).get(0),'#ff0000');
								 
								}else{								
										$('#'+devices[i].id).get(0).setAttributeNS("http://www.w3.org/1999/xlink", "href", devices[i].imagen);
										el=elementos[formas.indexOf($('#'+devices[i].id).get(0))];
										for(j=0;j<el.paths.length;j++){	 
										
											
											if(devices[i].status==2){
												$('#'+el.paths[j].path).get(0).setAttribute("stroke","#f09900");
											}
											if(devices[i].status==1){
												$('#'+el.paths[j].path).get(0).setAttribute("stroke","#ff0000");
											}
											if(devices[i].status==0){
												$('#'+el.paths[j].path).get(0).setAttribute("stroke","#00ff00");
											}
											
												$('#'+el.paths[j].path).get(0).setAttribute("stroke-opacity", ".65");		
										
										}
									}
									
							}

				
				
				});
		
		
		
		
		
		
		
	console.log("munuto");

	}