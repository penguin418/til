import validator.Validator;

public class Test {
    public static void main(String[] args) throws IllegalAccessException {

        try {
            RegisterForm form = new RegisterForm("email", "password", "010-1010-0102");
            System.out.println("validated!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            RegisterForm form = new RegisterForm("email@email.com", "password!", "010-1010-0102");
            System.out.println("validated!");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
