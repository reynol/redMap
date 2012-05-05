function reynol() {
//var reynol= function () {
var     doc = document,
        win = window,
		svg = {
		  svgns : "http://www.w3.org/2000/svg",
		  xlink : "http://www.w3.org/1999/xlink",
		  canvas: false,
		  grupo: null,
		  work_area: null
		  };
		  

	this.svgRoot=null;		
	this.targetDocument = null;

	this.dragTarget = null;
	this.mouse = null;
	this.downMouse = null;
	this.prevMouse = null;
	this.mouseText = null;

	// TODO: everything is now crappy :/
	this.mousemove = null;
	this.mouseup = null;
	
	this.selector={
	x:null,
	y:null,
	nuevaSeleccion:true,
	funcion:null,
	el:null,
	toggle: null,
	numero:0
	};
	this.select_line=null;
	
	this.menuContex=null;
//	this.conector={
//		funcion: null,
//		linea:null
//	};
	//lleva el conteo del numero de imagenes
	this.id=0;
	//lleva el target al hacer mausedown
	this.targertMouseDown=null;
	//lleva el tipo de mouseMove, normal ó Pan
	//pan onmouseDown, null onmouseup
	this.tipoMove='';
	//inicia el modo pan al primer click
	this.activaPan=null;
	//controlan MouseMove Pan
	var  stateTarget, stateOrigin, stateTf;
	
	
	var _reynol = this;
	


this.init=function(svg){

     _reynol.menuContex=document.getElementById("menuv");
	 _reynol.svgRoot=svg;
	 _reynol.targetDocument = _reynol.svgRoot.ownerDocument;
	 
	 if(navigator.userAgent.toLowerCase().indexOf('webkit') >= 0)
		window.addEventListener('mousewheel', _reynol.mouseWheel, false); // Chrome/Safari
	else
		window.addEventListener('DOMMouseScroll', _reynol.mouseWheel, false); // Others
	 
	 
	 

/*
	 
	this.svgRoot.onmousedown = function(e) {
		_reynol.downMouse = _reynol.prevMouse = _reynol.getMouseCoords(e, svg);
		//_reynol.selector.x=_reynol.prevMouse.x;
		//_reynol.selector.y=_reynol.prevMouse.y;
		_reynol.targertMouseDown=e.target;
		
		var parent = document.getElementById( "resultados" );
        var texto="("+_reynol.prevMouse.x+", "+_reynol.prevMouse.y;
		parent.innerHTML=texto;
		//quita los select y cambia iconos(si es que hay seleccionados)
				//regreso numero de seleccionados 0
					_reynol.selector.numero=0;
					//quita y pone los seleccionados
					_reynol.iconos();

		if (e.target.oncontextmenu&&e.button != 2){
			
				_reynol.menuContex.style.display="none";

				//el selector solo se sibuja sobre un svg//panel root
				if (e.target.nodeName=="svg"){
				
				//esto no hace nada util coloca un cero en la ezquina
				var parent = document.getElementById( "resultados" );
                var texto="<br>";
				parent.innerHTML=_reynol.selector.numero;
				
				
				
					//activo el selector en el mouseMove
					_reynol.selector.toggle=true;
					
					//ubico el selector donde se dio el click   // 
					//Evita que el selector  se quede con las dimenciones de la selecciona anterior
				//	_reynol.selector.funcion(e.clientX,e.clientY);
				//	//muestra el selector
					_reynol.show(_reynol.selector.rec);
				}
				
				
				
				
		}
		
		if(_reynol.activaPan=='pan')
			_reynol.panMouseDown(e);
		
		
		e.preventDefault();
	};
	this.targetDocument.onmousedown = function(e) {
		if(_reynol.activaPan=='pan')
			_reynol.panMouseDown(e);
		
		
		//e.preventDefault();
	};
	this.targetDocument.onmouseup = function(e) {
	if(_reynol.tipoMove == 'pan') {
			// Quit pan mode
			_reynol.tipoMove= '';
		}
		
		
		if(e.preventDefault)
			e.preventDefault();

		e.returnValue = false;
	};

	this.targetDocument.onmousemove = function(e) {
	
	if(_reynol.tipoMove=='pan'){
	 _reynol.panMouseMove(e);
	
	}else{
			_reynol.mouse = _reynol.getMouseCoords(e, svg);
			if (_reynol.mousemove) _reynol.mousemove(e);
			//alert(_reynol.selector.funcion);
			//if (_reynol.selector.toggle) _reynol.selector.funcion(e.clientX,e.clientY);
			
			   _reynol.prevMouse = _reynol.mouse;
			
			e.preventDefault();
		}
	};

	// well this certainly needs to be cleaned out :(
	this.svgRoot.onmouseup = function(e) {
		
		var target = _reynol.dragTarget;
		//selector hide 
		
		
		_reynol.selector.toggle=null;
		//solo si se hizo clieck inicialmente en el SVG
		if(_reynol.targertMouseDown.nodeName=="svg")
			_reynol.seleccionados();
			
		//se sombrean solo si el numero de seleccionados es >0
			if(_reynol.selector.numero>0)_reynol.iconos();
		_reynol.hide(_reynol.selector.rec);
		
		
		if (_reynol.mouseup) {_reynol.mouseup(e);
		
		}
		
		if (_reynol.conector.funcion) {
		    _reynol.conector.funcion(e);
			
		}
		
		if(_reynol.tipoMove == 'pan') {
			// Quit pan mode
			_reynol.tipoMove= '';
		}
		
		
		if(e.preventDefault)
			e.preventDefault();

		e.returnValue = false;
	};*/
}


this.Linea = function (x1, y1,x2,y2, stroke_w,stroke,id) {
            var el = doc.createElementNS(svg.svgns, "line");
            el.setAttribute("x1", x1);
            el.setAttribute("y1", y1);
            el.setAttribute("x2", x2);
            el.setAttribute("y2", y2);
	    
           // el.setAttribute("stroke", stroke);
	el.setAttribute("style","stroke-width:"+stroke_w+";");
	el.setAttribute("stroke-opacity", ".5");
	el.setAttribute("stroke", stroke);
	// el.setAttribute("style", "stroke:blue");
	el.setAttribute("id", id);
            if (svg.grupo) {
                svg.grupo.appendChild(el);
            }
            var res = new Element(el, svg);
			
            return res;
        };

		
this.Grupo = function () {
            var el = doc.createElementNS(svg.svgns, "g");
            el.setAttribute("id", "viewport");
            el.setAttribute("transform", "");
            if (svg.canvas) {
				svg.grupo=el;
                svg.canvas.appendChild(el);
            }
            var res = new Element(el, svg);
			
            return res;
};			
	

		
		
this.Path = function (x, y, r,fill,stroke, id) {
            var el = doc.createElementNS(svg.svgns, "path");
            el.setAttribute("cx", x);
            el.setAttribute("cy", y);
            el.setAttribute("r", r);
            el.setAttribute("fill", fill);
            el.setAttribute("stroke", stroke);
			el.setAttribute("id", id);
            if (svg.canvas) {
                svg.canvas.appendChild(el);
            }
            var res = new Element(el, svg);
			
            return res;
        };		
var Element = function (node, svg, nombre, ip,sel) {
            //DOM
	    this.node = node;
        this.svg = svg;
	    this.txt_nombre=nombre;
	    this.txt_ip=ip;
	    //Valores
	    this.b_puerto=false;  // indica si esta en modo puerto.
	    this.nombre=nombre;
	    this.puerto=null;
	    this.ip=ip;
            this.paths = []; // conexiones asignadas al elemento.
		//selector usado al seleccionar el elemento
		this.select=sel;
			
        };
this.enlaces = function (destino, path) {
            this.destino = destino;
            this.path = path;
        };		
this.Rectangulo = function (x, y, w, h, r,fill,stroke, opacity) {
            var el = doc.createElementNS(svg.svgns, "rect");
            el.setAttribute("x", x);
            el.setAttribute("y", y);
            el.setAttribute("width", w);
            el.setAttribute("height", h);
	    //el.setAttribute("style", "cursor:pointer;");
            if (r) {
                el.setAttribute("rx", r);
                el.setAttribute("ry", r);
            }
            el.setAttribute("fill",fill);
			el.setAttribute("fill-opacity",opacity);
            el.setAttribute("stroke", stroke);
			el.setAttribute("id", "selector");
            if (svg.grupo) {
                svg.grupo.appendChild(el);
            }
            var res = new Element(el, svg);

            return res;
        };

this.Imagen = function (src,cursor, x, y, w, h, id,clase) {
//alert("imagen");
	    var g = doc.createElementNS(svg.svgns, "g");
	   // g.setAttribute("style", "fill:red; stroke:#000000");
	    //=""
	    var txt_nom = doc.createElementNS(svg.svgns, "text");
	    txt_nom.setAttribute("x", x);
            txt_nom.setAttribute("y", y);
	    txt_nom.setAttribute("font-family", "verdana");
	    txt_nom.setAttribute("font-size", "10");
	    txt_nom.setAttribute("fill", "black");
	    txt_nom.setAttribute("font-weight", "bold")
	    var txt_ip = doc.createElementNS(svg.svgns, "text");
	    txt_ip.setAttribute("x", x);
            txt_ip.setAttribute("y", y+58);
	    txt_ip.setAttribute("font-family", "verdana");
	    txt_ip.setAttribute("font-size", "10");
	    txt_ip.setAttribute("fill", "black");
	    txt_ip.setAttribute("font-weight", "bold");
	     
            var el = doc.createElementNS(svg.svgns, "image");
	  
	  
		//creo el selector
		var selector=_reynol.Rectangulo(x, y, w, h, "","#b1b2b1","none", ".5").node;
		
		//oculto el selector
		_reynol.hide(selector);
		
	    
	    g.appendChild(el);
		g.appendChild(selector);
	    g.appendChild(txt_nom);
	    g.appendChild(txt_ip);
	    
		
	    
	 //   g.setAttribute('transform',"translate("+x+","+y+")");
            el.setAttribute("x", x);
            el.setAttribute("y", y);
            el.setAttribute("width", w);
            el.setAttribute("height", h);
	    el.setAttribute("style", "cursor:"+cursor+";");
            el.setAttributeNS(svg.xlink, "href", src);
			el.setAttribute("id", id);
			el.setAttribute("class", clase);
			
            if (svg.grupo) {
                svg.grupo.appendChild(g);
            }
			
			
			//se crea el nuevo elemento
            var res = new Element(el, svg, txt_nom, txt_ip,selector);
            return res;
        };
this.Texto = function (svg, x, y, text) {
            var el = doc.createElementNS(svg.svgns, "text");
            el.setAttribute("x", x);
            el.setAttribute("y", y);
            el.setAttributeNS(null,"font-family", "verdana");
	    el.setAttributeNS(null,"font-size", "10");
	    el.setAttributeNS(null,"fill", "black");
	    //font-family="Verdana" font-size="55" fill="blue" 

            if (svg.canvas) {
                svg.canvas.appendChild(el);
            }
            var res = new Element(el, svg);

            return res;
        };
 
    
		
this.create = function () {
            // container, width, height
            // x, y, width, height
            if (typeof arguments[0] == "string") {
                var container = doc.getElementById(arguments[0]);
                var width = arguments[1];
                var height = arguments[2];
            }
            if (typeof arguments[0] == "object") {
                var container = arguments[0];
                var width = arguments[1];
                var height = arguments[2];
            }
            if (typeof arguments[0] == "number") {
                var container = 1,
                    x = arguments[0],
                    y = arguments[1],
                    width = arguments[2],
                    height = arguments[3];
            }
            if (!container) {
                throw new Error("SVG container not found.");
            }
            svg.canvas = doc.createElementNS(svg.svgns, "svg");
			this.svgRoot=svg.canvas;
			_reynol.Grupo();
			this.init(svg.canvas);
			 
            svg.canvas.setAttribute("width", $(window).width());
            svg.canvas.setAttribute("height", $(window).height());
			svg.canvas.setAttribute("id", "universe");
			
			console.log($(window).width());
			console.log($(window).height());
			
			//svg.work_area= this.Rectangulo( 0, 0, $(window).width(), $(window).height(), "", "#fff","none","0.1").node;
			this.select_line= this.Linea(0,0,0,0,9,"#3879d9","select_line");
			//(x1, y1,x2,y2, r,stroke,id
			_reynol.addSelector(this.Rectangulo( 0, 0, 0, 0, "", "#fff","none","0.5").node);
			
			
			
			
            if (container == 1) {
                doc.body.appendChild(svg.canvas);
                svg.canvas.style.position = "absolute";
                svg.canvas.style.left = x + "px";
                svg.canvas.style.top = y + "px";
            } else {
                if (container.firstChild) {
                    container.insertBefore(svg.canvas, container.firstChild);
                } else {
                    container.appendChild(svg.canvas);
                }
            }
            
            
            return container;
        };
		

		//*******************************************************************************************************************//////////
		
		
		

	// INIT global mouse handlers
	
	// TODO: maybe elem or element, instead of shape?

	
	this.hide = function(elem) {
		elem.style.display = "none";
	}
	
	this.show = function(elem) {
		elem.style.display = "";
	}


	


	

	
	// translate(<tx>  [<ty>])
	
	this.translate = function(elem, dx, dy) {
					matrixG = svg.grupo.getCTM().inverse();
//alert(matrixG.d);
//alert(matrixG.f);
		elem.setAttributeNS(null,'x',(dx-24)+matrixG.e||0);	
		elem.setAttributeNS(null,'y',(dy-24)+matrixG.f||0);

	}
	
	
	// hopefully return the mouse coordinates inside parent element
	this.getMouseCoords = function(e, parent) {
		var x, y;

		x=e.clientX;
		y=e.clientY;
		return { x: x, y: y };
	}
	
	
	
	this.randomInt = function(max) {
		return Math.floor(Math.random() * max);
	}
	
	this.creaMenuContex = function(e, elementos,tipo) {
	
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
								 _reynol.addClick(li,elementos[i].funcion);
								 //li.appendChild(a);
								 
								 
								 //if (ul.firstChild) {
								//	ul.insertBefore(li, ul.firstChild);
								//} else {
									ul.appendChild(li);
								//}
								
						 }
						 
					 
					 
					 }
	}
		
	this.addSelector=function(el){
		_reynol.selector.rec=el;
		_reynol.selector.funcion=function(x,y){
			//var parent = document.getElementById( "resultados" );
			//var texto="("+_reynol.selector.x+", "+_reynol.selector.y;
			//parent.innerHTML=texto;	
			//cuadrante 1
				/*if(_reynol.selector.x-x<0&&_reynol.selector.y-y>0){
				_reynol.selector.rec.setAttributeNS(null,'x',_reynol.selector.x);
					_reynol.selector.rec.setAttributeNS(null,'y',y);
					_reynol.selector.rec.setAttributeNS(null,'width',x-_reynol.selector.x);
					_reynol.selector.rec.setAttributeNS(null,'height',_reynol.selector.y-y);
				
				}*/
				//cuadrante 1
				if(x-_reynol.selector.x>0&&_reynol.selector.y-y>0){
				_reynol.selector.rec.setAttributeNS(null,'x',_reynol.selector.x);
					_reynol.selector.rec.setAttributeNS(null,'y',y);
					_reynol.selector.rec.setAttributeNS(null,'width',x-_reynol.selector.x);
					_reynol.selector.rec.setAttributeNS(null,'height',_reynol.selector.y-y);
				
				}
				else
				
				if(_reynol.selector.x-x>0&&_reynol.selector.y-y<0){
				_reynol.selector.rec.setAttributeNS(null,'x',x);
					_reynol.selector.rec.setAttributeNS(null,'y',_reynol.selector.y);
					_reynol.selector.rec.setAttributeNS(null,'width',_reynol.selector.x-x);
					_reynol.selector.rec.setAttributeNS(null,'height',y-_reynol.selector.y);
				
				}else
				
				if(_reynol.selector.x-x>0&&_reynol.selector.y-y>0){
					_reynol.selector.rec.setAttributeNS(null,'x',x);
					_reynol.selector.rec.setAttributeNS(null,'y',y);
					_reynol.selector.rec.setAttributeNS(null,'width',_reynol.selector.x-x);
					_reynol.selector.rec.setAttributeNS(null,'height',_reynol.selector.y-y);
				}else{
				       if(_reynol.selector.x-x<0&&_reynol.selector.y-y<0){
					_reynol.selector.rec.setAttributeNS(null,'x',_reynol.selector.x);
					_reynol.selector.rec.setAttributeNS(null,'y',_reynol.selector.y);
					_reynol.selector.rec.setAttributeNS(null,'width',x-_reynol.selector.x);
					_reynol.selector.rec.setAttributeNS(null,'height',y-_reynol.selector.y);
					}
				
				}
				
			
		
		}
	
	}
	
	this.seleccionados=function(){
	//alert(formas.length);
	
	 var sel=_reynol.extremos(_reynol.selector.rec), elem;
	 //var cont=0;
	 var elemento;
	 
		if(formas.length>0){

			for(i=0;i<formas.length;i++){
				elem=_reynol.extremos(formas[i]);
				//Arriba, Abajo (centro, izq, der)
				if(elem.arriba<sel.arriba&&elem.abajo>sel.arriba||(elem.abajo>sel.abajo&&elem.arriba<sel.abajo)){
					if((elem.izq>sel.izq&&elem.der<sel.der)||(elem.der>sel.izq&&elem.der<sel.der)||(elem.izq<sel.der&&elem.izq>sel.izq)){
						_reynol.selector.numero++;
						//formas[i].setAttribute("class","selected");
						console.log(formas[i]);
						$(formas[i] ).addClass('selected_device');
						
						//se muestra el cuadro gris sobre la imagen indicando que esta seleccionado
						elemento=elementos[formas.indexOf(formas[i])];
						_reynol.show(elemento.select);
						
						}
				}
				//izquierda y derecha
				if(elem.arriba>sel.arriba&&elem.abajo<sel.abajo){
					if((elem.izq>sel.izq&&elem.der<sel.der)  //centro
					||(elem.der>sel.izq&&elem.der<sel.der)   //der
					||(elem.izq<sel.der&&elem.izq>sel.izq)	 //izq
					||(elem.izq<sel.izq&&elem.der>sel.der)){ // seleccion linea centro
						_reynol.selector.numero++;
						console.log(formas[i]);
						$(formas[i] ).addClass('selected_device');
						
						//formas[i].setAttribute("class","selected");
						
						//se muestra el cuadro gris sobre la imagen indicando que esta seleccionado
						elemento=elementos[formas.indexOf(formas[i])];
						_reynol.show(elemento.select);
						
					}
				}

			}
		}
		//alert(cont);
			
	}
	
	this.extremos=function(el){
		var box=el.getBBox();
		return {
			izq:box.x,
			der:box.x+box.width,
			arriba:box.y,
			abajo:box.y+box.height
		};
	
	}
	/**
	
	pone y quita los iconos seleccionados
	se los seleccionados agrega o quita Nombre  _sel.  png
	
	
	**/
	this.iconos=function(el){
			if(formas.length>0&&_reynol.selector.numero>0){
				el_selec=$('.selected');
				for(i=0;i<_reynol.selector.numero;i++){
					src=el_selec.get(i).getAttribute("href").replace(".","_sel.");
					el_selec.get(i).setAttributeNS(svg.xlink, "href", src);
					
					
				}
		}else{
				el_selec=$('.selected');
				for(i=0;i<el_selec.size();i++){
					  src=el_selec.get(i).getAttribute("href").replace("_sel","");
					  el_selec.get(i).setAttributeNS(svg.xlink, "href", src);
					  el_selec.get(i).removeAttribute('class');
				}
		
		}
	}
	
	
