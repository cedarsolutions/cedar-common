/*
 * Copyright 2010 Traction Software, Inc.
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
package com.tractionsoftware.gwt.user.client.ui;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;

/**
 * Input widget.
 *
 * <h3>Code Source</h3>
 *
 * <p>
 * This is external code that was copied into the CedarCommon codebase under
 * the terms of its license.
 * </p>
 *
 * <blockquote>
 *    <table border="1" cellpadding="5" cellspacing="0">
 *       <tbody>
 *          <tr>
 *             <td><i>Source:</i></td>
 *             <td><a href="http://tractionsoftware.github.io/gwt-traction/">Traction Software</a></td>
 *          </tr>
 *          <tr>
 *             <td><i>Date:</i></td>
 *             <td>July, 2014</td>
 *          </tr>
 *          <tr>
 *             <td><i>Reason:</i></td>
 *             <td>The official Traction-supplied jar in Maven only supports Java 1.7+, but CedarCommon supports Java 1.6+.</td>
 *          </tr>
 *       </tbody>
 *    </table>
 * </blockquote>
 *
 * @author Andy @ Traction Software
 */
public class InputWidget extends FocusWidget implements HasValue<String> {

    private String lastValue = null;

    public InputWidget(String type) {
        super(DOM.createElement("input"));
        getElement().setAttribute("type", type);

        // fire a change event on change or blur
        addDomHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                fireValueChangeHandler(getValue());
            }
        }, ChangeEvent.getType());

        addBlurHandler(new BlurHandler() {
            @Override
            public void onBlur(BlurEvent event) {
                fireValueChangeHandler(getValue());
            }
        });
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public String getValue() {
        return DOM.getElementProperty(getElement(), "value");
    }

    @Override
    public void setValue(String value) {
        setValue(value, false);
    }

    @Override
    public void setValue(String value, boolean fireEvents) {
        if (value == null) value = "";
        DOM.setElementProperty(getElement(), "value", value);
        if (fireEvents) {
            fireValueChangeHandler(value);
        }
        else {
            // we still want to keep track of changes to the value
            lastValue = value;
        }
    }

    private void fireValueChangeHandler(String value) {
        ValueChangeEvent.fireIfNotEqual(this, lastValue, value);
        lastValue = value;
    }

}
