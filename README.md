# JavaMySQL
Simple Class to establish mysql-connection and execute dml/dql queries

Usage dql:
```
import java.util.HashMap;
import java.util.Map;

import resource.*;

public class Main {

	public static void main(String[] args) {
		Map<Integer, Map<Object, Object>> arrData = new HashMap<>();
		Database objDatabase = new Database();
		String strSQL = "SELECT * FROM Einheit";
		arrData = objDatabase.dql(strSQL);
	}
}
```
Usage dml (please use prepared statements!):
```
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		Database objDatabase = new Database();
		String strSQL = "INSERT INTO yourdb (yourattribute) VALUES (?)";
	
		try{
			Connection objCon = objDatabase.getObjCon();
			PreparedStatement objPreparedStm = objCon.prepareStatement(strSQL);
			objPreparedStm.setString(1, "yourvalue");
			objPreparedStm.execute();
		}catch (SQLException objSQLEx) {
			System.out.println(objSQLEx.getMessage());
		}
	}
}
```

