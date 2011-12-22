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

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 17.12.11 07:34
 */
public final class YamlStyles {
  
  public static ResourceReference exampleDynamic3ColumnLayout() {
    return new PackageResourceReference(YamlStyles.class, "css/examples/dyn3col/layout.css");
  }

  public static ResourceReference exampleDynamic3ColumnFullheightLayout() {
    return new PackageResourceReference(YamlStyles.class, "css/examples/dyn3col/layoutfh.css");
  }

  public static void applyExampleDynamic3ColumnLayout(IHeaderResponse response) {
    response.renderCSSReference(exampleDynamic3ColumnLayout());
    response.renderCSSReference(new PackageResourceReference(YamlStyles.class,
        "css/examples/dyn3col/patches/patch_dynamic_layout_switching.css"), null, null, "lte IE 7");
  }

  public static void applyExampleDynamic3ColumnFullheightLayout(IHeaderResponse response) {
    response.renderCSSReference(exampleDynamic3ColumnFullheightLayout());
    response.renderCSSReference(new PackageResourceReference(YamlStyles.class,
        "css/examples/dyn3col/patches/patch_dynamic_layout_switching.css"), null, null, "lte IE 7");
  }
  
  public static ResourceReference getYamlCoreCss() {
    return new PackageResourceReference(YamlStyles.class, "css/yaml/core/base.css");
  }
  
  public static ResourceReference getNavigationShinyButtons() {
    return new PackageResourceReference(YamlStyles.class, "css/yaml/navigation/nav_shinybuttons.css");
  }

  public static ResourceReference getNavigationSlidingDoor() {
    return new PackageResourceReference(YamlStyles.class, "css/yaml/navigation/nav_slidingdoor.css");
  }

  public static ResourceReference getNavigationVerticalList() {
    return new PackageResourceReference(YamlStyles.class, "css/yaml/navigation/nav_vlist.css");
  }
}
