public class Sam {
    private HelloPolicy helloPolicy;
    public void  setHelloPolicy(HelloPolicy helloPolicy){
        this.helloPolicy = helloPolicy;
    }

    public void hello(){
        this.helloPolicy.hello();
    }
}
