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
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * A generic supplier for wicket components where the id is given
 * by the caller.
 *
 * @author <a href="mailto:eike.kettner@gmail.com">Eike Kettner</a>
 * @since 05.12.11 19:07
 */
public interface ComponentSupplier<T extends Component> extends Serializable {

  @NotNull
  T get(@NotNull String id);
}
