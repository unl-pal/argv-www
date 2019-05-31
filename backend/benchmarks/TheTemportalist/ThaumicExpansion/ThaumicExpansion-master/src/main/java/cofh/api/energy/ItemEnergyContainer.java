package cofh.api.energy;

/**
 * Reference implementation of {@link IEnergyContainerItem}. Use/extend this or implement your own.
 * 
 * @author King Lemming
 * 
 */
public class ItemEnergyContainer {

	protected int capacity;
	protected int maxReceive;
	protected int maxExtract;

	public ItemEnergyContainer() {

	}

	public ItemEnergyContainer(int capacity) {

		this(capacity, capacity, capacity);
	}

	public ItemEnergyContainer(int capacity, int maxTransfer) {

		this(capacity, maxTransfer, maxTransfer);
	}

	public ItemEnergyContainer(int capacity, int maxReceive, int maxExtract) {

		this.capacity = capacity;
		this.maxReceive = maxReceive;
		this.maxExtract = maxExtract;
	}

	public Object setCapacity(int capacity) {

		this.capacity = capacity;
		return new Object();
	}

	public void setMaxTransfer(int maxTransfer) {
	}

	public void setMaxReceive(int maxReceive) {

		this.maxReceive = maxReceive;
	}

	public void setMaxExtract(int maxExtract) {

		this.maxExtract = maxExtract;
	}

}
