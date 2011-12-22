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

package org.eknet.wicket.commons.yaml.pages.examples;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eknet.wicket.commons.yaml.YamlStyles;
import org.eknet.wicket.commons.yaml.pages.Yaml3Page;

/**
 * This page provides the markup for the 3col YAML page and adds the css stylesheets from the
 * <a href="http://www.yaml.de/fileadmin/examples/04_layouts_styling/dynamic_layout_switching.html">dynamic layout switching</a>
 * example.
 * <p/>
 * Just add your own css stylesheets after the ones of this page to override things.
 * <p/>
 * This page adds stylesheets from <a href="http://www.yaml.de">YAML</a> to the page, please
 * consult their licensing and documentation.
 *
 * @see org.eknet.wicket.commons.yaml.pages.AbstractYaml3Page
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 17.12.11 07:07
 */
public abstract class DynamicYaml3Page extends Yaml3Page {

  public DynamicYaml3Page() {
  }

  public DynamicYaml3Page(IModel<?> model) {
    super(model);
  }

  public DynamicYaml3Page(PageParameters parameters) {
    super(parameters);
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    YamlStyles.applyExampleDynamic3ColumnLayout(response);
  }
}
