/**
 * JASMIN, RAMON EMMIEL P.
 * 2230748
 *
 * SAMPLE RUN:
 *
 * RESULTS
 * -------
 * 5 ^ 2 = 25.0
 * 12 % 5 = 2.0
 * abc * 3 = Invalid
 * 8 $ 4 = Invalid
 * 6.7 + 3 = 9.7
 * 9 / 0 = Invalid
 * xyz ^ 2 = Invalid
 * 4 - 3 = 1.0
 * 7 * 7 = 49.0
 * 10 ' 2 = Invalid
 *
 * Termination key received, closing server...
 */
package pexer3;

import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.*;

import java.net.Socket;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;


public class PreExercise3Client {

    public static void main(String[] args) {
        try {

            Socket clientSocket = new Socket("localhost", 3704);

            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            File inputFile = new File("res/exer3.xml");
            Document document = builder.parse(inputFile);

            Element root = document.getDocumentElement();

            NodeList expressionList = root.getElementsByTagName("expression");

            /**
             * Will traverse through all expression nodes and convert it to Expression object.
             * The Expression object will then be sent to the server.
             */
            for (int i = 0; i < expressionList.getLength(); i++){

                Element expressionElement = (Element) expressionList.item(i);

                String operand1 = expressionElement.getElementsByTagName("operand1").item(0).getTextContent();
                String operator = expressionElement.getElementsByTagName("operator").item(0).getTextContent();
                String operand2 = expressionElement.getElementsByTagName("operand2").item(0).getTextContent();

                Expression expression = new Expression(operand1,operator,operand2);

                //sending an expression object to server
                outputStream.writeObject(expression);
            }

            // Sends the "bye" message after no line can be read from the xml file
            outputStream.writeObject("bye");
            outputStream.flush();

            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            String line;
            System.out.println("RESULTS");
            System.out.println("-------");


            //Printing of results sent by the server
            while (true){
                try {
                    line = (String) inputStream.readObject();
                    if (line == null) {
                        break; // end of stream reached
                    }
                    System.out.println(line);
                }catch (EOFException eofe){
                    break;
                }
            }

            outputStream.close();
            inputStream.close();
            clientSocket.close();

        }catch (IOException iox){
            iox.printStackTrace();
        }catch (ParserConfigurationException pce){
            pce.printStackTrace();
        }catch (SAXException se){
            se.printStackTrace();
        }catch (ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
    }
}
