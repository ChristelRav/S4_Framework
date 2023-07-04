package etu2064.framework.myAnnotations;

import java.lang.annotation.*;
@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)

public @interface Url {
    String url () default "";
}
