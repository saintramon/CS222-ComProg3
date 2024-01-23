/**
 * JASMIN, Ramon Emmiel P.
 * 2230748
 *
 * SAMPLE RUN
 * Host 1 - Type IP address/Hostname: yahoo.com
 * Number of Hosts/IP: 12
 *
 * Host Name            IP Address
 * yahoo.com            74.6.143.25
 * yahoo.com            74.6.231.20
 * yahoo.com            98.137.11.163
 * yahoo.com            74.6.231.21
 * yahoo.com            98.137.11.164
 * yahoo.com            74.6.143.26
 * yahoo.com            2001:4998:24:120d:0:0:1:0
 * yahoo.com            2001:4998:44:3507:0:0:0:8000
 * yahoo.com            2001:4998:124:1507:0:0:0:f000
 * yahoo.com            2001:4998:44:3507:0:0:0:8001
 * yahoo.com            2001:4998:24:120d:0:0:1:1
 * yahoo.com            2001:4998:124:1507:0:0:0:f001
 * Search another? [Y/N]Y
 *
 * Host 2 - Type IP address/Hostname: slu.edu.ph
 * Number of Hosts/IP: 1
 *
 * Host Name            IP Address
 * slu.edu.ph            122.53.179.133
 * Search another? [Y/N]Y
 *
 * Host 3 - Type IP address/Hostname: youtube.com
 * Number of Hosts/IP: 2
 *
 * Host Name            IP Address
 * youtube.com            142.251.220.206
 * youtube.com            2404:6800:4017:801:0:0:0:200e
 * Search another? [Y/N]N
 */
package pexer1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.*;
import java.net.InetAddress;

public class PreExercise1 implements Runnable{
    private final BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); //Reads keyboard input

    public static void main(String[] args) {
        try {
            Thread thread = new Thread(new PreExercise1());
            thread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method outputs the number of hosts/IP and the corresponding IP address of each host in a given host name or IP address
     */
    @Override
    public void run(){
        int hostNumber = 1;
        String userChoice = "";


        do {
            System.out.print("\nHost " + hostNumber + " - Type IP address/Hostname: ");

            try {
                String hostName = keyboard.readLine();
                InetAddress[] addresses = InetAddress.getAllByName(hostName); //stores the list of hosts/ip of the entered host name or ip address
                int numberOfHosts = addresses.length; //determines the number of host/ip

                System.out.println("Number of Hosts/IP: " + numberOfHosts);
                System.out.println();
                System.out.println("Host Name            IP Address");


                for (int i = 0; i < numberOfHosts; i++){
                    System.out.println(addresses[i].getHostName() + "            " + addresses[i].getHostAddress()); //prints the host name and its corresponding IP address
                }

                System.out.print("Search another? [Y/N]");
                userChoice = keyboard.readLine();
            }catch (IOException iox){
                iox.printStackTrace();
            }

            ++hostNumber;
        }while (userChoice.toUpperCase().equals("Y"));
    }
}