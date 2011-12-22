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
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.eknet.wicket.commons.ComponentSupplier;
import org.eknet.wicket.commons.ComponentSuppliers;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A simple panel that adds a button with a image and label. The markup is like
 * this:
 * <pre>
 *   &lt;button wicket:id="button"&gt;
 *     &lt;img src="#" alt="" wicket:id="icon"/&gt;
 *     &lt;span wicket:id="text"&gt;Save&lt;/span&gt;
 *   &lt;/button&gt;
 * </pre>
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 07.12.11 19:42
 */
public class ExtendedButton extends Panel {

  private final Button innerButton;
  
  public ExtendedButton(@NotNull String id) {
    super(id);
    this.innerButton = newButton("button");
    add(innerButton);
  }

  public ExtendedButton(@NotNull String id, @NotNull ComponentSupplier<? extends Button> innerButton) {
    super(id);
    this.innerButton = innerButton.get("button");
    add(this.innerButton);
  }

  protected Button newButton(String id) {
    return new Button(id);
  }

  public String getIconId() {
    return "icon";
  }
  
  public String getTextId() {
    return "text";
  }
  
  public ExtendedButton setType(String type) {
    innerButton.add(new AttributeModifier("type", type));
    return this;
  }

  public ExtendedButton setCssClass(@Nullable String cssClass) {
    if (cssClass != null) {
      innerButton.add(new AttributeModifier("class", cssClass));
    } else {
      innerButton.add(new AttributeModifier("class", AttributeModifier.VALUELESS_ATTRIBUTE_REMOVE));
    }
    return this;
  }

  public Button getInnerButton() {
    return innerButton;
  }

  public ExtendedButton setPositive(String text) {
    setPositive();
    setImage(IconSupplier.tick());
    setText(ComponentSuppliers.provide(Label.class).withArgument(text));
    return this;
  }
  public ExtendedButton setPositive() {
    setCssClass("positive");
    return this;
  }
  
  public ExtendedButton setNegative() {
    setCssClass("negative");
    return this;
  }
  
  public ExtendedButton setNeutral(@NotNull IModel<String> text) {
    setCssClass(null);
    setImage(IconSupplier.bulletBlue());
    setText(ComponentSuppliers.provide(Label.class, text));
    return this;
  }
  
  public <T extends Component> T setImage(@NotNull ComponentSupplier<T> imageComponent) {
    T image = imageComponent.get("icon");
    innerButton.add(image);
    return image;
  }

  public <T extends Component> T setText(@NotNull ComponentSupplier<T> textComponent) {
    T text = textComponent.get(getTextId());
    text.setRenderBodyOnly(true);
    innerButton.add(text);
    return text;
  }
  
  public static Builder create() {
    return new Builder();
  }

  public static class Builder extends ComponentSuppliers.Builder<ExtendedButton> {

    private ExtendedButton button;

    private ComponentSupplier<?> textComponent;
    private ComponentSupplier<?> iconComponent;
    private String type;
    private String cssClass;
    
    public Builder(ExtendedButton button) {
      super(ExtendedButton.class);
      this.button = button;
    }

    public Builder() {
      super(ExtendedButton.class);
    }

    @NotNull
    @Override
    public ExtendedButton get(@NotNull String id) {
      ExtendedButton button = this.button != null ? this.button : super.get(id);
      if (textComponent != null) {
        button.setText(textComponent);
      } else {
        button.setText(ComponentSuppliers.emptyInvisiblePanel());
      }
      if (iconComponent != null) {
        button.setImage(iconComponent);
      } else {
        button.setImage(ComponentSuppliers.emptyInvisiblePanel());
      }
      button.setCssClass(cssClass);
      if (type != null) {
        button.setType(type);
      }
      return button;
    }

    public Builder withText(@NotNull ComponentSupplier textComponent) {
      this.textComponent = textComponent;
      return this;
    }

    public Builder withText(@NotNull IModel<String> text) {
      this.textComponent = ComponentSuppliers.provide(Label.class, text);
      return this;
    }

    public Builder withText(@NotNull String text) {
      this.textComponent = ComponentSuppliers.provide(Label.class).withArgument(String.class, text);
      return this;
    }
    
    public Builder withIcon(@NotNull ComponentSupplier iconComponent) {
      this.iconComponent = iconComponent;
      return this;
    }

    public Builder withType(@NotNull String type) {
      this.type = type;
      return this;
    }
    
    public Builder asSubmit() {
      return withType("submit");
    }

    public Builder withCss(@NotNull String cssClass) {
      this.cssClass = cssClass;
      return this;
    }
    
    public Builder positive() {
      withIcon(IconSupplier.tick());
      return withCss("positive");
    }
    
    public Builder negative() {
      withIcon(IconSupplier.cross());
      return withCss("negative");
    }
    
    public Builder neutral() {
      this.cssClass = null;
      return withIcon(IconSupplier.bulletBlue());
    }
  }
}
