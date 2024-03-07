/**
 * JASMIN, Ramon Emmiel P.
 * 2230748
 *
 * SAMPLE RUN:
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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client implements Runnable{

    public static void main(String[] args) {
        (new Thread(new Client())).start();
    }


    @Override
    public void run() {
        try {
            MidInterface1 utility = (MidInterface1) Naming.lookup("rmi://localhost/profileString");

            System.out.println("TEST CASES");
            System.out.println("==========");
            System.out.println("TEST 1: sa1nt & louis = " + utility.profileString("sa1nt & louis"));
            System.out.println("TEST 2: un1v3rsityyyyy = " + utility.profileString("un1v3rsityyyyy"));
            System.out.println("TEST 3: R4mon Emmiel is V3ry h4nds0me$$$ = " + utility.profileString("R4mon Emmiel is V3ry h4nds0me$$$"));

        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
