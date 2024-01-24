/**
 * JASMIN, Ramon Emmiel P.
 * 2230748
 *
 * SAMPLE RUN (Continuous)
 *
 * saintramon@saintramon 2ndsem % telnet localhost 2000
 * Trying ::1...
 * Connected to localhost.
 * Escape character is '^]'.
 * What is your name?
 * Ramon
 * What is your age?
 * 18
 * Ramon, you may exercise your right to vote!
 * Thank you and have a good day!
 * Connection closed by foreign host.
 *
 * saintramon@saintramon 2ndsem % telnet localhost 2000
 * Trying ::1...
 * Connected to localhost.
 * Escape character is '^]'.
 * What is your name?
 * Allie
 * What is your age?
 * 19
 * Allie, you may exercise your right to vote!
 * Thank you and have a good day!
 * Connection closed by foreign host.
 *
 * saintramon@saintramon 2ndsem % telnet localhost 2000
 * Trying ::1...
 * Connected to localhost.
 * Escape character is '^]'.
 * What is your name?
 * Juan
 * What is your age?
 * 13
 * Juan, you are still too young to vote!
 * Thank you and have a good day!
 * Connection closed by foreign host.
 */
package pexer2;

import java.io.IOException;
import java.lang.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PreExercise2 {
    public static void main(String[] args) {

        int port = 2000;

        try(
                ServerSocket serverSocket = new ServerSocket(port); //The server socket is in the outer try-with-resources so that it will only close manually
                ){

            while (true){
                Socket clientSocket = serverSocket.accept();

                try (
                        //The stream reader and writer is in an inner try with resources so that it will close after every connection
                        BufferedReader streamRdr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter streamWtr = new PrintWriter(clientSocket.getOutputStream(),true);
                        ){

                    streamWtr.println("What is your name? ");

                    String name = streamRdr.readLine();
                    int age;

                    while (true){
                        streamWtr.println("What is your age? ");
                        try {
                            age = Integer.parseInt(streamRdr.readLine());

                            if (age <= 0){
                                throw new NumberFormatException();
                            }else {
                                break;
                            }
                        }catch (NumberFormatException nfe){
                            streamWtr.println("Please enter a valid age.");
                            continue;
                        }
                    }

                    if (age >= 18){
                        streamWtr.println(name + ", you may exercise your right to vote!");
                    }else {
                        streamWtr.println(name + ", you are still too young to vote!");
                    }

                    streamWtr.println("Thank you and have a good day!");
                }
            }

        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
}
