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
package org.camunda.bpm.variables.enums;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.variable.impl.type.AbstractValueTypeImpl;
import org.camunda.bpm.engine.variable.value.SerializableValue;
import org.camunda.bpm.engine.variable.value.TypedValue;

/**
 * @author Thorben Lindhauer
 *
 */
public class EnumValueTypeImpl extends AbstractValueTypeImpl implements EnumValueType {

  public static final String TYPE_NAME = "enum";

  public EnumValueTypeImpl() {
    super(TYPE_NAME);
  }

  public SerializableValue createValueFromSerialized(String serializedValue, Map<String, Object> valueInfo) {
    return new EnumValueImpl(serializedValue, (String) valueInfo.get(EnumValueType.ENUM_TYPE_NAME));
  }

  public boolean isPrimitiveValueType() {
    return false;
  }

  public Map<String, Object> getValueInfo(TypedValue typedValue) {
    Map<String, Object> valueInfo = new HashMap<String, Object>();

    EnumValue enumValue = (EnumValue) typedValue;
    valueInfo.put(EnumValueType.ENUM_TYPE_NAME, enumValue.getEnumTypeName());

    return valueInfo;
  }

  public TypedValue createValue(Object value, Map<String, Object> valueInfo) {
    throw new RuntimeException("not sure when this is used");
  }

}
