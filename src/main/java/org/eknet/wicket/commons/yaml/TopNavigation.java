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

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eknet.wicket.commons.ComponentSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 15.12.11 00:15
 */
public class TopNavigation extends Panel {

  private RepeatingView repeater = new RepeatingView("link") {
    @Override
    protected Iterator<? extends Component> renderIterator() {
      Iterator<? extends Component> iter = super.renderIterator();
      List<Component> list = new ArrayList<Component>();
      while (iter.hasNext()) list.add(iter.next());
      Collections.reverse(list);
      return list.iterator();
    }
  };
  
  public TopNavigation(String id) {
    super(id);
    add(repeater);
  }
  
  public String newChildId() {
    return repeater.newChildId();
  }

  public void addLink(Component link) {
    link.setRenderBodyOnly(true);
    repeater.add(link);
  }

  public <T extends Component> T addLink(ComponentSupplier<T> linkSupplier) {
    T link = linkSupplier.get(newChildId());
    link.setRenderBodyOnly(true);
    repeater.add(link);
    return link;
  }

  public void addLink(Class<? extends Page> pageClass, IModel<?> bodyModel) {
    BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(newChildId(), pageClass);
    link.setBody(bodyModel);
    addLink(link);
  }

  public void addLink(Class<? extends Page> pageClass, IModel<?> bodyModel, PageParameters pp) {
    BookmarkablePageLink<Void> link = new BookmarkablePageLink<Void>(newChildId(), pageClass, pp);
    link.setBody(bodyModel);
    addLink(link);
  }
  
}
