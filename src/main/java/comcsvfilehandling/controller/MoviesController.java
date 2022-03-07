package comcsvfilehandling.controller;

import com.google.common.io.Files;
import comcsvfilehandling.dao.MoviesDAO;
import comcsvfilehandling.exception.ResponeDetails;
import comcsvfilehandling.model.Movies;
import comcsvfilehandling.movieshelper.CsvHelper;
import comcsvfilehandling.movieshelper.ExcelHelper;
import comcsvfilehandling.service.MovieService;
import io.swagger.v3.core.model.ApiDescription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.poi.ss.usermodel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static comcsvfilehandling.constant.StringConstant.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;



@RestController
public class MoviesController
{
    //USING LOG
    Logger logger= LoggerFactory.getLogger(MoviesController.class);

    @Autowired
    MoviesDAO moviesDAO;
    @Autowired
    MovieService movieService;
    /***
     * UPLOAD CSV AND EXCEL FILE HERE,CONTENT OF FILE IS MOVIES COLLECTION
     * @param file
     * @return
     */
    @SecurityRequirement(name = "Movies")
    @Operation(summary = "CSV Format and Excel Format File", description = "If you want upload file get token from getauth method")
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponeDetails> uploadFile(@RequestParam("file")MultipartFile file)
    {
        String details = fromCurrentContextPath().path("/api/csv/download/").path(file.getOriginalFilename()).toUriString();
        if (CsvHelper.formatCheck(file))
        {
            movieService.saveCsv(file);
            logger.info(UPLOADED_SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponeDetails(LocalDateTime.now(), UPLOADED_SUCCESS + file.getOriginalFilename(), details, HttpStatus.OK));
        }
        else if (ExcelHelper.excelFormatCheck(file))
        {
            movieService.saveExcel(file);
            logger.info(UPLOADED_SUCCESS);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponeDetails(LocalDateTime.now(),UPLOADED_SUCCESS + file.getOriginalFilename(), "",HttpStatus.OK));
        }
        logger.warn("upload csv and excel file");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponeDetails(LocalDateTime.now(),SUPPORTED_ONLY, FILE_FORMATE + Files.getFileExtension(file.getOriginalFilename()),HttpStatus.NOT_IMPLEMENTED));
    }
    /***
     * GETTING LIST OF MOVIES COLLECTOIN
     * @return
     */
    @SecurityRequirement(name = "Movies")
    @GetMapping("/movies")
    @Operation(description = "get token from getauth method to access this method")
    public ResponseEntity<List<Movies>> getALLMovies()
    {
        logger.info("getting all moviesList");
        List<Movies> moviesList = movieService.getAllMovies();
        if (moviesList.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(moviesList, HttpStatus.OK);
    }
    /***
     * GET BY YEAR OF MOVIES IN MOVIES COLLECTON
     * @param year
     * @return
     */
    @SecurityRequirement(name = "Movies")
    @GetMapping(value = "/movies/year/{year}")
    @Operation(description = "get token from getauth method to access this method")
    public List<Movies> getByyear(@PathVariable(value = "year")Integer year)
    {
        logger.info("getting year of movies");
        return moviesDAO.findBYYear(year);
    }
    /***
     * GETTING SPESIFIC LIST OF ACTOR IN MOVIES_COLLECTION
     * @param startwith
     * @return
     */
    @SecurityRequirement(name = "Movies")
    @GetMapping("/movies/actor/{startwith}")
    @Operation(description = "get token from getauth method to access this method")
    public List<Movies> getByActor(@PathVariable(value = "startwith")String startwith)
    {
        return moviesDAO.findByActor(startwith);
    }

    /***
     * GETTING SPESIFIC LIST OF GENRE IN MOVIES_COLLECTION
     * @param type
     * @return
     */
    @SecurityRequirement(name = "Movies")
    @GetMapping("/movies/genre/{type}")
    @Operation(description = "get token from getauth method to access this method")
    public List<Movies> getByGenre(@PathVariable(value = "type")String type)
    {
        return moviesDAO.findByGenre(type);
    }

    /***
     * GETTING  FILE DOCUMENT
     * @return
     */
    @SecurityRequirement(name = "Movies")
    @GetMapping("downloadfile")
    @Operation(description = "get token from getauth method to access this method")
    public ResponseEntity<InputStreamResource> getMovies()
    {
        InputStreamResource File=new InputStreamResource(movieService.loadExcel());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + " ")
                .contentType(MediaType.parseMediaType(MEDIA_TYPE))
                .body(File);
    }

}
