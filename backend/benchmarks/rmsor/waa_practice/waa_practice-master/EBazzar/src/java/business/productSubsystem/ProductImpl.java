package business.productSubsystem;




public class ProductImpl  {
	/**
	 * @uml.property  name="prodName"
	 */
	private String prodName;
	/**
	 * @uml.property  name="price"
	 */
	private double price;
	/**
	 * @uml.property  name="quantityInStock"
	 */
	private int quantityInStock;
	/**
	 * @uml.property  name="description"
	 */
	private String description;
	/* (non-Javadoc)
	 * @see business.productSubsystem.Product#getDescription()
	 */
	public String getDescription() {
		return description;
	}
	/* (non-Javadoc)
	 * @see business.productSubsystem.Product#getPrice()
	 */
	public double getPrice() {
		return price;
	}
	/* (non-Javadoc)
	 * @see business.productSubsystem.Product#getProdName()
	 */
	public String getProdName() {
		return prodName;
	}
	/* (non-Javadoc)
	 * @see business.productSubsystem.Product#getQuantityInStock()
	 */
	public int getQuantityInStock() {
		return quantityInStock;
	}
	/**
	 * @param quantityInStock  the quantityInStock to set
	 * @uml.property  name="quantityInStock"
	 */
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	  

}
