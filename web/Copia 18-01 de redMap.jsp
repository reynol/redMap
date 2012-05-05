<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);

session = request.getSession(false);
Object usuario=session.getAttribute("logon");
if(usuario==null){
 response.sendRedirect("index.jsp");
 }
%>

<html>
<head>
<title> tabletest </title>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta name="expires" content="Wed, 01 Jan 1997 00:00:00 GMT"/>

<link rel="stylesheet" href="stilo.css" type="text/css" media="screen">

<script src="menusContextuales.js" type="text/javascript"></script>


	<link rel="stylesheet" type="text/css" media="screen" href="menus.css" />
	<link rel="stylesheet" href="jquery-ui/jquery-ui.css"> 
	<link rel="stylesheet" href="jquery.contextmenu.css"> 	
	
	<link rel="stylesheet" type="text/css" href="mnu2.css" />
	
	<script src="jquery-ui/jquery-1.6.min.js"></script> 
	<script src="jquery.contextmenu.js" type="text/javascript"></script>
	<script src="jquery-ui/jquery.ui.core.js"></script> 
	<script src="jquery-ui/jquery.ui.widget.js"></script> 
	<script src="jquery-ui/jquery.ui.progressbar.js"></script> 
	<script src="jquery-ui/jquery.ui.mouse.js"></script> 
	<script src="jquery-ui/jquery.ui.button.js"></script> 
	<script src="jquery-ui/jquery.ui.draggable.js"></script> 
	<script src="jquery-ui/jquery.ui.position.js"></script> 
	<script src="jquery-ui/jquery.ui.dialog.js"></script> 
	
	<script src="jquery-ui/jquery.svg.min.js"></script> 
	<script src="jquery-ui/jquery.svgdom.min.js"></script> 
	
	<script src="jquery-ui/jquery.json-2.3.min.js"></script> 

	<script src="JS-UI/accionesCanvas.js"></script> 
	<script src="JS-UI/accionesDispositivo.js"></script> 
	<script src="JS-UI/scriptCargaInicial.js"></script> 
	<script src="JS-UI/scriptLineas.js"></script> 
	<script src="JS-UI/scriptNuevoDispositivo.js"></script> 
	<script src="JS-UI/scriptUpdates.js"></script> 
	<script src="JS-UI/scriptCargaBase.js"></script> 
	<script src="JS-UI/scriptEventosBase.js"></script> 
	
<script src="reynoljsJQUERY.js" type="text/javascript"></script>


		<style> 
		body { font-size: 80.5%; }
		label, input { display:block; }
		input.text { margin-bottom:12px; width:95%; padding: .4em; }
		fieldset { padding:0; border:0; margin-top:25px; }
		h1 { font-size: 1.2em; margin: .6em 0; }
		div#users-contain { width: 350px; margin: 20px 0; }
		div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
		div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
		.ui-dialog .ui-state-error { padding: .3em; }
		.validateTips { border: 1px solid transparent; padding: 0.3em; }
	</style> 
<script>
var conexiones=[], formas=[], elementos=[], lineas=[],lineasDOM=[], svg, Root,band=false;
var temp,e, item_menu_temp;
var menuContex=document.getElementById("menuv");
var mouseDownX,mouseDownY; // guarda las posiciones X y Y donde se creara un nuevo dispositivo.
var nuevoDevice = false;  // indica si se crea un nuevo dispositivo(true) o se mudifica un existente (false)
var b_dragMap=false;  // indica si se esta arrastrando el miniMapa
conector={
		b_conectorCentral: false,  		// indica si los dispositivos tendran imagen de conector central
		DOM_itm_cnct:null,			//contiene el elemento dom del conectorCentral
		DOM_dispositivoTarget1:null,		//contiene el elemento dom del dispositivo origen del conector
		DOM_dispositivoTarget2:null		//contiene el elemento dom del dispositivo destino del conector
};




	
window.onload = function () {
	  

$("#progressbar").progressbar({ value: 1 });  
//Carga los scripts

cargaBase();

$("#progressbar").progressbar({ value: 50 });  
				
	//cargar menu Tool Bar	
	cargaMenuToolBar();
	//carga el shift Map
	loadShiftMap();
$("#progressbar").progressbar({ value: 60 });  

	svg= new reynol();
	svg.create('container',640,480);

	Root=svg.svgRoot;
	
$("#progressbar").progressbar({ value: 65 });  	


// Carga dispositivos en canvas.
	cargaDevices();
	console.log("termino carca devices");
$("#progressbar").progressbar({ value: 80 });  	
//Eventos sobre la pagina base

eventosBase();
$("#progressbar").progressbar({ value: 100 });  
	  
	  
	
	root=Root;
	
$('#carga').hide('slow');		
$('#container').show();
$('#m_izq').show();
$('#toolbar').show();
$('#shiftMap').show();	
	
	
	
};






