package utils;

import java.util.List;

import models.Game.Screenshot;

public class ScreenshotApiResponse {
    private List<Screenshot> results;

    public List<Screenshot> getResults() {
        return results;
    }

    public void setResults(List<Screenshot> results) {
        this.results = results;
    }
}
