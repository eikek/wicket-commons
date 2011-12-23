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

import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import org.jetbrains.annotations.NotNull;

import org.eknet.wicket.commons.components.DivContainer;

/**
 * An editable text component that can work in two modes: In {@link Mode#VIEW} mode
 * the component displays its model contents using labels. In {@link Mode#EDIT} the
 * component renders a form where the user can edit the label contents.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 18.12.11 13:31
 */
public abstract class ViewOrEdit extends DivContainer {

  public static final String MODE_PARAM = "edit";
  public enum Mode {
    VIEW, EDIT;
    
    public static Mode fromParameters(PageParameters pp) {
      StringValue value = pp.get(MODE_PARAM);
      if (value.isNull()) {
        return VIEW;
      }
      return EDIT;
    }
  }
  
  private Mode mode;
  
  private final IModel<TextNodeStore> storeModel;
  
  public ViewOrEdit(@NotNull IModel<TextNodeStore> storeModel, String id, Mode mode) {
    super(id);
    this.storeModel = storeModel;
    this.mode = mode;
  }

  public ViewOrEdit(@NotNull IModel<TextNodeStore> storeModel, String id, PageParameters pp) {
    this(storeModel, id, Mode.fromParameters(pp));
  }

  public Mode getMode() {
    return mode;
  }

  @Override
  protected void onInitialize() {
    super.onInitialize();
    setDefaultModel(newTextNodeModel());
    if (getMode() == Mode.EDIT) {
      setEditMode();
    } else {
      setViewMode();
    }
  }

  public IModel<TextNodeStore> getStoreModel() {
    return storeModel;
  }

  protected TextNodeModel newTextNodeModel() {
    return new TextNodeModel(storeModel, getPage().getPageClass());
  }

  protected abstract void setEditMode();

  protected abstract void setViewMode();

  public TextNodeModel getModel() {
    return (TextNodeModel) getDefaultModel();
  }

  @Override
  public boolean isApplyCss() {
    return false;
  }

  /**
   * Returns a readonly model that will return the object from the given {@code defaultValue} model
   * only if the {@link TextNode} of this component has not been modified yet. If modified, it
   * returns an empty string.
   * <p/>
   * This is used as the default behaviour when rendering default values.
   *
   * @param defaultValue
   * @return
   */
  @NotNull
  protected AbstractReadOnlyModel<String> getDefaultValueModel(@NotNull final IModel<String> defaultValue) {
    return new AbstractReadOnlyModel<String>() {
      @Override
      public String getObject() {
        if (!getModel().getObject().isModified()) {
          return defaultValue.getObject();
        }
        return "";
      }
    };
  }
}
