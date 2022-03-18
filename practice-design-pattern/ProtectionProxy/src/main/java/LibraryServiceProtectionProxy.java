import java.util.ArrayList;
import java.util.List;

public class LibraryServiceProtectionProxy implements LibraryService{
    private List<String> oldSystemBlacklist = new ArrayList<>();
    private final LibraryService realService = new LibraryServiceImpl();

    @Override
    public Boolean checkOut(String username, String bookname) {
        if(oldSystemBlacklist.contains(username)) return false;

        return realService.checkOut(username,bookname);
    }

    @Override
    public Boolean checkIn(String username, String bookname) {
        return realService.checkIn(username,bookname);
    }
}
