package euro_24.prize.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private ModelMapper modelMapper;

    @Bean
    public ModelMapper modelMapper(){
        if (this.modelMapper == null) {
            ModelMapper localModelMapper = new ModelMapper();
            localModelMapper.getConfiguration()
                    .setMatchingStrategy(MatchingStrategies.STRICT)
                    .setFieldMatchingEnabled(true)
                    .setFieldAccessLevel( org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
            this.modelMapper = localModelMapper;
        }
        return this.modelMapper;
    }


    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule ());
        return objectMapper;
    }
}
