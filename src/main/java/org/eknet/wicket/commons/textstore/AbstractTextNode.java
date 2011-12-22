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
 * Abstract implementation of {@link TextNode} to simplify the task of implementing this interface.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 20.12.11 13:33
 */
public abstract class AbstractTextNode implements TextNode {

  public final static String UUID_KEY = "___uuid";
  
  @NotNull
  @Override
  public String getId() {
    return requireText(UUID_KEY);
  }

  @Override
  public String getText(@NotNull String key, @Nullable String defaultValue) {
    String value = getText(key);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  @Override
  @NotNull
  public String requireText(@NotNull String key) {
    String value = getText(key);
    if (value == null) {
      throw new IllegalArgumentException("No text found for key: " + key);
    }
    return value;
  }

  @Override
  public void setText(@NotNull String key, @NotNull String value) {
    setProperty(key, value);
  }

  protected abstract void setProperty(@NotNull String key, @NotNull String value);
  
}
