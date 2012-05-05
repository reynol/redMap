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
package monitoreo;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import json.Items;
import json.Path;

/**
 * Servlet usado en pruebas de GSON.
 * @author Reynol Zacapala.
 */
public class guardarSrv extends HttpServlet {
   
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
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet guardarSrv</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet guardarSrv at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */

             Gson gson = new Gson();
           //  Type typeOfT = new TypeToken<Collection<Items>>(){}.getType();
     //   Items json = gson.fromJson(request.getParameter("item"),Items.class);

        System.out.println("1 "+request.getParameter("json"));
        out.println("1 "+request.getParameter("json"));
        out.println("2 "+request.getParameter("id"));
        out.println("3 "+request.getParameter("enlaces"));

  //      out.println("4 "+json.getId());

        //Type type = new TypeToken<List<Path>>(){}.getType();
        Type type = new TypeToken<List<Path>>(){}.getType();

        try{
         Path[] enlaces= gson.fromJson(request.getParameter("enlaces"), Path[].class);
                  out.println("sin error "+request.getParameter("enlaces"));
for (Path student : enlaces) {

out.println("student.getName() = " + student.getId());

}
         out.println("5 "+enlaces[0].getId());
         out.println("6 "+enlaces[0].getDestino());

        }catch(Exception e){
            out.println(e.getMessage());
        }




        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
