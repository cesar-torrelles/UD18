import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class metodos {
	
	public static String contraseña = solicitarContraseña();
	public static Connection conexion;

	
	
	//METODO para solicitar contraseña al servidor
	public static String solicitarContraseña() {
		
		String contraseña = JOptionPane.showInputDialog("Introduce la contraseña del servidor: ");
		
		return contraseña;
		
		
	}
		//METODO para abrir la conexion con MySQL
		public static void openConnection() {
			
			try {
				
				
				
				Class.forName("com.mysql.cj.jdbc.Driver");
				conexion=DriverManager.getConnection("jdbc:mysql://localhost:3306?useTimezone=true&serverTimezone=UTC","root",contraseña);//credenciales temporales
				System.out.println("Server Connected");
				//añadimos un metodo con la fecha
				fecha();
				
			}catch(SQLException | ClassNotFoundException ex  ){
				System.out.println("No se ha podido conectar con la base de datos");
				//añadimos un metodo con la fecha
				fecha();
				System.out.println(ex.getMessage());
				
			}
			
		}
	
		//METODO para cerrar la conexion con MySQL
		public static void closeConnection() {
			try {
		
				conexion.close();
				System.out.println("Server Disconnected");
				//añadimos un metodo con la fecha
				fecha();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
				System.out.println("Error cerrando conexion");
				//añadimos un metodo con la fecha
				fecha();
			}
		}
	
		
		//METODO para crear la base de datos
		public static void createDB(String name) {
			try {
				String Query="DROP DATABASE IF EXISTS "+ name;
				Statement st= conexion.createStatement();
				st.executeUpdate(Query);
				
				Query="CREATE DATABASE "+ name;
				st= conexion.createStatement();
				st.executeUpdate(Query);
				System.out.println("DB creada con exito!");
				
			JOptionPane.showMessageDialog(null, "Se ha creado la DB " +name+ " de forma exitosa.");
			}catch(SQLException ex) {
				System.out.println(ex.getMessage());
				System.out.println("Error creando la DB.");
			}	
		}

		//METODO para crear tablas en MYSQL
		public static void createTable(String db,String name, String columnas) {
			try {
				String Querydb = "USE "+db+";";
				Statement stdb= conexion.createStatement();
				stdb.executeUpdate(Querydb);
				
				//creamos un try-catch mas, para avisar de error por sintaxis del codigo 
				try {
				String Query = "CREATE TABLE " + name + columnas;
				Statement st= conexion.createStatement();
				st.executeUpdate(Query);
				}catch(SQLException ex) {
					System.out.println(ex.getMessage());
					System.out.println("Error de sintaxis del codigo MySQL pasado por cadena.");
				}
				System.out.println("Tabla creada con exito!");
				
			}catch (SQLException ex){
				System.out.println(ex.getMessage());
				System.out.println("Error creando tabla.");
				
			}
			
		}
		
		//METODO para insertar datos en una tabla MYSQL
		public static void insertData(String db, String table_name, String datos) {
			try {
				String Querydb = "USE "+db+";";
				Statement stdb= conexion.createStatement();
				stdb.executeUpdate(Querydb);
							
				String Query = "INSERT INTO " + table_name + " " + datos;
				Statement st = conexion.createStatement();
				st.executeUpdate(Query);
				
				System.out.println("Datos almacenados correctamente");
				
			} catch (SQLException ex ) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en el almacenamiento");
			}
						
		}
		
		//METODO para obtener los valores de  MYSQL
		public static void getValues(String db, String table_name, int numColumnas) {
			try {
				String Querydb = "USE "+db+";";
				Statement stdb= conexion.createStatement();
				stdb.executeUpdate(Querydb);
							
				String Query = "SELECT * FROM " + table_name;
				Statement st = conexion.createStatement();
				java.sql.ResultSet resultSet;
				resultSet = st.executeQuery(Query);
				
				while (resultSet.next()) {
					System.out.println("");
					for (int j = 1; j <= numColumnas; j++)
						System.out.print(resultSet.getString(j) + " ");
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
				System.out.println("Error en la adquisicion de datos");
			}
		
		}
		 
		//METODO para limpiar las tablas en MYSQL
		public static void deleteRecord(String db, String table_name, String ID) {
			try {
				String Querydb = "USE "+db+";";
				Statement stdb= conexion.createStatement();
				stdb.executeUpdate(Querydb);
							
				String Query = "DELETE FROM " + table_name + " WHERE ID = \"" + ID + "\"";
				Statement st = conexion.createStatement();
				st.executeUpdate(Query);
				
				System.out.println("Registros de tabla ELIMINADOS con exito!");
							
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error borrando el registro especificado");
			}
		}	

		
		//METODO para eliminar tablas en MYSQL
		public static void deleteTable(String db, String table_name) {
			try {
				String Querydb = "USE "+db+";";
				Statement stdb= conexion.createStatement();
				stdb.executeUpdate(Querydb);
							
				String Query = "DROP TABLE " + table_name + ";";
				Statement st = conexion.createStatement();
				st.executeUpdate(Query);
				
				System.out.println("TABLA ELIMINADA con exito!");
							
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error borrando la tabla");
			}
		}	
		
		
		//METODO para enseñar la  FECHA
		public static void fecha() {
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
			System.out.println(" Date: " + hourdateFormat.format(date));
			}
}
