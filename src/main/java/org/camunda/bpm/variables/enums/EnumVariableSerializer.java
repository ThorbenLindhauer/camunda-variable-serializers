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

import java.nio.charset.Charset;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.util.StringUtil;
import org.camunda.bpm.engine.impl.variable.serializer.AbstractSerializableValueSerializer;
import org.camunda.bpm.engine.impl.variable.serializer.ValueFields;
import org.camunda.bpm.engine.variable.impl.value.UntypedValueImpl;

/**
 * @author Thorben Lindhauer
 *
 */
public class EnumVariableSerializer extends AbstractSerializableValueSerializer<EnumValue> {

  public static final Charset CHARSET = Charset.forName("ascii");
  public static final String SERIALIZATION_FORMAT = "plain";

  public EnumVariableSerializer() {
    super(EnumValueType.TYPE, SERIALIZATION_FORMAT);
  }

  public static final String NAME = "enum";

  public String getName() {
    return NAME;
  }

  public EnumValue convertToTypedValue(UntypedValueImpl untypedValue) {
    // TODO: use builder here
    return new EnumValueImpl((Enum<?>) untypedValue.getValue(), null, null, true);
  }

  @Override
  protected EnumValue createDeserializedValue(Object deserializedObject, String serializedStringValue, ValueFields valueFields) {
    return new EnumValueImpl((Enum<?>) deserializedObject, serializedStringValue, valueFields.getTextValue2(), true);
  }

  @Override
  protected EnumValue createSerializedValue(String serializedStringValue, ValueFields valueFields) {
    return new EnumValueImpl(serializedStringValue, valueFields.getTextValue2());
  }

  @Override
  protected void writeToValueFields(EnumValue value, ValueFields valueFields, byte[] serializedValue) {
    valueFields.setTextValue(StringUtil.fromBytes(serializedValue));
    valueFields.setTextValue2(getEnumTypeName(value));

  }

  protected String getEnumTypeName(EnumValue value) {
    if (value.isDeserialized()) {
      return value.getValue().getClass().getName();
    }
    else {
      // TODO: throw exception if null
      return value.getEnumTypeName();
    }
  }

  @Override
  protected void updateTypedValue(EnumValue value, String serializedStringValue) {
    EnumValueImpl enumValue = (EnumValueImpl) value;
    enumValue.setSerializedValue(serializedStringValue);
    enumValue.setEnumTypeName(getEnumTypeName(value));


  }

  @Override
  protected boolean canSerializeValue(Object value) {
    return value instanceof Enum;
  }

  @Override
  protected byte[] serializeToByteArray(Object deserializedObject) throws Exception {
    return StringUtil.toByteArray(((Enum<?>) deserializedObject).name());
  }

  @Override
  protected byte[] readSerializedValueFromFields(ValueFields valueFields) {
    return StringUtil.toByteArray(valueFields.getTextValue());
  }

  @Override
  protected Object deserializeFromByteArray(byte[] object, ValueFields valueFields) throws Exception {
    String enumName = StringUtil.fromBytes(object);
    String typeName = valueFields.getTextValue2();

    Class<Enum> enumClass = (Class<Enum>) ProcessEngine.class.getClassLoader().loadClass(typeName);
    Object enumValue = Enum.valueOf(enumClass, enumName);

    return enumValue;

    // TODO: this should become process application aware
  }

  @Override
  protected boolean isSerializationTextBased() {
    return true;
  }

}
