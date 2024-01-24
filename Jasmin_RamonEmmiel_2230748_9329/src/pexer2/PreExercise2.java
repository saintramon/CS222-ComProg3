/**
 * JASMIN, Ramon Emmiel P.
 * 2230748
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
                ServerSocket serverSocket = new ServerSocket(port);
                Socket clientSocket = serverSocket.accept();

                BufferedReader streamRdr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter streamWtr = new PrintWriter(clientSocket.getOutputStream(), true);

                ){

            streamWtr.print("What is your name? ");

            String name = streamRdr.readLine();
            int age;

            while (true){
                streamWtr.print("What is your age? ");
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

        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }
}