function Color(){

	return "rgb("+parseInt(Math.random()*255)+","+parseInt(Math.random()*255)+","+parseInt(Math.random()*255)+")"

}

function getRadioButtonSelectedValue()
{
var ctrl=document.frmSO.so;
    for(i=0;i<ctrl.length;i++)
        if(ctrl[i].checked) return ctrl[i].value;
}


		
		

		


	
	</script>

  <style type="text/css" media="screen">
    .ui-selected, .ui-unselecting {
      border: 5px solid purple !important;
    }

    .box {
      cursor: move;
      position: absolute;
      left: 0;
	   background: url(imagenes/canvas/workstation_sel.png) no-repeat;
	   z-index: 999;
    }
    
  
    
    /* Endless desktop, Panning and scrolling */
    #scroll-trigger {
      background: transparent url(img/scroll_small.png) no-repeat top left;
      position: absolute;
      bottom: 0;
      right: 0;
      width: 32px;
      height: 32px;
      cursor: pointer;
    }

    #scroll-overlay {
      background: transparent url(img/scroll_big.png) no-repeat center center;
      position: absolute;
      z-index: -1;
      cursor: move;
    }

    #scroll-overlay BUTTON {
      position: absolute;
     /* bottom: 5px;*/
      margin: 0 auto;
      font-size: 120%;
      left: 0;
      right: 0;
      width: 100px;
    }

    #container {
    /*  position: absolute; 
        /*background-color: #e5ecf9;*/
	/*background: #e5ecf9 url(grid_BG.png);*/
      top: 0px;
      left: 0;
      right: 0;
      bottom: 0;
	  
display: none; 

      /*border: 0px solid #7aa5d6;*/
      /*overflow: hidden;*/
    }

    #universe {
      position: absolute; 
      background: #e5ecf9 url(grid_BG.png);
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
    /*  border: 1px dotted #000;*/ /* uncomment this to debug endless desktop */
    }    

    
    .device {
     
      background: #000;
      height: 50px; 
      width: 50px; 
    /*  border: 1px dotted #000;*/ /* uncomment this to debug endless desktop */
    }        
    
	#toolbar_iner {
		margin: 20px 4px;
		text-align:center;
		position: fixed;
		top: 0%;
		left: 0;
		height: 430px; 
		width: 38px; 
		opacity: 1; 
		display: block; 
		
		  background:#ededed;
		 /* border:solid 1px #666;
		  border-width: 1px 0 0 1px;*/
		  
		  /*padding:2px 5px;*/
		  overflow:hidden; 
		  /* CSS3 */
		  -webkit-border-top-right-radius: 4px;
		  -moz-border-radius-topright: 4px;
		  border-top-right-radius: 4px;
		  -webkit-border-bottom-right-radius: 4px;
		  -moz-border-radius-bottomright: 4px;
		  border-bottom-right-radius: 4px;
		  
		  -webkit-border-top-left-radius: 4px;
		  -moz-border-radius-topleft: 4px;
		  border-top-right-radius: 4px;
		  -webkit-border-bottom-left-radius: 4px;
		  -moz-border-radius-bottomleft: 4px;
		  border-bottom-left-radius: 4px;
		  
		  
		  /*horizontal vertical difuminado color*/
		  -moz-box-shadow: 0px 3px 3px rgba(0,0,0,0.5);
		  -webkit-box-shadow: 0px 3px 3px rgba(0,0,0,0.5);
		  box-shadow: 0px 3px 3px rgba(0,0,0,0.5);

	}
	
	
	#toolbar {
		position: fixed;
		text-align:center;
		top: 0%;
		left: 0;
		height: 456px; 
		width: 46px; 
		opacity: 1; 
		display: none; 
		
		  background:#5b5b5b;
		  border:solid 1px #404040;
		  border-width: 1px;
		  
		 /*padding-top:10px ;*/
		  overflow:hidden; 
		  
		  -moz-box-shadow: inset  -1px 1px 0px #797979; 
		  /*-webkit-box-shadow: inset  0px 0px 2px #797979; 
		  box-shadow: inset  0px 0px 2px #797979; */

	}

	#Map {

	}
	
	#shiftMap {
	position: fixed;
	bottom: 0%;
		right: 0;
		height: 0px; 
		width: 0px; 
		opacity: 1; 
		display: none; 
		
		  background:#5b5b5b;
		  border:solid 1px #404040;
		  border-width: 1px;
		  
		  overflow:hidden; 
		  
		  -moz-box-shadow: inset  -1px 1px 0px #797979; 
		  -webkit-box-shadow: inset  -1px 1px 0px #797979; 
		  box-shadow: inset  -1px 1px 0px #797979; 
		  
		  -moz-box-shadow:   -3px 3px 10px #797979; 
		  -webkit-box-shadow:   -3px 3px 10px #797979; 
		  box-shadow:  -3px 3px 10px #797979; 
		  
	}	
	
	#shiftMap_drag {
		text-align:center;
		position: fixed;
		height: 0px; 
		width: 0px; 
		opacity: 1; 
		display: block; 
		
		  background:#ededed;

		  overflow:hidden; 
		  /* CSS3 */

		  
		  
		  /*horizontal vertical difuminado color*/
	/*	  -moz-box-shadow: 0px 3px 3px rgba(0,0,0,0.5);
		  -webkit-box-shadow: 0px 3px 3px rgba(0,0,0,0.5);
		  box-shadow: 0px 3px 3px rgba(0,0,0,0.5);
*/		  

	}
	
	
	#resultados{
	top: 600;
      
      right: 0;
	
	}
 
    
    .item_selected { 
		text-align:center;
		width: 26px; 
		height: 26px;
		margin-left:5px;
		
		 background:#ffffff;
		 border:solid 1px #797979;
		 
  
		  /* CSS3 */
		  -webkit-border-top-right-radius: 4px;
		  -moz-border-radius-topright: 4px;
		  border-top-right-radius: 4px;
		  -webkit-border-bottom-right-radius: 4px;
		  -moz-border-radius-bottomright: 4px;
		  border-bottom-right-radius: 4px;
		  
		  -webkit-border-top-left-radius: 4px;
		  -moz-border-radius-topleft: 4px;
		  border-top-right-radius: 4px;
		  -webkit-border-bottom-left-radius: 4px;
		  -moz-border-radius-bottomleft: 4px;
		  border-bottom-left-radius: 4px;

   
   }
