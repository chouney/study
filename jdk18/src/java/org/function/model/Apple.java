package org.function.model;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2020/3/18
 */
public class Apple {
    private Integer weight;
    private String color;

    public Apple(Integer weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
