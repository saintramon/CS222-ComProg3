package mexer1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MidInterface1 extends Remote {
    public String profileString(String s) throws RemoteException;

}
