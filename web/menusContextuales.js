function   creaMenuContextual(elementos,tipo) {
	
	//alert(elementos[0].funcion);
					ul=document.getElementById('lista');
				//	alert(tipo);
					li=ul.getElementsByTagName('li');

					 if(ul.tipo==tipo){
						//Ya existe no se realiza nada.
						 
					 }
					 else{
						//se crea el nuevo menu
						ul.tipo=tipo;
						while (li.length>0) {
							ul.removeChild(li[li.length-1]);
						}
						 
						
						for(i=0;i<elementos.length;i++)
						 {
								 li = document.createElement("li");
								 var img;
								 
								  // alert('imagen' in elementos[i]);
								 
								 if('imagen' in elementos[i]){
									img = document.createElement("img");
									img.src=elementos[i].imagen;
									li.appendChild(img);
									
								 }
								 li.innerHTML += elementos[i].nombre;
								//li.appendChild(img);
								 
								 
								 //li.insertBefore(img, li.firstChild);
								 $(li).click(elementos[i].funcion);
								 //_reynol.addClick(li,elementos[i].funcion);
								 //li.appendChild(a);
								 
								 
								 //if (ul.firstChild) {
								//	ul.insertBefore(li, ul.firstChild);
								//} else {
									ul.appendChild(li);
								//}
								
						 }
						 
					 
					 
					 }
	}
		
		
		




function addMenuContextualDispositivo(e,el){

conector.DOM_dispositivoTarget1=el;
creaMenuContextual([
		{nombre: "Conectar",
		 imagen: "conectar.png",
         funcion: function() {
             
			conectarDispositivos();
			
 
          }},
		{ nombre: "Desconectar",
		imagen: "desconectar.png",
          funcion: function() {
 
            alert('...');
 
          }},
		{ nombre:"Eliminar",
		imagen: "eliminar.png",
          funcion: function() {
 
            alert('..');
 
          }},
		{ nombre:"Configuracion",
		  imagen: "configurar.png",
          funcion: function() {
 
            alert('..');
 
          }
		}
        
 
      ], "listaElem"




);


document.getElementById("menuv").style.top=e.clientY;
document.getElementById("menuv").style.left=e.clientX;
document.getElementById("menuv").style.display="block";


return false;
}

function addMenuContextualRoot(evt,el){

e=evt;
temp=el;

creaMenuContextual( [
		{nombre: "Nuevo Dispositivo ",
		 imagen:"computer_add.png",
         funcion: function() {
 
            addNuevoDispositivo(e);
 
          }},
		{ nombre: "Agregar Nota",
		  imagen:"nota.png",
          funcion: function() {
 
            alert(elementos);
 
          }},
		{ nombre:"Modo Grid",
		  imagen:"grid.png",
          funcion: function() {
				
			}
		}
        
 
      ], "listaRoot"




);


document.getElementById("menuv").style.top=evt.clientY;
document.getElementById("menuv").style.left=evt.clientX;
document.getElementById("menuv").style.display="block";


return false;
}