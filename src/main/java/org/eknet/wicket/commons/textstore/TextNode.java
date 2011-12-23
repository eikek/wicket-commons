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
 * This is a simple interface for accessing texts in a map-like structure. The intention
 * is to implement this interface using different storage backends, like SQL database
 * or Neo4J.
 * <p/>
 * Implementations are responsible for persisting texts in {@link #setText(String, String)}
 * and {@link #removeKey(String)}. That's why this is not extending {@link java.io.Serializable}
 * as it should not be used directly in components since implementations probably need to carry
 * references to certain services. Instead, use {@link TextNodeModel} with a {@link TextNodeStore}
 * implementation.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 20.12.11 11:30
 */
public interface TextNode {

  /**
   * Returns whether this node was newly created ({@code true}) or read
   * from the database ({@code false}).
   *
   * @return
   */
  boolean isCreated();

  /**
   * Returns whether this node has been modified.
   *
   * @return
   */
  boolean isModified();

  /**
   * Returns the unique id of this {@link TextNode}.
   * 
   * @return
   */
  @NotNull
  String getId();

  /**
   * Returns the value to the specified key, which may
   * be {@code null} if there is no value attached.
   *
   * @param key
   * @return
   */
  @Nullable
  String getText(@NotNull String key);

  /**
   * Returns the value to the specified key or the {@code defaultValue}
   * if no value is attached to the key.
   *
   * @param key
   * @param defaultValue
   * @return
   */
  String getText(@NotNull String key, @Nullable String defaultValue);

  /**
   * Returns the value attached to the key or throws an exception.
   *
   * @param key
   * @return
   */
  @NotNull
  String requireText(@NotNull String key);

  /**
   * Sets the value for the specified key.
   *
   * @param key
   * @param value
   */
  void setText(@NotNull String key, @NotNull String value);

  /**
   * Removes a value associated to the specified key.
   *
   * @param key
   */
  void removeKey(@NotNull String key);
}
