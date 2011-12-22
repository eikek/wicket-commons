/*
 * Copyright 2011 Eike Kettner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eknet.wicket.commons.components;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import org.jetbrains.annotations.NotNull;

import org.eknet.wicket.commons.ComponentSupplier;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 07.12.11 18:29
 */
public abstract class AbstractContainer extends Panel {

  protected AbstractContainer(String id) {
    super(id);
  }

  protected AbstractContainer(String id, IModel<?> model) {
    super(id, model);
  }
  
  public <T extends Component> T add(@NotNull ComponentSupplier<T> supplier) {
    T comp = supplier.get(newChildId());
    add(comp);
    return comp;
  }

  public abstract String newChildId();
}
