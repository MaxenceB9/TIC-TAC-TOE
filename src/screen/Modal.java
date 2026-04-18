package screen;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Modal {
	private final Map<ModalType, String> index = new EnumMap<>(ModalType.class);
	
	Modal()
	{
		index.put(ModalType.SETTIGNS_PLAYER, "/fxml/settings_player.fxml");
		index.put(ModalType.GAME, "/fxml/game.fxml");
		index.put(ModalType.RULE, "/fxml/rules.fxml");
	}

	public String getModal(ModalType type) {
        return index.get(type);
    }
}