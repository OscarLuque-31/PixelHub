package utils;

import java.util.List;

import models.Game;

public class ApiResponse {
    private int count;
    private String next;
    private String previous;
    private List<Game> results;

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getNext() {
        return next;
    }
    public void setNext(String next) {
        this.next = next;
    }
    public String getPrevious() {
        return previous;
    }
    public void setPrevious(String previous) {
        this.previous = previous;
    }
    public List<Game> getResults() {
        return results;
    }
    public void setResults(List<Game> results) {
        this.results = results;
    }
}
