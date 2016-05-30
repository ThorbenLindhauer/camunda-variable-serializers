/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.variables.processinstance;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.variable.serializer.TypedValueSerializer;
import org.camunda.bpm.variables.enums.EnumVariableSerializer;

/**
 * @author Thorben Lindhauer
 *
 */
// TODO: move class to other package
public class ProcessInstanceVariablePlugin implements ProcessEnginePlugin {

  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    List<TypedValueSerializer> customSerializers = new ArrayList<TypedValueSerializer>();
    customSerializers.add(new ProcessInstanceVariableSerializer());
    customSerializers.add(new EnumVariableSerializer());

    processEngineConfiguration.setCustomPreVariableSerializers(customSerializers);
  }

  public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

  }

  public void postProcessEngineBuild(ProcessEngine processEngine) {

  }

}
