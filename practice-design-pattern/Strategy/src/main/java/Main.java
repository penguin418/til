public class Main {
    public static void main(String[] args) {
        Sam sam = new Sam();
        sam.setHelloPolicy(new HelloHelloPolicy());
        sam.hello();

        sam.setHelloPolicy(new HiPolicy());
        sam.hello();
    }
}
