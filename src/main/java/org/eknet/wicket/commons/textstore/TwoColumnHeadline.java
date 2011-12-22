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
import org.eknet.wicket.commons.ComponentSupplier;
import org.eknet.wicket.commons.components.FormattedLabel;
import org.eknet.wicket.commons.yaml.SubcolumnDef;
import org.eknet.wicket.commons.yaml.Subcolumns;
import org.jetbrains.annotations.NotNull;

/**
 * A component that contains 3 text components of the following layout:
 * <pre>
 *   +-------------------+
 *   | headline h2       |
 *   +-------------------+
 *   | column1 | column2 |
 *   +-------------------+
 * </pre>
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 18.12.11 14:26
 */
public class TwoColumnHeadline extends ViewOrEdit {

  private final static String headline = "headline";
  private final static String columnContent1 = "columnContent1";
  private final static String columnContent2 = "columnContent2";

  public TwoColumnHeadline(@NotNull IModel<TextNodeStore> storeModel, String id, Mode mode) {
    super(storeModel, id, mode);
  }

  public TwoColumnHeadline(@NotNull IModel<TextNodeStore> storeModel, String id, PageParameters pp) {
    super(storeModel, id, pp);
  }

  @Override
  protected void setEditMode() {
    add(new ComponentSupplier<Component>() {
      @NotNull
      @Override
      public Component get(@NotNull String id) {
        return new EditForm(id);
      }
    });
  }

  @Override
  protected void setViewMode() {
    add(FormattedLabel.create()
        .asHeadline(2)
        .withText(new TextNodePropertyModel(getModel(), headline, getHeadlineDefault())));
    Subcolumns.Builder cols = Subcolumns.create()
        .addColumn(SubcolumnDef.create()
            .setColumnDef(getLeftColumn1Def())
            .setContentLeft(getLeftContent()))
        .addColumn(SubcolumnDef.create()
            .setColumnDef(getRightColumnDef())
            .setContentRight(getRightContent()));
    add(cols);
  }
  
  private ComponentSupplier<FormattedLabel> getLeftContent() {
    return FormattedLabel.create()
        .escapeModelString(false)
        .mulitLine(true)
        .withText(new TextNodePropertyModel(getModel(), columnContent1, getColumnContent1Default()));
  }

  private ComponentSupplier<FormattedLabel> getRightContent() {
    return FormattedLabel.create()
        .escapeModelString(false)
        .mulitLine(true)
        .withText(new TextNodePropertyModel(getModel(), columnContent2, getColumnContent2Default()));
  }

  protected IModel<String> getHeadlineDefault() {
    return Model.of("");
  }

  protected IModel<String> getColumnContent1Default() {
    return Model.of("");
  }

  protected IModel<String> getColumnContent2Default() {
    return Model.of("");
  }

  protected SubcolumnDef.ColumnDef getLeftColumn1Def() {
    return SubcolumnDef.ColumnDef.L_50;
  }
  
  protected SubcolumnDef.ColumnDef getRightColumnDef() {
    return SubcolumnDef.ColumnDef.R_50;
  }
  
  final class EditForm extends TextNodeFormPanel {

    public EditForm(String id) {
      super(getStoreModel(), id);
      addDefaultButton();
      addTextField(headline, new ResourceModel("headline"), getHeadlineDefault());
      addTextArea(columnContent1, new ResourceModel("columnContent1"), getColumnContent1Default());
      addTextArea(columnContent2, new ResourceModel("columnContent2"), getColumnContent1Default());
    }

    @Override
    protected void onInitialize() {
      super.onInitialize();
      FormComponent<String> fc = getFormComponent(columnContent1);
      if (fc != null) {
        fc.add(new AttributeModifier("rows", "12"));
      }
      fc = getFormComponent(columnContent2);
      if (fc != null) {
        fc.add(new AttributeModifier("rows", "12"));
      }
    }

    @Override
    protected IModel<TextNode> newFormModel() {
      return newTextNodeModel();
    }
  }
}
