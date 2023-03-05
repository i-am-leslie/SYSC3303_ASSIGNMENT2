import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author Ejeh Leslie 101161386
 * Class for the Client of the system
 */
public class Client {
    private DatagramPacket sendPacket, receivePacket;
    private DatagramSocket sendReceiveSocket;


    //Constructor that initializes the socket for sending and receiving packets
    public Client()
    {
        try {
            sendReceiveSocket = new DatagramSocket();
        } catch (SocketException se) {
            se.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to send and receive packets
     */
    public void doYourJob()
    {
        for(int i=0;i<11;i++) {
            String req = "";
            byte[] byteReq = null;
            if(i == 10) {
                req = ("invalid");
                byteReq = req.getBytes();
            }
            else if(i%2 == 0) {
                req = ("0 1" + " test.txt "  + 0 + " netAscii " + 0);
                byteReq = req.getBytes();
            }
            else if(i%2 != 0) {
                req = ( "0 2" + " test.txt "  + 0 + " netAscii " + 0);
                byteReq = req.getBytes();
            }

            System.out.println("Sending a packet containing:\nString: " + req + "\nByte: " + byteReq.toString() + "\n");

            // Creating a packet in order to send the information to the host

            try {
                sendPacket = new DatagramPacket(byteReq, byteReq.length,InetAddress.getLocalHost(), 23);// Sending data to the host InetAddress.getLocalHost() gets your devuces address
            } catch (UnknownHostException e) {
                e.printStackTrace();
                System.exit(1);
            }

            //Send packet to the host
            try {
                sendReceiveSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            System.out.println("Packet sent.\n");


            byte data[] = new byte[1024];
            receivePacket = new DatagramPacket(data, data.length);

            //Receive packet from the host
            try {
                sendReceiveSocket.receive(receivePacket);
            } catch(IOException e) {
                e.printStackTrace();
                System.exit(1);
            }

            System.out.println("Packet Received!\n");

            String received = new String(data,0,receivePacket.getLength());
            System.out.println(received + "\n");

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e ) {
                e.printStackTrace();
                System.exit(1);
            }

        }

        sendReceiveSocket.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Client client = new Client();
        client.doYourJob();
    }

}
