package cz.kofron.foodinventory.client.adapter;

import cz.kofron.foodinventory.client.model.InventoryItem;

/**
 * Created by kofee on 27.4.14.
 */
public interface DeleteInventoryListener
{
	public void onDeleteItem(InventoryItem item, ReloadCallback reloadCallBackOnDelete);
}
