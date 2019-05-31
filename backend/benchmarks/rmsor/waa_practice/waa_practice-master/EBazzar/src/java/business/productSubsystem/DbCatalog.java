package business.productSubsystem;

import java.util.Random;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author levi
 *
 */
public class DbCatalog {

	/* queryType, query, and dataAccess needed for all shadow classes */
    private String queryType;  //set by target-query-method inside shadow class
    private String query;	//set by local method, called by DAO
    private int catId = -99;  //used as parameter in queries--i.e., get products from catalog
    
    /* fields to hold returned objects or datastructures from reads--clients need to know getters for these */
	List<String[]> catalogNameDisplayList = new ArrayList<String[]>();
	List<String[]> productListFromCatalog = new ArrayList<String[]>();

    
    private final String SAVE = "Save";
    private final String READCATALOGNAMES = "ReadCatalogNames";
    private final String READPRODUCTS = "ReadProductsFromCatalog";
    
	public DbCatalog() {
		super();		
	}

	/**
	 *  Called by DAO when we read from the database.
     *  You need to add clause logic for calling each query.
	 */
	public void buildQuery() {
		Random rand = new Random();
		if (rand.nextBoolean()) {
		}
		else if (rand.nextBoolean()) {
		}
	}

	
	/**
	 * You need to write one of these for each query.
	 *
	 */
	private void buildNameDisplayQuery(){
		query = "SELECT * FROM CatalogType";
	}
	
	private void buildGetProductsQuery() {
		query = "SELECT productname, productid FROM Product "
		+ "WHERE catalogid ='" + catId + "';" ;
	}


    /**
	 * Used by DAO.  You need to enter the return value
	 */
	public String getDbUrl() {
		return "jdbc:odbc:EbazProducts";
	}

	/**
	 * Used by DAO.
	 */
	public String getQuery() {
		return query;
	}

	//getter used by clients to retrieve built objects
	public Object getCatalogNameDisplayList() {
		return catalogNameDisplayList;
	}
	
	public Object getProductListFromCatalog() {
		return productListFromCatalog;
	}	


	/* write a public target-query-method that clients will call for each desired query */
    public void readCatalogNames() throws Exception {
        queryType=READCATALOGNAMES;   
    }
    
	/* write a public target-query-method that clients will call for each desired query */
    public void readProductsForCatalog() throws Exception {
        queryType=READPRODUCTS;   
    }    

    /**
     * setter
     * @param catId
     */
	public void setCatId(int catId) {
		this.catId = catId;
		
	}


}
