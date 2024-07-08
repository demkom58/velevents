/*
 * Copyright (C) 2018-2021 Velocity Contributors
 *
 * The Velocity API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the api top-level directory.
 */

package io.invokegs.velevents.api;

import java.util.concurrent.CompletableFuture;

/**
 * Allows plugins to register and deregister listeners for event handlers.
 *
 * @param <OWNER> the type of the plugin that owns the event manager
 */
public interface EventManager<OWNER> {

  /**
   * Requests that the specified {@code listener} listen for events and associate it with the {@code
   * plugin}.
   *
   * @param plugin the plugin to associate with the listener
   * @param listener the listener to register
   */
  void register(OWNER plugin, Object listener);

  /**
   * Requests that the specified {@code handler} listen for events and associate it with the {@code
   * plugin}.
   *
   * @param plugin the plugin to associate with the handler
   * @param eventClass the class for the event handler to register
   * @param handler the handler to register
   * @param <EVENT> the event type to handle
   */
  default <EVENT> void register(OWNER plugin, Class<EVENT> eventClass, EventHandler<EVENT> handler) {
    register(plugin, eventClass, PostOrder.NORMAL, handler);
  }

  /**
   * Requests that the specified {@code handler} listen for events and associate it with the {@code
   * plugin}.
   *
   * @param plugin the plugin to associate with the handler
   * @param eventClass the class for the event handler to register
   * @param postOrder the order in which events should be posted to the handler
   * @param handler the handler to register
   * @param <EVENT> the event type to handle
   */
  <EVENT> void register(OWNER plugin, Class<EVENT> eventClass, PostOrder postOrder,
                        EventHandler<EVENT> handler);

  /**
   * Fires the specified event to the event bus asynchronously. This allows Velocity to continue
   * servicing connections while a plugin handles a potentially long-running operation such as a
   * database query.
   *
   * @param event the event to fire
   * @return a {@link CompletableFuture} representing the posted event
   */
  <EVENT> CompletableFuture<EVENT> fire(EVENT event);

  /**
   * Posts the specified event to the event bus and discards the result.
   *
   * @param event the event to fire
   */
  default void fireAndForget(Object event) {
    fire(event);
  }

  /**
   * Unregisters all listeners for the specified {@code plugin}.
   *
   * @param plugin the plugin to deregister listeners for
   */
  void unregisterListeners(OWNER plugin);

  /**
   * Unregisters a specific listener for a specific plugin.
   *
   * @param plugin the plugin associated with the listener
   * @param listener the listener to deregister
   */
  void unregisterListener(OWNER plugin, Object listener);

  /**
   * Unregisters a specific event handler for a specific plugin.
   *
   * @param plugin the plugin to associate with the handler
   * @param handler the handler to register
   * @param <EVENT> the event type to handle
   */
  <EVENT> void unregister(OWNER plugin, EventHandler<EVENT> handler);
}
