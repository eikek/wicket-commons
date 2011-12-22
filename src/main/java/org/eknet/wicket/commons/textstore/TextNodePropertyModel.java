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

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 17.12.11 15:00
 */
public class TextNodePropertyModel implements IModel<String> {
  
  private IModel<TextNode> nodeModel;
  private String key;
  private IModel<String> defaultValue;

  protected TextNodePropertyModel(@NotNull IModel<TextNode> nodeModel) {
    this.nodeModel = nodeModel;
  }

  public TextNodePropertyModel(@NotNull IModel<TextNode> nodeModel, @NotNull String key, String defaultValue) {
    this(nodeModel, key, Model.of(defaultValue));
  }

  public TextNodePropertyModel(@NotNull IModel<TextNode> nodeModel, @NotNull String key) {
    this(nodeModel, key, (IModel<String>) null);
  }

  public TextNodePropertyModel(@NotNull IModel<TextNode> nodeModel, @NotNull String key, IModel<String> defaultValue) {
    this.nodeModel = nodeModel;
    this.key = key;
    this.defaultValue = defaultValue;
  }

  public TextNodePropertyModel(@NotNull IModel<TextNodeStore> storeModel, @NotNull String nodeId, @NotNull String key, IModel<String> defaultValue) {
    this(new TextNodeModel(storeModel, true, true, nodeId), key, defaultValue);
  }

  public TextNodePropertyModel(@NotNull IModel<TextNodeStore> storeModel, @NotNull TextNode node, @NotNull String key, IModel<String> defaultValue) {
    this(new TextNodeModel(storeModel, node, true, true), key, defaultValue);
  }

  public String getKey() {
    return key;
  }

  public IModel<String> getDefaultValue() {
    return defaultValue;
  }

  @Override
  public String getObject() {
    TextNode node = nodeModel.getObject();
    if (node == null) {
      throw new IllegalStateException("No textnode available");
    }
    String defaultValue = getDefaultValue() != null ? getDefaultValue().getObject() : null;
    return node.getText(getKey(), defaultValue);
  }

  @Override
  public void setObject(String object) {
    TextNode node = nodeModel.getObject();
    if (node == null) {
      throw new IllegalStateException("No textnode available");
    }
    if (Strings.isEmpty(object)) {
      node.removeKey(getKey());
    } else {
      node.setText(getKey(), object);
    }
  }

  @Override
  public void detach() {
    nodeModel.detach();
  }
}
