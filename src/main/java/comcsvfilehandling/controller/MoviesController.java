package comcsvfilehandling.controller;

import com.google.common.io.Files;
import comcsvfilehandling.model.Movies;
import comcsvfilehandling.movieshelper.MoviesCsvHelper;
import comcsvfilehandling.movieshelper.MoviesxlsHelper;
import comcsvfilehandling.response.ResponseMessage;
import comcsvfilehandling.service.MovieService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static comcsvfilehandling.stringconstant.StringConstant.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;



@RestController
public class MoviesController {
    Logger logger= LoggerFactory.getLogger(MoviesController.class);



    @Autowired
    MovieService movieService;



    @SecurityRequirement(name = "Movies")
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file)
    {
        String message="";
        if(MoviesCsvHelper.formatCheck(file))
        {
            try {
                movieService.saveCsv(file);
                message=UPLOADED_SUCCESS+file.getOriginalFilename();
                logger.info(UPLOADED_SUCCESS);

                         String fileUri = fromCurrentContextPath()
                        .path("/api/csv/download/")
                        .path(file.getOriginalFilename())
                        .toUriString();

                         return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileUri));
                }catch (Exception e)
                {
                logger.info(File_NotUpload);
                message=File_NotUpload+file.getOriginalFilename()+"!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
                }
        }
        else if(MoviesxlsHelper.excelFormatCheck(file))
        {
            try{
                movieService.saveExcel(file);
                message=UPLOADED_SUCCESS+file.getOriginalFilename();
                logger.info(UPLOADED_SUCCESS);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,""));
               }catch (Exception e)
              {
                logger.info(File_NotUpload+e.getMessage());
                message=File_NotUpload+file.getOriginalFilename();
                String fileUri= String.valueOf(fromCurrentContextPath().path(file.getOriginalFilename()));
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessage(message,fileUri));
              }
        }
        logger.warn("upload csv and excel file ");
        message = SUPPORTED_ONLY;
        String fileUri;
        fileUri = "YOUR's -------->"+Files.getFileExtension(file.getOriginalFilename());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage(message,fileUri));

    }
    @SecurityRequirement(name = "Movies")
    @GetMapping("/movies")
    public ResponseEntity<List<Movies>> getALLMovies(){
        try {
            List<Movies> moviesList = movieService.getAllMovies();
            if (moviesList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(moviesList, HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
        }
    }
    @GetMapping("/movies/year/{year}")
    public List<Movies> getByyear(@PathVariable(value = "year")Integer year)
    {

        try {
            logger.info("movies release year" + year);
            List<Movies> moviesYear = movieService.getAllMovies();
            List<Movies> getByYear=new ArrayList<>();
             if (moviesYear.stream().anyMatch(years->years.getYear()>=year))
            {
                getByYear= moviesYear.stream()
                        .filter(years -> years.getYear().equals(year))
                        .collect(Collectors.toList());
                logger.info("year of list "+year+""+getByYear);
                return getByYear;
            }
            return (List<Movies>) new ResponseEntity<Movies>(HttpStatus.OK);

        } catch (Exception e) {
            logger.error("file not found");
            throw new RuntimeException("fail to get year"+e.getMessage());
        }
    }
    @SecurityRequirement(name = "Movies")
    @GetMapping("/movies/actor/{startwith}")
    public List<Movies> getByActor(@PathVariable(value = "startwith")String startwith)
    {
        try {
            logger.info("sorted by actor name");
            List<Movies> moviesList = movieService.getAllMovies();
            List<Movies> actorByName = new ArrayList<>();
            actorByName= moviesList.stream().filter(Name->Name.getActorName().toLowerCase().startsWith(startwith)).collect(Collectors.toList());
            return actorByName;
        }catch(Exception e)
        {
            throw new RuntimeException("getting some issue");
        }
    }
    @SecurityRequirement(name = "Movies")
    @GetMapping("downloadfile")
    public ResponseEntity<InputStreamResource> getMovies()
    {
        InputStreamResource File=new InputStreamResource(movieService.loadExcel());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + " ")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(File);
    }








}
