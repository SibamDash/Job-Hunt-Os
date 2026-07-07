package com.jobhuntos.event;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventBus {
    private static final EventBus instance = new EventBus();
    private final List<Consumer<String>> listeners = new ArrayList<>();
    
    private EventBus() {}
    public static EventBus getInstance() { return instance; }
    
    public void subscribe(Consumer<String> listener) {
        listeners.add(listener);
    }
    
    public void publish(String eventType) {
        for (Consumer<String> listener : listeners) {
            listener.accept(eventType);
        }
    }
}
