/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Fares
 */
import java.net.*;
import java.util.Date;

public class Serveur {
    public static void main(String[] args) {
        try {
            int port = 1250;
            DatagramSocket socket = new DatagramSocket(port);
            
            System.out.println("Serveur UDP démarré sur le port " + port + "...");
            
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                
                // Attente de la réception d'un datagramme
                socket.receive(packet);
                
                // Récupération de l'adresse et du port de l'émetteur
                InetAddress address = packet.getAddress();
                int portEmetteur = packet.getPort();
                
                // Création de la réponse contenant la date et l'heure courante
                String reponse = "Date et heure courantes : " + new Date().toString();
                byte[] responseData = reponse.getBytes();
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, address, portEmetteur);
                
                // Envoi de la réponse à l'émetteur
                socket.send(responsePacket);
            }
        } catch (Exception e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}

