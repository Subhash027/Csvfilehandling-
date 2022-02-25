package comcsvfilehandling.controller;

import com.google.common.io.Files;
import comcsvfilehandling.movieshelper.MoviesCsvHelper;
import comcsvfilehandling.movieshelper.MoviesxlsHelper;
import comcsvfilehandling.response.ResponseMessage;
import comcsvfilehandling.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import comcsvfilehandling.stringconstant.StringConstant.*;

import static comcsvfilehandling.stringconstant.StringConstant.*;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

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
