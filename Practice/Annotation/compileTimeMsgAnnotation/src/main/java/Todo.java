import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
public @interface Todo {
    String value();
    String[] todos() default "[unassigned]";
    String[] suggestions() default "[unassigned]";
}
