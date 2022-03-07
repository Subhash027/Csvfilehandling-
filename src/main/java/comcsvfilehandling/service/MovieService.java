package comcsvfilehandling.service;
import comcsvfilehandling.exception.NoContantException;
import comcsvfilehandling.exception.ResponeDetails;
import comcsvfilehandling.model.Movies;
import comcsvfilehandling.movieshelper.CsvHelper;
import comcsvfilehandling.movieshelper.ExcelHelper;
import comcsvfilehandling.repository.MoviesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static comcsvfilehandling.constant.StringConstant.*;

@Service
public class MovieService
{
    Logger logger= LoggerFactory.getLogger(MovieService.class);
    @Autowired
    MoviesRepository repository;

    //save method
    public void  saveCsv(MultipartFile file) {
        try
        {
            List<Movies> moviesList = CsvHelper.csvToMovies(file.getInputStream());
            if(moviesList.isEmpty())
            {
                throw new NoContantException("there is no contant in the file!");
            }
            repository.saveAll(moviesList);
        }
        catch (IOException e)
        {
             new RuntimeException(FAIL + e.getMessage());
        }
    }



    public ByteArrayInputStream loadcsv() {
        List<Movies> movies = repository.findAll();

        ByteArrayInputStream in = CsvHelper.moviesTocsv(movies);
        return in;
    }
    public ByteArrayInputStream loadExcel()
    {
        List<Movies> movies=repository.findAll();
        ByteArrayInputStream inputStream= ExcelHelper.movieToExcel(movies);
        return inputStream;
    }

    //get all movies list
    public List<Movies> getAllMovies()
    {
        return repository.findAll();
    }

    //save excel file
    public void saveExcel(MultipartFile file)
    {
        try
        {
                List<Movies> movies = ExcelHelper.excelToMovies(file.getInputStream());
                if(movies.isEmpty())
                {
                    throw new NoContantException("there is no contant in the file!");
                }
                repository.saveAll(movies);
        } catch (IOException e)
        {
            new ResponeDetails(LocalDateTime.now(),"fail to store data:"+e.getMessage(),"", HttpStatus.NO_CONTENT);
        }

    }


}

