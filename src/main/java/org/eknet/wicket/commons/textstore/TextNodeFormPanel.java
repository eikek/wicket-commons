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

package org.eknet.wicket.commons.textstore;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.eknet.wicket.commons.components.buttons.ButtonPanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a form panel that can be used with {@link TextNode} models.
 * <p/>
 * By default, it creates a new text node with the class name of the page this
 * component is attached to. You can override {@link #newFormModel()} to change
 * this, which is called in {@link #onInitialize()} to set the form model.
 * <p/>
 * So after the constructor finishs, you need to add components to the form
 * using {@link #addTextArea(String, IModel, IModel)} and
 * {@link #addTextField(String, IModel, IModel)}, respectively.
 * The form is then constructed at {@link #onInitialize()}.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 18.12.11 13:08
 */
public class TextNodeFormPanel extends GenericPanel<TextNode> {

  private Form<TextNode> form;
  private ButtonPanel buttons;

  private List<Descriptor> components = new LinkedList<Descriptor>();

  private final IModel<TextNodeStore> storeModel;
  
  private static enum Type {
    TEXT, TEXT_AREA
  }

  protected TextNodeFormPanel(@NotNull IModel<TextNodeStore> storeModel, String id) {
    this(storeModel, id, false);
  }

  public TextNodeFormPanel(@NotNull IModel<TextNodeStore> storeModel, String id, boolean addDefaultButton) {
    super(id);
    this.storeModel = storeModel;
    buttons = new ButtonPanel("buttons");
    if (addDefaultButton) {
      this.addDefaultButton();
    }
  }

  /**
   * Adds a text field to the form creating a {@link TextNodePropertyModel} of the 
   * supplied information.
   * 
   * @param key
   * @param label
   * @param defaultValue
   * @return this
   */
  @NotNull
  public TextNodeFormPanel addTextField(@NotNull String key, @NotNull IModel<?> label, @Nullable IModel<String> defaultValue) {
    this.components.add(new Descriptor(key, Type.TEXT, label, defaultValue));
    return this;
  }

  /**
   * Adds a text area field to the form creating a {@link TextNodePropertyModel} of
   * the supplied information.
   *
   * @param key
   * @param label
   * @param defaultValue
   * @return this
   */
  @NotNull
  public TextNodeFormPanel addTextArea(@NotNull String key, @NotNull IModel<?> label, @Nullable IModel<String> defaultValue) {
    this.components.add(new Descriptor(key, Type.TEXT_AREA, label, defaultValue));
    return this;
  }

  @Override
  protected void onInitialize() {
    super.onInitialize();
    //create the form components here
    form = new Form<TextNode>("storeForm") {
      @Override
      protected void onSubmit() {
        super.onSubmit();
        onFormSubmit(this);
      }

      @Override
      protected void onValidate() {
        super.onValidate();
        onFormValidate(this);
      }

      @Override
      protected void onError() {
        super.onError();
        onFormError(this);
      }
    };
    form.setModel(newFormModel());
    form.add(new FormComponentList("inputComponent"));
    form.add(buttons);
    for (Descriptor descriptor : components) {
      descriptor.createComponents(form);
    }
    add(form);
  }

  protected void onFormSubmit(Form<TextNode> form) {

  }

  protected void onFormValidate(Form<TextNode> form) {

  }

  protected void onFormError(Form<TextNode> form) {

  }

  /**
   * Returns the form. The form is available after {@link #onInitialize()} returns.
   *
   * @return
   */
  public Form<TextNode> getForm() {
    return form;
  }

  /**
   * Returns the buttons panel. The button panel is available right
   * after this constructor finishes.
   *
   * @return
   */
  public ButtonPanel getButtons() {
    return buttons;
  }

  /**
   * After the form is created (after {@link #onInitialize()}), this method
   * can be used to find the form component that was created for a provided
   * key.
   * 
   * @param key
   * @return
   */
  public FormComponent<String> getFormComponent(@NotNull String key) {
    int index = components.indexOf(new Descriptor(key, null, null, null));
    if (index >= 0) {
      Descriptor descriptor = components.get(index);
      return descriptor.formComponent;
    }
    return null;
  }

  /**
   * After the form is created (after {@link #onInitialize()}), this method
   * can be used to find the label component that was created for a provided
   * key.
   *
   * @param key
   * @return
   */
  public Label getLabelComponent(@NotNull String key) {
    int index = components.indexOf(new Descriptor(key, null, null, null));
    if (index >= 0) {
      Descriptor descriptor = components.get(index);
      return descriptor.labelComponent;
    }
    return null;
  }

  /**
   * This is called during {@link #onInitialize()} to get the model
   * to use for this form. By default it creates a new {@link TextNodeModel}
   * using the current page class name as node id.
   * 
   * @return
   */
  protected IModel<TextNode> newFormModel() {
    return new TextNodeModel(storeModel, getPage().getPageClass());
  }

  public void addDefaultButton() {
    buttons.addPositiveSubmitButton(new ResourceModel("save"));
  }
  
  private final class FormComponentList extends ListView<Descriptor> {
    private FormComponentList(String id) {
      super(id, components);
    }

    @Override
    protected void populateItem(ListItem<Descriptor> components) {
      Descriptor descriptor = components.getModelObject();
      components.add(descriptor.formComponent);
      components.add(descriptor.labelComponent);
      components.add(descriptor.invisible);
    }
  }

  private static class Descriptor implements Serializable {
    private final String key;
    private final Type type;
    private final IModel<?> label;
    private final IModel<String> defaultValue;

    private FormComponent<String> formComponent;
    private FormComponent<String> invisible;
    private Label labelComponent;

    private Descriptor(String key, Type type, IModel<?> label, IModel<String> defaultValue) {
      this.key = key;
      this.type = type;
      this.label = label;
      this.defaultValue = defaultValue;
    }

    public void setFormComponent(FormComponent<String> formComponent) {
      this.formComponent = formComponent;
    }

    public void setLabelComponent(Label labelComponent) {
      this.labelComponent = labelComponent;
    }

    /**
     * Creates the formcomponents from the supplied description.
     * <p/>
     * This is called in {@link TextNodeFormPanel#onInitialize()}
     * to give subclasses access to the components. The listview would create them on render
     * time, which is too late.
     * 
     * @param form
     */
    private void createComponents(Form<TextNode> form) {
      FormComponent<String> fc;
      if (type == Type.TEXT) {
        fc = new TextField<String>("inputText", new TextNodePropertyModel(form.getModel(), key, defaultValue));
        fc.setOutputMarkupId(true);
        TextArea<String> area = new TextArea<String>("inputArea");
        area.setVisible(false);
        this.invisible = area;
        this.formComponent = fc;
      } else {
        fc = new TextArea<String>("inputArea", new TextNodePropertyModel(form.getModel(), key, defaultValue));
        fc.setOutputMarkupId(true);
        TextField<String> field = new TextField<String>("inputText");
        field.setVisible(false);
        this.invisible = field;
        this.formComponent = fc;
      }

      Label label = new Label("label", this.label);
      label.add(new AttributeModifier("for", fc.getMarkupId()));
      this.labelComponent = label;
    }
    
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Descriptor that = (Descriptor) o;

      if (key != null ? !key.equals(that.key) : that.key != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return key != null ? key.hashCode() : 0;
    }
  }
}
