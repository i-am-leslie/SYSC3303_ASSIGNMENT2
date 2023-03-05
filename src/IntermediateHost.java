import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


/**
 * @author Ejeh Leslie 101161386
 * Class for the Intermediate Host of the system responsible for receiving and sending data from and to Client and Server
 *
 */
public class IntermediateHost {
    private DatagramPacket sendPacket, receivePacket;
    private DatagramSocket sendSocket, receiveSocket, receiveSocket2;

    //Constructor that initializes all the required sockets
    public IntermediateHost()
    {
        try {
            sendSocket = new DatagramSocket();

            receiveSocket = new DatagramSocket(23);
            receiveSocket2 = new DatagramSocket(5004);

        } catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to send and receive packets to and from Server and client
     */
    public void doYourJob()
    {
        byte data[] = new byte[1024];
        receivePacket = new DatagramPacket(data, data.length);

        //Receive packet from the Client
        try {
            System.out.println("Waiting for Packet from Client...\n");
            receiveSocket.receive(receivePacket);
        } catch (IOException e) {
            System.out.print("IO Exception: likely:");
            System.out.println("Receive Socket Timed Out.\n" + e);
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Packet received from Client\n");
        String clientReceived = new String(data,0,receivePacket.getLength());
        int clientPort = receivePacket.getPort();
        System.out.println("String: " + clientReceived+""+clientPort);
        System.out.println("Bytes: " + data + "\n");

        try {
            Thread.sleep(50);
        } catch (InterruptedException e ) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            sendPacket = new DatagramPacket(data, receivePacket.getLength(),InetAddress.getLocalHost(), 69);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Sending Packet to Server...\n");

        //Send packet to the Server
        try {
            sendSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Packet sent to Server!\n");

        //Receive packet from the server
        try {
            System.out.println("Waiting for Packet from Server...\n"); // so we know we're waiting
            receiveSocket2.receive(receivePacket);
        } catch (IOException e) {
            System.out.print("IO Exception: likely:");
            System.out.println("Receive Socket Timed Out.\n" + e);
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Packet received from Server:\n");
        String serverReceived = new String(sendPacket.getData(),0,receivePacket.getLength());
        System.out.println("String: " + serverReceived);
        System.out.println("Bytes: " + data + "\n");


        try {
            Thread.sleep(500);
        } catch (InterruptedException e ) {
            e.printStackTrace();
            System.exit(1);
        }


        sendPacket = new DatagramPacket(data, receivePacket.getLength(),receivePacket.getAddress(), clientPort);

        System.out.println( "Sending Packet to Client\n");

        //Send packet to the client
        try {
            sendSocket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Packet sent to Client!\n");

        sendSocket.close();
        receiveSocket.close();
        receiveSocket2.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        while(true) {
            IntermediateHost intermediateHost = new IntermediateHost();
            intermediateHost.doYourJob();
        }
    }

}


