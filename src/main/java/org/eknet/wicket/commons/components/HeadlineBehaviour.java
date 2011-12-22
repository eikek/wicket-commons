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
import org.apache.wicket.behavior.Behavior;

/**
 * Wraps the attached component in &lt;hX&gt; tags.
 * 
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 17.12.11 15:52
 */
public class HeadlineBehaviour extends Behavior {

  private int level = 2;

  public HeadlineBehaviour(int level) {
    this.level = level;
  }

  public HeadlineBehaviour() {
  }

  @Override
  public void beforeRender(Component component) {
    super.beforeRender(component);
    component.getResponse().write("<h" + level + ">");
  }

  @Override
  public void afterRender(Component component) {
    super.afterRender(component);
    component.getResponse().write("</h" + level + ">");
  }
}
