/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.datos;

import com.mycompany.consulta_bd.Conexion_db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;

/**
 *
 * @author Christian
 */
public class DPersona {

    java.sql.Statement st;
    ResultSet rs;
    Conexion_db db = new Conexion_db();

    public int longitudMayor(HashMap<Integer, String[]> lista) {
        int m = 0;
        for (int i = 0; i < lista.size(); i++) {
            String value[] = lista.get(i);
            for (int j = 0; j < value.length; j++) {
                if (value[j].length() > m) {
                    m = value[j].length();
                }
            }
        }
        return m;
    }

    public String rellenarEspacio(String cadena, int n) {
        String c = cadena;
        for (int i = cadena.length(); i < n; i++) {
            c = c + " ";
        }
        return c;
    }

    public String StringHashMap(HashMap<Integer, String[]> lista) {
        // System.out.println("tamaño hashmap: "+lista.size());
        int n = longitudMayor(lista);
        String c = "";
        for (int i = 0; i < lista.size(); i++) {
            String value[] = lista.get(i);
            for (int j = 0; j < value.length; j++) {
                c = c + rellenarEspacio(value[j], n) + "|";
            }
            c = c + "\n";
        }
        return c;
    }

    public void mostrarHashMap(HashMap<Integer, String[]> lista) {
        // System.out.println("tamaño hashmap: "+lista.size());
        int n = longitudMayor(lista);
        for (int i = 0; i < lista.size(); i++) {
            String value[] = lista.get(i);
            for (int j = 0; j < value.length; j++) {
                System.out.print(rellenarEspacio(value[j], n) + "|");
            }
            System.out.println("");
        }
    }

    public HashMap<Integer, String[]> consultaWhere(String patron) {

        HashMap<Integer, String[]> registros = new HashMap<>();
        try {
            Connection connection = db.establecerConexion();
            st = connection.createStatement();
            String sql = "SELECT * FROM persona WHERE per_nom like '%" + patron + "%' or per_appm like '%" + patron + "%' or per_email like '%" + patron + "%' or per_dir like '%" + patron + "%';";
            rs = st.executeQuery(sql);
            // Obtener información sobre las columnas
            ResultSetMetaData metadata = rs.getMetaData();
            int numColumnas = metadata.getColumnCount();

            // Agregar los nombres de las columnas a la cadena de salida
            String reg[] = new String[numColumnas];
            for (int i = 1; i <= numColumnas; i++) {
                // System.out.print("|"+metadata.getColumnName(i));
                reg[i - 1] = metadata.getColumnName(i);
            }
            registros.put(0, reg);
            //    mostrarHashMap(registros);
            System.out.println("***************************************");
            // Recorrer los resultados y agregar cada fila a la cadena de salida
            int i = 1;
            while (rs.next()) {
                String registro[] = new String[numColumnas];
                //System.out.print(rs.getString("per_cod")+", "+rs.getString("per_nom")+", "+rs.getString("per_appm")+", "+rs.getString("per_prof"));
                registro[0] = rs.getString("per_cod").trim();
                registro[1] = rs.getString("per_nom").trim();
                registro[2] = rs.getString("per_appm").trim();
                registro[3] = rs.getString("per_prof").trim();
                registro[4] = rs.getString("per_telf").trim();
                registro[5] = rs.getString("per_cel").trim();
                registro[6] = rs.getString("per_email").trim();
                registro[7] = rs.getString("per_dir").trim();
                registro[8] = rs.getString("per_fnac").trim();
                registro[9] = rs.getString("per_flug").trim();
                registro[10] = rs.getString("per_type").trim();
                registro[11] = rs.getString("per_pass").trim();
                registro[12] = rs.getString("per_create").trim();
                registro[13] = rs.getString("per_update").trim();

                registros.put(i, registro);
                i++;
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (Exception e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return registros;
    }

    public HashMap<Integer, String[]> selectAll() {

        HashMap<Integer, String[]> registros = new HashMap<>();
        try {
            Connection connection = db.establecerConexion();
            st = connection.createStatement();
            String sql = "SELECT * FROM persona;";
            rs = st.executeQuery(sql);
            // Obtener información sobre las columnas
            ResultSetMetaData metadata = rs.getMetaData();
            int numColumnas = metadata.getColumnCount();

            // Agregar los nombres de las columnas a la cadena de salida
            String reg[] = new String[numColumnas];
            for (int i = 1; i <= numColumnas; i++) {
                // System.out.print("|"+metadata.getColumnName(i));
                reg[i - 1] = metadata.getColumnName(i);
            }
            registros.put(0, reg);
            //    mostrarHashMap(registros);
            System.out.println("***************************************");
            // Recorrer los resultados y agregar cada fila a la cadena de salida
            int i = 1;
            while (rs.next()) {
                String registro[] = new String[numColumnas];
                //System.out.print(rs.getString("per_cod")+", "+rs.getString("per_nom")+", "+rs.getString("per_appm")+", "+rs.getString("per_prof"));
                registro[0] = rs.getString("per_cod").trim();
                registro[1] = rs.getString("per_nom").trim();
                registro[2] = rs.getString("per_appm").trim();
                registro[3] = rs.getString("per_prof").trim();
                registro[4] = rs.getString("per_telf").trim();
                registro[5] = rs.getString("per_cel").trim();
                registro[6] = rs.getString("per_email").trim();
                registro[7] = rs.getString("per_dir").trim();
                registro[8] = rs.getString("per_fnac").trim();
                registro[9] = rs.getString("per_flug").trim();
                registro[10] = rs.getString("per_type").trim();
                registro[11] = rs.getString("per_pass").trim();
                registro[12] = rs.getString("per_create").trim();
                registro[13] = rs.getString("per_update").trim();

                registros.put(i, registro);
                i++;
            }

            rs.close();
            connection.close();
            // System.out.println("EL REGISTRO SE REALIZO EXITOSAMENTE");
        } catch (Exception e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }

        return registros;
    }
}
