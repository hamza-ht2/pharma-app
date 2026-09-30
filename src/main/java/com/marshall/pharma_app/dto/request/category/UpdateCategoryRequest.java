package com.marshall.pharma_app.dto.request.category;

import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateCategoryRequest {
    private String name;
    private String description;
    private String nameFr;
    private String nameAr;
    private UUID parentCategoryId;

    @Min(value = 0, message = "display order must be equal or greater than 0")
    private Integer displayOrder;

    private Boolean active;
}