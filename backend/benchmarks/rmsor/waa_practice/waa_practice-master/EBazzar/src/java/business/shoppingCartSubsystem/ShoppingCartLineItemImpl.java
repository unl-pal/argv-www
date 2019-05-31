package business.shoppingCartSubsystem;

import java.util.Random;

/**
 * For now, am assuming that product names are unique.  Might need to refactor that later.
 * @author levi
 *
 */
public class ShoppingCartLineItemImpl {
	private int quantity;
	
	public Object getCartProduct() {
		return new Object();
	}



	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public double getItemCost() {
		Random rand = new Random();
		return rand.nextFloat();
	}
	
	
	

}
