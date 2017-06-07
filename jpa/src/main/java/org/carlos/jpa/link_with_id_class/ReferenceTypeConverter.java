package org.carlos.jpa.link_with_id_class;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ReferenceTypeConverter implements AttributeConverter<ReferenceType, Integer> {

	@Override
	public Integer convertToDatabaseColumn( ReferenceType type ) {
		return type.getValue();
	}

	@Override
	public ReferenceType convertToEntityAttribute( Integer value ) {
		return ReferenceType.valueOf( value );
	}
}
