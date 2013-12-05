/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013 Kenneth J. Pronovici.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Apache License, Version 2.0.
 * See LICENSE for more information about the licensing terms.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Author   : Kenneth J. Pronovici <pronovic@ieee.org>
 * Language : Java 6
 * Project  : Common Java Functionality
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.cedarsolutions.client.gwt.custom.table;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel.AbstractSelectionModel;
import com.google.gwt.view.client.SetSelectionModel;

/**
 * Selection model that allows switching the selection type, for use with DataTable.
 *
 * <h3>
 * Code Source
 * </h3>
 *
 * <p>
 * This code was initially copied from GWT under the terms of its license, and
 * was later modified to allow both single and multiple selection types.
 * </p>
 *
 * <blockquote>
 *    <table border="1" cellpadding="5" cellspacing="0">
 *       <tbody>
 *          <tr>
 *             <td><i>Source:</i></td>
 *             <td>{@link com.google.gwt.view.client.MultiSelectionModel}</td>
 *          </tr>
 *          <tr>
 *             <td><i>Version:</i></td>
 *             <td>GWT 2.5.1</td>
 *          </tr>
 *          <tr>
 *             <td><i>Date:</i></td>
 *             <td>December, 2013</td>
 *          </tr>
 *       </tbody>
 *    </table>
 * </blockquote>
 *
 * @param <T> Type of the associated table.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SwitchableSelectionModel<T> extends AbstractSelectionModel<T> implements SetSelectionModel<T> {

    private SelectionType selectionType;
    private Map<Object, T> selectedSet;
    private Map<Object, SelectionChange<T>> selectionChanges;

    public SwitchableSelectionModel(ProvidesKey<T> keyProvider, SelectionType selectionType) {
        super(keyProvider);
        this.selectionType = selectionType;
        this.selectedSet = new HashMap<Object, T>();
        this.selectionChanges = new HashMap<Object, SelectionChange<T>>();
    }

    public void setSelectionType(SelectionType selectionType) {
        this.selectionType = selectionType;
        this.selectedSet.clear();
        this.selectionChanges.clear();
    }

    public SelectionType getSelectionType() {
        return this.selectionType;
    }

    @Override
    public void clear() {
        this.selectionChanges.clear();

        for (T value : this.selectedSet.values()) {
            this.selectionChanges.put(getKey(value), new SelectionChange<T>(value, false));
        }

        scheduleSelectionChangeEvent();
    }

    @Override
    public Set<T> getSelectedSet() {
        this.resolveChanges();
        return new HashSet<T>(this.selectedSet.values());
    }

    @Override
    public boolean isSelected(T object) {
        this.resolveChanges();
        return this.selectedSet.containsKey(getKey(object));
    }

    @Override
    public void setSelected(T object, boolean selected) {
        if (this.selectionType == SelectionType.MULTI) {
            this.selectionChanges.put(getKey(object), new SelectionChange<T>(object, selected));
            this.scheduleSelectionChangeEvent();
        } else {
            if (selected) {
                for (T value : this.selectedSet.values()) {
                    this.selectionChanges.put(getKey(value), new SelectionChange<T>(value, false));
                }

                this.selectionChanges.put(getKey(object), new SelectionChange<T>(object, true));
                this.scheduleSelectionChangeEvent();
            } else {
                if (isSelected(object)) {
                    this.selectionChanges.put(getKey(object), new SelectionChange<T>(object, false));
                    this.scheduleSelectionChangeEvent();
                }
            }
        }
    }

    @Override
    protected void fireSelectionChangeEvent() {
        if (isEventScheduled()) {
            setEventCancelled(true);
        }

        this.resolveChanges();
    }

    private void resolveChanges() {
        if (this.selectionChanges.isEmpty()) {
            return;
        }

        boolean changed = false;
        for (Map.Entry<Object, SelectionChange<T>> entry : this.selectionChanges.entrySet()) {
            Object key = entry.getKey();
            SelectionChange<T> value = entry.getValue();
            boolean selected = value.isSelected;

            T oldValue = this.selectedSet.get(key);
            if (selected) {
                this.selectedSet.put(key, value.item);
                Object oldKey = getKey(oldValue);
                if (!changed) {
                    changed = (oldKey == null) ? (key != null) : !oldKey.equals(key);
                }
            } else {
                if (oldValue != null) {
                    this.selectedSet.remove(key);
                    changed = true;
                }
            }
        }

        this.selectionChanges.clear();

        if (changed) {
            SelectionChangeEvent.fire(this);
        }
    }

    /** Available selection types. */
    public enum SelectionType {
        SINGLE,
        MULTI,
    }

    /** Stores an item and its pending selection state. */
    private static class SelectionChange<T> {
        private final T item;
        private final boolean isSelected;

        SelectionChange(T item, boolean isSelected) {
            this.item = item;
            this.isSelected = isSelected;
        }
    }

}
