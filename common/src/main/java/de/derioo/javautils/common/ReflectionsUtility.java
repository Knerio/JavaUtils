package de.derioo.javautils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.BiPredicate;

@UtilityClass
public class ReflectionsUtility {

    public Object callMethod(@NotNull Method method, Object instance, Object... args) throws InvocationTargetException, IllegalAccessException {
        return callMethod(method, instance, (o, parameter) -> true, args);
    }

    public Object callMethod(@NotNull Method method, Object instance, List<Object> args) throws InvocationTargetException, IllegalAccessException {
        return callMethod(method, instance, (o, parameter) -> true, args.toArray(Object[]::new));
    }

    public Object callMethod(@NotNull Method method, Object instance, BiPredicate<Object, Parameter> matches, List<Object> args) throws InvocationTargetException, IllegalAccessException {
        return callMethod(method, instance, matches, args.toArray(Object[]::new));
    }

    public Object callMethod(@NotNull Method method, Object instance, BiPredicate<Object, Parameter> matches, Object... args) throws InvocationTargetException, IllegalAccessException {
        List<Object> params = new ArrayList<>();
        List<Object> currentArgs = new ArrayList<>(List.of(args));
        for (Parameter parameter : method.getParameters()) {
            Pair<Object, Integer> result = find(parameter, matches, currentArgs.toArray(Object[]::new));
            params.add(result.getFirst());
            if (result.getSecond() != null) currentArgs.remove((int) result.getSecond());
        }
        method.setAccessible(true);
        return method.invoke(instance, params.toArray(Object[]::new));
    }


    private Pair<Object, Integer> find(Parameter parameter, BiPredicate<Object, Parameter> matches, Object... args) {
        return Arrays.stream(args)
                .filter(o -> {
                    boolean assignableFrom = o.getClass().isAssignableFrom(parameter.getType());
                    boolean test = matches.test(o, parameter);
                    return assignableFrom && test;
                })
                .findFirst()
                .map(o -> new Pair<>(o, Arrays.asList(args).indexOf(o)))
                .orElseGet(() -> {
                    for (int i = 0; i < args.length; i++) {
                        Object current = args[i];
                        for (Method method : current.getClass().getDeclaredMethods()) {
                            if (method.getParameters().length != 0) continue;
                            if (!method.getName().equals("get")) continue;
                            if (!method.getReturnType().equals(parameter.getType())) continue;
                            try {
                                return new Pair<>(method.invoke(current), i);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                return new Pair<>(null, null);
                            }
                        }
                    }
                    return new Pair<>(null, null);
                });
    }

    @AllArgsConstructor
    @Getter
    private static class Pair<T, V> {

        private final T first;
        private final V second;

    }

}
