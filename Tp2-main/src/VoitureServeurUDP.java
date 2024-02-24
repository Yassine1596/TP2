/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Fares
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class VoitureServeurUDP {
    public static void main(String argv[]) {
        int port = 0;
        Scanner keyb = new Scanner(System.in);

        // Demande à l'utilisateur le numéro de port
        System.out.print("Port d'écoute : ");
        try {
            // Lecture du numéro de port saisi par l'utilisateur
            port = keyb.nextInt();
        } catch (NumberFormatException e) {
            // Gestion des exceptions
            System.err.println("Le paramètre n'est pas un entier.");
            System.err.println("Usage : java ServeurUDP port-serveur");
            System.exit(-1);
        }
        try {
            DatagramSocket socket = new DatagramSocket(port);
            
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            socket.receive(packet);

            // Deserialize the received object
            ByteArrayInputStream inputStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Voiture voiture = (Voiture) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println("Received: " + voiture);
            System.out.println("From: " + packet.getAddress() + ":" + packet.getPort());

            // Do whatever you need to do with the received Voiture object
            // For example, set the carburant
            voiture.setCarburant(60); // Example value

            // Send the updated Voiture object back to the client
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(voiture);
            byte[] responseData = outputStream.toByteArray();
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length,
                    packet.getAddress(), packet.getPort());
            socket.send(responsePacket);
            objectInputStream.close();
            socket.close();
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
}


