/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.consulta_bd;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Christian
 */
public class Conexion_db {

    Connection conectar = null;
    String servidor = "www.tecnoweb.org.bo";// (PostgreSQL);
    String usuario = "agenda";
    String contraseña = "agendaagenda";
    String db = "db_agenda";
    String tabla = "persona";

    String cadena = "jdbc:postgresql://" + servidor + "/" + db;

    public Connection establecerConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contraseña);
            System.out.println("Se estableció la conexión a la base de datos correctamente");
        } catch (Exception e) {
            System.out.println("Error al conectar a la Base de datos: " + e.toString());
        }
        return conectar;
    }
}
