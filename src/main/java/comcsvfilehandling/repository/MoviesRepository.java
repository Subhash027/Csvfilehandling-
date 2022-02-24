package comcsvfilehandling.repository;

import comcsvfilehandling.model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movies,Integer> {

    //all methods in jparepository

}
