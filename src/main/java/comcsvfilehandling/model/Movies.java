package comcsvfilehandling.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "moviescollection")
public class Movies {
    @Id
    private Long id;

    private String titleName;

    private String actorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public Movies(Long id, String titleName, String actorName) {
        this.id = id;
        this.titleName = titleName;
        this.actorName = actorName;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "id=" + id +
                ", titleName='" + titleName + '\'' +
                ", actorName='" + actorName + '\'' +
                '}';
    }
    public Movies()
    {

    }
}
