public class Account {
    @CommentAnnotation("must be longer than 3 characters")
    private String username;

    public Account(String username) {
        this.username = username;
    }

    @CommentAnnotation(value = "new username should be checked in advance")
    public void updateUsername(String username) {
        if (username.length() < 3) {
            throw new IllegalArgumentException();
        }
        this.username = username;
    }
}
