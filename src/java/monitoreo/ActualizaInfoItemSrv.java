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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import json.Items;

/**
 * Servlet que procesa las solicitudes para actualizar la informacion de un dispositivo.
 * @author Reynol Zacapala.
 */
public class ActualizaInfoItemSrv extends HttpServlet {

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
            /*
             * TODO output your page here. You may use following sample code.
             */
        Gson gson = new Gson();
        System.out.println(request.getParameter("item"));
        Items itm = gson.fromJson(request.getParameter("item"),Items.class);

        conexion c = new conexion();
        System.out.println(itm.getId());
        System.out.println(itm.getDireccion());
        System.out.println(itm.getPuerto());
        System.out.println(itm.getNombre());
        System.out.println(itm.getImg());
        Boolean res=c.updateInfoItem(itm.getId(), itm.getImg(), itm.getDireccion(), itm.getPuerto(),  itm.getNombre());
        
        if(res)
        out.print(res);

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
