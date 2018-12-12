package me.devkevin.core.staff.freeze;

import me.devkevin.core.CorePlugin;
import me.devkevin.core.staff.freeze.managers.FrozenManager;
import me.devkevin.core.staff.freeze.managers.InventoryManager;
import me.devkevin.core.staff.freeze.managers.PlayerSnapshotManager;

public class ManagerHandler
{
    private CorePlugin plugin;
    private InventoryManager inventoryManager;
    private FrozenManager frozenManager;
    private PlayerSnapshotManager playerSnapshotManager;

    public ManagerHandler(final CorePlugin plugin) {
        this.plugin = plugin;
        this.loadManagers();
    }

    private void loadManagers() {
        this.inventoryManager = new InventoryManager(this.plugin);
        this.frozenManager = new FrozenManager(this.plugin);
        this.playerSnapshotManager = new PlayerSnapshotManager(this.plugin);
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public FrozenManager getFrozenManager() {
        return this.frozenManager;
    }

    public PlayerSnapshotManager getPlayerSnapshotManager() {
        return this.playerSnapshotManager;
    }
}

