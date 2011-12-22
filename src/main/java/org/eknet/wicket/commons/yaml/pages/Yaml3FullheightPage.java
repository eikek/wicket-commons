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

package org.eknet.wicket.commons.yaml.pages;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Page overrides markup to create the "full height" layout.
 * 
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 18.12.11 17:44
 */
public class Yaml3FullheightPage extends AbstractYaml3Page {

  public Yaml3FullheightPage() {
  }

  public Yaml3FullheightPage(IModel<?> model) {
    super(model);
  }

  public Yaml3FullheightPage(PageParameters parameters) {
    super(parameters);
  }
}
