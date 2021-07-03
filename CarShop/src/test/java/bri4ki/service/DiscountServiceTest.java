package bri4ki.service;

import bri4ki.helper.Helpers;
import bri4ki.model.dto.AddDiscountRequestDTO;
import bri4ki.model.pojo.Discount;
import bri4ki.model.repository.DiscountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static bri4ki.helper.Helpers.createMockDiscount;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceTest {

    @Mock
    private DiscountRepository repository;
    @InjectMocks
    private DiscountService service;


    @Test
    public void getById_Should_Return_Correct_Discount() {
        Mockito.when(repository.findById(1))
                .thenReturn(java.util.Optional.of(createMockDiscount()));

        Discount result = service.getDiscount(1);

        Assertions.assertEquals(1, result.getId());
    }

    @Test
    public void getAllDiscounts_Should_Return_Correct() {
        List<Discount> result = service.getAll();
        result.add(createMockDiscount());

        verify(repository, Mockito.timeout(1)).findAll();
    }


}
