package net.ibaixin.chat.api.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import net.ibaixin.chat.api.model.Vcard.Gender;

/**
 * 性别json的序列化方案,使用enum的value来生存json
 * @author huanghui1
 *
 */
public class GenderSerializer extends JsonSerializer<Gender> {

	@Override
	public void serialize(Gender value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeNumber(value.getValue());
//		gen.writeStartObject();
//		gen.writeFieldName("value");
//		gen.writeNumber(value.getValue());
//		gen.writeEndObject();
	}

}
