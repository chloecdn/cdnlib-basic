package de.chloedev.cdnlib.event;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Event {

    public Event invoke() {
        final List<EventData> l = EventManager.get(this.getClass());
        if (l != null) {
            for (EventData data : l) {
                try {
                    data.target().invoke(data.source(), this);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return this;
    }
}
