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

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 17.12.11 18:06
 */
public class BookmarkableLinkSupplier<T> implements ComponentSupplier<BookmarkablePageLink<T>> {

  private Class<? extends Page> pageClass;
  private PageParameters parameters;
  private IModel<?> body;


  public BookmarkableLinkSupplier<T> setPageClass(Class<? extends Page> pageClass) {
    this.pageClass = pageClass;
    return this;
  }

  public BookmarkableLinkSupplier<T> setParameters(PageParameters pageParameters) {
    this.parameters = pageParameters;
    return this;
  }

  public BookmarkableLinkSupplier<T> setBody(IModel<?> body) {
    this.body = body;
    return this;
  }

  public BookmarkableLinkSupplier<T> setBody(String body) {
    this.body = Model.of(body);
    return this;
  }

  public Class<? extends Page> getPageClass() {
    return pageClass;
  }

  public PageParameters getParameters() {
    return parameters;
  }

  public IModel<?> getBody() {
    return body;
  }

  @NotNull
  @Override
  public BookmarkablePageLink<T> get(@NotNull String id) {
    BookmarkablePageLink<T> link = new BookmarkablePageLink<T>(id, pageClass, parameters);
    if (body != null) {
      link.setBody(body);
    }
    return link;
  }
}
