package de.chloedev.cdnlib.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

    private static final Map<Class<? extends Event>, List<EventData>> REGISTRY_MAP = new HashMap<>();

    private static void sort(Class<? extends Event> clazz) {
        ArrayList<EventData> flexableArray = new ArrayList<EventData>();
        for (byte b : Priority.values()) {
            for (EventData methodData : EventManager.REGISTRY_MAP.get(clazz)) {
                if (methodData.priority() == b) {
                    flexableArray.add(methodData);
                }
            }
        }
        EventManager.REGISTRY_MAP.put(clazz, flexableArray);
    }

    private static boolean isMethodBad(Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventHandler.class);
    }

    public static List<EventData> get(Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

    @SuppressWarnings("unchecked")
    public static void register(Method method, Object o) {
        Class<?> clazz = method.getParameterTypes()[0];
        EventData methodData = new EventData(o, method, method.getAnnotation(EventHandler.class).value());
        if (!methodData.target().isAccessible()) methodData.target().setAccessible(true);
        if (REGISTRY_MAP.containsKey(clazz)) {
            if (!REGISTRY_MAP.get(clazz).contains(methodData)) {
                REGISTRY_MAP.get(clazz).add(methodData);
                sort((Class<? extends Event>) clazz);
            }
        } else {
            List<EventData> l = new ArrayList<>();
            l.add(methodData);
            REGISTRY_MAP.put((Class<? extends Event>) clazz, l);
        }

    }

    public static void register(Object o) {
        for (Method method : o.getClass().getMethods()) {
            if (!isMethodBad(method)) register(method, o);
        }
    }
}