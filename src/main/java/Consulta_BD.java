import com.mycompany.cliente.ClientePOP3;

/**
 *
 * @author Christian
 */
public class Consulta_BD {

    public static void main(String[] args) {
        //Conexión a la base de datos;
        //Conexion_db mConexion = Conexion_db.getInstancia();//mailtecnoweb.org.bo
        ClientePOP3 cpop=new ClientePOP3("www.tecnoweb.org.bo", 110);
        cpop.iniciar();
    }
}
