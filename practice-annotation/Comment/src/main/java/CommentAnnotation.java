import static java.lang.annotation.ElementType.*;

import java.lang.annotation.*;


@Documented // javadoc detail 항목에 표시된다
@Retention(RetentionPolicy.CLASS)
@Target({FIELD, METHOD})
public @interface CommentAnnotation {
    String value();
}