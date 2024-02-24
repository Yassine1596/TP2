import java.io.*;
import java.net.*;

public class VoitureUDP {
    public static void main(String argv[]) {
        int port = 0;
        String host = "localhost"; // Change this to the server's address
        try {
            port = 12345; // Change this to the server's port
            InetAddress adr = InetAddress.getByName(host);

            DatagramSocket socket = new DatagramSocket();
            
            // Create a Voiture object and set its carburant
            Voiture voiture = new Voiture("SUV", "Toyota");
            voiture.setCarburant(50); // Example value

            // Serialize the Voiture object
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(voiture);
            byte[] data = outputStream.toByteArray();
            objectOutputStream.flush();
            objectOutputStream.close();

            // Send the serialized object to the server
            DatagramPacket packet = new DatagramPacket(data, data.length, adr, port);
            socket.send(packet);
            System.out.println("Sent: " + voiture.toString());

            socket.close();
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
}
