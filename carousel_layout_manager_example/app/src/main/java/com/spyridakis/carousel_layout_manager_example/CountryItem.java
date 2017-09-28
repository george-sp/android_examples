package com.spyridakis.carousel_layout_manager_example;

public class CountryItem {

    private String name;
    private int drawableId;

    public CountryItem(String name, int drawableId) {
        this.name = name;
        this.drawableId = drawableId;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
