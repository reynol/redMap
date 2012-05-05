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
package correo;

import com.lowagie.text.pdf.codec.Base64.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

/**
 * Envia un correo electronico al usuario de la aplicacion cuando un/unos dispositivos dejen de estar disponibles.
 * @author Reynol Zacapala
 */
public class EnviarCorreo {
    /**
     * Constructor vacio.
     */
    public EnviarCorreo(){
        
    }
    /**
     * Se encarga de enviar un correo electronico.
     * @param arrayInputStream Flujo de byete con la informacion del reporte a adjuntar en el email.
     * @param email Direccion de correo electronico al que se enviaran
     */
        public  void getSource(ByteArrayInputStream arrayInputStream,String  email)
    {
        try
        {
          // se obtiene el objeto Session. La configuración es para
          // una cuenta de gmail.
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", "reynol@gmail.com");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props, null);
            // session.setDebug(true);

            // Se compone la parte del texto
            BodyPart texto = new MimeBodyPart();
            texto.setText("Se detecto un problema en la red a las  "+new Date().toString());

            // Se compone el adjunto con la imagen
            BodyPart adjunto = new MimeBodyPart();
  //          adjunto.setDataHandler(
  //              new DataHandler(new FileDataSource("D:/java/reporte2PDF.pdf")));
  //          adjunto.setFileName("REPORTE.pdf");
            
            
            
     if (arrayInputStream != null && arrayInputStream instanceof ByteArrayInputStream) {
        // create the second message part with the attachment from a OutputStrean
        //MimeBodyPart adjunto= new MimeBodyPart();
        ByteArrayDataSource ds = new ByteArrayDataSource(arrayInputStream, "application/pdf"); 
        
        adjunto.setDataHandler(new DataHandler(ds));
        adjunto.setFileName("Reporte.pdf");
       // mimeMultipart.addBodyPart(attachment);
    }
            
            
            
            

            // Una MultiParte para agrupar texto e imagen.
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);

           
            
            
            // Se compone el correo, dando to, from, subject y el
            // contenido.
            MimeMessage message = new MimeMessage(session);
           // message.setFrom(new InternetAddress("yo@yo.com"));
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(email));
            message.setSubject("Importante: Hay un problema en la red");
            message.setContent(multiParte);

            // Se envia el correo.
            Transport t = session.getTransport("smtp");
            t.connect("reynol@gmail.com", "11205802");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            
            System.out.println("El correo se envio correctamente");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        
        
    }
    
}