.item_over { 
		text-align:center;
		width: 26px; 
		height: 26px;
		margin-left:5px;
		
		 background:#fefefe;
		 border:solid 1px #bebebe;
		 
  
		  /* CSS3 */
		  -webkit-border-top-right-radius: 4px;
		  -moz-border-radius-topright: 4px;
		  border-top-right-radius: 4px;
		  -webkit-border-bottom-right-radius: 4px;
		  -moz-border-radius-bottomright: 4px;
		  border-bottom-right-radius: 4px;
		  
		  -webkit-border-top-left-radius: 4px;
		  -moz-border-radius-topleft: 4px;
		  border-top-right-radius: 4px;
		  -webkit-border-bottom-left-radius: 4px;
		  -moz-border-radius-bottomleft: 4px;
		  border-bottom-left-radius: 4px;
		  /*horizontal vertical grado  (cero en ver y ori  poner sombra en todos los contornos)*/
		  -moz-box-shadow: inset  -1px -1px 5px #cccccc; 
		  -webkit-box-shadow: inset  -1px -1px 5px #cccccc; 
		  box-shadow: inset  -1px -1px 5px #dddddd; 
		  
   
   }
   
.item_conector {

background-color: #EEEEEE;
width: 60px; 
height: 60px;
z-index: 500;      
}


.radio {
  width: 19px;
  height: 15px;
  padding: 0 5px 0 0;
  display: block;
  clear: left;
  float: left;
}

.bloque {
  height: 40px;
/*  margin: 0 0 20px 0;*/
  padding: 0 10px 0 0;
  display: block;
  font: 12px/21px arial,sans-serif;
}
   
.form_bloque_der {
  width: 200px;
  height: 40px;
  clear: right;
  float: right;
}
.form_bloque_izq {
  font: 12px/21px arial,sans-serif;
  font-weight: bolder; 
  width: 100px;
  height: 40px;
  clear: left;
  float: left;
}
.negritas{
 font-weight: bolder; 
}
.select {
  width: 190px;
  height: 25px;
  font: 12px/21px arial,sans-serif;
}
   
   #progressbar {
  width: 400px;
  height: 19px;
}
 #carga {
 margin: 40px 0 0 40px;
  font: 12px/21px arial,sans-serif;
    font-weight: bolder; 
}
   
  </style>

