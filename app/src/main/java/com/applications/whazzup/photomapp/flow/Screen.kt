package com.applications.whazzup.photomapp.flow

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by Alex on 28.05.2017.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class Screen(val value: Int)
