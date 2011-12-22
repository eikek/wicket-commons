package org.eknet.wicket.commons.components;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.Strings;
import org.eknet.wicket.commons.ComponentSuppliers;
import org.eknet.wicket.commons.DelegatingSupplier;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * A label that applies its body content to {@link String#format(String, Object...)} with
 * a specified format.
 * 
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 09.12.11 22:25
 */
public class FormattedLabel extends Label {

  private IModel<String> format;
  private boolean multiLine = false;
  
  public FormattedLabel(String id) {
    super(id);
  }

  public FormattedLabel(String id, String label) {
    super(id, label);
  }

  public FormattedLabel(String id, IModel<?> model) {
    super(id, model);
  }

  public FormattedLabel setFormat(String format) {
    this.format = new Model<String>(format);
    return this;
  }

  public FormattedLabel setFormat(IModel<String> format) {
    this.format = format;
    return this;
  }

  public IModel<String> getFormat() {
    return format;
  }

  public boolean isMultiLine() {
    return multiLine;
  }

  public void setMultiLine(boolean multiLine) {
    this.multiLine = multiLine;
  }

  public FormattedLabel bold() {
    return setFormat("<b>%s</b>");
  }

  public FormattedLabel headline(int level) {
    return setFormat("<h" + level + ">%s</h" + level + ">");
  }

  @Override
  public void onComponentTagBody(MarkupStream markupStream, ComponentTag openTag) {
    String body = getDefaultModelObjectAsString();
    if (Strings.isEmpty(body)) {
      replaceComponentTagBody(markupStream, openTag, body);
      return;
    }

    if (isMultiLine()) {
      body = Strings.toMultilineMarkup(getDefaultModelObjectAsString()).toString();
    }
    replaceComponentTagBody(markupStream, openTag, getFormattedString(body));
  }
  
  protected String getFormattedString(String text) {
    if (format == null) {
      return text;
    }
    return String.format(format.getObject(), text);
  }
  
  public static Builder create() {
    return new Builder();
  }

  public static class Builder extends DelegatingSupplier<FormattedLabel> {
    
    private IModel<String> format;
    private IModel<?> text;
    private boolean escapeModelString = true;
    private boolean multiLine = false;
    
    public Builder() {
      super(ComponentSuppliers.provide(FormattedLabel.class));
    }

    @Override
    protected void onCreation(@NotNull FormattedLabel component) {
      if (format != null) {
        component.setFormat(format);
      }
      if (text != null) {
        component.setDefaultModel(text);
      }
      component.setEscapeModelStrings(escapeModelString);
      component.setMultiLine(multiLine);
    }

    public Builder withText(IModel<?> text) {
      this.text = text;
      return this;
    }

    public Builder withText(String text) {
      this.text = new Model<Serializable>(text);
      return this;
    }

    public Builder withFormat(String format) {
      this.format = new Model<String>(format);
      return this;
    }

    public Builder withFormat(IModel<String> format) {
      this.format = format;
      return this;
    }

    public Builder escapeModelString(boolean flag) {
      this.escapeModelString = flag;
      return this;
    }

    public Builder mulitLine(boolean flag) {
      this.multiLine = flag;
      return this;
    }
    
    public Builder inBold() {
      return withFormat("<b>%s</b>");
    }

    public Builder asHeadline(int level) {
      return withFormat("<h" + level + ">%s</h" + level + ">");
    }

    public Builder asParagraph() {
      return withFormat("<p>%s</p>");
    }

    public Builder asParagraph(String cssClass) {
      if (Strings.isEmpty(cssClass)) {
        return asParagraph();
      } else {
        return withFormat("<p class=\"" + cssClass + "\">%s</p>");
      }
    }

    public Builder asHeadlineWithClass(int level, String cssClass) {
      if (level <= 0) {
        level = 1;
      }
      if (cssClass != null) {
        return withFormat("<h" + level + " class='" + cssClass + "'>%s</h" + level + ">");
      } else {
        return withFormat("<h" + level + ">%s</h" + level + ">");
      }
    }
  }
}
