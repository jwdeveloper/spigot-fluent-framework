package io.github.jwdeveloper.spigot.fluent.core.common.java;


import org.bukkit.Material;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


public class ObjectUtility {

    private static final Set<Class<?>> WRAPPER_TYPES = new HashSet(Arrays.asList(
            Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));

    public static boolean isPrimitiveType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    public static <T extends Enum<T>> T enumFromString(Class<T> c, String string) {
        if (c != null && string != null) {
            return Enum.valueOf(c, string.trim().toUpperCase());
        }
        return null;
    }

    public static Object castStringToPrimitiveType(String value, Class<?> type) {
        return switch (type.getName()) {
            case "java.lang.String" -> value;
            case "org.bukkit.Material" -> Material.valueOf(value);
            case "int", "java.lang.Number", "java.lang.Integer" -> Integer.parseInt(value);
            case "float" -> Float.parseFloat(value);
            case "double" -> Double.parseDouble(value);
            case "boolean", "java.lang.Boolean" -> Boolean.parseBoolean(value);
            default -> null;
        };
    }

    public static Object getPrivateField(Object object, String field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field objectField = clazz.getDeclaredField(field);
        objectField.setAccessible(true);
        Object result = objectField.get(object);
        objectField.setAccessible(false);
        return result;
    }

    public static boolean copyToObject(Object obj, Object desination, Class type) throws IllegalAccessException {
        for (var field : type.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            field.setAccessible(true);
            field.set(desination, field.get(obj));
        }
        return true;
    }

    public static <T> boolean copyToObjectDeep(T obj, T destination) throws IllegalAccessException {
        if (!obj.getClass().equals(destination.getClass())) {
           // FluentLogger.LOGGER.error("Unable deep copy object" + obj.getClass() + " to " + destination.getClass() + " this are different classes");
            return false;
        }

        var currentClass = obj.getClass();
        while (true) {
            var result = copyToObject(obj, destination, currentClass);
            if (!result) {
                break;
            }
            if (currentClass.getSuperclass().equals(Object.class)) {
                break;
            }
            currentClass = currentClass.getSuperclass();
        }
        return true;
    }

    public static Object copyObjectDeep(Object obj, Class type) throws InstantiationException, IllegalAccessException {
        var result = type.newInstance();
        if (!copyToObjectDeep(obj, result)) {
            return null;
        }
        return result;
    }

    public static Object copyObject(Object obj, Class type) throws InstantiationException, IllegalAccessException {
        var result = type.newInstance();
        if (!copyToObject(obj, result, type)) {
            return null;
        }
        return result;
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        var field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static Object getFieldValue(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Object result = null;
        var field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        result = field.get(obj);
        field.setAccessible(false);
        return result;
    }

    public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        var tempClass = type;
        while (tempClass != Object.class) {
            for (final Method method : tempClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    // Annotation annotInstance = method.getAnnotation(annotation);
                    methods.add(method);
                }
            }
            tempClass = tempClass.getSuperclass();
        }
        return methods;
    }
}
