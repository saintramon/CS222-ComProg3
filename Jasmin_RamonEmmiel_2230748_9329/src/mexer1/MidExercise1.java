/**
 * JASMIN, Ramon Emmiel P.
 * 2230748
 *
 * SAMPLE RUN (using client class under mexer1):
 *
 * TEST CASES
 * ==========
 * TEST 1: sa1nt & louis = 11 aoui 4 sntls 5
 * TEST 2: un1v3rsityyyyy = 14 ui 2 nvrstyyyyy 10
 * TEST 3: R4mon Emmiel is V3ry h4nds0me$$$ = 28 oEieie 6 RmnmmlsVryhndsm 15
 */
package mexer1;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class MidExercise1 extends UnicastRemoteObject implements MidInterface1 {

    public MidExercise1() throws RemoteException{}

    public static void main(String[] args) {
        try {
            MidInterface1 program = new MidExercise1();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("profileString", program);

        }catch (RemoteException re){
            re.printStackTrace();
        }catch (MalformedURLException mue){
            mue.printStackTrace();
        }
    }

    /**
     * GENERATES A STRING: number of letters (alphabets) in a string + space + all vowels in order of their appearance + space + number of vowels
     * + space + all consonants in order of their appearance + space + number of consonants
     * @param s
     * @return
     * @throws RemoteException
     */
    @Override
    public String profileString(String s) throws RemoteException {
        StringBuilder generatedString = new StringBuilder();
        StringBuilder charVowels = new StringBuilder();
        StringBuilder charConsonants = new StringBuilder();

        int stringSize = 0;
        int numberOfVowels = 0;
        int numberOfConsonants = 0;
        String vowels = "aeiou";

        // Counting of characters (Non letters are counted)
        // Whitespace are ignored
        for (int i = 0; i < s.length(); i++){
            if (!Character.isWhitespace(s.charAt(i))){
                ++stringSize;
            }
        }

        //Counting of vowels and consonants
        for (int i = 0; i < s.length(); i++){
            if (Character.isLetter(s.charAt(i))){
                if (vowels.indexOf(Character.toLowerCase(s.charAt(i))) != -1){
                    ++numberOfVowels;
                    charVowels.append(s.charAt(i));
                }else {
                    ++numberOfConsonants;
                    charConsonants.append(s.charAt(i));
                }
            }
        }

        //Appending the required information into one string
        generatedString.append(stringSize);
        generatedString.append(" ");
        generatedString.append(charVowels);
        generatedString.append(" ");
        generatedString.append(numberOfVowels);
        generatedString.append(" ");
        generatedString.append(charConsonants);
        generatedString.append(" ");
        generatedString.append(numberOfConsonants);

        return generatedString.toString();
    }

}
