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

import java.util.Map;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.impl.type.PrimitiveValueTypeImpl;
import org.camunda.bpm.engine.variable.impl.value.PrimitiveTypeValueImpl;
import org.camunda.bpm.engine.variable.type.PrimitiveValueType;

/**
 * @author Thorben Lindhauer
 *
 */
public class ProcessInstanceValues {

  public static final PrimitiveValueType VALUE_TYPE = new ProcessInstanceValueTypeImpl();

  public static ProcessInstanceValue create(ProcessInstance processInstance) {
    return new ProcessInstanceValueImpl(processInstance);
  }

  public static class ProcessInstanceValueImpl extends PrimitiveTypeValueImpl<ProcessInstance> implements ProcessInstanceValue {

    private static final long serialVersionUID = 1L;

    public ProcessInstanceValueImpl(ProcessInstance value) {
      super(value, VALUE_TYPE);
    }
  }

  public static class ProcessInstanceValueTypeImpl extends PrimitiveValueTypeImpl {

    private static final long serialVersionUID = 1L;

    public ProcessInstanceValueTypeImpl() {
      super(ProcessInstance.class);
    }

    public ProcessInstanceValue createValue(Object value, Map<String, Object> valueInfo) {
      // TODO: or from id?
      return ProcessInstanceValues.create((ProcessInstance) value);
    }
  }
}
