package cr0s.WarpDrive.client.gui;

import uedevkit.gui.GuiSimpleBase;

public class GuiRectangle {

	private int x;
	private int y;
	private int width;
	private int height;
	
	public GuiRectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}
	
	public boolean isInRect(GuiSimpleBase gui, int mouseX, int mouseY) {
		mouseX -= gui.getLeft();
		mouseY -= gui.getTop();
		
		return x <= mouseX && mouseX <= x + width && y <= mouseY && mouseY <= y + height;
	}
	
	public void draw(GuiSimpleBase gui, int srcX, int srcY) {
		gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, width, height);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}