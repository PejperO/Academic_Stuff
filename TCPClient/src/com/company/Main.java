package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {


        Socket socket = null;
        PrintWriter out = null; //Obiekt do wystawiania danych w strone serwera
        BufferedReader in = null;   //Obiekt do pobierania danych od serwera
        String address = "localhost"; //Adres serwera
        int port = 7878;  //Port serwera
        try {
            socket = new Socket();  //Obiekt gniazda
            socket.connect(new InetSocketAddress(address, port), 500); //Socket TCP na konkretny adres i port
            out = new PrintWriter(socket.getOutputStream(), true);  //W stronę serwera
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Od serwera
        }
        catch (UnknownHostException e) {
            System.out.println("Unknown host");
            System.exit(-1);
        }
        catch  (IOException e) {
            System.out.println("No I/O");
            System.exit(-1);
        }

        try {
//            String get = in.readLine();
//            System.out.println(get);
//            //Wysłanie do serwera
            out.println("195185");
            out.flush();
            //Odbiór
            String resp = in.readLine();
            //Wyświetlenie w konsoli
            System.out.println(resp);
            //Wysyłamy port swojej aplikacji klienckiej
            out.println(socket.getLocalPort());
            resp = in.readLine();
            System.out.println(resp);
        }
        catch (IOException e) {
            System.out.println("Error during communication");
            System.exit(-1);
        }

        try {
            socket.close();
        }
        catch (IOException e) {
            System.out.println("Cannot close the socket");
            System.exit(-1);
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
