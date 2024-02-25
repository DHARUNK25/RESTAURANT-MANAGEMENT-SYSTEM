package com.FeedbackManagement;

public class Feedback {
    private String review;
    private int rating;
    private int customerId;
    public Feedback(String review, int rating, int customerId) {
        this.review = review;
        this.rating = rating;
        this.customerId = customerId;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    @Override
    public String toString() {
        return "Feedback{" +
                "review='" + review + '\'' +
                ", rating=" + rating +
                ", customerId=" + customerId +
                '}';
    }
}

