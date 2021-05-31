public class Client {
    private TargetInterfafce target;

    public Client(TargetInterfafce target) {
        this.target = target;
    }

    public void callAdapterFunction(){
        System.out.println("Client's callAdapterFunction: callFunction");
        target.callFunction();
    }

    public static void main(String[] args) {
        // 원래 사용하던 라이브러리가 있었던 상황에서
        Client client1 = new Client(new CurrentLibrary());

        // 기존엔 이렇게 호출했지만,
        client1.callAdapterFunction();

        // client 는 라이브러리를 직접 갖지 않고 adapter 를 거쳐서 갖는다
        Client client2 =
                // adapter 인터페이스는 기존 라이브러리가 상속받던 것을 그대로 사용한다
                new Client((TargetInterfafce) new Adapter(new ThirdPartyLibrary()));

        // client 가 target interface 를 호출하는 것처럼 보이지만,
        client2.callAdapterFunction();

        // 실제로는 target이 가리키는 ThirdPartyLibrary 를 호출한다
        client2.target.callFunction();
    }
}
