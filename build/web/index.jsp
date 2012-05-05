<%@ page contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1);

session = request.getSession(false);
Object usuario=session.getAttribute("logon");

if(usuario!=null){
 response.sendRedirect("redMap.jsp");
 }
%>
<html>
    <head>
        <title>Iniciar Sesion</title>
        <meta http-equiv="Expires" content="<%= new java.util.Date() %>"/>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="Red, Monitoreo, Graficar Red" />
        <meta name="keywords" content="Red, Mapeo, Monitoreo, Graficar, Web"/>
		
                
                
		<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon"/>
        <link rel="stylesheet" type="text/css" href="css/style.css" />
		<script src="js/cufon-yui.js" type="text/javascript"></script>
		<script src="js/ChunkFive_400.font.js" type="text/javascript"></script>
		<script type="text/javascript">
		window.onload = function () {
		$('.wrapper').hide();
				$.ajax(
						{
						url: 'probandoSesion',
						async:false,
						type: "POST",
						success: function(data){

								if(data){
									console.log("hay sesion");
									window.location="./redMap.jsp";
								}else{
									$('.wrapper').show();	
								}

				}
				});	
								
				Cufon.replace('h1',{ textShadow: '1px 1px #fff'});
				Cufon.replace('h2',{ textShadow: '1px 1px #fff'});
				Cufon.replace('h3',{ textShadow: '1px 1px #000'});
				Cufon.replace('.back');
			}
		
			
			
		</script>
    </head>
    <body>
		<div class="wrapper">
			<h1>redMap</h1>
			<h2> Monitoreo de red</h2>
			<div class="content">
				<div id="form_wrapper" class="form_wrapper">
					<form class="login active" type="POST" action="LoginSrv">
						<h3>Iniciar sesion</h3>
						<div>
							<label>Usuario:</label>
							<input type="text" name="user"/>
							<span class="error">">Error: Intente nuevamente</span>
						</div>
						<div>
							<input type="password" name="pass"></input>
							<span class="error">Error: Intente nuevamente</span>
						</div>
						<div class="bottom">
							<input type="submit" value="Iniciar sesion"></input>
							<div class="clear"></div>
						</div>
					</form>
					<form class="forgot_password">
						<h3>¿Olvidaste tu contraseña?</h3>
						<div>
							<label>Usuario or Email:</label>
							<input type="text" />
							<span class="error">This is an error</span>
						</div>
						<div class="bottom">
							<input type="submit" value="Enviar"></input>
							<a href="index.html" rel="login" class="linkform">Regresar al inicio de sesion</a>							
							<div class="clear"></div>
						</div>
					</form>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		

		<!-- The JavaScript -->
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
		<script type="text/javascript">
			$(function() {
					//the form wrapper (includes all forms)
				var $form_wrapper	= $('#form_wrapper'),
					//the current form is the one with class active
					$currentForm	= $form_wrapper.children('form.active'),
					//the change form links
					$linkform		= $form_wrapper.find('.linkform');
						
				//get width and height of each form and store them for later						
				$form_wrapper.children('form').each(function(i){
					var $theForm	= $(this);
					//solve the inline display none problem when using fadeIn fadeOut
					if(!$theForm.hasClass('active'))
						$theForm.hide();
					$theForm.data({
						width	: $theForm.width(),
						height	: $theForm.height()
					});
				});
				
				//set width and height of wrapper (same of current form)
				setWrapperWidth();
				
				/*
				clicking a link (change form event) in the form
				makes the current form hide.
				The wrapper animates its width and height to the 
				width and height of the new current form.
				After the animation, the new form is shown
				*/
				$linkform.bind('click',function(e){
					var $link	= $(this);
					var target	= $link.attr('rel');
					$currentForm.fadeOut(400,function(){
						//remove class active from current form
						$currentForm.removeClass('active');
						//new current form
						$currentForm= $form_wrapper.children('form.'+target);
						//animate the wrapper
						$form_wrapper.stop()
									 .animate({
										width	: $currentForm.data('width') + 'px',
										height	: $currentForm.data('height') + 'px'
									 },500,function(){
										//new form gets class active
										$currentForm.addClass('active');
										//show the new form
										$currentForm.fadeIn(400);
									 });
					});
					e.preventDefault();
				});
				
				function setWrapperWidth(){
					$form_wrapper.css({
						width	: $currentForm.data('width') + 'px',
						height	: $currentForm.data('height') + 'px'
					});
				}
				
				/*
				for the demo we disabled the submit buttons
				if you submit the form, you need to check the 
				which form was submited, and give the class active 
				to the form you want to show
				*/
		/*		$form_wrapper.find('input[type="submit"]')
							 .click(function(e){
								e.preventDefault();
							 });	
							 */
			});
        </script>
    </body>
</html>