package com.model;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2020/7/15
 */
public class Order {

    private int amount;

    private int score;

    public Order(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
