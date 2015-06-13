/**
 * Copyright 2011 ArcBees Inc.
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

package com.gwtplatform.idhandler.rebind;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for {@link ElementIdStatement}.
 */
public class ElementIdStatementTest {

    private ElementIdStatement tested;

    @Before
    public void setUp() {
        tested = new ElementIdStatement("owner.a.b.c", "abcId");
    }

    @Test
    public void buildIdSetterStatement() {
        assertThat(tested.buildIdSetterStatement(),
                equalTo("setElementId(owner.a.b.c, \"abcId\")"));
    }

    @Test
    public void buildGuardCondition() {
        assertThat(tested.buildGuardCondition(), equalTo(
                "owner != null && owner.a != null && owner.a.b != null && owner.a.b.c != null"));
    }

    @Test
    public void getSubPathsSinglePathElement() {
        String[] subPaths = tested.getSubPaths("a");
        assertThat(subPaths.length, equalTo(1));
        assertThat(subPaths[0], equalTo("a"));
    }

    @Test
    public void getSubPathsMultiplePathElements() {
        String[] subPaths = tested.getSubPaths("a.b.c");
        assertThat(subPaths.length, equalTo(3));
        assertThat(subPaths[0], equalTo("a"));
        assertThat(subPaths[1], equalTo("a.b"));
        assertThat(subPaths[2], equalTo("a.b.c"));
    }

    @Test(expected = IllegalStateException.class)
    public void getSubPathsMalformedPath() {
        tested.getSubPaths(".a.");
    }

}
