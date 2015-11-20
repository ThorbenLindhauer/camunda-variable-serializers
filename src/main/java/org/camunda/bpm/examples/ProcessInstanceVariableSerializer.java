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
package org.camunda.bpm.examples;

import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.core.variable.value.UntypedValueImpl;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.variable.serializer.PrimitiveValueSerializer;
import org.camunda.bpm.engine.impl.variable.serializer.ValueFields;
import org.camunda.bpm.engine.runtime.ProcessInstance;

/**
 * @author Thorben Lindhauer
 *
 */
public class ProcessInstanceVariableSerializer extends PrimitiveValueSerializer<ProcessInstanceValue> {

  public ProcessInstanceVariableSerializer() {
    super(ProcessInstanceValues.VALUE_TYPE);
  }

  public void writeValue(ProcessInstanceValue value, ValueFields valueFields) {
    ProcessInstance pi = value.getValue();
    if (pi != null) {
      valueFields.setTextValue(pi.getId());
    }

  }

  public ProcessInstanceValue convertToTypedValue(UntypedValueImpl untypedValue) {
    return ProcessInstanceValues.create((ProcessInstance) untypedValue.getValue());
  }

  public ProcessInstanceValue readValue(ValueFields valueFields) {
    String processInstanceId = valueFields.getTextValue();

    ExecutionEntity executionEntity = Context
      .getCommandContext()
      .getExecutionManager()
      .findExecutionById(processInstanceId);

    if (executionEntity == null || !executionEntity.isProcessInstanceExecution()) {
      throw new RuntimeException("oh no");
    }

    return ProcessInstanceValues.create(executionEntity);
  }

}
