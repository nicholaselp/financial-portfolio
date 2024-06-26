package com.elpidoroun.financialportfolio.service.kafka;

import com.elpidoroun.financialportfolio.config.MainTestConfig;
import com.elpidoroun.financialportfolio.model.ExpenseCategory;
import com.elpidoroun.financialportfolio.repository.ImportRequestLineRepository;
import com.elpidoroun.financialportfolio.repository.ImportRequestRepository;
import com.elpidoroun.financialportfolio.service.normalize.ExpenseCategoryNormalizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static com.elpidoroun.financialportfolio.config.RedisCacheConfig.EXPENSE_CATEGORY_CACHE;
import static com.elpidoroun.financialportfolio.factory.ExpenseCategoryTestFactory.createExpenseCategory;
import static com.elpidoroun.financialportfolio.factory.ImportRequestLineTestFactory.createPendingImpReqLine;
import static com.elpidoroun.financialportfolio.factory.ImportRequestTestFactory.createImportRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ImpReqLineCreateProcessorTest {

    private final MainTestConfig mainTestConfig = new MainTestConfig();

    @Mock
    private ExpenseCategoryNormalizer normalizer;

    private ImpReqLineCreateProcessor processor;
    private final ImportRequestRepository importRequestRepository = mainTestConfig.getExpenseTestConfig().getImportRequestRepository();
    private final ImportRequestLineRepository importRequestLineRepository = mainTestConfig.expenseTestConfig.getImportRequestLineRepository();
    private final RedisTemplate<String, ExpenseCategory> redisTemplate = mainTestConfig.getExpenseTestConfig().getExpenseCategoryRedisTemplate();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        processor = new ImpReqLineCreateProcessor(
                mainTestConfig.getExpenseTestConfig().getCreateExpenseService(),
                normalizer,
                importRequestLineRepository,
                mainTestConfig.getExpenseTestConfig().getImportRequestDao());
    }

    @Test
    public void success_process(){
        var expenseCategory = createExpenseCategory("loan");

        when(normalizer.getExpenseCategoryByName(any()))
                .thenReturn(Optional.of(expenseCategory));

        when(redisTemplate.opsForHash().get(EXPENSE_CATEGORY_CACHE,
                expenseCategory.getId().toString()))
                .thenReturn(expenseCategory);

        var impReq = importRequestRepository.save(createImportRequest());

        var impReqLine = createPendingImpReqLine(impReq);

        processor.execute(impReqLine);

        assertThat(importRequestRepository.findById(impReq.getId())).isPresent().hasValueSatisfying(req -> {
            assertThat(req.getTotalNumberOfSuccessRows()).isEqualTo(1L);
        });

        var importRequestLineList = importRequestLineRepository.findAll();
        assertThat(importRequestLineList.size()).isEqualTo(1L);
        assertThat(importRequestLineList.get(0).getExpense()).isPresent();
        assertThat(importRequestLineList.get(0).getId()).isPresent();
        assertThat(importRequestLineList.get(0).getError()).isEmpty();


    }

    @Test
    public void process_failed(){
        {
            var expenseCategory = createExpenseCategory("loan");

            when(normalizer.getExpenseCategoryByName(any()))
                    .thenReturn(Optional.of(expenseCategory));

            var impReq = importRequestRepository.save(createImportRequest());

            var impReqLine = createPendingImpReqLine(impReq);

            processor.execute(impReqLine);

            assertThat(importRequestRepository.findById(impReq.getId())).isPresent().hasValueSatisfying(req -> {
                assertThat(req.getNumberOfFailedRows()).isEqualTo(1L);
            });

            var importRequestLineList = importRequestLineRepository.findAll();
            assertThat(importRequestLineList.size()).isEqualTo(1L);
            assertThat(importRequestLineList.get(0).getExpense()).isEmpty();
            assertThat(importRequestLineList.get(0).getId()).isPresent();
            assertThat(importRequestLineList.get(0).getError()).isPresent();

        }
    }
}
