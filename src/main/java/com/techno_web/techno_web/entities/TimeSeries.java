package com.techno_web.techno_web.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class TimeSeries {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id ;

    @NotNull
    @Column
    private String title;

    @Column
    private String comments;

    @NotNull
    @Column
    private Calendar creation_date;

    @OneToMany
    private List<Event> eventList = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Calendar getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Calendar creation_date) {
        this.creation_date = creation_date;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
}
