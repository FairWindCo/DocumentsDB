package ua.pp.fairwind.favorid.internalDB.model.proxy;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Сергей on 02.11.2015.
 */
public class MyDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ssz");
        jgen.writeString(formatter.format(value));
    }
}
