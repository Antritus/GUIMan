package bet.astral.guiman.listener;

import bet.astral.guiman.model.Clickable;
import bet.astral.guiman.model.ClickableBuilder;
import bet.astral.guiman.model.GUI;
import bet.astral.guiman.model.InteractableGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.BiConsumer;

public class ClickListener implements Listener {
	@EventHandler
	private void onClick(InventoryClickEvent event){
		if (!(event.getWhoClicked() instanceof Player player)){
			return;
		}
		if (event.getInventory().getHolder() instanceof InteractableGUI interactableGUI){
			ItemStack itemStack = event.getCurrentItem();
			GUI gui = interactableGUI.getCore();
			int id = gui.getId(itemStack);
			if (id == Clickable.empty_air.getId()){
				return;
			}
			Clickable clickable = gui.getIds().get(id);
			if (clickable == null){
				return;
			}
			BiConsumer<ItemStack, Player> consumer = clickable.getActions().get(event.getClick());
			consumer.accept(itemStack, player);
		}
	}
}
