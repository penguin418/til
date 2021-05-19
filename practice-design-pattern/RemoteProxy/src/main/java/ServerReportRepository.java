import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class ServerReportRepository implements ReportRepository {
    private Map<Integer, String> reportMap = new HashMap<>();

    public void save(int id, String text) throws RemoteException{
        this.reportMap.put(id, text);
    }
    public String findById(int id) throws RemoteException{
        return this.reportMap.get(id);
    }

    public static void main(String[] args) {
        try{
            ServerReportRepository repository = new ServerReportRepository();
            ReportRepository stub = (ReportRepository) UnicastRemoteObject.exportObject(repository, 0);
            Registry registry = LocateRegistry.getRegistry("localhost", 9999);
            registry.bind("repo", stub);

            System.out.println("ready");
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
