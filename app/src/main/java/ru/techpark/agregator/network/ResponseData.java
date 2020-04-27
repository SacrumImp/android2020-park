package ru.techpark.agregator.network;

import java.util.List;

import ru.techpark.agregator.event.Event;

public class ResponseData {
    public int count;
    public String next;
    public String previous;
    public List<Event> results;

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public List<Event> getEvents() {
        return results;
    }
    public void setEvents(List<Event> events) {
        this.results = events;
    }

    public String getNextPage() {
        return next;
    }
    public void setNextPage(String nextPage) {
        this.next = nextPage;
    }

    public String getPreviousPage() {
        return previous;
    }
    public void setPreviousPage(String previousPage) {
        this.previous = previousPage;
    }

}
