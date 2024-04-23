package mk.frizer.utilities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mk.frizer.model.Salon;

import java.io.IOException;

public class SalonSerializer extends JsonSerializer<Salon> {

//    "id": 3,
//    "name": "Frizerski salon Asim",
//    "description": "Frizerski salon za mazhi",
//    "location": "veles",
//    "phoneNumber": "broj3",
//    "employees": [],
//    "salonTreatments": []

    @Override
    public void serialize(Salon salon, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", salon.getId());
        jsonGenerator.writeStringField("name", salon.getName());
        jsonGenerator.writeStringField("description", salon.getDescription());
        jsonGenerator.writeStringField("location", salon.getLocation());
        jsonGenerator.writeStringField("phoneNumber", salon.getPhoneNumber());
        jsonGenerator.writeNumberField("owner", salon.getOwner().getId());
        //TODO salon treatments and employees
        jsonGenerator.writeEndObject();
    }
}

