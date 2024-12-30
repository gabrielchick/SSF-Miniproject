package gabriel.investmentportfolio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

  @Value("${redis.db.host}")
  private String dbHost;

  @Value("${redis.db.port}")
  private Integer dbPort;

  @Value("${redis.db.database}")
  private Integer dbNumber;

  @Value("${redis.db.username}")
  private String dbUsername;

  @Value("${redis.db.password}")
  private String dbPassword;

  @Bean("redis")
  public RedisTemplate<String, Object> createRedisTemplate() {

    final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(dbHost, dbPort);
    config.setDatabase(dbNumber);
    if (!dbUsername.trim().equals("")) {
      config.setUsername(dbUsername);
      config.setPassword(dbPassword);
    }
    final JedisClientConfiguration jedisClient = JedisClientConfiguration
        .builder().build();

    final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient);
    jedisFac.afterPropertiesSet();

    final RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisFac);
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());

    // Use Jackson2JsonRedisSerializer for values
    ObjectMapper objectMapper = new ObjectMapper();
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

    // Set the value serializer and hash value serializer
    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.setHashValueSerializer(jackson2JsonRedisSerializer);

    return template;
  }
  
  @Bean
  public RestTemplate restTemplate() {
      return new RestTemplate(); // Creates a RestTemplate instance
  }
}
