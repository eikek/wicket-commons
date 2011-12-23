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
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import org.jetbrains.annotations.NotNull;

import org.eknet.wicket.commons.ComponentSupplier;
import org.eknet.wicket.commons.components.FormattedLabel;

/**
 * Creates a editable component with the following layout:
 * <pre>
 *   +----------------+
 *   | headline h2    |
 *   +----------------+
 *   | column         |
 *   +----------------+
 * </pre>
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 18.12.11 13:34
 */
public class OneColumnHeadline extends ViewOrEdit {

  private final static String headline = "headline";
  private final static String columnContent = "columnContent";

  public OneColumnHeadline(IModel<TextNodeStore> storeModel, String id, Mode mode) {
    super(storeModel, id, mode);
  }

  public OneColumnHeadline(@NotNull IModel<TextNodeStore> storeModel, @NotNull String id, @NotNull PageParameters pp) {
    super(storeModel, id, pp);
  }

  protected void setEditMode() {
    add(new ComponentSupplier<Component>() {
      @NotNull
      @Override
      public Component get(@NotNull String id) {
        return new EditForm(id);
      }
    });
  }
  
  protected void setViewMode() {
    add(FormattedLabel.create()
        .asHeadline(2)
        .withText(new TextNodePropertyModel(getModel(), headline,
            getDefaultValueModel(getHeadlineDefault()))));
    add(FormattedLabel.create()
        .mulitLine(true)
        .escapeModelString(false)
        .withText(new TextNodePropertyModel(getModel(), columnContent,
            getDefaultValueModel(getColumnContentDefault()))));
  }

  /**
   * Override this to return a default value that is rendered if the node
   * does not contain a value.
   *
   * @return
   */
  @NotNull
  protected IModel<String> getHeadlineDefault() {
    return Model.of("");
  }

  @NotNull
  protected IModel<String> getColumnContentDefault() {
    return Model.of("");
  }

  final class EditForm extends TextNodeFormPanel {

    public EditForm(String id) {
      super(getStoreModel(), id);
      addTextField(headline, new ResourceModel("headline"), getHeadlineDefault());
      addTextArea(columnContent, new ResourceModel("content"), getColumnContentDefault());
    }

    @Override
    protected void onInitialize() {
      super.onInitialize();
      FormComponent fc = getFormComponent(columnContent);
      if (fc != null) {
        fc.add(new AttributeModifier("rows", "20"));
      }
    }

    @Override
    protected IModel<TextNode> newFormModel() {
      return newTextNodeModel();
    }
  }
}
