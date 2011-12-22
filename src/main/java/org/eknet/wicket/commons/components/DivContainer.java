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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a div containing multiple other divs. If layout is "Bottom-Up", then the
 * children are rendered in reverse order.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 05.12.11 19:05
 */
public class DivContainer extends AbstractContainer {

  private WebMarkupContainer container = new WebMarkupContainer("container");
  private final Layout layout;

  protected RepeatingView repeater;

  private String singleChildId;
  
  public enum Layout {
    TOP_DOWN, BOTTOM_UP
  }

  public DivContainer(String id) {
    this(id, Layout.TOP_DOWN);
  }

  public DivContainer(String id, Layout layout) {
    super(id);
    this.layout = layout;
    this.repeater = newRepeater();
    container.add(repeater);
    super.add(container);

    if (isApplyCss()) {
      container.add(new AttributeModifier("class", new AbstractReadOnlyModel<String>() {
        @Override
        public String getObject() {
          return getContainerClass();
        }
      }));
      repeater.add(new AttributeModifier("class", new AbstractReadOnlyModel<String>() {
        @Override
        public String getObject() {
          return getItemClass();
        }
      }));
    }
  }
  
  public boolean isApplyCss() {
    return false;
  }
  
  public String getContainerClass() {
    return "verticalContainer";
  }
  
  public String getItemClass() {
    return "verticalItem";
  }

  protected RepeatingView newRepeater() {
    return new RepeatingView("item") {
      @Override
      protected Iterator<? extends Component> renderIterator() {
        if (layout == Layout.BOTTOM_UP) {
          List<Component> list = new LinkedList<Component>();
          Iterator<? extends Component> iter = super.renderIterator();
          while (iter.hasNext()) list.add(iter.next());
          Collections.reverse(list);
          return list.iterator();
        }
        return super.renderIterator();
      }
    };
  }


  public WebMarkupContainer getContainer() {
    return container;
  }

  public Layout getLayout() {
    return layout;
  }


  @Override
  public <T extends Component> T add(@NotNull ComponentSupplier<T> supplier) {
    final T c = supplier.get(newChildId());
    repeater.add(c);
    return c;
  }

  public <T extends Component> T addOrReplace(@NotNull ComponentSupplier<T> supplier) {
    final T c = supplier.get(newChildId());
    repeater.addOrReplace(c);
    return c;
  }

  public <T extends Component> T setSingle(@NotNull ComponentSupplier<T> supplier) {
    if (singleChildId == null) {
      singleChildId = newChildId();
    }
    final T compo = supplier.get(singleChildId);
    repeater.addOrReplace(compo);
    return compo;
  }

  @Override
  public String newChildId() {
    if (singleChildId == null) {
      singleChildId = repeater.newChildId();
      return singleChildId;
    }
    return repeater.newChildId();
  }

  @Override
  public MarkupContainer add(Component... childs) {
    return repeater.add(childs);
  }

  @Override
  public MarkupContainer addOrReplace(Component... childs) {
    return repeater.addOrReplace(childs);
  }

  @Override
  public boolean contains(Component component, boolean recurse) {
    return repeater.contains(component, recurse);
  }

  @Override
  public Iterator<Component> iterator() {
    return repeater.iterator();
  }

  @Override
  public MarkupContainer remove(Component component) {
    return repeater.remove(component);
  }

  @Override
  public MarkupContainer remove(String id) {
    return repeater.remove(id);
  }

  @Override
  public MarkupContainer removeAll() {
    return repeater.removeAll();
  }

  @Override
  public MarkupContainer replace(Component child) {
    return repeater.replace(child);
  }
  
  public static Builder create() {
    return new Builder();
  }

  public static class Builder implements ComponentSupplier<DivContainer> {

    private final List<ComponentSupplier<?>> contents = new LinkedList<ComponentSupplier<?>>();
    
    @NotNull
    @Override
    public DivContainer get(@NotNull String id) {
      DivContainer c = new DivContainer(id);
      for (ComponentSupplier cs : contents) {
        c.add(cs);
      }
      return c;
    }

    public Builder add(ComponentSupplier<?> content) {
      if (content != null) {
        this.contents.add(content);
      }
      return this;
    }
  }

}
