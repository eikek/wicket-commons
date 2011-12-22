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

package org.eknet.wicket.commons.components.bar;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A simple container that contains 3 div elements that are floated
 * dependend on which method is used to add the contents:
 * <ol>
 *   <li>left</li>
 *   <li>not aligned</li>
 *   <li>right</li>
 * </ol>
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 05.12.11 23:00
 */
public class Bar extends Panel {

  private final RepeatingView left = new RepeatingView("left");
  private final RepeatingView middle = new RepeatingView("middle");
  private final RepeatingView right = new RepeatingView("right");

  public Bar(@NotNull String id) {
    this(id, null);
  }
  
  public Bar(@NotNull String id, @Nullable String cssClass) {
    super(id);
    
    WebMarkupContainer container = new WebMarkupContainer("container");
    if (cssClass != null) {
      container.add(new AttributeModifier("class", cssClass));
    }
    add(container);
    container.add(left);
    container.add(middle);
    container.add(right);
  }

  public static ResourceReference getDefaultCssResourceReference() {
    return new PackageResourceReference(Bar.class, "bar.css");
  }

  protected ResourceReference getCss() {
    return getDefaultCssResourceReference();
  }

  protected boolean isAutoAddCss() {
    return true;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    if (isAutoAddCss()) {
      response.renderCSSReference(getCss());
    }
  }

  /**
   * Adds the new content to this container, floating to the left.
   *
   * @param component
   * @return this
   */
  @NotNull
  public Bar addToLeft(@NotNull ComponentSupplier component) {
    left.add(component.get(newLeftChildId()));
    return this;
  }

  /**
   * Adds the new content to this container floating to the right.
   *
   * @param component
   * @return this
   */
  @NotNull
  public Bar addToRight(@NotNull ComponentSupplier component) {
    right.add(component.get(newRightChildId()));
    return this;
  }

  /**
   * Adds new content to the container in between the two floating
   * containers.
   *
   * @param component
   * @return this
   */
  @NotNull
  public Bar addToMiddle(@NotNull ComponentSupplier component) {
    middle.add(component.get(newMiddleChildId()));
    return this;
  }

  @NotNull
  public String newRightChildId() {
    return right.newChildId();
  }

  @NotNull
  public String newLeftChildId() {
    return left.newChildId();
  }

  @NotNull
  public String newMiddleChildId() {
    return middle.newChildId();
  }

  @NotNull
  public RepeatingView getLeft() {
    return left;
  }

  @NotNull
  public RepeatingView getMiddle() {
    return middle;
  }

  @NotNull
  public RepeatingView getRight() {
    return right;
  }
}
