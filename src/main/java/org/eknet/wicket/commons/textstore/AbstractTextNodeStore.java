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

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Abstract implementation of {@link TextNodeStore} to simplify the task of implementing this interface.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 20.12.11 13:19
 */
public abstract class AbstractTextNodeStore implements TextNodeStore {

  @NotNull
  @Override
  public TextNode createTextNode() {
    return createTextNode(UUID.randomUUID().toString());
  }

  @NotNull
  @Override
  public TextNode requireTextNode(@NotNull String id) {
    TextNode node = getTextNode(id);
    if (node == null) {
      throw new IllegalArgumentException("No node found for id: " + id);
    }
    return node;
  }

  @NotNull
  @Override
  public TextNode getOrCreateNode(@NotNull String id) {
    TextNode textNode = getTextNode(id);
    if (textNode == null) {
      textNode = createTextNode(id);
    }
    return textNode;
  }
}
