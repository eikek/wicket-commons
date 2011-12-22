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

package org.eknet.wicket.commons.components.navlist;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;

import static org.eknet.wicket.commons.ComponentSuppliers.link;

/**
 * Renders an unordered list with the elements added with {@link #addItem(ComponentSupplier)}. Each
 * item added is wrapped with {@code &lt;li&gt;} automatically. If a item is not visible or
 * not authorized to render, the {@code &lt;li&gt;} tags are not rendered, too.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 15.12.11 00:46
 */
public class Navigation extends Panel {

  private RepeatingView repeater = new RepeatingView("itemContent");
  private WebMarkupContainer ulTag = new WebMarkupContainer("ulTag");

  public Navigation(String id) {
    super(id);
    ulTag.add(repeater);
    super.add(ulTag);
  }

  @NotNull
  public WebMarkupContainer getUlTag() {
    return ulTag;
  }

  private <T extends Component> T addListItemWrapper(T component) {
    component.add(new ListItemWrapper());
    return component;
  }

  public String newChildId() {
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

  public <T extends Component> T addItem(ComponentSupplier<T> link) {
    T c = addListItemWrapper(link.get(newChildId()));
    repeater.add(c);
    return c;
  }

  @NotNull
  public Navigation addMenuItem(ComponentSupplier<? extends Navigation> item) {
    return addItem(item);
  }

  public void addLinkItem(Class<? extends Page> pageClass, PageParameters parameters, String text) {
    addItem(fragmentSupplier(link(pageClass, parameters, text)));
  }

  public void addLinkItem(Class<? extends Page> pageClass, String text) {
    addItem(fragmentSupplier(link(pageClass, text)));
  }

  public void addLinkItem(Class<? extends Page> pageClass, PageParameters parameters, IModel<?> text) {
    addItem(fragmentSupplier(link(pageClass, parameters, text)));
  }

  public void addLinkItem(Class<? extends Page> pageClass, IModel<?> text) {
    addItem(fragmentSupplier(link(pageClass, text)));
  }

  private <T> ComponentSupplier<LinkFragment> fragmentSupplier(final ComponentSupplier<?> linkComponentSupplier) {
    return new ComponentSupplier<LinkFragment>() {
      @NotNull
      @Override
      public LinkFragment get(@NotNull String id) {
        return new LinkFragment(id, linkComponentSupplier);
      }
    };
  }
  private class LinkFragment extends Fragment {
    private LinkFragment(String id, ComponentSupplier<?> linkSupplier) {
      super(id, "linkFragment", Navigation.this);
      add(linkSupplier.get("link"));
    }
  }

}
