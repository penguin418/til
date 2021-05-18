public class SomeHeavyWorkVirtualProxy implements HeavyWork{
    private String value;
    private SomeHeavyWork someHeavyWork = null;
    public SomeHeavyWorkVirtualProxy(String value){
        this.value = value;
    }

    @Override
    public String getValue() throws InterruptedException {
        if (someHeavyWork == null)
            someHeavyWork = new SomeHeavyWork(this.value);
        return someHeavyWork.getValue();
    }
}
