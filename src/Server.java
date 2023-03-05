import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 *
 */

/**
 * @author Ejeh Leslie 101161386
 * Class for the Server of the system
 *
 */
public class Server {
    private DatagramPacket sendPacket, receivePacket;
    private DatagramSocket sendSocket, receiveSocket;

    /*
     * The constructor which initialized the socket for receiving packets
     */
    public Server()
    {
        try {
            receiveSocket = new DatagramSocket(69);
        } catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Method to receive packets and send responses accordingly
     */
    public void doYourJob()
    {
        while(true) {
            byte data[] = new byte[1024];
            receivePacket = new DatagramPacket(data, data.length);
            System.out.println("Waiting for Packet...\n");

            //Receive the packet from host
            try {
                receiveSocket.receive(receivePacket);
            } catch (IOException e) {
                System.out.print("IO Exception: likely:");
                System.out.println("Receive Socket Timed Out.\n" + e);
                e.printStackTrace();
                System.exit(1);
            }

            System.out.println("Packet received!\n");

            String received = new String(data,0,receivePacket.getLength());
            System.out.println("String: " + received);
            System.out.println("Bytes: " + data + "\n");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e ) {
                e.printStackTrace();
                System.exit(1);
            }


            try {
                sendSocket = new DatagramSocket();
            } catch (SocketException se) {
                se.printStackTrace();
                System.exit(1);
            }

            //Conditions for deciding what response to send
            String res = "";
            if(received.contains("0 1 test.txt 0 netAscii 0")) {
                res = "0 3 0 1";
                byte byteRes[] = res.getBytes();
                sendPacket = new DatagramPacket(byteRes, byteRes.length,receivePacket.getAddress(), 5004);
            }
            else if(received.contains("0 2 test.txt 0 netAscii 0")) {
                res = "0 4 0 0";
                byte byteRes[] = res.getBytes();
                sendPacket = new DatagramPacket(byteRes, byteRes.length,receivePacket.getAddress(), 5004);
            }
            else {
                System.out.println("Invalid request");
                System.exit(1);
            }


            System.out.println( "Sending packet:\n");
            System.out.println("String: " + res);
            System.out.println("Bytes: " + res.getBytes() + "\n");

            //Send packet to host
            try {
                sendSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            System.out.println("Packet Sent!\n");

            sendSocket.close();
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.doYourJob();
    }

}
