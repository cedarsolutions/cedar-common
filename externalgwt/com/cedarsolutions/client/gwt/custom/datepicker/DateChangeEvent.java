/*
 * Copyright 2008 Google Inc.
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

package com.cedarsolutions.client.gwt.custom.datepicker;

import java.util.Date;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * Creates a new value every time a date is accessed (CUSTOMIZED).
 *
 * <h3>
 * Code Source
 * </h3>
 *
 * <p>
 * This is customized code that that was directly copied from GWT under the terms of its license.
 * </p>
 *
 * <blockquote>
 *    <table border="1" cellpadding="5" cellspacing="0">
 *       <tbody>
 *          <tr>
 *             <td><i>Source:</i></td>
 *             <td>{@link com.google.gwt.user.datepicker.client.DateChangeEvent}</td>
 *          </tr>
 *          <tr>
 *             <td><i>Version:</i></td>
 *             <td>GWT 2.4.0</td>
 *          </tr>
 *          <tr>
 *             <td><i>Date:</i></td>
 *             <td>October, 2011</td>
 *          </tr>
 *          <tr>
 *             <td><i>Purpose:</i></td>
 *             <td>DateBox doesn't notice when a user clears the field.</td>
 *          </tr>
 *          <tr>
 *             <td><i>See also:</i></td>
 *             <td><a href="http://code.google.com/p/google-web-toolkit/issues/detail?id=4084">GWT Issue #4084</a></td>
 *          </tr>
 *       </tbody>
 *    </table>
 * </blockquote>
 *
 * <p>
 * The code was copied rather than extended because the original GWT code
 * does not faciliate extension.  GWT code is often difficult to extend due
 * to design choices like the use of private or final variables, methods,
 * or dependent classes.
 * </p>
 *
 * @author Google
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
class DateChangeEvent extends ValueChangeEvent<Date> {

  /**
   * Fires value change event if the old value is not equal to the new value.
   * Use this call rather than making the decision to short circuit yourself for
   * safe handling of null.
   *
   * @param <S> The event source
   * @param source the source of the handlers
   * @param oldValue the oldValue, may be null
   * @param newValue the newValue, may be null
   */
  public static <S extends HasValueChangeHandlers<Date> & HasHandlers> void fireIfNotEqualDates(
      S source, Date oldValue, Date newValue) {
    if (ValueChangeEvent.shouldFire(source, oldValue, newValue)) {
      source.fireEvent(new DateChangeEvent(newValue));
    }
  }

  /**
   * Creates a new date value change event.
   *
   * @param value the value
   */
  protected DateChangeEvent(Date value) {
    // The date must be copied in case one handler causes it to change.
    super(CalendarUtil.copyDate(value));
  }

  @Override
  public Date getValue() {
    return CalendarUtil.copyDate(super.getValue());
  }
}
