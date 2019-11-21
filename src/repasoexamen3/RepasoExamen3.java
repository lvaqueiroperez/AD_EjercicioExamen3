package repasoexamen3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class RepasoExamen3 {

    public static Connection conexion = null;

    public static Connection getConexion() throws SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;

        conexion = DriverManager.getConnection(ulrjdbc);
        return conexion;
    }

    public static void closeConexion() throws SQLException {
        conexion.close();
    }

    public static void insertarFilaXerado() throws FileNotFoundException, IOException, SQLException {

        //LEEMOS EL FICHERO DELIMITADO
        FileReader readFile1 = new FileReader("/home/oracle/Desktop/ExamenRepaso3/analisis.txt");
        BufferedReader bufferRead1 = new BufferedReader(readFile1);

        String[] arrayDatos;
        Analisis obj = new Analisis();
        String linea;

        //DATOS A OBTENER:
        String coda = "";
        String nomeuva = "";
        int acidez = 0;
        int acidezmin = 0;
        int acidezmax = 0;
        String acidezTexto = "";
        int total = 0;
        //b
        int numeroa;

        while ((linea = bufferRead1.readLine()) != null) {

            arrayDatos = linea.split(",");

            //CREAMOS OBJETOS:
            obj.setCodigoa(arrayDatos[0]);
            obj.setAcidez(Integer.parseInt(arrayDatos[1]));
            obj.setGrao(Integer.parseInt(arrayDatos[2]));
            obj.setTaninos(Integer.parseInt(arrayDatos[3]));
            obj.setTipoUva(arrayDatos[4]);
            obj.setCantidade(Integer.parseInt(arrayDatos[5]));
            obj.setDni(arrayDatos[6]);

            System.out.println(obj);

            //YA TENEMOS EL CÓDIGO DEL ANALISIS EN EL OBJETO
            coda = obj.getCodigoa();
            //YA TENEMOS LA ACIDEZ EN EL OBJETO
            acidez = obj.getAcidez();

            //OBTENEMOS NOMBRE DE LA UVA:
            PreparedStatement pst1 = conexion.prepareStatement("select nomeu from uvas where tipo = ?");

            pst1.setString(1, obj.getTipoUva());

            ResultSet rs1 = pst1.executeQuery();
            rs1.next();

            nomeuva = rs1.getString("nomeu");

            System.out.println("Nombre uva: " + nomeuva);

            //PARA ESCRIBIR LA ACIDEZ:
            PreparedStatement pst2 = conexion.prepareStatement("select acidezmin,acidezmax from uvas where tipo = ?");

            pst2.setString(1, obj.getTipoUva());

            ResultSet rs2 = pst2.executeQuery();

            while (rs2.next()) {

                acidezmin = rs2.getInt("acidezmin");
                acidezmax = rs2.getInt("acidezmax");

            }

            if (acidez < acidezmin) {

                acidezTexto = "subir acidez";

            } else if (acidez > acidezmax) {

                acidezTexto = "bajar acidez";

            } else if ((acidez <= acidezmax) && (acidez >= acidezmin)) {

                acidezTexto = "acidez correcta";

            }

            //CÁLCULO DEL TOTAL:
            total = obj.getCantidade() * 15;

            //INSERTAR FILA EN XERADO:
            PreparedStatement pst3 = conexion.prepareStatement("insert into xerado values(?,?,?,?)");
            pst3.setString(1, coda);
            pst3.setString(2, nomeuva);
            pst3.setString(3, acidezTexto);
            pst3.setInt(4, total);

            pst3.executeQuery();

            System.out.println("CAMPO INSERTADO !");

            //APARTADO B: AUMENTAR EL NUMERO DE ANALISIS REALIZADOS EN TABLA CLIENTES
            //UPDATE DE LA TABLA CLIENTES: 
            PreparedStatement pst4 = conexion.prepareStatement("select numerodeanalisis from clientes where dni = ?");

            pst4.setString(1, obj.getDni());

            ResultSet rs4 = pst4.executeQuery();
            rs4.next();

            numeroa = rs4.getInt("numerodeanalisis");

            numeroa++;

            PreparedStatement pst5 = conexion.prepareStatement("update clientes set numerodeanalis = ? where dni = ?");

            pst5.setInt(1, numeroa);
            pst5.setString(2, obj.getDni());

            pst5.executeUpdate();

            System.out.println("CAMPO CLIENTES ACTUALIZADO!1");

        }

        bufferRead1.close();
        readFile1.close();

    }

    public static void main(String[] args) throws IOException {

        try {
            RepasoExamen3.getConexion();

            RepasoExamen3.insertarFilaXerado();

            RepasoExamen3.closeConexion();

        } catch (SQLException ex) {
            Logger.getLogger(RepasoExamen3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
