package com.elpidoroun.financialportfolio.controller.delegate;

import com.elpidoroun.financialportfolio.exceptions.CustomExceptionHandler;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.security.user.Permissions;
import com.elpidoroun.financialportfolio.service.cache.RedisCacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
@Testcontainers
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class SpringTestsHelper {

    @Autowired private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected RedisTemplate<String, ExpenseCategory> redisTemplate;

    @MockBean
    private HashOperations<String, String, ExpenseCategory> hashOperations;

    @MockBean
    private RedisCacheService redisCacheService;

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @DynamicPropertySource
    public static void initKafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    }

    @BeforeEach
    public void setUp() {
        Mockito.<HashOperations<String, String, ExpenseCategory>>when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public <T> T extractResponse(MvcResult result, Class<T> clazz) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(), clazz);
    }

    public <T> ArrayList<T> extractToList(MvcResult mvcResult, Class<T> clazz) throws Exception {
        String content = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(content, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
    }

    public void assertErrorResponse(MvcResult result, String message, String errorType) throws Exception {
        var errorResponse =  objectMapper.readValue(result.getResponse().getContentAsString(), CustomExceptionHandler.ErrorResponse.class);

        assertThat(errorResponse.getMessage()).isEqualTo(message);
        assertThat(errorResponse.getErrorType()).isEqualTo(errorType);
    }


    public MockMvc mockMvc() { return mockMvc; }

    public String generateAdminToken(){
        return generateToken("test_admin@gmail.com");
    }

    public String generateUserToken(){
        return generateToken("test_user@gmail.com");
    }


    private String generateToken(String user) {
        long expirationTimeInMillis = 3600000; // 1 hour

        List<String> allPermissions = Stream.of(Permissions.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("permissions", allPermissions);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMillis))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}