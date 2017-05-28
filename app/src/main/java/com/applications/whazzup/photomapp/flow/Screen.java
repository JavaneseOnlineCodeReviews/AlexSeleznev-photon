package com.applications.whazzup.photomapp.flow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Alex on 28.05.2017.
 */
@Retention(RetentionPolicy.RUNTIME) @Target(ElementType.TYPE)
public @interface Screen {
    int value();
}
