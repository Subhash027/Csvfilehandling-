package comcsvfilehandling.dao;
import comcsvfilehandling.exception.ActorNotFoundException;
import comcsvfilehandling.exception.GenreNotFoundException;
import comcsvfilehandling.exception.YearNotFoundException;
import comcsvfilehandling.model.Movies;

import java.util.List;

public interface MoviesDAO {

    public List<Movies> findBYYear(Integer year)throws YearNotFoundException;

    public List<Movies> findByActor(String actor)throws ActorNotFoundException;

    public List<Movies> findByGenre(String genre)throws GenreNotFoundException;



}
