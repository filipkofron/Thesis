package cz.kofron.foodinventory.client.adapter;

import cz.kofron.foodinventory.client.model.InventoryItem;

// TODO: Auto-generated Javadoc
/**
 * Created by kofee on 27.4.14.
 *
 * @see DeleteInventoryEvent
 */
public interface DeleteInventoryListener
{
	
	/**
	 * On delete item.
	 *
	 * @param item the item
	 * @param reloadCallBackOnDelete the reload call back on delete
	 */
	public void onDeleteItem(InventoryItem item, ReloadCallback reloadCallBackOnDelete);
}
