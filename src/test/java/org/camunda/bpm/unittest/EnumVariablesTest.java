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
package org.camunda.bpm.unittest;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.variables.enums.EnumValue;
import org.camunda.bpm.variables.enums.EnumValueImpl;
import org.junit.Rule;
import org.junit.Test;

public class EnumVariablesTest {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"testProcess.bpmn"})
  public void shouldExecuteProcess() {

    // TODO: builder
    EnumValueImpl enumValue = new EnumValueImpl(MyEnum.FOO, null, null, true);

    // Given we create a new process instance
    ProcessInstance instance = runtimeService().startProcessInstanceByKey("testProcess",
        Variables.createVariables().putValueTyped("enumValue", enumValue));
    // Then it should be active
    assertThat(instance).isActive();
    // And it should be the only instance
    assertThat(task(instance)).isNotNull();

    // get typed enum value
    EnumValue returnedEnumValue = runtimeService().getVariableTyped(instance.getId(), "enumValue");
    assertThat(returnedEnumValue.getValue()).isEqualTo(MyEnum.FOO);
  }

}
