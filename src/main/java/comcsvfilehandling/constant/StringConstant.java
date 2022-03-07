package comcsvfilehandling.constant;

/***
 * using string to constant
 */
public class StringConstant
{
    //content of string
    public static String File_NotUpload="Cloud not upload the file:";
    public static String SUPPORTED_ONLY="supported format only CSV & EXCEL";
    public static String UPLOADED_SUCCESS="file suceessfully upload";
    public static String FILE_FORMATE="yours file formate=";
    public static String MEDIA_TYPE="application/vnd.ms-excel";
    public static String EXCEL_TYPE="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String[] excelHeader={"id","title","actor"};
    public static String FileFORMAT = "text/csv";
    public static String SHEET="movies1";
    public static String FAIL="fail to store csv data: ";
    //jwt token
    private static final long serialVersionUID = 234234523523L;
    public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60* 1000;
}
