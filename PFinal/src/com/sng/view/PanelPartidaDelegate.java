package com.sng.view;

import com.sng.model.Partida;

public interface PanelPartidaDelegate {
    /**
     * End method will be called when the play ends
     */
    void panelPartidaDidEnd(Partida partida);
}
