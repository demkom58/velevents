/*
 * Copyright (C) 2021 Velocity Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.invokegs.velevents.impl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class EventTypeTracker {
    private final ConcurrentMap<Class<?>, Set<Class<?>>> friends;

    public EventTypeTracker() {
        this.friends = new ConcurrentHashMap<>();
    }

    public Collection<Class<?>> getFriendsOf(final Class<?> eventType) {
        if (friends.containsKey(eventType)) {
            return friends.get(eventType);
        }

        final Collection<Class<?>> types = getEventTypes(eventType);
        for (Class<?> type : types) {
            if (type == eventType) {
                continue;
            }

            this.friends.merge(type, Set.of(eventType), (oldVal, newSingleton) -> {
                Set<Class<?>> newSet = new HashSet<>();
                newSet.addAll(oldVal);
                newSet.addAll(newSingleton);
                return Collections.unmodifiableSet(newSet);
            });
        }
        return types;
    }

    private static Collection<Class<?>> getEventTypes(final Class<?> eventType) {
        List<Class<?>> types = new ArrayList<>();
        Class<?> currentClass = eventType;

        while (currentClass != null && currentClass != Object.class) {
            types.add(currentClass);
            types.addAll(Arrays.asList(currentClass.getInterfaces()));
            currentClass = currentClass.getSuperclass();
        }

        return types.stream().distinct().toList();
    }
}
