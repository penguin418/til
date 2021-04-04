import static validator.FieldType.*;

import validator.Valid;
import validator.Validator;


public class RegisterForm {
    @Valid(EMAIL)
    String email;
    @Valid(PASSWORD)
    String password;
    @Valid(PHONE_NUMBER)
    String phoneNumber;

    public RegisterForm(String email, String password, String phoneNumber) throws IllegalAccessException {
        this.email = email;
        this.password =password;
        this.phoneNumber = phoneNumber;

        Validator validator = new Validator();
        validator.validate(this);
    }
}