this.mouseWheel=function(evt) {
	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

	var svgDoc = evt.target.ownerDocument;

	var delta;

	if(evt.wheelDelta)
		delta = evt.wheelDelta / 3600; // Chrome/Safari
	else
		delta = evt.detail / -90; // Mozilla

	var z = 1 + delta; // Zoom factor: 0.9/1.1

	var g = svgDoc.getElementById("viewport");
	
	var p = getEventPoint(evt);

	p = p.matrixTransform(g.getCTM().inverse());

	// Compute new scale matrix in current mouse position
	var k = root.createSVGMatrix().translate(p.x, p.y).scale(z).translate(-p.x, -p.y);

        setCTM(g, g.getCTM().multiply(k));

	if(typeof(stateTf) == "undefined")
		stateTf = g.getCTM().inverse();

	stateTf = stateTf.multiply(k.inverse());
}


	this.setMousePan=function() {
		
		// Pan mode
			_reynol.activaPan = 'pan';
		
	}
	this.removeMousePan=function() {
		
		// Pan mode
			_reynol.activaPan = '';
			 
	}
	

	this.panMouseDown=function(evt) {
			_reynol.tipoMove= 'pan';
			stateTf = svg.grupo.getCTM().inverse();

			stateOrigin = getEventPoint(evt).matrixTransform(stateTf);
			
			
				//alert(stateTf.d);
				//alert(stateTf.f);
				//alert(evt.clientX);
				//alert(evt.clientY);
			
		 
	}	
	
this.panMouseMove =function (evt) {
	if(evt.preventDefault)
		evt.preventDefault();

	evt.returnValue = false;

		// Pan mode
		var p = getEventPoint(evt).matrixTransform(stateTf);

		setCTM(svg.grupo, stateTf.inverse().translate(p.x - stateOrigin.x, p.y - stateOrigin.y));
	
}


function getEventPoint(evt) {
	var p = svg.canvas.createSVGPoint();

	p.x = evt.clientX;
	p.y = evt.clientY;

	return p;
}

function setCTM(element, matrix) {
	var s = "matrix(" + matrix.a + "," + matrix.b + "," + matrix.c + "," + matrix.d + "," + matrix.e + "," + matrix.f + ")";

	element.setAttribute("transform", s);
}


	
}