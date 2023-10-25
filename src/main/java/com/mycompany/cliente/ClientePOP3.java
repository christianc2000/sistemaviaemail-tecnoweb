/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cliente;

import com.mycompany.datos.DPersona;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Christian
 */

/**
 *
 * @author daniel
 */
public class ClientePOP3 {
    //se establecen las cadenas con informacion del servidor,usuario receptor y emisor del email y puerto de conexion

    String servidor;//="mail.tecnoweb.org.bo";
    //String servidor="172.20.172.254";
    String usuario;//="grupo21sa";
    String contrasena;//="grup021grup021";
    String comando = "";
    String linea = "";
    int puerto;//=110;
    Socket socket;
    BufferedReader entrada;
    DataOutputStream salida;

    public ClientePOP3(String servidor, int puerto) {
        this.servidor = servidor;
        this.puerto = puerto;
        this.usuario = "grupo21sa";
        this.contrasena = "grup021grup021";
        try {
            //se establece conexion abriendo un socket especificando el servidor y puerto SMTP
            this.socket = new Socket(servidor, puerto);
            this.entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientePOP3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void executeCommand(String command, char type) {
        try {
            System.out.println("C: " + command);
            salida.writeBytes(command);
            if (type == 'm') {
                System.out.println("S: " + getMultiline(entrada));
            } else {
                System.out.println("S: " + entrada.readLine());
            }

        } catch (Exception e) {
            System.out.println("S: Connection con el servidor perdida");
        }

    }

    public void initConnection() {

        try {
            String command;
            socket = new Socket(servidor, puerto);
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            salida = new DataOutputStream(socket.getOutputStream());
            if (socket != null && entrada != null && salida != null) {
                System.out.println("S : " + entrada.readLine());
                command = "USER " + usuario + "\r\n";
                executeCommand(command, 'l');
                command = "PASS " + contrasena + "\r\n";
                executeCommand(command, 'l');
            }

        } catch (Exception e) {
            System.out.println("S: No se pudo conectar al servidor");
        }

    }

    public void closeConnection() {
        try {
            String command = "QUIT\r\n";
            executeCommand(command, 'l');
            salida.close();
            entrada.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("S: Connection con el servidor perdida");
        }

    }

    public void iniciar() {
        try {
            //se establece conexion abriendo un socket especificando el servidor y puerto SMTP
            /*    Socket socket = new Socket(servidor, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());*/
            // Escribimos datos en el canal de salida establecido con el puerto del protocolo SMTP del servidor
            if (socket != null && entrada != null && salida != null) {

                //executeCommand("QUIT", '1');
                int i = getCantToMessages();
                int c = i;
                while (true) {
                    c = getCantToMessages();
                    System.out.println("c: " + c);
                    if (c != i) {
                        DPersona dp = new DPersona();
                        String patron = getContentSubject(c);
                        System.out.println("patron: " + patron.trim());
                        dp.mostrarHashMap( dp.consultaWhere(patron));
                        System.out.println("se encontró un nuevo mensaje");
                       ClienteSMTP csmtp=new ClienteSMTP("www.tecnoweb.org.bo", 25);
                        csmtp.iniciar("grupo21sa@tecnoweb.org.bo", "chrstncelso@gmail.com",dp.StringHashMap(dp.consultaWhere(patron)));
                        i = c;
                    }
                }

            }
            // Cerramos los flujos de salida y de entrada y el socket cliente
            salida.close();
            entrada.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println(" S : no se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String expresionRegular(String er, String cadena) {
        Pattern pattern = Pattern.compile(er);
        Matcher matcher = pattern.matcher(linea);
        String data = "";
        if (matcher.find()) {
            data = matcher.group(1);
            return data;
        } else {
            return "";
        }
    }

    public String getMessage(int retr) {
        String result = "";
        initConnection();
        String command = "RETR " + retr + "\r\n";
        try {
            salida.writeBytes(command);
            result = getMultiline(entrada);
        } catch (Exception e) {
        }
        closeConnection();
        return result;
    }

    public String getEmisorCorreo(int n) {
        String value = "";
        if (n <= getCantToMessages()) {
            String comando = "RETR " + n;
            System.out.println("C: " + comando);
            try {
                salida.writeBytes(comando);
                String linea = getNumberLine(entrada, 2);
                value = expresionRegular("<(.*?)>", linea);

            } catch (IOException ex) {
                Logger.getLogger(ClientePOP3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return value;
    }

    public String getContentSubject(int n) {
        String result = "";
        initConnection();
        String command = "RETR " + n + "\r\n";
        String expresionRegular = "^[^\\s]+:.*";

        try {
            salida.writeBytes(command);
            String value = "";
            boolean bandera = false;
            while (true) {
                String line = entrada.readLine();
                if (line == null) {

                    throw new IOException(" S : Server unawares closed theconnection.");
                }
                if (line.length() > 0) {
                    bandera = bandera && ((byte) line.charAt(0)) == 32;
                } else {
                    bandera = false;
                }

                if (line.contains("Subject:") || bandera) {
                    bandera = true;
                    // line.split todo excepto lo -lo que coincida con la expresion regular
                    String aux[] = line.split("(Subject:)\\s*");

                    for (int i = 0; i < aux.length; i++) {
                        if (aux[i] != "") {
                            value = value + aux[i];
                        }
                    }
                }
                if (line.equals(".")) {
                    result = value;
                    break;
                }

            }

        } catch (Exception e) {
            System.out.println("S: Connection con el servidor perdida");
        }

        closeConnection();
        return result;

    }

    public int getCantToMessages() {
        int result = 0;
        initConnection();
        String command = "STAT " + "\r\n";
        try {
            salida.writeBytes(command);
            String aux = entrada.readLine();
            String aux2[] = aux.split("[\\s]");
            result = Integer.parseInt(aux2[1]);

        } catch (Exception e) {
            System.out.println("Error durante la consulta");
        }

        closeConnection();
        return result;
    }

    static protected String getNumberLine(BufferedReader in, int b) {
        String lines = "";
        int i = 1;
        while (true) {
            String line;
            try {
                line = in.readLine();

                if (line == null) {
                    // Server closed connection
                    throw new IOException(" S : Server unawares closed the connection.");
                }
                if (line.equals(".")) {
                    // No more lines in the server response
                    break;
                }
                if ((line.length() > 0) && (line.charAt(0) == '.')) {
                    // The line starts with a "." - strip it off.
                    line = line.substring(1);
                }
                // Add read line to the list of lines
                if (i == b) {

                    //    System.out.println("line: "+line);
                    lines = lines + "\n" + line;
                }
                i++;

            } catch (IOException ex) {
                Logger.getLogger(ClientePOP3.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return lines;
    }

    public void deleteMessages(int retr) {
        String command = "DELE " + retr + "\r\n";
        try {
            System.out.println("C: " + command);
            salida.writeBytes(command);
            System.out.println("S: " + entrada.readLine());

        } catch (Exception e) {
            System.out.println("Error durante la consulta");
        }

    }

// Permite leer multiples lineas de respuesta del Protocolo POP
    static protected String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Server closed connection
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                // No more lines in the server response
                break;
            }
            if ((line.length() > 0) && (line.charAt(0) == '.')) {
                // The line starts with a "." - strip it off.
                line = line.substring(1);
            }
            // Add read line to the list of lines
            lines = lines + "\n" + line;
        }
        return lines;
    }
 
}
