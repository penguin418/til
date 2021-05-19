import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReportRepository extends Remote {
    public void save(int id, String text) throws RemoteException;
    public String findById(int id) throws RemoteException;
}
