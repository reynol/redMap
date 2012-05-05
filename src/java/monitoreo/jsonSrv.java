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
import database.conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import json.Items;
import json.Path;

/**
 * Servlet que proporcina la  lista de dispositivos al realizar la carga.
 * @author Reynol Zacapala.
 */
public class jsonSrv extends HttpServlet {
   
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
            out.println("<title>Servlet jsonSrv</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet jsonSrv at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */

ArrayList <Items> devices = new ArrayList<Items>();
Items img;

 HttpSession session = request.getSession();
 if(session!=null){
    System.out.println(session.getAttribute("idusuario").toString());
   conexion cn= new conexion();
Integer idmapa=cn.getCurrentUserMap(session.getAttribute("idusuario").toString());
   ResultSet rs= cn.getDevices(idmapa.toString());

   try{
         while (rs.next())
        {
            img= new Items(rs.getInt (3),rs.getInt (4),
                    rs.getInt(1), rs.getString(5),  rs.getString(6), rs.getString(2), rs.getInt(7));

            ResultSet rs2= cn.getEnlaces(rs.getInt(1));
             while (rs2.next())
            {
                    img.enlaces.add(new Path(rs2.getInt(2),rs2.getString(4)));
 

             }
            devices.add(img);

        }
         
    }catch(SQLException e){
            e.printStackTrace();

        }


       Gson gson = new Gson();
        String json = gson.toJson(devices);

        out.print(json);
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
