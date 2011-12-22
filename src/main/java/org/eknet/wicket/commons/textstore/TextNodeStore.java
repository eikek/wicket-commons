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
import org.jetbrains.annotations.Nullable;

/**
 * The persist service for {@link TextNode}s.
 * <p/>
 * The idea is, that {@link TextNode}s itself are responsible for persisting its
 * values they receive. So this interface lacks a {@code saveNode()} method.
 * 
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 17.12.11 14:40
 */
public interface TextNodeStore {

  /**
   * Creates a new {@link TextNode} using a generated unique id.
   * 
   * @return
   */
  @NotNull
  TextNode createTextNode();

  /**
   * Creates a new {@link TextNode} using the supplied id. It
   * must check for duplicate ids.
   * 
   * @param id
   * @return
   */
  @NotNull
  TextNode createTextNode(@NotNull String id);

  /**
   * Either returns a {@link TextNode} from the underlying store,
   * or creates a new one using the supplied id.
   * 
   * @param id
   * @return
   */
  @NotNull
  TextNode getOrCreateNode(@NotNull String id);

  /**
   * Returns a available {@link TextNode} of the supplied id from
   * the store or {@code null} if none is found.
   * @param id
   * @return
   */
  @Nullable
  TextNode getTextNode(@NotNull String id);

  /**
   * Returns a available {@link TextNode} of the supplied id from
   * the store or throws an exception if none is found.
   * 
   * @param id
   * @return
   */
  @NotNull
  TextNode requireTextNode(@NotNull String id);

}
