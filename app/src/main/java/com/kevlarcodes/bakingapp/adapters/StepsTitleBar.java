package com.kevlarcodes.bakingapp.adapters;

public class StepsTitleBar {

    private String titleText;
    private String format = "%d Steps";

    public StepsTitleBar(int count) {
        titleText = String.format(format, count);
    }

    public String getHeader() {
        return titleText;
    }


}
