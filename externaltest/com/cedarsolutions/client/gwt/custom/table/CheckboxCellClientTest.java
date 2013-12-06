/*
 * Copyright 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.cedarsolutions.client.gwt.custom.table;

import com.cedarsolutions.client.gwt.custom.table.CheckboxCell;
import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

/**
 * Tests for {@link CheckboxCell}.
 */
// Modified slightly for CedarCommon (original was CheckboxCellTest).
// Added new tests to verify the new disabled functionality.
public class CheckboxCellClientTest extends EditableCellTestBase<Boolean, Boolean> {

  public void testConstructor() {
    {
      CheckboxCell cell = new CheckboxCell(true, false);
      assertTrue(cell.dependsOnSelection());
      assertFalse(cell.handlesSelection());
    }

    {
      CheckboxCell cell = new CheckboxCell(false, true);
      assertFalse(cell.dependsOnSelection());
      assertTrue(cell.handlesSelection());
    }
  }

  public void testOnBrowserEventChecked() {
    NativeEvent event = Document.get().createChangeEvent();
    testOnBrowserEvent("<input type=\"checkbox\" checked/>", event, false,
        null, Boolean.TRUE, true);
  }

  public void testOnBrowserEventUnchecked() {
    NativeEvent event = Document.get().createChangeEvent();
    testOnBrowserEvent("<input type=\"checkbox\"/>", event, true, null,
        Boolean.FALSE, false);
  }

  // Added for CedarCommon
  public void testRenderViewDataDisabledChecked() {
    CheckboxCell cell = createCell();
    cell.setEnabled(false);
    Boolean value = Boolean.TRUE;
    cell.setViewData(DEFAULT_KEY, createCellViewData());
    SafeHtmlBuilder sb = new SafeHtmlBuilder();
    Context context = new Context(0, 0, DEFAULT_KEY);
    cell.render(context, value, sb);
    String expectedInnerHtmlViewData = getExpectedInnerHtmlViewDisabled();
    String asString = sb.toSafeHtml().asString();
    assertEquals(expectedInnerHtmlViewData, asString);
  }

  // Added for CedarCommon
  public void testRenderViewDataDisabledUnchecked() {
    CheckboxCell cell = createCell();
    cell.setEnabled(false);
    Boolean value = Boolean.FALSE;
    cell.setViewData(DEFAULT_KEY, createCellViewData());
    SafeHtmlBuilder sb = new SafeHtmlBuilder();
    Context context = new Context(0, 0, DEFAULT_KEY);
    cell.render(context, value, sb);
    String expectedInnerHtmlViewData = getExpectedInnerHtmlViewDisabled();
    String asString = sb.toSafeHtml().asString();
    assertEquals(expectedInnerHtmlViewData, asString);
  }

  @Override
  protected CheckboxCell createCell() {
    return new CheckboxCell(false, false);
  }

  @Override
  protected Boolean createCellValue() {
    return true;
  }

  @Override
  protected Boolean createCellViewData() {
    return false;
  }

  @Override
  protected boolean dependsOnSelection() {
    return false;
  }

  @Override
  protected String[] getConsumedEvents() {
    return new String[]{"change", "keydown"};
  }

  @Override
  protected String getExpectedInnerHtml() {
    return "<input type=\"checkbox\" tabindex=\"-1\" checked/>";
  }

  @Override
  protected String getExpectedInnerHtmlNull() {
    return "<input type=\"checkbox\" tabindex=\"-1\"/>";
  }

  @Override
  protected String getExpectedInnerHtmlViewData() {
    return "<input type=\"checkbox\" tabindex=\"-1\"/>";
  }

  // Added for CedarCommon
  protected String getExpectedInnerHtmlViewDisabled() {
    // It always shows as unchecked when disabled, even if it's underlying state is checked
    return "<input type=\"checkbox\" tabindex=\"-1\" disabled=\"disabled\"/>";
  }
}
