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

package org.eknet.wicket.commons.yaml;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 15.12.11 11:26
 */
public class Subcolumns extends Panel {

  private RepeatingView repeater;

  public Subcolumns(String id) {
    super(id);

    repeater = new RepeatingView("columnDefs");
    add(repeater);
  }

  public String newChildId() {
    return repeater.newChildId();
  }
  
  public void addColumn(ComponentSupplier<? extends SubcolumnDef> column) {
    repeater.add(column.get(newChildId()));
  }

  public void addColumn(SubcolumnDef column) {
    repeater.add(column);
  }
  
  public static Builder create() {
    return new Builder();
  }
  
  public static class Builder implements ComponentSupplier<Subcolumns> {

    private List<ComponentSupplier<? extends SubcolumnDef>> columns =
        new LinkedList<ComponentSupplier<? extends SubcolumnDef>>();
    
    @NotNull
    @Override
    public Subcolumns get(@NotNull String id) {
      Subcolumns subcolumns = new Subcolumns(id);
      for (ComponentSupplier<? extends SubcolumnDef> coldef : columns) {
        subcolumns.addColumn(coldef);
      }
      return subcolumns;
    }

    public Builder addColumn(ComponentSupplier<? extends SubcolumnDef> columnDef) {
      this.columns.add(columnDef);
      return this;
    }
  }
}
