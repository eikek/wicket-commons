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

package org.eknet.wicket.commons.components.tab;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 21.12.11 10:12
 */
public enum Style {
  
  WICKET("tabpanel"),
  SLIDING_DOORS1("tabpanel1"),
  SLIDING_DOORS2("tabpanel2"),
  SLIDING_DOORS3("tabpanel3"),
  PLAIN("tabpanel4");

  private final String cssClass;

  private Style(String cssClass) {
    this.cssClass = cssClass;
  }

  public String getCssClass() {
    return cssClass;
  }
}
