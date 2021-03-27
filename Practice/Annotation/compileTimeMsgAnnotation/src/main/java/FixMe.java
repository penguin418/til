import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
public @interface FixMe {
    String value();
    String[] suggestions() default "[unassigned]";
}
