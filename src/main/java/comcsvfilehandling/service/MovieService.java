package comcsvfilehandling.service;

import comcsvfilehandling.model.Movies;
import comcsvfilehandling.movieshelper.MoviesCsvHelper;
import comcsvfilehandling.repository.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    MoviesRepository repository;

    public void save(MultipartFile file) {
        try {
            List<Movies> tutorials = MoviesCsvHelper.csvToMovies(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<Movies> movies = repository.findAll();

        ByteArrayInputStream in = MoviesCsvHelper.moviesTocsv(movies);
        return in;
    }

    public List<Movies> getAllMovies() {
        return repository.findAll();
    }
}

