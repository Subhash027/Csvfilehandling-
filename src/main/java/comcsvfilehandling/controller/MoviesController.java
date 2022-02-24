package comcsvfilehandling.controller;

import comcsvfilehandling.model.Movies;
import comcsvfilehandling.movieshelper.MoviesCsvHelper;
import comcsvfilehandling.movieshelper.MoviesxlsHelper;
import comcsvfilehandling.response.ResponseMessage;
import comcsvfilehandling.service.MovieService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/csv")
public class MoviesController {
    Logger logger= LoggerFactory.getLogger(MoviesController.class);


    @Autowired
    MovieService movieService;


    @PostMapping(value = "/file/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file")MultipartFile file)
    {
        String message="";
        if(MoviesCsvHelper.formatCheck(file))
        {
            try {
                movieService.saveCsv(file);
                message="upload the file successfully:"+file.getOriginalFilename();
                logger.info("upload successfully");

                String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/api/csv/download/")
                        .path(file.getOriginalFilename())
                        .toUriString();

                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileUri));
            }catch (Exception e)
            {
                logger.info("file unsupported ");
                message="Cloud not upload the file:"+file.getOriginalFilename()+"!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
            }
        }
        else if(MoviesxlsHelper.excelFormatCheck(file))
        {
            try{
                movieService.saveExcel(file);
                message="excel file upload successfully:"+file.getOriginalFilename();
                logger.info("upload your excel in the database");
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,""));
            }catch (Exception e)
            {
                logger.info("unsupport file "+e.getMessage());
                message="Cloud not upload your file:"+file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseMessage(message,""));
            }
        }
        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));

    }
//    @GetMapping("/movies")
//    public ResponseEntity<List<Movies>> getALLMovies(){
//        try {
//            List<Movies> moviesList = movieService.getAllMovies();
//            if (moviesList.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(moviesList, HttpStatus.OK);
//        }catch (Exception e)
//        {
//            return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
//        }
//    }






}
