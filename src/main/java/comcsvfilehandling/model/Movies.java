package comcsvfilehandling.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Entity
@Table(name = "moviescollection")
public class Movies {
    @Id
    private Long id;

    private String titleName;

    private String Director;

    private String actorName;

    private Integer year;

    private String Description;

    private Double Rating;

    private String Genre;

    public Movies(Long id, String titleName, String director, String actorName, Integer year, String description, Double rating, String genre) {
        this.id = id;
        this.titleName = titleName;
        Director = director;
        this.actorName = actorName;
        this.year = year;
        Description = description;
        Rating = rating;
        Genre = genre;
    }
    public Movies()
    {

    }


}
