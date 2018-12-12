package me.devkevin.core.utils.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

@Getter
@Setter
public class CancellableEvent extends BaseEvent implements Cancellable {

    private boolean cancelled;

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean b) {

    }
}
