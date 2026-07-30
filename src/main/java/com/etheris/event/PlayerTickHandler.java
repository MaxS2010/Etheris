package com.etheris.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PlayerTickHandler {
    
    public static int tickcount = 0;
    private static int count = 0;
    public static void register() {
         
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickcount++;
            
            if (tickcount >= 100 && count <= 5) {
                count++;
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    player.sendMessage(Text.of("."), false);
                }
                tickcount = 0; 
            }
        });
    }
}
       
