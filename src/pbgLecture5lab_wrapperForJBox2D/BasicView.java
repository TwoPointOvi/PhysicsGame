package pbgLecture5lab_wrapperForJBox2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import static pbgLecture5lab_wrapperForJBox2D.BasicPhysicsEngineUsingBox2D.SCREEN_WIDTH;
import static pbgLecture5lab_wrapperForJBox2D.BasicPhysicsEngineUsingBox2D.SCREEN_HEIGHT;

public class BasicView extends JComponent {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-01-28
	 * Significant changes applied:
	 */
	// background colour
	public static final Color BG_COLOR = Color.BLACK;

	private BasicPhysicsEngineUsingBox2D game;

	public BasicView(BasicPhysicsEngineUsingBox2D game) {
		this.game = game;
	}
	
	@Override
	public void paintComponent(Graphics g0) {
		BasicPhysicsEngineUsingBox2D game;
		synchronized(this) {
			game=this.game;
		}
		Graphics2D g = (Graphics2D) g0;
		// paint the background
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		for (BasicParticle p : game.particles)
			p.draw(g);
		for (BasicPolygon p : game.polygons)
			p.draw(g);		
		for (ElasticConnector c : game.connectors)
			c.draw(g);
		for (AnchoredBarrier b : game.barriers)
			b.draw(g);
                
                //When the game is over draw a losing screen
                if (CollisionDetection.collisionBetweenParticleAndPlayer) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font(Font.SERIF, Font.BOLD, 40));
                    FontMetrics fontMetrics = g.getFontMetrics(g.getFont());
                    String text = "You Lose!";
                    int textLength = fontMetrics.stringWidth(text);
                    g.drawString(text, SCREEN_WIDTH / 2 - textLength + 100, SCREEN_HEIGHT / 2);
                } else if (BasicKeyListener.isEnterKeyPressed()) {
                    g.setColor(Color.WHITE);
                    g.setFont(new Font(Font.SERIF, Font.BOLD, 40));
                    FontMetrics fontMetrics = g.getFontMetrics(g.getFont());
                    String text = "Paused";
                    int textLength = fontMetrics.stringWidth(text);
                    g.drawString(text, SCREEN_WIDTH / 2 - textLength + 80, SCREEN_HEIGHT / 2);
                }
	}

	@Override
	public Dimension getPreferredSize() {
		return BasicPhysicsEngineUsingBox2D.FRAME_SIZE;
	}
	
	public synchronized void updateGame(BasicPhysicsEngineUsingBox2D game) {
		this.game=game;
	}
}