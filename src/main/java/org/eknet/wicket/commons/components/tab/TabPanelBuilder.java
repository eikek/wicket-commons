/*
 * photozone - web shop application
 * Copyright (C) 2011 Eike Kettner <eike.kettner@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eknet.wicket.commons.components.tab;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.eknet.wicket.commons.ComponentSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Builder for {@link TabbedPanel}s. The css styles from the wicket examples page are packaged and the 
 * style can be chosen with {@link #setStyleClass(Style)}.
 * <p/>
 * Example
 * <pre>
 * DivContainer.Builder content = DivContainer.create();
 * content.add(new TabPanelBuilder()
 *   .useAjax(true)
 *   .setStyleClass(Style.PLAIN)
 *   .setAdditionalCssClasses("float_left")
 *   .setStyleAttribute("width:100%;")
 *   .addTab(ComponentSuppliers.provide(InfoPanel.class), new ResourceModel("infoPanelTitle"))
 *   .addTab(ComponentSuppliers.provide(MailSettingsPanel.class), new ResourceModel("mailSettings"))
 *   .addTab(ComponentSuppliers.provide(WebSettingsForm.class), new ResourceModel("webSettings")));
 * add(content.get("panel"));
 * </pre>
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 19.12.11 12:26
 */
public class TabPanelBuilder implements ComponentSupplier<TabbedPanel> {

  private final List<ITab> tabs = new LinkedList<ITab>();
  private boolean ajax;
  private Style style;

  private String addtionalCssClasses;
  private String styleCss;
  
  public TabPanelBuilder addTab(final ComponentSupplier<? extends WebMarkupContainer> panel, IModel<String> title) {
    tabs.add(new AbstractTab(title) {
      @Override
      public WebMarkupContainer getPanel(String panelId) {
        WebMarkupContainer component = panel.get(panelId);
        component.add(new AttributeAppender("class", " float_left"));
        component.add(new AttributeAppender("style", " width: 100%;"));
        return component;
      }
    });
    return this;
  }

  /**
   * Set whether to create a {@link AjaxTabbedPanel} or a normal {@link TabbedPanel}.
   *
   * @param flag
   * @return
   */
  public TabPanelBuilder useAjax(boolean flag) {
    this.ajax = flag;
    return this;
  }

  /**
   * Set one of the styles from
   * <a href="http://wicketstuff.org/wicket13/compref/?wicket:bookmarkablePage=:org.apache.wicket.examples.compref.TabbedPanelPage">wicket's examples page</a>.
   *
   * @param style
   * @return
   */
  public TabPanelBuilder setStyleClass(Style style) {
    this.style = style;
    return this;
  }

  /**
   * Adds additional css classes to the tabbed pane container.
   *
   * @param cssClasses
   * @return
   */
  public TabPanelBuilder setAdditionalCssClasses(String... cssClasses) {
    StringBuilder buf = new StringBuilder();
    for (String css : cssClasses) {
      buf.append(" ").append(css);
    }
    this.addtionalCssClasses = buf.toString();
    return this;
  }

  /**
   * Sets the style attribute of the tag of the tabbed pane container.
   *
   * @param css
   * @return
   */
  public TabPanelBuilder setStyleAttribute(String css) {
    this.styleCss = css;
    return this;
  }

  @NotNull
  @Override
  public TabbedPanel get(@NotNull String id) {
    TabbedPanel panel;
    if (ajax) {
      panel = new StyledAjaxTabbedPanel(id, tabs, style);
    } else {
      panel = new StyledTabbedPanel(id, tabs, style);
    }
    if (addtionalCssClasses != null) {
      panel.add(new AttributeAppender("class", addtionalCssClasses));
    }
    if (styleCss != null) {
      panel.add(new AttributeModifier("style", styleCss));
    }
    return panel;
  }
  
  static class StyledAjaxTabbedPanel extends AjaxTabbedPanel {
    
    private final Style style;
    
    StyledAjaxTabbedPanel(String id, List<ITab> tabs, Style style) {
      super(id, tabs);
      this.style = style;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
      super.renderHead(response);
      if (style != null) {
        response.renderCSSReference(new PackageResourceReference(TabPanelBuilder.class, "css/tabpanelstyles.css"));
      }
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
      super.onComponentTag(tag);
      if (style != null) {
        tag.put("class", style.getCssClass());
      }
    }
  }
  
  static class StyledTabbedPanel extends TabbedPanel {
    private final Style style;

    StyledTabbedPanel(String id, List<? extends ITab> tabs, Style style) {
      super(id, tabs);
      this.style = style;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
      super.renderHead(response);
      if (style != null) {
        response.renderCSSReference(new PackageResourceReference(TabPanelBuilder.class, "css/tabpanelstyles.css"));
      }
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
      super.onComponentTag(tag);
      if (style != null) {
        tag.put("class", style.getCssClass());
      }
    }
  }
}
