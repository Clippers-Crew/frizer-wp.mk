package mk.frizer.utilities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mk.frizer.model.Appointment;
import mk.frizer.model.Treatment;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AppointmentSerializer extends JsonSerializer<Appointment> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Override
    public void serialize(Appointment appointment, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", appointment.getId());
        jsonGenerator.writeStringField("dateFrom", appointment.getDateFrom().format(formatter));
        jsonGenerator.writeStringField("dateTo", appointment.getDateTo().format(formatter));
        jsonGenerator.writeNumberField("treatmentId", appointment.getTreatment().getId());
        jsonGenerator.writeNumberField("salonId", appointment.getSalon().getId());
        jsonGenerator.writeNumberField("employeeId", appointment.getEmployee().getId());
        jsonGenerator.writeNumberField("customerId", appointment.getCustomer().getId());
        jsonGenerator.writeBooleanField("attended", appointment.isAttended());
        jsonGenerator.writeEndObject();
    }
}