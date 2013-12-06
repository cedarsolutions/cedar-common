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

import com.cedarsolutions.client.gwt.custom.table.SwitchableSelectionModel.SelectionType;
import com.google.gwt.view.client.AbstractSelectionModelTest;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;

/**
 * Tests for {@link SwitchableSelectionModel}.
 */
//Modified for CedarCommon (original was SingleSelectionModelTest)
//These are the same as the original tests, but use SwitchableSelectionModel instead.
//That way, SwitchableSelectionModel in SINGLE mode is equivalent to the standard SingleSelectionModel.
public class SingleSelectionModelClientTest extends AbstractSelectionModelTest {

  /**
   * Test that deselecting a value other than the pending selection does not
   * cause the pending selection to be lost.
   */
  public void testDeselectWhileSelectionPending() {
    SwitchableSelectionModel<String> model = createSelectionModel(null);
    model.setSelected("test", true);
    model.setSelected("other", false);
    assertTrue(model.isSelected("test"));
    assertEquals("test", model.getSelectedObject());
  }

  public void testGetSelectedObject() {
    SwitchableSelectionModel<String> model = createSelectionModel(null);
    assertNull(model.getSelectedObject());

    model.setSelected("test", true);
    assertEquals("test", model.getSelectedObject());
    assertEquals("test", model.getSelectedSet().iterator().next());

    model.setSelected("test", false);
    assertNull(model.getSelectedObject());
    assertEquals(0, model.getSelectedSet().size());
  }

  public void testSelectedChangeEvent() {
    SwitchableSelectionModel<String> model = createSelectionModel(null);
    SelectionChangeEvent.Handler handler = new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        finishTest();
      }
    };
    model.addSelectionChangeHandler(handler);

    delayTestFinish(2000);
    model.setSelected("test", true);
  }

  public void testNoDuplicateChangeEvent() {
    SwitchableSelectionModel<String> model = createSelectionModel(null);
    SelectionChangeEvent.Handler handler = new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        fail();
      }
    };

    model.setSelected("test", true);
    model.addSelectionChangeHandler(handler);
    model.setSelected("test", true); // Should not fire change event
    model.setSelected("test", true); // Should not fire change event
  }

  public void testNoDuplicateChangeEvent2() {
    SwitchableSelectionModel<String> model = createSelectionModel(null);
    SelectionChangeEvent.Handler handler = new SelectionChangeEvent.Handler() {
      public void onSelectionChange(SelectionChangeEvent event) {
        fail();
      }
    };

    model.setSelected("test", true);
    model.setSelected("test", false);
    assertFalse(model.isSelected("test"));  // for CedarCommon: triggers cleanup of internal state so test below start at the right place
    model.addSelectionChangeHandler(handler);
    model.setSelected("test", false); // Should not fire change event
    model.setSelected("test", false); // Should not fire change event
  }

  public void testSetSelected() {
    SwitchableSelectionModel<String> model = createSelectionModel(null);
    assertFalse(model.isSelected("test0"));

    model.setSelected("test0", true);
    assertTrue(model.isSelected("test0"));
    assertEquals("test0", model.getSelectedSet().iterator().next());

    model.setSelected("test1", true);
    assertTrue(model.isSelected("test1"));
    assertFalse(model.isSelected("test0"));
    assertEquals("test1", model.getSelectedSet().iterator().next());
    assertEquals(1, model.getSelectedSet().size());

    model.setSelected("test1", false);
    assertFalse(model.isSelected("test1"));
    assertFalse(model.isSelected("test0"));
    assertEquals(0, model.getSelectedSet().size());
  }

  public void testSetSelectedNull() {
    SwitchableSelectionModel<String> model = createSelectionModel(null);

    model.setSelected("test", true);
    assertTrue(model.isSelected("test"));

    // Null cannot be selected, but it deselects the current item.
    model.setSelected(null, true);
    assertNull(model.getSelectedObject());
    assertFalse(model.isSelected("test"));
    assertFalse(model.isSelected(null));
    assertEquals(0, model.getSelectedSet().size());
  }

  public void testSetSelectedWithKeyProvider() {
    ProvidesKey<String> keyProvider = new ProvidesKey<String>() {
      public Object getKey(String item) {
        return item.toUpperCase();
      }
    };
    SwitchableSelectionModel<String> model = createSelectionModel(keyProvider);
    assertFalse(model.isSelected("test0"));

    model.setSelected("test0", true);
    assertTrue(model.isSelected("test0"));
    assertTrue(model.isSelected("TEST0"));

    model.setSelected("test1", true);
    assertTrue(model.isSelected("test1"));
    assertTrue(model.isSelected("TEST1"));
    assertFalse(model.isSelected("test0"));

    model.setSelected("test1", false);
    assertFalse(model.isSelected("test1"));
    assertFalse(model.isSelected("test0"));
  }

  @Override
  protected SwitchableSelectionModel<String> createSelectionModel(ProvidesKey<String> keyProvider) {
    return new SwitchableSelectionModel<String>(keyProvider, SelectionType.SINGLE);
  }
}
