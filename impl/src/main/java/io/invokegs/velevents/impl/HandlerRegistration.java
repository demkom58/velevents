package io.invokegs.velevents.impl;


import io.invokegs.velevents.api.EventHandler;

/**
 * Represents the registration of a single {@link EventHandler}.
 */
public final class HandlerRegistration<OWNER> {
    final OWNER plugin;
    final short order;
    final Class<?> eventType;
    final EventHandler<Object> handler;

    /**
     * The instance of the {@link EventHandler} or the listener instance that was registered.
     */
    final Object instance;

    HandlerRegistration(final OWNER plugin, final short order,
                                final Class<?> eventType, final Object instance, final EventHandler<Object> handler) {
        this.plugin = plugin;
        this.order = order;
        this.eventType = eventType;
        this.instance = instance;
        this.handler = handler;
    }

    public OWNER getPlugin() {
        return plugin;
    }

    public short getOrder() {
        return order;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public EventHandler<Object> getHandler() {
        return handler;
    }
}
