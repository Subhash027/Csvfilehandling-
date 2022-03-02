package comcsvfilehandling.movieshelper;

import comcsvfilehandling.model.Movies;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoviesxlsHelper {
    public static String EXCELTYPE="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String[] excelHeader={"id","title","actor"};
   static String SHEET="movies1";

    public static boolean excelFormatCheck(MultipartFile file)
    {
        if(EXCELTYPE.equals(file.getContentType()))
        {
            return true;
        }
        return false;
    }
    public static ByteArrayInputStream movieToExcel(List<Movies> movies)
    {

        try(
        Workbook workbook=new XSSFWorkbook();
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow=sheet.createRow(0);
            for(int col=0;col<excelHeader.length;col++)
            {
                Cell cell=headerRow.createCell(col);
                cell.setCellValue(excelHeader[col]);
            }
            int rowIdx = 1;
            for (Movies movies1 : movies) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(movies1.getId());
                row.createCell(1).setCellValue(movies1.getTitleName());
                row.createCell(2).setCellValue(movies1.getDirector());
                row.createCell(3).setCellValue(movies1.getActorName());
                row.createCell(4).setCellValue(movies1.getDescription());
                row.createCell(5).setCellValue(movies1.getRating());
                row.createCell(6).setCellValue(movies1.getRating());

            }
            workbook.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        }catch (IOException e){
            throw new RuntimeException("fail to import to xsl"+e.getMessage());
        }
    }

    public static List<Movies> excelToMovies(InputStream inputStream) {

            List<Movies> movies=new ArrayList<>();
            try{
            XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
            XSSFSheet workbookSheet=workbook.getSheetAt(0);

            Iterator<Row> iterator = workbookSheet.iterator();



            int rowNumber = 0;
            while (iterator.hasNext())
            {
                Row currentRow = iterator.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                Movies movies1 = new Movies();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    switch (cellIdx) {
                        case 0:
                            movies1.setId((long) currentCell.getNumericCellValue());
                            break;

                        case 1:
                            movies1.setTitleName(currentCell.getStringCellValue());
                            break;

                        case 2:
                            movies1.setDirector(currentCell.getStringCellValue());
                            break;

                        case 3:
                            movies1.setActorName(currentCell.getStringCellValue());
                            break;
                        case 4:
                            movies1.setYear((int) currentCell.getNumericCellValue());
                            break;
                        case 5:
                            movies1.setDescription(currentCell.getStringCellValue());
                            break;
                        case 6:
                            movies1.setRating( currentCell.getNumericCellValue());
                            break;
                        case 7:
                            movies1.setGenre(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }

                    cellIdx++;
                }

                movies.add(movies1);
            }

            return movies;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
