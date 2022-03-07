package comcsvfilehandling.dao;
import comcsvfilehandling.exception.ActorNotFoundException;
import comcsvfilehandling.exception.GenreNotFoundException;
import comcsvfilehandling.exception.YearNotFoundException;
import comcsvfilehandling.model.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MoviesDAOImpl implements MoviesDAO{

    @Autowired
    JdbcTemplate jdbcTemplate;
    //querys
    String findByYear="SELECT*FROM moviescollection WHERE YEAR=?";
    String findByActor="SELECT*FROM moviescollection WHERE actor_name=?";
    String findBygenre="SELECT*FROM moviescollection WHERE genre=?";


    @Override
    public List<Movies> findBYYear(Integer year) {
        List<Movies> movies=jdbcTemplate.query(findByYear,new BeanPropertyRowMapper<Movies>(Movies.class),year);
        if(movies.isEmpty())
        {
            throw new YearNotFoundException("Year not found in moviecollection");
        }
        return movies;
    }

    @Override
    public List<Movies> findByActor(String actor) {
        List<Movies> movies=jdbcTemplate.query(findByActor,new BeanPropertyRowMapper<Movies>(Movies.class),actor);
        if(movies.isEmpty())
        {
            throw new ActorNotFoundException("Actor not found in moviecollection");
        }
        return movies;


    }

    @Override
    public List<Movies> findByGenre(String genre) {
        List<Movies> movies=jdbcTemplate.query(findBygenre,new BeanPropertyRowMapper<Movies>(Movies.class),genre);
        if(movies.isEmpty())
        {
            throw new GenreNotFoundException("Genre not found in moviecollection");
        }
        return movies;
    }
}
