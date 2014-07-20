package com.theoriginalbit.minecraft.moarperipherals.gui;

import com.theoriginalbit.minecraft.moarperipherals.reference.Constants;
import com.theoriginalbit.minecraft.moarperipherals.tile.TileKeyboard;
import com.theoriginalbit.minecraft.moarperipherals.utils.KeyboardUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

public class GuiKeyboard extends GuiScreen {

    private static final String EVENT_PASTE = "paste";
    private static final String EVENT_KEY = "key";
    private static final String EVENT_CHAR = "char";

    private final EntityPlayer player;
    private final TileKeyboard tile;
    private int terminateTimer, rebootTimer, shutdownTimer = 0;

    public GuiKeyboard(TileKeyboard tile, EntityPlayer player) {
        this.player = player;
        this.tile = tile;
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float par3) {
        String exitMessage = Constants.GUI.KEYBOARD_EXIT.getLocalised();
        int xPos = (width - fontRenderer.getStringWidth(exitMessage)) / 2;
        int yPos = player.capabilities.isCreativeMode ? height - 33 : height - 59;
        int color = 0xffffff;
        fontRenderer.drawString(exitMessage, xPos, yPos, color, true);
    }

    @Override
    public void updateScreen() {
        if (KeyboardUtils.isCtrlKeyDown()) {
            // T
            if (++terminateTimer > 50 && Keyboard.isKeyDown(20)) {
                tile.terminateTarget();
                terminateTimer = 0;
            }

            // R
            if (++rebootTimer > 50 && Keyboard.isKeyDown(19)) {
                tile.rebootTarget();
                rebootTimer = 0;
            }
            // S
            if (++shutdownTimer > 50 && Keyboard.isKeyDown(31)) {
                tile.shutdownTarget();
                shutdownTimer = 0;
            }
        } else {
            terminateTimer = rebootTimer = shutdownTimer = 0;
        }
    }

    @Override
    protected void keyTyped(char ch, int keyCode) {
        if (ch == '\026') {
            String clipboard = getClipboardString();
            if (clipboard != null) {
                int newLineIndex1 = clipboard.indexOf("\r");
                int newLineIndex2 = clipboard.indexOf("\n");
                if ((newLineIndex1 >= 0) && (newLineIndex2 >= 0)) {
                    clipboard = clipboard.substring(0, Math.max(newLineIndex1, newLineIndex2));
                } else if (newLineIndex1 >= 0) {
                    clipboard = clipboard.substring(0, newLineIndex1);
                } else if (newLineIndex2 >= 0) {
                    clipboard = clipboard.substring(0, newLineIndex2);
                }

                if (!clipboard.isEmpty()) {
                    if (clipboard.length() > 128) {
                        clipboard = clipboard.substring(0, 128);
                    }

                    tile.queueEventToTarget(EVENT_PASTE, clipboard);
                }
            }
            return;
        }
        // Escape was pressed
        if (keyCode == 1) {
            super.keyTyped(ch, keyCode);
        } else {
            // A different key was pressed, queue it to the computer
            tile.queueEventToTarget(EVENT_KEY, keyCode);
            if (ChatAllowedCharacters.isAllowedCharacter(ch)) {
                tile.queueEventToTarget(EVENT_CHAR, Character.toString(ch));
            }
        }
    }

}