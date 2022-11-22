package ru.rusyaevmax.digitalLibrary2Boot.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Имя книги не должно быть пустым")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Имя книги не должно быть пустым")
    @Column(name = "author")
    private String author;

    @NotNull(message = "Имя книги не должно быть пустым")
    @Column(name = "year", nullable = false)
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person owner;
}
