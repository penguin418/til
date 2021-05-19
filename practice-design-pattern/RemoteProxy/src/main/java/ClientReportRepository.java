import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ClientReportRepository {

    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("localhost", 9999);
            ReportRepository repository = (ReportRepository) registry.lookup("repo");

            repository.save(1, "hello world");

            System.out.println(repository.findById(1));

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
