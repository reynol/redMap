function cargaTree(){
	/*$.ajax(
		{
        url: 'ArbolMapas',
		async:false,
        type: "POST",
		cache:false,
        success: function(data){
                console.log("TreeData: "+data);
				treeData=eval(data);
				
				$("#tree3").dynatree({
			checkbox: true,
			selectMode: 3,
			children: treeData,
			onSelect: function(select, node) {
			
			},
			onDblClick: function(node, event) {
				node.toggleSelect();
			},
			onKeydown: function(node, event) {
				if( event.which == 32 ) {
					node.toggleSelect();
					return false;
				}
			},
			idPrefix: "dynatree-Cb3-"
		});
		
		}
});*/

	$("#tree3").dynatree({
			checkbox: true,
			selectMode: 3,
			initAjax: {
					url: "ArbolMapas"
			},
			onSelect: function(select, node) {
			
			},
			onDblClick: function(node, event) {
				node.toggleSelect();
			},
			onKeydown: function(node, event) {
				if( event.which == 32 ) {
					node.toggleSelect();
					return false;
				}
			},
			idPrefix: "dynatree-Cb3-"
		});
		
		
		
		/*
		 $("#tree").dynatree({
      // In real life we would call a URL on the server like this:
//          initAjax: {
//              url: "/getTopLevelNodesAsJson",
//              data: { mode: "funnyMode" }
//              },

      initAjax: {
        url: "sample-data1.json"
        },
      onActivate: function(node) {
        $("#echoActive").text(node.data.title);
      },
      onDeactivate: function(node) {
        $("#echoActive").text("-");
      }
    });
*/

}

function recorreTree(){
				$(".dynatree-partsel").each(function(){
					var padre = $.ui.dynatree.getNode(this);
					/*
					var selectedNodes = node.getSelectedNodes();
            var selectedKeys = $.map(selectedNodes, function(node){
                return node.data.key;
            });
            console.log("Selected keys: " + selectedKeys.join(", "));
					
					*/
				console.log("entro recorreTree");
					//console.log(node);
					var hijos=padre.getChildren();
					for(var i=0; i<hijos.length;i++){
						//if(hijos[i].bSelected){
								console.log(hijos[i].data.key);
								guardarCambiosTree({idmapa:padre.data.key, iddevice:hijos[i].data.key,evento: ((hijos[i].bSelected)?1:0) });
					//	}
						
					}
					
					//console.log(node.data.title);
				});

}

function guardarCambiosTree(dataNodo){
console.log(dataNodo);
$.ajax(
		{
        url: 'ActualizaEventosSrv',
		async:false,
        type: "POST",
        success: function(data){
			console.log("nodo actualizado con exito");
		},
		data: dataNodo
});

}
