package org.unibl.etf.springlearning.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.unibl.etf.springlearning.models.validators.SqlValidators;
import org.unibl.etf.springlearning.models.validators.XssValidators;

@Data
public class CommentRequest {
    private Integer userByUserIdId;
    @XssValidators
    @SqlValidators
    @NotBlank
    @Size(max=400)
    private String content;
    private Integer categoryByCategoryIdId;


}
