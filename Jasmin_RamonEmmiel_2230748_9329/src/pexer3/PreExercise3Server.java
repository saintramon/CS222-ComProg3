/**
 * JASMIN, RAMON EMMIEL P.
 * 2230748
 */
package pexer3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.*;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PreExercise3Server {
    /**
     * Main method that creates a new thread for new clients
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try (
                ServerSocket serverSocket = new ServerSocket(3704);
                ){

            while (true){
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ClientHandler(clientSocket));

            }
        }catch (IOException iox){
            iox.printStackTrace();
        }finally {
           executorService.shutdown();
        }
    }
}

/**
 * Handles the server-side logic for client requests
 */
class ClientHandler implements Runnable{

    private Socket clientSocket;

    private volatile boolean shouldTerminate = false;

    public ClientHandler(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            List<String> results = new ArrayList<>();

            //This loop will receive the Nodes from the client and convert it to Expression object, will terminate if "bye" message is received.
            while (true){
                Object object = inputStream.readObject();
                // If "bye" is received, it will break the loop and would terminate the program after sending the processed Nodes.
                if (object == null || object.equals("bye")){
                    shouldTerminate = true;
                    break;
                }
                Expression expression = (Expression) object;
                String result = expression.getOperand1() + " " + expression.getOperator() + " " + expression.getOperand2() + " = " + evaluateExpression(expression);
                results.add(result);
            }

            // Returns the list of results to the client
            for (String result : results){
                outputStream.writeObject(result);
            }

            outputStream.writeObject("\nTermination key received, closing server...");
            outputStream.flush();


            outputStream.close();
            inputStream.close();
            clientSocket.close();

        }catch (IOException iox){
            iox.printStackTrace();
        }catch (ClassNotFoundException cnfx){
            cnfx.printStackTrace();
        }finally {
            if (shouldTerminate){
                System.exit(0);
            }
        }
    }

    /**
     * This method handles the logic for the evaluation of an Expression object
     * @param expression
     * @return
     */
    public String evaluateExpression(Expression expression){
        try {
            double operand1 = Double.parseDouble(expression.getOperand1());
            String operator = expression.getOperator();
            double operand2 = Double.parseDouble(expression.getOperand2());

            switch (operator) {
                case "+":
                    return String.valueOf(operand1 + operand2);
                case "-":
                    return String.valueOf(operand1 - operand2);
                case "*":
                    return String.valueOf(operand1 * operand2);
                case "/":
                    if (operand2 == 0){
                        return "Invalid";
                    }
                    return String.valueOf(operand1 / operand2);
                case "%":
                    return String.valueOf(operand1 % operand2);
                case "^":
                    return String.valueOf(Math.pow(operand1, operand2));
                default:
                    return "Invalid";
            }
        }catch (NumberFormatException nfe){
            return "Invalid";
        }
    }
}
