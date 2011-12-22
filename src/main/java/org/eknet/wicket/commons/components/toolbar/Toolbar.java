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

package org.eknet.wicket.commons.components.toolbar;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.eknet.wicket.commons.ComponentSupplier;
import org.eknet.wicket.commons.ComponentSuppliers;
import org.eknet.wicket.commons.components.SimpleLink;
import org.eknet.wicket.commons.components.bar.Bar;

/**
 * A very simple bar for image-links.
 * 
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 12.12.11 22:37
 */
public class Toolbar extends Bar {

  public Toolbar(String id) {
    super(id, "toolbar");
    getLeft().add(new AttributeAppender("class", " toolbarItem"));
    getMiddle().add(new AttributeAppender("class", " toolbarItem"));
    getRight().add(new AttributeAppender("class", " toolbarItem"));
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    if (isAutoAddCss()) {
      response.renderCSSReference(new ToolbarCss());
    }
  }

  public <T extends Component> T addLink(ComponentSupplier<T> linkComponent) {
    T c = linkComponent.get(newLeftChildId());
    getLeft().add(c);
    return c;
  }

  public SimpleLink addLink(ComponentSupplier<?> imageComponent, Class<? extends Page> pageClass) {
    return addLink(SimpleLink.create()
        .withIcon(imageComponent)
        .withLink(ComponentSuppliers.link(pageClass)));
  }

  @Override
  public boolean isVisible() {
    return getLeft().iterator().hasNext()
        || getRight().iterator().hasNext()
        || getMiddle().iterator().hasNext();
  }

}
