package com.challenge.rental.tools;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ToolRepository {
    // This class would be a front for a DB in a real system. Here I'm mocking it out using a map for simplicity

    public static final String CHNS = "CHNS";
    public static final String LADW = "LADW";
    public static final String JAKD = "JAKD";
    public static final String JAKR = "JAKR";
    public static final String BRAND_STIHL = "Stihl";
    public static final String BRAND_WERNER = "Werner";
    public static final String BRAND_DEWALT = "DeWalt";
    public static final String BRAND_RIGID = "Rigid";

    private final Map<String, Tool> tools = Map.ofEntries(
            Map.entry(CHNS, Tool.builder().toolCode(CHNS).toolType(ToolType.CHAINSAW).brand(BRAND_STIHL).build()),
            Map.entry(LADW, Tool.builder().toolCode(LADW).toolType(ToolType.LADDER).brand(BRAND_WERNER).build()),
            Map.entry(JAKD, Tool.builder().toolCode(JAKD).toolType(ToolType.JACKHAMMER).brand(BRAND_DEWALT).build()),
            Map.entry(JAKR, Tool.builder().toolCode(JAKR).toolType(ToolType.JACKHAMMER).brand(BRAND_RIGID).build())
    );

    public Tool getToolByToolCode(String toolCode) {
        return tools.get(toolCode);
    }
}
