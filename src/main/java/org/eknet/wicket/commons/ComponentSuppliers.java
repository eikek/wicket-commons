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
import org.apache.wicket.Page;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 05.12.11 19:07
 */
public final class ComponentSuppliers {
  
  private ComponentSuppliers() {}

  @NotNull
  public static <T extends Component> ComponentSupplier<T> ofInstance(@NotNull final T c) {
    return new ComponentSupplier<T>() {
      @NotNull
      @Override
      public T get(@NotNull String id) {
        return c;
      }
    };
  }

  @NotNull
  public static <T extends Component> Builder<T> provide(@NotNull final Class<T> componentClass, @NotNull final IModel<?> model) {
    return provide(componentClass).withArgument(IModel.class, model);
  }

  @NotNull
  public static <T extends Component> Builder<T> provide(@NotNull Class<T> componentClass) {
    return new Builder<T>(componentClass);
  }

  /**
   * Returns a factory for invisible {@link EmptyPanel}s.
   * 
   * @return
   */
  @NotNull
  public static ComponentSupplier<EmptyPanel> emptyInvisiblePanel() {
    return invisible(provide(EmptyPanel.class));
  }

  @NotNull
  public static <T extends Component> ComponentSupplier<T> invisible(@NotNull final ComponentSupplier<T> delegate) {
    if (delegate instanceof InvisibleSupplier) {
      return delegate;
    }
    return new InvisibleSupplier<T>(delegate);
  }

  @NotNull
  public static <T extends Component> ComponentSupplier<T> disabled(@NotNull final ComponentSupplier<T> delegate) {
    if (delegate instanceof DisabledSupplier) {
      return delegate;
    }
    return new DisabledSupplier<T>(delegate);
  }

  @NotNull
  public static <T> BookmarkableLinkSupplier<T> link(@NotNull Class<? extends Page> pageClass, @NotNull PageParameters pp) {
    return new BookmarkableLinkSupplier<T>().setPageClass(pageClass).setParameters(pp);
  }

  @NotNull
  public static <T> BookmarkableLinkSupplier<T> link(@NotNull Class<? extends Page> pageClass, IModel<?> body) {
    return new BookmarkableLinkSupplier<T>()
        .setPageClass(pageClass)
        .setBody(body);
  }
  @NotNull
  public static <T> BookmarkableLinkSupplier<T> link(@NotNull Class<? extends Page> pageClass, String body) {
    return new BookmarkableLinkSupplier<T>()
        .setPageClass(pageClass)
        .setBody(body);
  }

  @NotNull
  public static <T> ComponentSupplier<BookmarkablePageLink<T>> link(@NotNull Class<? extends Page> pageClass) {
    return new BookmarkableLinkSupplier<T>().setPageClass(pageClass);
  }

  @NotNull
  public static <T> BookmarkableLinkSupplier<T> link(@NotNull Class<? extends Page> pageClass, PageParameters pp, IModel<?> body) {
    return new BookmarkableLinkSupplier<T>()
        .setPageClass(pageClass)
        .setParameters(pp)
        .setBody(body);
  }
  
  @NotNull
  public static <T> BookmarkableLinkSupplier<T> link(@NotNull Class<? extends Page> pageClass, PageParameters pp, String body) {
    return new BookmarkableLinkSupplier<T>()
        .setPageClass(pageClass)
        .setParameters(pp)
        .setBody(body);
  }

  private static class InvisibleSupplier<T extends Component> extends DelegatingSupplier<T>{

    private InvisibleSupplier(@NotNull ComponentSupplier<T> delegate) {
      super(delegate);
    }

    @Override
    protected void onCreation(@NotNull T component) {
      component.setVisible(false);
    }
  }
  
  private static class DisabledSupplier<T extends Component> extends DelegatingSupplier<T> {
    private DisabledSupplier(@NotNull ComponentSupplier<T> delegate) {
      super(delegate);
    }

    @Override
    protected void onCreation(@NotNull T component) {
      component.setEnabled(false);
    }
  }
  
  public static class Builder<T extends Component> implements ComponentSupplier<T> {

    private final Class<T> clazz;

    public Builder(Class<T> clazz) {
      this.clazz = clazz;
    }

    private static class Pair implements Serializable {
      private Class<?> parameter;
      private Object argument;

      private Pair(Class<?> parameter, Object argument) {
        this.parameter = parameter;
        this.argument = argument;
      }
    }

    private final List<Pair> arguments = new LinkedList<Pair>();
    private final List<Behavior> behaviors = new LinkedList<Behavior>();
    
    public Builder<T> withArgument(@NotNull Class<?> parameterType, @Nullable Object argument) {
      Pair pair = new Pair(parameterType, argument);
      this.arguments.add(pair);
      return this;
    }

    public Builder<T> withArgument(@NotNull Object argument) {
      this.arguments.add(new Pair(argument.getClass(), argument));
      return this;
    }

    public Builder<T> withBehavior(@NotNull Behavior behavior) {
      this.behaviors.add(behavior);
      return this;
    }

    @NotNull
    public T get(@NotNull String id) {
      List<Pair> arguments = new ArrayList<Pair>(this.arguments);
      arguments.add(0, new Pair(String.class, id));
      Class[] types = new Class[arguments.size()];
      for (int i = 0; i < types.length; i++) {
        types[i] = arguments.get(i).parameter;
      }
      try {
        Constructor<T> ctor = clazz.getConstructor(types);
        Object[] args = new Object[arguments.size()];
        for (int i = 0; i < args.length; i++) {
          args[i] = arguments.get(i).argument;
        }
        ctor.setAccessible(true);
        T component = ctor.newInstance(args);
        for (Behavior behavior : behaviors) {
          component.add(behavior);
        }
        return component;
      } catch (Exception e) {
        throw new IllegalArgumentException("Unable to instantiate class '" + clazz + "' with arguments '" + arguments + "'!", e);
      }
    }
  }
}
