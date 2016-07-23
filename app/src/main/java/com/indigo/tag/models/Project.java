package com.indigo.tag.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Shane on 7/22/2016.
 */
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@IgnoreExtraProperties
public class Project implements Serializable {
    private String name;
    private String description;
    private List<Location> locations;
}