</head>

<body  style="cursor: auto;" >


  <div id="container">

		<div id="mapa">
		</div>

  		
   <div class="menu"  style="position: absolute; z-index: 9998;">
	<!--
	m_izq produce una sombra.. se coloca z-indez abajo del padre para que la sombra no aparezca sobre el menu desplegable que 
	esta al nivel de "menu"
	-->
	<div id="m_izq" style="z-index: 9990;">
	
        <ul>
	
	
	    <li class="m_Tab">
                <div id="guardar" class="m_itm"></div>
            </li>
	    
	    <li class="m_Tab">
                <div id="rayo" class="m_itm"></div>
            </li>
	    
            <li class="m_Tab">
	                    
		<div id="chart" class="m_itm"></div>
		
            </li>
            <li class="m_Tab">
                <div id="" class="m_sub gear"></div>
                      <div class="m_desp" >
			
			<ul>
			    <li><a href="">Opciones</a></li>
			    <li><a href="http://localhost:8084/ProbandoJSON_WEB/LogoutSrv">Salir</a></li>
			    <li></li>
			</ul>
			
		</div>
            </li>

        </ul>
	
	</div>
    </div>


	<div id="scroll-trigger" title="Haga clic para desplazarse por la pantalla" > </div>   
  </div>
  <div id="scroll-overlay" style="display: none">
    <div><button id="scroll-done">OK</button></div>
  </div>
		
		
		






	<div id="menuv" style="position: absolute; z-index: 500; left: 603px; top: 148px; display: none; ">
		<ul id="lista"></ul>
	</div>
	
	<div id="dialog-form" title="Configuracion"> 
	<form> 
		<div class="bloque">
			<div class="form_bloque_izq">
				Tipo
			</div>
			<div class="form_bloque_der">
				<select id="tipo" class="select">
					<option value="imagenes/canvas/workstation.png">PC</option>
					<option value="imagenes/canvas/laptop.png">Laptop</option>
					<option value="imagenes/canvas/server.png">Servidor</option>
					<option value="imagenes/canvas/impresora.png">Impresora</option>
					<option value="imagenes/canvas/wireless.png">Wireless</option>
					<option value="imagenes/canvas/router.png">Router</option>
					<option value="imagenes/canvas/switch.png">Switch</option>
					<option value="imagenes/canvas/firewall.png">FireWall</option>
					<option value="baz">Otro</option>
				</select>
			</div>
		</div>
		<div class="bloque">
			<div class="form_bloque_izq">
				Dirección:
			</div>
			<div class="form_bloque_der">
				<input type="text" name="direccion" id="direccion" value="127.0.0.1" class="text ui-widget-content ui-corner-all" /> 
			</div>
		</div>
		<div class="bloque">
			<div class="form_bloque_izq">
				<input type="radio" name="grupoRadio" class="radio" id="opc_ping" value="ping" checked/> Ping  
			</div>
			<div id="ping" class="form_bloque_der negritas">
				ICMP/ECHO
			</div>
		</div>
		<div class="bloque">
			<div class="form_bloque_izq">
				<input type="radio" name="grupoRadio" class="radio" id="opc_puerto" value="puerto" /> Puerto  
			</div>
			<div class="form_bloque_der">
				<input type="text" name="puerto" id="puerto" value="" class="text ui-widget-content ui-corner-all" disabled />
			</div>
		</div>
		<div class="bloque">
			<div class="form_bloque_izq">
				<label for="nombre">Nombre(Opcional)</label> 
			</div>
			<div class="form_bloque_der">
				<input type="text" name="nombre" id="nombre" value="" class="text ui-widget-content ui-corner-all" /> 
			</div>
		</div>
	
	</form> 
</div> 
	
<div id="toolbar" >
<a href="#min" id="minimize" title="Minimize"> 
		<img alt="Workstation" src="imagenes/toolbar/collapse.png" style="display: inline;">
		</a>
		
<div id="toolbar_iner"> 

		</div>

		</a>

</div>
<div id="Map">	
<div id="shiftMap">	
	<div id="shiftMap_drag">	
	</div>	
</div>	
</div>	

		
<div>
<div id='mc'></div>
</div>


<div id="carga">

<p>Cargando informacion, favor de esperar...</p>

<div id="progressbar"></div>
</div>








</body>
 
</html>