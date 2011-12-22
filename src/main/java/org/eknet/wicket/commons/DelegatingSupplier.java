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

package org.eknet.wicket.commons;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;

import static org.eknet.wicket.commons.ComponentSuppliers.provide;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 08.12.11 09:58
 */
public abstract class DelegatingSupplier<T extends Component> implements ComponentSupplier<T> {
  
  protected final ComponentSupplier<T> delegate;

  public DelegatingSupplier(@NotNull ComponentSupplier<T> delegate) {
    this.delegate = delegate;
  }

  public DelegatingSupplier(@NotNull Class<T> componentClass) {
    this.delegate = provide(componentClass);
  }

  public DelegatingSupplier(@NotNull Class<T> componentClass, @NotNull IModel<?> model) {
    this.delegate = provide(componentClass).withArgument(IModel.class, model);
  }

  @NotNull
  @Override
  public T get(@NotNull String id) {
    T component = delegate.get(id);
    onCreation(component);
    return component;
  }

  protected void onCreation(@NotNull T component) {
  }
}

