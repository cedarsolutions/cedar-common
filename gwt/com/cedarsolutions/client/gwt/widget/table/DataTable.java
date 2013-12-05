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
package com.cedarsolutions.client.gwt.widget.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedarsolutions.client.gwt.custom.table.CheckboxCell;
import com.cedarsolutions.client.gwt.custom.table.SwitchableSelectionModel;
import com.cedarsolutions.client.gwt.custom.table.SwitchableSelectionModel.SelectionType;
import com.cedarsolutions.web.metadata.NativeEventType;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.ProvidesKey;

/**
 * Standardizes CellTable behavior.
 * @param <T> Type of the table.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DataTable<T> extends CellTable<T> {

    /** Selection column that is configured, if any. */
    private SelectionColumn selectionColumn;

    /** Selection column that is configured, if any. */
    private SelectionHeader selectionHeader;

    /** No rows message that is configured, if any. */
    private String noRowsMessage;

    /** Column headers, for reference. */
    private Map<Integer, Object> columnHeaders = new HashMap<Integer, Object>();

    /** Column footers, for reference. */
    private Map<Integer, Object> columnFooters = new HashMap<Integer, Object>();

    /** Create a DataTable. */
    public DataTable(int pageSize, String width) {
        this(getStandardResources(), pageSize, width);
    }

    /** Create a DataTable. */
    public DataTable(Resources resources, int pageSize, String width) {
        super(pageSize, resources);
        this.setWidth(width, true);
    }

    /** Create a DataTable. */
    public DataTable(int pageSize, String width, ProvidesKey<T> keyProvider) {
        this(getStandardResources(), pageSize, width, keyProvider);
    }

    /** Create a DataTable. */
    public DataTable(Resources resources, int pageSize, String width, ProvidesKey<T> keyProvider) {
        super(pageSize, resources, keyProvider);
        this.setWidth(width, true);
    }

    /** Create a DataTable. */
    public DataTable(int pageSize, String width, ProvidesKey<T> keyProvider, Widget loadingIndicator) {
        this(getStandardResources(), pageSize, width, keyProvider, loadingIndicator);
    }

    /** Create a DataTable. */
    public DataTable(Resources resources, int pageSize, String width, ProvidesKey<T> keyProvider, Widget loadingIndicator) {
        super(pageSize, resources, keyProvider, loadingIndicator);
        this.setWidth(width, true);
    }

    /** Get a standard pager for use with this table. */
    public DataTablePager getPager() {
        return new DataTablePager(this);
    }

    /** Get the standard resources for this widget. */
    public static Resources getStandardResources() {
        return GWT.create(StandardResources.class);
    }

    /** Get the disabled resources for this widget. */
    public static Resources getDisabledResources() {
        return GWT.create(DisabledResources.class);
    }

    /** Set a message to be displayed if there are no rows. */
    public void setNoRowsMessage(String noRowsMessage) {
        setNoRowsMessage(noRowsMessage, null);
    }

    /** Set a message to be displayed if there are no rows. */
    public void setNoRowsMessage(String noRowsMessage, String styleName) {
        this.noRowsMessage = noRowsMessage;
        Label label = new Label(noRowsMessage);
        if (styleName != null) {
            label.setStyleName(styleName);
        }
        this.setEmptyTableWidget(label);
    }

    /** Get the message to be displayed if there are no rows (null if no message is set). */
    public String getNoRowsMessage() {
        return this.noRowsMessage;
    }

    /** Get the selected records (if a selection model is configured). */
    public List<T> getSelectedRecords() {
        List<T> selected = new ArrayList<T>();

        if (this.getSelectionModel() != null) {
            for (T record : this.getVisibleItems()) {
                if (this.getSelectionModel().isSelected(record)) {
                    selected.add(record);
                }
            }
        }

        return selected;
    }

    /** Select all visible items (if a selection model is configured). */
    public void selectAll() {
        this.selectItems(true);
    }

    /** Deselect all visible items (if a selection model is configured). */
    public void selectNone() {
        this.selectItems(false);
    }

    /** Select or deselect all visible items (if a selection model is configured). */
    public void selectItems(boolean selected) {
        if (this.getSelectionModel() != null) {
            for (T item : this.getVisibleItems()) {
                this.getSelectionModel().setSelected(item, selected);
            }
        }
    }

    /** Select a specific visible item (if a selection model is configured). */
    public void selectItem(T item) {
        if (this.getSelectionModel() != null) {
            if (this.getVisibleItems().contains(item)) {
                this.getSelectionModel().setSelected(item, true);
            }
        }
    }

    /** Deselect a specific visible item (if a selection model is configured). */
    public void deselectItem(T item) {
        if (this.getSelectionModel() != null) {
            if (this.getVisibleItems().contains(item)) {
                this.getSelectionModel().setSelected(item, false);
            }
        }
    }

    /** Whether a selection column is configured. */
    public boolean hasSelectionColumn() {
        return this.selectionHeader != null && this.selectionColumn != null;
    }

    /** Set the selection type on the selection column. */
    public void setSelectionType(SelectionType selectionType) {
        if (this.hasSelectionColumn()) {
            this.selectionHeader.setSelectionType(selectionType);
            this.selectionColumn.setSelectionType(selectionType);
        }
    }

    /** Get the selection type on the selection column. */
    public SelectionType getSelectionType() {
        return this.hasSelectionColumn() ? this.selectionHeader.getSelectionType() : null;
    }

    /** Add a selection column, which uses the MULTI selection type by default. */
    public void addSelectionColumn(double width, Unit unit, ProvidesKey<T> keyProvider) {
        this.addSelectionColumn(width, unit, keyProvider, SelectionType.MULTI);
    }

    /** Add a section column. */
    public void addSelectionColumn(double width, Unit unit, ProvidesKey<T> keyProvider, SelectionType selectionType) {
        SwitchableSelectionModel<T> selectionModel = new SwitchableSelectionModel<T>(keyProvider, selectionType);
        this.setSelectionModel(selectionModel, DefaultSelectionEventManager.<T>createCheckboxManager());
        this.selectionHeader = new SelectionHeader(this, selectionModel);
        this.selectionColumn = new SelectionColumn(selectionModel);
        this.addColumn(this.selectionColumn, this.selectionHeader);
        this.setColumnWidth(this.selectionColumn, width, unit);
    }

    /** {@inheritDoc} */
    @Override
    public void addColumn(Column<T, ?> col) {
        super.addColumn(col);
        this.trackColumn(col, null, null);
    }

    /** {@inheritDoc} */
    @Override
    public void addColumn(Column<T, ?> col, Header<?> header) {
        super.addColumn(col, header);
        this.trackColumn(col, header, null);
    }

    /** {@inheritDoc} */
    @Override
    public void addColumn(Column<T, ?> col, Header<?> header, Header<?> footer) {
        super.addColumn(col, header, footer);
        this.trackColumn(col, header, footer);
    }

    /** {@inheritDoc} */
    @Override
    public void addColumn(Column<T, ?> col, String headerString) {
        super.addColumn(col, headerString);
        this.trackColumn(col, headerString, null);
    }

    /** {@inheritDoc} */
    @Override
    public void addColumn(Column<T, ?> col, SafeHtml headerHtml) {
        super.addColumn(col, headerHtml);
        this.trackColumn(col, headerHtml, null);
    }

    /** {@inheritDoc} */
    @Override
    public void addColumn(Column<T, ?> col, String headerString, String footerString) {
        super.addColumn(col, headerString, footerString);
        this.trackColumn(col, headerString, footerString);
    }

    /** {@inheritDoc} */
    @Override
    public void addColumn(Column<T, ?> col, SafeHtml headerHtml, SafeHtml footerHtml) {
        super.addColumn(col, headerHtml, footerHtml);
        this.trackColumn(col, headerHtml, footerHtml);
    }

    /** Track the header and footer applied to a column. */
    protected void trackColumn(Column<T, ?> col, Object header, Object footer) {
        this.columnHeaders.put(getColumnCount() - 1, header);
        this.columnFooters.put(getColumnCount() - 1, footer);
    }

    /** Get the header for a column. */
    public Object getColumnHeader(int col) {
        if (this.columnHeaders.containsKey(col)) {
            return this.columnHeaders.get(col);
        } else {
            return null;
        }
    }

    /** Get the header for a column. */
    public Object getColumnFooter(int col) {
        if (this.columnFooters.containsKey(col)) {
            return this.columnFooters.get(col);
        } else {
            return null;
        }
    }

    /**
     * Resources used to render the standard DataTable.
     * @see <a href="https://code.google.com/p/google-web-toolkit/issues/detail?id=6144">Google Code</a>
     */
    public interface StandardResources extends CellTable.Resources {

        /** Standard style. */
        public interface StandardStyle extends CellTable.Style { }

        /** Get the standard style from DataTable.css. */
        @Override
        @Source({ CellTable.Style.DEFAULT_CSS, "DataTable.css" })
        StandardStyle cellTableStyle();

    }

    /**
     * Resources used to render the disabled DataTable.
     *
     * <p>
     * The disabled style "grays-out" the UI so that the table looks disabled,
     * somewhat consistent with what happens to dropdowns, buttons, etc.
     * Unfortunately, as far as I can tell, it's not possible to change the
     * style on a table once it's been set.  So, the only way I've been able to
     * work as of this writing is to create two tables in parallel and flip
     * between them (i.e.  hide one HTML panel and show another).
     * </p>
     *
     * <p>
     * On StackOverflow, I got several suggestions for workarounds, but both
     * require customizing the rendering code for the table, and for the time
     * being I'm not interested in dealing with that.  The solutions work, I'm
     * just not convinced that tinkering in the table implementation is any
     * less clunky or intrusive than maintaining two tables.
     * </p>
     *
     * @see <a href="https://code.google.com/p/google-web-toolkit/issues/detail?id=6144">Google Code</a>
     * @see <a href="http://stackoverflow.com/questions/20086134/change-the-style-of-a-gwt-celltable-dynamically-to-visually-mark-it-as-disabled">StackOverflow</a>
     */
    public interface DisabledResources extends CellTable.Resources {

        /** Disabled style. */
        public interface DisabledStyle extends CellTable.Style { }

        /** Get the disabled style from DisabledDataTable.css. */
        @Override
        @Source({ CellTable.Style.DEFAULT_CSS, "DisabledDataTable.css" })
        DisabledStyle cellTableStyle();

    }

    /** Selection column, which holds the selection checkbox. */
    public class SelectionColumn extends Column<T, Boolean> {
        private SwitchableSelectionModel<T> selectionModel;

        public SelectionColumn(SwitchableSelectionModel<T> selectionModel) {
            super(new CheckboxCell(true, false));
            this.setSortable(false);
            this.selectionModel = selectionModel;
        }

        @Override
        public Boolean getValue(T object) {
            return selectionModel.isSelected(object);
        }

        public void setSelectionType(SelectionType selectionType) {
            this.selectionModel.setSelectionType(selectionType);
        }

        public SelectionType getSelectionType() {
            return this.selectionModel.getSelectionType();
        }
    }

    /** Selection header, which holds a select all/select none checkbox. */
    public class SelectionHeader extends Header<Boolean> {
        private boolean selected = false;
        private DataTable<T> table;
        private SwitchableSelectionModel<T> selectionModel;

        public SelectionHeader(DataTable<T> table, SwitchableSelectionModel<T> selectionModel) {
            super(new CheckboxCell(true, false));
            this.table = table;
            this.selectionModel = selectionModel;
            this.refreshCheckboxState();
        }

        @Override
        public Boolean getValue() {
            return selected;
        }

        @Override
        public void onBrowserEvent(Context context, Element elem, NativeEvent event) {
            if (NativeEventType.CHANGE.equals(NativeEventType.convert(event.getType()))) {
                this.selected = !this.selected;
                this.table.selectItems(this.selected);
            }
        }

        public void setSelectionType(SelectionType selectionType) {
            this.selectionModel.setSelectionType(selectionType);
            this.refreshCheckboxState();
        }

        public SelectionType getSelectionType() {
            return this.selectionModel.getSelectionType();
        }

        private void refreshCheckboxState() {
            CheckboxCell cell = (CheckboxCell) this.getCell();
            cell.setEnabled(this.selectionModel.getSelectionType() == SelectionType.MULTI);
        }
    }

}
