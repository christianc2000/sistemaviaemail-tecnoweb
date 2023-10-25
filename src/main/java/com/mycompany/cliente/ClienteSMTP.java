package com.mycompany.cliente;

import java.io.*;
import java.net.*;

public class ClienteSMTP {

    String servidor;
    int puerto;

    public ClienteSMTP(String servidor, int puerto) {
        this.servidor = servidor;
        this.puerto = puerto;
    }

    public void iniciar(String emisor, String receptor, String mensaje) {
        try {
            Socket socket = new Socket(servidor, puerto);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());

            if (socket != null && entrada != null && salida != null) {
                System.out.println("S: " + entrada.readLine());

                String comando = "HELO " + servidor + "\r\n";
                System.out.print("C: " + comando);
                salida.writeBytes(comando);
                System.out.println("S: " + getMultiline(entrada));

                comando = "MAIL FROM: <" + emisor + ">\r\n";
                System.out.print("C: " + comando);
                salida.writeBytes(comando);
                System.out.println("S: " + entrada.readLine());

                comando = "RCPT TO: <" + receptor + ">\r\n";
                System.out.print("C: " + comando);
                salida.writeBytes(comando);
                System.out.println("S: " + entrada.readLine());

                comando = "DATA\r\n";
                System.out.print("C: " + comando);
                salida.writeBytes(comando);
                System.out.println("S: " + entrada.readLine());

                comando = "Subject: " + mensaje + "\r\n\r\n";
                System.out.print("C: " + comando);
                salida.writeBytes(comando);

                comando = mensaje + "\r\n.\r\n";
                System.out.print("C: " + comando);
                salida.writeBytes(comando);
                System.out.println("S: " + entrada.readLine());

                comando = "QUIT\r\n";
                System.out.print("C: " + comando);
                salida.writeBytes(comando);
                System.out.println("S: " + entrada.readLine());
            }

            salida.close();
            entrada.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("S: No se pudo conectar con el servidor indicado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static protected String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException("S: El servidor cerró la conexión inesperadamente.");
            }
            if (line.charAt(3) == ' ') {
                lines = lines + "\n" + line;
                break;
            }
            lines = lines + "\n" + line;
        }
        return lines;
    }

    public static void main(String[] args) {
        ClienteSMTP csmtp = new ClienteSMTP("mail.tecnoweb.org.bo", 25);
        csmtp.iniciar("christiancelso@gmail.com", "grupo21sa@tecnoweb.org.bo", "ll");
        System.out.println("Se inicia el cliente SMTP.");
    }
}
