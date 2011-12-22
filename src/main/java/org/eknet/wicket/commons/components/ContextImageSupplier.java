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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;

/**
 * Factory for {@link ContextImage}s.
 * 
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 10.12.11 13:42
 */
public class ContextImageSupplier implements ComponentSupplier<ContextImage> {

  private IModel<String> resoure;
  private IModel<Integer> width;
  private IModel<Integer> height;
  
  public ContextImageSupplier(IModel<String> resoure) {
    this.resoure = resoure;
  }

  public static ContextImageSupplier create(@NotNull IModel<String> resourceModel) {
    return new ContextImageSupplier(resourceModel);
  }

  public static ContextImageSupplier create(@NotNull String resource) {
    return new ContextImageSupplier(new Model<String>(resource));
  }

  public ContextImageSupplier width(int width) {
    this.width = new Model<Integer>(width);
    return this;
  }

  public ContextImageSupplier height(int height) {
    this.height = new Model<Integer>(height);
    return this;
  }
  public ContextImageSupplier width(IModel<Integer> width) {
    this.width = width;
    return this;
  }

  public ContextImageSupplier height(IModel<Integer> height) {
    this.height = height;
    return this;
  }

  @NotNull
  @Override
  public ContextImage get(@NotNull String id) {
    return new Image(id, resoure, width, height);
  }
  
  static class Image extends ContextImage {

    Image(String id, IModel<String> contextRelativePath, final IModel<Integer> width, final IModel<Integer> height) {
      super(id, contextRelativePath);
      if (width != null) {
        add(new AttributeModifier("width", new AbstractReadOnlyModel<String>() {
          @Override
          public String getObject() {
            return width.getObject() + "px";
          }
        }));
      }
      if (height != null) {
        add(new AttributeModifier("height", new AbstractReadOnlyModel<String>() {
          @Override
          public String getObject() {
            return height + "px";
          }
        }));
      }
    }

  }
}
