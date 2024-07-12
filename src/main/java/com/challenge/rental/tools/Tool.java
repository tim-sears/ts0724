package com.challenge.rental.tools;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tool {
    private String toolCode;
    private ToolType toolType;
    private String brand;
}
