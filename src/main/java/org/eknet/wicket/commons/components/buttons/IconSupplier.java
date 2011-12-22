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

package org.eknet.wicket.commons.components.buttons;

import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.resource.PackageResourceReference;

import org.jetbrains.annotations.NotNull;

import org.eknet.wicket.commons.ComponentSupplier;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 07.12.11 20:01
 */
public class IconSupplier implements ComponentSupplier<Image> {

  private final String image;

  public IconSupplier(String image) {
    this.image = image;
  }

  @NotNull
  @Override
  public Image get(@NotNull String id) {
    return new Image(id, new PackageResourceReference(IconSupplier.class, image));
  }
  
  public static IconSupplier bulletBlue() {
    return new IconSupplier("bullet_blue.png");
  }
  
  public static IconSupplier cross() {
    return new IconSupplier("cross.png");
  }
  
  public static IconSupplier tick() {
    return new IconSupplier("tick.png");
  }
}
