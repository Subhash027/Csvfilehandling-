package comcsvfilehandling.service;

import comcsvfilehandling.model.Movies;
import comcsvfilehandling.movieshelper.MoviesCsvHelper;
import comcsvfilehandling.movieshelper.MoviesxlsHelper;
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

    public void saveCsv(MultipartFile file) {
        try {

            List<Movies> tutorials = MoviesCsvHelper.csvToMovies(file.getInputStream());
            repository.saveAll(tutorials);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }


    public ByteArrayInputStream loadcsv() {
        List<Movies> movies = repository.findAll();

        ByteArrayInputStream in = MoviesCsvHelper.moviesTocsv(movies);
        return in;
    }
    public ByteArrayInputStream loadExcel()
    {
        List<Movies> movies=repository.findAll();
        ByteArrayInputStream inputStream=MoviesxlsHelper.movieToExcel(movies);
        return inputStream;
    }

    public List<Movies> getAllMovies() {
        return repository.findAll();
    }

    public void saveExcel(MultipartFile file) {

            try {
                List<Movies> movies = MoviesxlsHelper.excelToMovies(file.getInputStream());
                repository.saveAll(movies);
            } catch (IOException e) {
                throw new RuntimeException("fail to store excel data: " + e.getMessage());
            }

    }

}

