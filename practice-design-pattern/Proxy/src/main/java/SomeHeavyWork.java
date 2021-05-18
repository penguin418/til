public class SomeHeavyWork implements HeavyWork{
    private String value = "none";

    public SomeHeavyWork(String value){
        System.out.println("do heavy pre-process");
        this.value = value.repeat(2);
    }

    @Override
    public String getValue() throws InterruptedException {
        return this.value;
    }
}
