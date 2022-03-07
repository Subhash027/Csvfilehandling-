package comcsvfilehandling.movieshelper;

import comcsvfilehandling.model.Movies;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

//import static comcsvfilehandling.movieshelper.MoviesxlsHelper.SHEET;

public class CsvHelper {

    public static String FileFORMAT = "text/csv";
    static String[] HEADERs = {"id", "title", "actor"};

    public static boolean formatCheck(MultipartFile file)
    {
        Logger logger = Logger.getLogger(CsvHelper.class.getName());
        logger.info("checking formate");
        if (FileFORMAT.equals(file.getContentType()))
        {
            return true;
        }

        return false;
    }
    public static List<Movies> csvToMovies(InputStream inputStream)
    {
        Logger logger = Logger.getLogger(CsvHelper.class.getName());
        logger.info("dumping data of csv file int database");

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
             List<Movies> moviesList = new ArrayList<>();
             Iterable<CSVRecord> csvRecords = csvParser.getRecords();
             logger.info("iterator in csvrecords");
             for (CSVRecord csvRecord : csvRecords)
             {
                Movies movies = new Movies(Long.parseLong(csvRecord.get("id")), csvRecord.get("title"),
                        csvRecord.get("director"),csvRecord.get("actor"),Integer.parseInt(csvRecord.get("year")),
                        csvRecord.get("description"),Double.parseDouble(csvRecord.get("rating")),csvRecord.get("genre"));
                moviesList.add(movies);
             }
            logger.info("save all movies list in movieslist");
            return moviesList;
            } catch (IOException e)
            {
            throw new RuntimeException("fail to parse csv file:" + e.getMessage());
            }

    }

    public static ByteArrayInputStream moviesTocsv(List<Movies> moviesList)
    {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), format);)
        {
            for (Movies movies : moviesList)
            {
                List<String> data = Arrays.asList(String.valueOf(movies.getId()),
                        movies.getTitleName(), movies.getActorName());
                csvPrinter.printRecord(data);
            }
            csvPrinter.flush();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to csv file:" + e.getMessage());
        }
    }




}


