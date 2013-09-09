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

package com.gwtplatform.idhandler.client;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test for {@link BaseElementIdHandler}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BaseElementIdHandlerTest {

    @Mock
    private HasElementId object;

    private BaseElementIdHandler<HasElementId> tested;

    @Before
    public void setUp() {
        tested = new BaseElementIdHandler<HasElementId>() {
            @Override
            public void generateAndSetIds(HasElementId owner) {
            }
        };
    }

    @Test
    public void setElementIdWithoutIdExtension() {
        tested.setElementId(object, "ElementId");
        verify(object).setElementId("ElementId");
    }

    @Test
    public void setElementIdWithIdExtensionDefaultBehavior() {
        tested.setIdExtension("IdExtension");
        tested.setElementId(object, "ElementId");
        verify(object).setElementId("ElementId_IdExtension");
    }

    @Test
    public void setElementIdWithIdExtensionNullValue() {
        tested.setIdExtension(null);
        tested.setElementId(object, "ElementId");
        verify(object).setElementId("ElementId");
    }

    @Test
    public void setElementIdWithIdExtensionEmptyString() {
        tested.setIdExtension("");
        tested.setElementId(object, "ElementId");
        verify(object).setElementId("ElementId");
    }

}
