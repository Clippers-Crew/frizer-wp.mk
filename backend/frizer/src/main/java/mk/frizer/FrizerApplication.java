package mk.frizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import mk.frizer.model.BusinessOwner;
import mk.frizer.model.Salon;
import mk.frizer.utilities.BusinessOwnerSerializer;
import mk.frizer.utilities.SalonSerializer;
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
		objectMapper.registerModule(module);
		return objectMapper;
	}
}
