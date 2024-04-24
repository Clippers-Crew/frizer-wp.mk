package mk.frizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Salon;
import mk.frizer.model.Tag;
import mk.frizer.utilities.BusinessOwnerSerializer;
import mk.frizer.utilities.SalonSerializer;
import mk.frizer.utilities.TagSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FrizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrizerApplication.class, args);
	}

	@Bean
	public ObjectMapper customObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addSerializer(BusinessOwner.class, new BusinessOwnerSerializer());
		module.addSerializer(Salon.class, new SalonSerializer());
		module.addSerializer(Tag.class, new TagSerializer());
		objectMapper.registerModule(module);

		objectMapper.registerModule(new ParameterNamesModule())
					.registerModule(new Jdk8Module())
					.registerModule(new JavaTimeModule());

		return objectMapper;
	}
}
