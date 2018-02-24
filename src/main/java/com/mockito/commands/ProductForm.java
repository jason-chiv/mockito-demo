package com.mockito.commands;

import lombok.Data;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by Vetty on 2/20/18.
 */
@Data
public class ProductForm {
    private Long id;
    @NotBlank
    private String description;
    private String khDescription;
    private BigDecimal price;
    @URL
    private String imageUrl;
}
