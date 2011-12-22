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
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eknet.wicket.commons.ComponentSupplier;
import org.eknet.wicket.commons.ComponentSuppliers;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static org.eknet.wicket.commons.ComponentSuppliers.provide;

/**
 * A link containing a span and img element. The markup is this:
 * <pre>
 *   &lt;a href="#" wicket:id="getLinkId()"&gt;
 *     &lt;img src="#" alt="" wicket:id="getIconId()"&gt;&lt;span wicket:id="getNameId()"&gt;&lt;/span&gt;
 *   &lt;/a&gt;
 * </pre>
 * Use the nested {@link Builder} class.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 06.12.11 11:58
 */
public class SimpleLink extends Panel {

  public SimpleLink(String id) {
    super(id);
  }

  public String getLinkId() {
    return "link";
  }
  
  public String getNameId() {
    return "name";
  }
  
  public String getIconId() {
    return "icon";
  }
  
  public static Builder create() {
    return new Builder();
  }

  public static class Builder extends ComponentSuppliers.Builder<SimpleLink> implements Serializable {

    private ComponentSupplier<? extends Link> linkComponent;
    private ComponentSupplier<?> textComponent;
    private ComponentSupplier<?> iconComponent;
    
    private SimpleLink link;
    
    public Builder(SimpleLink link) {
      super(SimpleLink.class);
      this.link = link;
    }

    public Builder() {
      super(SimpleLink.class);
    }

    @NotNull
    @Override
    public SimpleLink get(@NotNull String id) {
      if (linkComponent == null) {
        throw new IllegalArgumentException("Link is required");
      }
      SimpleLink link = this.link != null? this.link : super.get(id);
      Link lc = linkComponent.get(link.getLinkId());
      if (iconComponent != null) {
        lc.addOrReplace(iconComponent.get(link.getIconId()));
      } else {
        WebMarkupContainer repl = new WebMarkupContainer(link.getIconId());
        repl.setVisible(false);
        lc.addOrReplace(repl);
      }
      if (textComponent != null) {
        Component c = textComponent.get(link.getNameId());
        c.setRenderBodyOnly(true);
        lc.addOrReplace(c);
      } else {
        WebMarkupContainer repl = new WebMarkupContainer(link.getNameId());
        repl.setVisible(false);
        lc.addOrReplace(repl);
      }
      link.addOrReplace(lc);
      return link;
    }

    public Builder withLink(ComponentSupplier<? extends Link> linkComponent) {
      this.linkComponent = linkComponent;
      return this;
    }

    public Builder withText(ComponentSupplier<?> textComponent) {
      this.textComponent = textComponent;
      return this;
    }

    public Builder withText(IModel<String> text) {
      this.textComponent = provide(Label.class, text);
      return this;
    }

    public Builder withText(String text) {
      this.textComponent = provide(Label.class, new Model<String>(text));
      return this;
    }
    
    public Builder withIcon(ComponentSupplier<?> iconComponent) {
      this.iconComponent = iconComponent;
      return this;
    }
  }
  
}
