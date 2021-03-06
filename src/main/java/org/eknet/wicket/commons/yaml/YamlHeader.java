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
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;


/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 14.12.11 20:56
 */
public class YamlHeader extends Panel {

  public YamlHeader(String id) {
    super(id);
  }

  public <T extends WebMarkupContainer> T setTopNavigation(@NotNull ComponentSupplier<T> componentSupplier) {
    T c = componentSupplier.get("topNavigation");
    addOrReplace(c);
    return c;
  }

  public <T extends Component> T setPageHeadline(ComponentSupplier<T> h1) {
    T c = h1.get("pageHeading");
    addOrReplace(c);
    return c;
  }

  public <T extends Component> T setPageSubHeadline(ComponentSupplier<T> h1) {
    T c = h1.get("subHeading");
    addOrReplace(c);
    return c;
  }
}
