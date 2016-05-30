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

import org.camunda.bpm.engine.variable.impl.value.AbstractTypedValue;
import org.camunda.bpm.engine.variable.type.SerializableValueType;

/**
 * @author Thorben Lindhauer
 *
 */
public class EnumValueImpl extends AbstractTypedValue<Enum<?>> implements EnumValue  {

  private static final long serialVersionUID = 1L;

  protected boolean isDeserialized;
  protected String serializedValue;
  protected String enumTypeName;

  public EnumValueImpl(Enum<?> enumValue,
      String serializedValue,
      String enumTypeName,
      boolean isDeserialized) {
    super(enumValue, EnumValueType.TYPE);
    this.serializedValue = serializedValue;
    this.enumTypeName = enumTypeName;
    this.isDeserialized = true;
  }

  public EnumValueImpl(String serializedValue, String enumTypeName) {
    this(null, serializedValue, enumTypeName, false);
  }

  public boolean isDeserialized() {
    return isDeserialized;
  }

  public String getValueSerialized() {
    return serializedValue;
  }

  public void setSerializedValue(String serializedValue) {
    this.serializedValue = serializedValue;
  }

  @Override
  public SerializableValueType getType() {
    return (SerializableValueType) super.getType();
  }

  public String getSerializationDataFormat() {
    return null;
  }

  public String getEnumTypeName() {
    return enumTypeName;
  }

  public void setEnumTypeName(String enumTypeName) {
    this.enumTypeName = enumTypeName;
  }

}
