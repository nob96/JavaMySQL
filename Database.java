import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class Database {
	private int intPort = 3306;
	private String strHost = "localhost";
	private String strDatabase = "yourdb";
	private String strPasswd = "yourpasswd";
	private String strUsername = "youruser";
	private String strUrl;
	private Connection objCon;

	public Database(){
		this.createConnection();
	}
	
  /*
   * Constructor to overwrite datamembers
   */
	public Database(String strHost, String strDatabase, int intPort, String strUsername, String strPasswd) {
		this.strHost = strHost;
		this.strDatabase = strDatabase;
		this.intPort = intPort;
		this.strUsername = strUsername;
		this.strPasswd = strPasswd;
		
		this.createConnection();
	}
	
  /*
   * opens mysql connection
   */
	public boolean createConnection() {
		try{
			this.strUrl = "jdbc:mysql://" + this.strHost + ":" + this.intPort + "/" + this.strDatabase + "?useSSL=false&serverTimezone=UTC";
			this.objCon = DriverManager.getConnection(this.strUrl, this.strUsername, this.strPasswd);
			return true;
			
		}catch (Exception objEx) {
			System.out.println("MySQL-Connection not established: " + objEx.getMessage());
			return false;
		}
	}
	
  /*
   * Executes dql statements and returns an associative array wich contains the result set
   */
	public Map<Integer, Map<Object, Object>> dql(String strQuery) {
		Map<Integer, Map<Object, Object>> arrData = new HashMap<>();
		int intAnzahlRows = 0;
		
		try{
			Statement objStatement = this.objCon.createStatement();
			ResultSet objResultSet = objStatement.executeQuery(strQuery);
			
			while(objResultSet.next()) {
				Map<Object, Object> arrTmp = new HashMap<Object, Object>();
				intAnzahlRows++;
				
				for(int i = 1; i < objResultSet.getMetaData().getColumnCount() + 1; i++) {
					arrTmp.put(objResultSet.getMetaData().getColumnName(i), objResultSet.getObject(i));
				}
				
				arrData.put(intAnzahlRows, arrTmp);
			}
			
			return arrData;
			
		}catch (Exception objEx) {
			System.out.println("Query error: " + objEx.getMessage());
			return null;
		}
	}
	
  /*
   * Executes dml statement
   */
	public int dml(String strDML) {
		try {
			Statement objStatement = this.objCon.createStatement();
			return objStatement.executeUpdate(strDML);
		}catch (SQLException objSQLEx) {
			System.out.println("DML failed: " + objSQLEx.getMessage());
			return 0;
		}
	}
	
  /*
   * Returns connection object
   */
	public Connection getObjCon() {
		return objCon;
	}
}
