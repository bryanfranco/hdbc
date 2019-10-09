package testingwithhsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DAO {
	private final DataSource myDataSource;
	
	public DAO(DataSource dataSource) {
		myDataSource = dataSource;
	}

	/**
	 * Renvoie le nom d'un client à partir de son ID
	 * @param id la clé du client à chercher
	 * @return le nom du client (LastName) ou null si pas trouvé
	 * @throws SQLException 
	 */
	public String nameOfCustomer(int id) throws SQLException {
		String result = null;
		
		String sql = "SELECT LastName FROM Customer WHERE ID = ?";
		try (Connection myConnection = myDataSource.getConnection(); 
		     PreparedStatement statement = myConnection.prepareStatement(sql)) {
			statement.setInt(1, id); // On fixe le 1° paramètre de la requête
			try ( ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					// est-ce qu'il y a un résultat ? (pas besoin de "while", 
                                        // il y a au plus un enregistrement)
					// On récupère les champs de l'enregistrement courant
					result = resultSet.getString("LastName");
				}
			}
		}
		// dernière ligne : on renvoie le résultat
		return result;
	}
        
        public void insertProduct(ProductEntity p){
            String sql = "INSERT INTO PRODUCT(id, name, price)"
                    + " VALUES(?,?,?)";
            try(Connection co = myDataSource.getConnection();
                PreparedStatement stm = co.prepareStatement(sql);){
                    stm.setInt(1, p.id);
                    stm.setString(2, p.name);
                    stm.setInt(3, p.price);
                    stm.executeUpdate();
            }catch(SQLException ex){
                System.out.println("ui");
            }
        }
	
        public ProductEntity findProduct(int productID){
            String sql = "SELECT NAME,PURCHASE_COST FROM PRODUCT WHERE ID = ? ";
            int p = 0;
            String n = "";
            try(Connection co = myDataSource.getConnection();
                PreparedStatement stm = co.prepareStatement(sql);){
                
                stm.setInt(1,productID);
                try(ResultSet rs = stm.executeQuery()){
                    rs.next();
                    n = rs.getString(1);
                    p = rs.getInt(2);
                }
            } catch(SQLException ex){
                
            }
            return new ProductEntity(productID, p, n);
        }
}
