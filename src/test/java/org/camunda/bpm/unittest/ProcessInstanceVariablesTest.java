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

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.complete;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.task;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.variables.processinstance.ProcessInstanceValues;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Thorben Lindhauer
 *
 */
public class ProcessInstanceVariablesTest {

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {"testProcess.bpmn"})
  public void shouldExecuteProcess() {
    ProcessInstance instance1 = runtimeService().startProcessInstanceByKey("testProcess");

    // Given we create a new process instance
    ProcessInstance instance2 = runtimeService().startProcessInstanceByKey("testProcess",
        Variables.createVariables().putValueTyped("relatedInstance", ProcessInstanceValues.create(instance1)));
    // Then it should be active
    assertThat(instance2).isActive();
    // And it should be the only instance
    assertThat(task(instance2)).isNotNull();

    // get process instance directly
    ProcessInstance referencedInstance = (ProcessInstance) runtimeService().getVariable(instance2.getId(), "relatedInstance");
    assertThat(referencedInstance.getId()).isEqualTo(instance1.getId());

    // get query for variable instances
    VariableInstance variableInstance = runtimeService()
      .createVariableInstanceQuery()
      .variableValueEquals("relatedInstance", referencedInstance)
      .singleResult();

    // TODO: if we could filter for all variables of a certain type,
    //   we could build a generic application that displays a graph of process instance relations

    assertThat(variableInstance).isNotNull();

    // query for process instances that are related to this instance
    ProcessInstance queriedInstance = runtimeService()
      .createProcessInstanceQuery()
      .variableValueEquals("relatedInstance", referencedInstance)
      .singleResult();

    assertThat(queriedInstance).isNotNull();
    assertThat(queriedInstance.getId()).isEqualTo(instance2.getId());

    // When we complete that task
    complete(task(instance2));
    // Then the process instance should be ended
    assertThat(instance2).isEnded();
  }
}
