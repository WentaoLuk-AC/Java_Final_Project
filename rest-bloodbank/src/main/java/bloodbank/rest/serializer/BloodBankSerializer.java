package bloodbank.rest.serializer;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import bloodbank.entity.BloodBank;
import bloodbank.entity.SecurityRole;

public class BloodBankSerializer extends StdSerializer<BloodBank> implements Serializable {
    private static final long serialVersionUID = 1L;

	public BloodBankSerializer() {
		this(null);
	}

	public BloodBankSerializer(Class<BloodBank> t) {
		super(t);
	}

	@Override
	public void serialize(BloodBank value, JsonGenerator gen, SerializerProvider provider) throws IOException {

		gen.writeStartObject();
		gen.writeNumberField("id", value.getId());
		gen.writeStringField("name", value.getName());
		gen.writeNumberField("donation_count", value.getDonations() == null? 0 : value.getDonations().size());
		gen.writeBooleanField("is_public", value.isPublic());
		gen.writeObjectField("created", value.getCreated());
		gen.writeObjectField("updated", value.getUpdated());
		gen.writeNumberField("version", value.getVersion());
		gen.writeEndObject();
	}
	
}
