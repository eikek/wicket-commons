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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 15.12.11 11:27
 */
public class SubcolumnDef extends Panel {

  private WebMarkupContainer columnDef;
  
  public SubcolumnDef(@NotNull String id, @NotNull ColumnDef def) {
    super(id);
    columnDef = new WebMarkupContainer("cdef");
    columnDef.add(new AttributeModifier("class", def.getCssClass()));
    add(columnDef);
  }
  
  public void setColumnContent(ComponentSupplier<?> supplier, ContentAlign align) {
    Component c = supplier.get("ccontent");
    c.add(new AttributeModifier("class", align.getCssClass()));
    columnDef.addOrReplace(c);
  }
  
  public enum ContentAlign {
    RIGHT("subcr"), CENTER("subc"), LEFT("subcl");

    private final String cssClass;

    private ContentAlign(String cssClass) {
      this.cssClass = cssClass;
    }

    public String getCssClass() {
      return cssClass;
    }
  }
  
  public static Builder create() {
    return new Builder();
  }

  public static class Builder implements ComponentSupplier<SubcolumnDef> {

    private ColumnDef columnDef;
    private ComponentSupplier<?> content;
    private ContentAlign align;
    
    @NotNull
    @Override
    public SubcolumnDef get(@NotNull String id) {
      SubcolumnDef scol = new SubcolumnDef(id, columnDef);
      scol.setColumnContent(content, align);
      return scol;
    }

    public Builder setColumnDef(ColumnDef def) {
      this.columnDef = def;
      return this;
    }

    public Builder setContent(ComponentSupplier<?> content, ContentAlign align) {
      this.content = content;
      this.align = align;
      return this;
    }

    public Builder setContentLeft(ComponentSupplier<?> content) {
      return setContent(content, ContentAlign.LEFT);
    }

    public Builder setContentCenter(ComponentSupplier<?> content) {
      return setContent(content, ContentAlign.CENTER);
    }

    public Builder setContentRight(ComponentSupplier<?> content) {
      return setContent(content, ContentAlign.RIGHT);
    }
    
  }
  public enum ColumnDef {
    
    L_20,
    L_25,
    L_33,
    L_40,
    L_38,
    L_50,
    L_60,
    L_62,
    L_66,
    L_75,
    L_80,
    R_20,
    R_25,
    R_33,
    R_40,
    R_38,
    R_50,
    R_60,
    R_66,
    R_62,
    R_75,
    R_80;
    
    String getCssClass() {
      String align = name().substring(0, 1);
      String size = name().substring(2, 4);
      return "c" + size + align.toLowerCase();
    } 
  }
}
