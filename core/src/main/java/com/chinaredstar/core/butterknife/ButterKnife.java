package com.chinaredstar.core.butterknife;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.chinaredstar.core.base.BaseFragment;

import java.lang.reflect.Field;


/**
 * Created by hairui.xiang on 2017/9/25.
 */

public class ButterKnife {

    private static boolean isUsingAndroidAnnotations(@NonNull Object object) {
        if (!object.getClass().getSimpleName().endsWith("_")) {
            return false;
        }
        try {
            Class clazz = Class.forName("org.androidannotations.api.view.HasViews");
            return clazz.isInstance(object);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void inject(Activity target) {
        try {
            Class clazz = target.getClass();
//            if (isUsingAndroidAnnotations(target)) {
//                clazz = clazz.getSuperclass();
//            }
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(InjectView.class)) {
                    InjectView inject = field.getAnnotation(InjectView.class);
                    int id = inject.value();
                    if (id > 0) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(target, target.findViewById(id));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void inject(BaseFragment target) {
        try {
            Class clazz = target.getClass();
//            if (isUsingAndroidAnnotations(target)) {
//                clazz = clazz.getSuperclass();
//            }
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(InjectView.class)) {
                    InjectView inject = field.getAnnotation(InjectView.class);
                    int id = inject.value();
                    if (id > 0) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(target, target.findViewById(id));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
