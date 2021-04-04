import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * \@Todo 내용을 출력해 줍니다
 */
//@AutoService(Processor.class)
public class KeywordProcessor extends AbstractProcessor {

    private String packageName;
    private Messager messager;
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (Class<? extends Annotation> annotation : supportedAnnotations()) {
                processAnnotations(roundEnv, annotation);
            }
        }
        return true;
    }

    private void processAnnotations(RoundEnvironment roundEnv, Class<? extends Annotation> annotation) {
        final Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
        for (Element annotatedElement : annotatedElements) {
            String msg = "";
            if (annotation == Todo.class) {
                handleTodoMsg(annotatedElement);
            } else if (annotation == FixMe.class) {
                handleFixMeMsg(annotatedElement);
            } else if (annotation == XXX.class) {
                handleXXXMsg(annotatedElement);
            }
//            if (annotatedElement.getKind() != ElementKind.ANNOTATION_TYPE){
//                messager.printMessage(Diagnostic.Kind.ERROR, "hello");
//            }


//            if (annotatedElement.getKind() != ElementKind.CONSTRUCTOR) {
//                messager.printMessage(Diagnostic.Kind.WARNING, "@BuilderFactory only support constructor", annotatedElement);
//                throw new IllegalStateException();
//            }
//            // 오예, 생성자입니다. 이제 생성자로 형변환해도 됩니다
//            ExecutableElement constructor = (ExecutableElement) annotatedElement;
//            for (VariableElement param : constructor.getParameters()) {
//                System.out.println(param + ":" + param.getClass());
//            }
        }
    }

    private void handleTodoMsg(Element element) {
        String msg = "[TODO]" + element.getAnnotation(Todo.class).value();
        messager.printMessage(Diagnostic.Kind.WARNING, msg, element);
    }

    private void handleFixMeMsg(Element element) {
        String msg = "[FixMe]" + element.getAnnotation(FixMe.class).value();
        String suggestions = String.join(",", element.getAnnotation(FixMe.class).suggestions());
        messager.printMessage(Diagnostic.Kind.WARNING, msg + suggestions, element);
    }

    private void handleXXXMsg(Element element) {
        String msg = "[XXX]" + element.getAnnotation(XXX.class).value();
        String suggestions = String.join(",", element.getAnnotation(XXX.class).suggestions());
        messager.printMessage(Diagnostic.Kind.WARNING, msg + suggestions, element);
    }


    private Set<Class<? extends Annotation>> supportedAnnotations() {
        // 지원하는 어노테이션들
        return new HashSet<>(Arrays.asList(
                Todo.class,
                FixMe.class,
                XXX.class
        ));
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // 지원하는 어노테이션을 반환합니다
        return supportedAnnotations().stream().map(Class::getCanonicalName).collect(Collectors.toSet());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // 지원하는 버전을 반환합니다
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }
}
