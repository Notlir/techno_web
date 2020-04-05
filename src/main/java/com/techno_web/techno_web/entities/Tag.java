package com.techno_web.techno_web.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;

@Entity
@Table
public class Tag {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id ;

    @NotNull
    @Column
    private String tag_title;

    public String getTagTitle() {
        return tag_title;
    }

    public void setTagTitle(String tag_title) {
        this.tag_title = tag_title;
    }
}
