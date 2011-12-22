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
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.request.Response;

import java.util.Iterator;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 17.12.11 08:44
 */
class ListItemWrapper extends Behavior {

  private boolean isChildVisible(Component component) {
    if (component instanceof MarkupContainer) {
      MarkupContainer container = (MarkupContainer) component;
      Iterator<Component> iter = container.iterator();
      if (iter.hasNext()) {
        Component child = iter.next();
        return child.isVisible() && child.isActionAuthorized(Component.RENDER);
      }
    }
    return true;
  }

  @Override
  public void beforeRender(Component component) {
    super.beforeRender(component);
    if (isChildVisible(component)) {
      Response response = component.getResponse();
      response.write("<li>");
    }
  }

  @Override
  public void afterRender(Component component) {
    super.afterRender(component);
    if (isChildVisible(component)) {
      Response response = component.getResponse();
      response.write("</li>");
    }
  }
}
