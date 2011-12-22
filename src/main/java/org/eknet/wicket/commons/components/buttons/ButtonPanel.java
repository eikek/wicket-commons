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

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.eknet.wicket.commons.ComponentSupplier;
import org.eknet.wicket.commons.components.DivContainer;
import org.jetbrains.annotations.NotNull;

/**
 * A simple button panel using the css stylesheets from <a href="http://particletree.com/features/rediscovering-the-button-element/">here</a>.
 * <p/>
 * The various {@code #addButton()} methods are for conveniently adding {@link ExtendedButton}s. Besides buttons, you
 * can add normal links (&lt;a&gt;) to it using the {@link #add(ComponentSupplier)} with the
 * {@link org.eknet.wicket.commons.components.SimpleLink} component.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 04.12.11 11:23
 */
public class ButtonPanel extends DivContainer {

  public ButtonPanel(String id) {
    super(id);
    getContainer().add(new AttributeModifier("class", "buttons"));
  }

  public static ResourceReference getDefaultCssResourceReference() {
    return new PackageResourceReference(ButtonPanel.class, "buttons.css");
  }
  
  protected ResourceReference getCss() {
    return getDefaultCssResourceReference();
  }
  
  protected boolean isAutoaddCss() {
    return true;
  }

  @Override
  public void renderHead(IHeaderResponse response) {
    super.renderHead(response);
    if (isAutoaddCss()) {
      response.renderCSSReference(getCss());
    }
  }

  public ButtonPanel addButton(@NotNull ComponentSupplier<ExtendedButton> button) {
    add(button);
    return this;
  }

  public ButtonPanel addPositiveButton(String type, ComponentSupplier image, IModel<String> text) {
    add(new ExtendedButton.Builder()
            .withCss("positive")
            .withType(type)
            .withIcon(image)
            .withText(text)).setRenderBodyOnly(true);

    return this;
  }

  public ButtonPanel addNegativeButton(String type, ComponentSupplier image, IModel<String> text) {
    add(new ExtendedButton.Builder()
            .withCss("negative")
            .withType(type)
            .withIcon(image)
            .withText(text)).setRenderBodyOnly(true);
    return this;
  }

  public ButtonPanel addPositiveSubmitButton(IModel<String> text) {
    add(new ExtendedButton.Builder()
            .positive()
            .withType("submit")
            .withText(text)).setRenderBodyOnly(true);
    return this;
  }

  public ButtonPanel addPositiveSubmitButton(String text) {
    add(new ExtendedButton.Builder()
            .positive()
            .withType("submit")
            .withText(text)).setRenderBodyOnly(true);
    return this;
  }

  public ButtonPanel addPositiveButton(String type, IModel<String> text) {
    add(new ExtendedButton.Builder()
            .positive()
            .withType(type)
            .withText(text));
    return this;
  }

  public ButtonPanel addNegativeButton(String type, IModel<String> text) {
    add(new ExtendedButton.Builder()
            .negative()
            .withType(type)
            .withText(text));
    return this;
  }

  public ButtonPanel addStandardButton(String type, IModel<String> text) {
    add(new ExtendedButton.Builder()
            .withType(type)
            .neutral()
            .withText(text));
    return this;
  }

}
