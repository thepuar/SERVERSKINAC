/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import static app.AppACSkin.rutacars;
import static app.AppACSkin.rutasskins;
import static app.AppACSkin.teclado;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author thepu
 */
public class ServerAC extends Thread {

    private ServerSocket ss;
    private Socket socket;
    private String host = "localhost";
    private String pathfile;
    private Util util;
    private int port;
    private BufferedWriter bw;
    private BufferedReader br;

    public ServerAC(int port, String pathfile) {
        this.port = port;
        init();
        this.pathfile = pathfile;
    }

    public ServerAC(int port) {
        this.port = port;
        init();
    }

    public void init() {
        this.util = new Util();
        try {
            ss = new ServerSocket(port);
            socket = ss.accept();
            //Send msg to client
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            bw = new BufferedWriter(osw);
            br = new BufferedReader(isr);

        } catch (IOException ioe) {
        }
    }

    public void run() {

        //Bienvenida
        String texto = "ACSKIN - SERVER (thepuar) Version: 1.0";
        util.sendLineTextSocket(bw, texto);
        //Menu eleccion Skin
        util.sendListTextSocket(bw, menuEleccionSkin());

        //Esperando respuesta
        int opcion = Util.leerOpcionCliente(br);

        Map.Entry<String, String> datos = util.getEntryMap(rutasskins, opcion);
        String nombre = datos.getKey();
        System.out.println("Se ha elegido " + nombre);

        //Preparando compresión
        System.out.println("Preparando compresión");
        String rutazip = rutacars + File.separator + rutasskins.get(nombre) +File.separator+ nombre + ".zip";
        util.ZipSkin(rutacars + File.separator + rutasskins.get(nombre), nombre + ".zip");
        System.out.println("Archivo comprimido");

        //Preparando para enviar
        System.out.println("Preparando para enviar");
        try {
            DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            FileInputStream fin = new FileInputStream(rutazip);
            
            int count;
            byte[] buffer = new byte[8192]; // or 4096, or more
            while ((count =  fin.read(buffer)) > 0) {
                dout.write(buffer, 0, count);
            }
            dout.flush();
            
            dout.close();
            fin.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
         System.out.println("Fichero enviado");
    }

    private void saveFile(Socket clientSock) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream(pathfile);
        byte[] buffer = new byte[4096];
        File file = new File(pathfile);
        int filesize = (int) file.length(); // Send file size in separate msg
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while ((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }

        fos.close();
        dis.close();
    }

    public void sendFile(String file) throws IOException {
        Socket s = ss.accept();
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];

        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }

        fis.close();
        dos.close();
        s.close();
    }

    public static List<String> menuEleccionSkin() {
        List<String> mensajes = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        mensajes.add("Listado de skins para enviar:");
        int i = 0;
        for (Map.Entry<String, String> entry : rutasskins.entrySet()) {
            mensajes.add(i + " - " + entry.getKey());
            nombres.add(entry.getKey());
            i++;
        }

        return mensajes;
    }

}
