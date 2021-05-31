public class Adapter implements TargetInterfafce {
    ThirdPartyLibrary library;
    public Adapter(ThirdPartyLibrary library){
        this.library = library;
    }

    @Override
    public void callFunction() {
        System.out.println("adapter's callFunction: call function");
        library.function();
    }
}
