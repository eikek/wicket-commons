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

import org.apache.wicket.Component;
import org.apache.wicket.model.IComponentInheritedModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.IWrapModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.jetbrains.annotations.NotNull;

/**
 * Loadable model for {@link TextNode}s. It needs a {@link TextNodeStore} to work, which is 
 * supplied via the {@link IModel} interface. Usually (if some sort of DI is used), you can
 * implement this like the follwoing:
 * <pre>
 *   public class TextNodeStoreModel extends AbstractReadOnlyModel&lt;TextNodeStore&gt; {
 *
 *     &#x40;Inject || &#x40;SpringBean
 *     private TextNodeStore store;
 *
 *     public TextNodeStoreModel() {
 *       Injector.get().inject(this);
 *     }
 *
 *     public TextNodeStore getObject() {
 *       return store;
 *     }
 *   }
 * </pre>
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 17.12.11 14:41
 */
public final class TextNodeModel extends LoadableDetachableModel<TextNode> implements IComponentInheritedModel<TextNode> {

  private final IModel<TextNodeStore> textNodeStore;

  private final String id;
  private final boolean required;
  private final boolean createNonExistingNode;


  public TextNodeModel(@NotNull IModel<TextNodeStore> textNodeStore, @NotNull Class<?> clazz) {
    this(textNodeStore, clazz.getName());
  }

  public TextNodeModel(@NotNull IModel<TextNodeStore> textNodeStore, @NotNull String nodeId) {
    this(textNodeStore, true, true, nodeId);
  }

  public TextNodeModel(@NotNull IModel<TextNodeStore> textNodeStore, boolean required, boolean createNonExistingNode, @NotNull String id) {
    this.textNodeStore = textNodeStore;
    this.id = id;
    this.required = required;
    this.createNonExistingNode = createNonExistingNode;
  }

  public TextNodeModel(@NotNull IModel<TextNodeStore> textNodeStore, @NotNull TextNode node, boolean required, boolean createNonExistingNode) {
    super(node);
    this.textNodeStore = textNodeStore;
    this.id = node.getId();
    this.required = required;
    this.createNonExistingNode = createNonExistingNode;
  }

  public String getId() {
    return id;
  }

  public boolean isRequired() {
    return required;
  }

  @Override
  protected TextNode load() {
    TextNode node;
    if (createNonExistingNode) {
      node = textNodeStore.getObject().getOrCreateNode(id);
    } else {
      node = textNodeStore.getObject().getTextNode(id);
    }
    if (node == null && isRequired()) {
      throw new IllegalStateException("Required node not found: " + id);
    }
    return node;
  }

  protected String getKey(Component component) {
    return component.getId();
  }
  
  @Override
  public <W> IWrapModel<W> wrapOnInheritance(Component component) {
    //noinspection unchecked
    return (IWrapModel<W>) new TextNodeKeyModel(component);
  }
  
  
  private class TextNodeKeyModel extends TextNodePropertyModel implements IWrapModel<String> {

    private final Component owner;

    private TextNodeKeyModel(Component owner) {
      super(TextNodeModel.this);
      this.owner = owner;
    }

    @Override
    public String getKey() {
      return TextNodeModel.this.getKey(owner);
    }

    @Override
    public IModel<?> getWrappedModel() {
      return TextNodeModel.this;
    }

  }
}
