import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryServiceImpl implements LibraryService{
    private Map<String, Integer> books = new HashMap<>(){{
        put("A", 1);
        put("B", 3);
        put("D", 2);
    }};

    private Map<String, List<String>> bookRentalHistory = new HashMap<>(){{
    }};

    @Override
    public Boolean checkOut(String username, String bookname) {
        if (books.containsKey(bookname) && books.get(bookname)>0){
            books.replace(bookname, books.get(bookname)-1);
            record(username, bookname, true);
            return true;
        }
        else return false;
    }

    @Override
    public Boolean checkIn(String username, String bookname) {
        if (books.containsKey(bookname)) {
            books.replace(bookname, books.get(bookname) + 1);
            record(username, bookname, false);
            return true;
        }else
            return false;
    }

    public void record(String username, String bookname, boolean isRental){
        List<String> rentals = bookRentalHistory.get(username);

        if (isRental){
            if (rentals == null)
                rentals = new ArrayList<>();
            rentals.add(bookname);
        }else{
            rentals.remove(bookname);
        }

        bookRentalHistory.remove(username);
        bookRentalHistory.put(username, rentals);
    }
}
