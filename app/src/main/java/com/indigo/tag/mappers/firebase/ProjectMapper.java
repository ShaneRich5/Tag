package com.indigo.tag.mappers.firebase;

import java.util.Map;

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
public class ProjectMapper {
    private String name;
    private Map<String, String> locations;
}
