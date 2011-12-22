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

package org.eknet.wicket.commons.yaml.pages;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * This page contains code for the markup of a 3col yaml page.
 *
 * <pre>
 *   +-------------------------+
 *   | header                  |
 *   +-------------------------+
 *   | navigation              |
 *   +-------------------------+
 *   | before main             |
 *   +-------------------------+
 *   | col1                    |
 *   +-------------------------+
 *   | col2                    |
 *   +-------------------------+
 *   | col3                    |
 *   +-------------------------+
 *   | after main              |
 *   +-------------------------+
 *   | footer                  |
 *   +-------------------------+
 * </pre>
 *
 * You must set the defined components into this page, either using one
 * of the {@code setXxx(ComponentSupplier)} methods, or create the component
 * using the corresponding id from the {@code getXxxId()} method and set
 * the component using {@link #setPageComponent(Component)}. DO NOT USE the
 * usual {@code add()} methods of this page! The components are set to empty
 * panels at the beginning.
 * <p/>
 * There are example pages to start with in the {@code example}
 * package you can extend and add own stylesheets. It's also easy to create
 * your own set of css files after reading  <a href="http://www.yaml.de">YAML</a>'s
 * documentation. Besides providing all the css magic, <a href="http://www.yaml.de">YAML</a>
 * is very well documented.
 * <p/>
 * The {@link ColumnLayout} can be used to set a css class to the
 * {@code &lt;body&gt;} element that can hide the first or second column. In order for
 * this to work, the corresponding css must be available though.
 *
 * @see org.eknet.wicket.commons.yaml.pages.examples.DynamicYaml3Page
 * @see org.eknet.wicket.commons.yaml.pages.examples.DynamicYaml3FullheightPage
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 18.12.11 17:59
 */
public abstract class AbstractYaml3Page extends WebPage {
  private WebMarkupContainer body = new WebMarkupContainer("body");
  private ColumnLayout layout;

  private final static Map<ColumnLayout, AttributeModifier> cssModifier = new HashMap<ColumnLayout, AttributeModifier>();
  static {
    cssModifier.put(ColumnLayout.HIDE_BOTH, new AttributeModifier("class", "hideboth"));
    cssModifier.put(ColumnLayout.HIDE_COL1, new AttributeModifier("class", "hidecol1"));
    cssModifier.put(ColumnLayout.HIDE_COL2, new AttributeModifier("class", "hidecol2"));
    cssModifier.put(ColumnLayout.SHOW_ALL, new AttributeModifier("class", AttributeModifier.VALUELESS_ATTRIBUTE_REMOVE));
  }

  static class EmptyPanelSupplier implements ComponentSupplier<EmptyPanel> {
    @NotNull
    @Override
    public EmptyPanel get(@NotNull String id) {
      EmptyPanel p = new EmptyPanel(id);
      p.setVisible(false);
      return p;
    }
  }

  public AbstractYaml3Page() {
    init();
  }

  public AbstractYaml3Page(IModel<?> model) {
    super(model);
    init();
  }

  public AbstractYaml3Page(PageParameters parameters) {
    super(parameters);
    init();
  }

  private void init() {
    add(body);
    add(new Label("pageTitle", new AbstractReadOnlyModel<Object>() {
      @Override
      public Object getObject() {
        return getPageTitle();
      }
    }));
    setHeader(emptyInvisiblePanel());
    setNavigation(emptyInvisiblePanel());
    setBeforeMain(emptyInvisiblePanel());
    setColumn1(emptyInvisiblePanel());
    setColumn2(emptyInvisiblePanel());
    setColumn3(emptyInvisiblePanel());
    setAfterMain(emptyInvisiblePanel());
    setFooter(emptyInvisiblePanel());
  }

  private ComponentSupplier<EmptyPanel> emptyInvisiblePanel() {
    return new EmptyPanelSupplier();
  }

  public void setColumnLayout(ColumnLayout layout) {
    if (this.layout != null) {
      body.remove(cssModifier.get(this.layout));
    }
    this.layout = layout;
    if (this.layout != null) {
      body.add(cssModifier.get(this.layout));
    }
  }

  public String getHeaderId() {
    return "header";
  }

  public <T extends Component> T setHeader(ComponentSupplier<T> header) {
    T h = header.get(getHeaderId());
    body.addOrReplace(h);
    return h;
  }

  public void setPageComponent(Component component) {
    body.addOrReplace(component);
  }

  public String getFooterId() {
    return "footer";
  }
  public <T extends Component> T setFooter(ComponentSupplier<T> footer) {
    T f = footer.get(getFooterId());
    body.addOrReplace(f);
    return f;
  }

  public String getColumn1Id() {
    return "columnContent1";
  }

  public <T extends Component> T setColumn1(@NotNull ComponentSupplier<T> componentSupplier) {
    T c = componentSupplier.get(getColumn1Id());
    body.addOrReplace(c);
    return c;
  }

  public String getColumn2Id() {
    return "columnContent2";
  }

  public <T extends Component> T setColumn2(@NotNull ComponentSupplier<T> componentSupplier) {
    T c = componentSupplier.get(getColumn2Id());
    body.addOrReplace(c);
    return c;
  }

  public String getColumn3Id() {
    return "columnContent3";
  }

  public <T extends Component> T setColumn3(@NotNull ComponentSupplier<T> componentSupplier) {
    T c = componentSupplier.get(getColumn3Id());
    body.addOrReplace(c);
    return c;
  }

  public String getNavigationId() {
    return "navigation";
  }

  public <T extends Component> T setNavigation(@NotNull ComponentSupplier<T> componentSupplier) {
    T c = componentSupplier.get(getNavigationId());
    body.addOrReplace(c);
    return c;
  }

  public String getBeforeMainId() {
    return "beforeMain";
  }

  public <T extends Component> T setBeforeMain(@NotNull ComponentSupplier<T> componentSupplier) {
    T c = componentSupplier.get(getBeforeMainId());
    body.addOrReplace(c);
    return c;
  }

  public String getAfterMainId() {
    return "afterMain";
  }

  public <T extends Component> T setAfterMain(@NotNull ComponentSupplier<T> componentSupplier) {
    T c = componentSupplier.get(getAfterMainId());
    body.addOrReplace(c);
    return c;
  }

  protected String getPageTitle() {
    return "My Page Title";
  }

}
