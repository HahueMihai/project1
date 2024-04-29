package com.example.project.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Integer id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String address;

    @ManyToMany(mappedBy = "participants")
    private List<Event> eventList;
}
