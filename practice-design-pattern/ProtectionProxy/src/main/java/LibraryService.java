public interface LibraryService {
    public Boolean checkOut(String username, String bookname);
    public Boolean checkIn(String username, String bookname);
}
