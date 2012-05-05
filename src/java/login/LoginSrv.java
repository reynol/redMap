//    RedMap es un software de monitoreo de red diseñado para un ambiente web.
//    Autor: Reynol Zacapala.
//    http://www.reynol.net
//
//    This file is part of RedMap.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package login;


import database.conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet que procesa la solicitud del usuario de unicio de sesion.
 * @author Reynol Zacapala
 */
public class LoginSrv extends HttpServlet {

    /**
     * Procesa solicitudes HTTP para los metodos 
     * <code>GET</code> y
     * <code>POST</code>.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Pragma","no-cache");
	response.setDateHeader ("Expires", 0);
        PrintWriter out = response.getWriter();
        try {
String usuario=request.getParameter("user");
String contraseña=request.getParameter("pass");
  out.println("antes de la conexiom");
conexion c = new conexion();
  out.println("despues de la conexion");
System.out.println("Log: "+usuario);
System.out.println("pas: "+contraseña);
  out.println("antes de logueo");
ArrayList <Integer> login=c.logeo(usuario, contraseña);
  out.println("despues de logueo");
 if (login.get(0) ==0) {
      //out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
 
     response.sendRedirect("index.jsp");
    } else {
      // Valid login. Make a note in the session object.
      HttpSession session = request.getSession();
      session.setAttribute("logon", usuario);
      session.setAttribute("idusuario", login.get(1));
      // Couldn't redirect to the target. Redirect to the site's home page.
      
      //response.setHeader(usuario, usuario);

      
       //response.sendRedirect(response.encodeURL("redMap.jsp"));
      response.sendRedirect("redMap.jsp");
    }

            
        }catch(Exception e){
        out.println(e.getMessage());
    } finally {       
            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
