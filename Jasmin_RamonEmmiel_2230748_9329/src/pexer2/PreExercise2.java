/**
 * JASMIN, Ramon Emmiel P.
 * 2230748
 *
 * SAMPLE RUN (Done concurrently in different terminals)
 *
 * What is your name?
 * Aaliyah
 * What is your age?
 * 19
 * Aaliyah, you may exercise your right to vote!
 * Thank you and have a good day!
 *
 * What is your name?
 * Juan Dela Cruz
 * What is your age?
 * 15
 * Please enter a valid age.
 * What is your age?
 * 15
 * Juan Dela Cruz, you are still too young to vote!
 * Thank you and have a good day!
 *
 * What is your name?
 * Ramon
 * What is your age?
 * 0
 * Please enter a valid age.
 * What is your age?
 * -12
 * Please enter a valid age.
 * What is your age?
 * 10
 * Ramon, you are still too young to vote!
 * Thank you and have a good day!
 *
 */
package pexer2;

import java.io.IOException;
import java.lang.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PreExercise2 {

    /**
     * Handles the concurrency of the program using fixed thread pool of size 10
     * @param args
     */
    public static void main(String[] args) {
        int port = 2000;
        final int threadPoolSize = 10;

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);

        try (ServerSocket serverSocket = new ServerSocket(port);){

            while (true){
                Socket acceptedClientSocket = serverSocket.accept();
                executorService.submit(new ClientTask(acceptedClientSocket));
            }

        }catch (IOException iox){
            iox.printStackTrace();
        }finally {
            executorService.shutdown();
        }
    }
}

/**
 * Handles the logic side of the server where the input and output are run
 */
class ClientTask implements Runnable{

    private Socket clientSocket;

    public ClientTask(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try(
                //Instantiating the needed stream readers that should close after a complete interaction with the server
                BufferedReader streamRdr = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter streamWtr = new PrintWriter(clientSocket.getOutputStream(), true);

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
            clientSocket.close();

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}


