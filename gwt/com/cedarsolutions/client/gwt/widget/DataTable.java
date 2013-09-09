/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2011-2013 Kenneth J. Pronovici.
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
package com.cedarsolutions.client.gwt.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedarsolutions.web.metadata.NativeEventType;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.cell.client.CheckboxCell;
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
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionModel;

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
        super(pageSize, getStyle());
        this.setWidth(width, true);
    }

    /** Create a DataTable. */
    public DataTable(int pageSize, String width, ProvidesKey<T> keyProvider) {
        super(pageSize, getStyle(), keyProvider);
        this.setWidth(width, true);
    }

    /** Create a DataTable. */
    public DataTable(int pageSize, String width, ProvidesKey<T> keyProvider, Widget loadingIndicator) {
        super(pageSize, getStyle(), keyProvider, loadingIndicator);
        this.setWidth(width, true);
    }

    /** Get a standard pager for use with this table. */
    public DataTablePager getPager() {
        return new DataTablePager(this);
    }

    /** Get the customized style for this widget. */
    protected static DataTableStyle getStyle() {
        return GWT.create(DataTableStyle.class);
    }

    /** Set a message to be displayed if there are no rows. */
    public void setNoRowsMessage(String noRowsMessage) {
        this.noRowsMessage = noRowsMessage;
        Label label = new Label(noRowsMessage);
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
        return this.selectionColumn != null;
    }

    /** Add a section column. */
    public void addSelectionColumn(double width, Unit unit, ProvidesKey<T> keyProvider) {
        SelectionModel<T> selectionModel = new MultiSelectionModel<T>(keyProvider);
        this.setSelectionModel(selectionModel, DefaultSelectionEventManager.<T>createCheckboxManager());
        this.selectionColumn = new SelectionColumn(selectionModel);
        this.selectionHeader = new SelectionHeader(this);
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

    /** Resources used for CellTable style. */
    public interface DataTableStyle extends CellTable.Resources {
        @Override
        @Source({ CellTable.Style.DEFAULT_CSS, "DataTable.css" })
        CellTable.Style cellTableStyle();
    }

    /** Selection column, which holds the selection checkbox. */
    public class SelectionColumn extends Column<T, Boolean> {
        private SelectionModel<T> selectionModel;

        public SelectionColumn(SelectionModel<T> selectionModel) {
            super(new CheckboxCell(true, false));
            this.setSortable(false);
            this.selectionModel = selectionModel;
        }

        @Override
        public Boolean getValue(T object) {
            return selectionModel.isSelected(object);
        }
    }

    /** Selection header, which holds a select all/select none checkbox. */
    public class SelectionHeader extends Header<Boolean> {
        private boolean selected = false;
        private DataTable<T> table;

        public SelectionHeader(DataTable<T> table) {
            super(new CheckboxCell(true, false));
            this.table = table;
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
    }

}
