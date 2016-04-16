package pbgLecture5lab_wrapperForJBox2D;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.MouseJoint;

import pbgLecture5lab_wrapperForJBox2D.BasicKeyListener;

public class BasicPhysicsEngineUsingBox2D {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-02-05 (JBox2d version)
	 * Significant changes applied:
	 */
	
	// frame dimensions
	public static final int SCREEN_HEIGHT = 480;
	public static final int SCREEN_WIDTH = 860;
	public static final Dimension FRAME_SIZE = new Dimension(
			SCREEN_WIDTH, SCREEN_HEIGHT);
	public static final float WORLD_WIDTH=10;//metres
	public static final float WORLD_HEIGHT=SCREEN_HEIGHT*(WORLD_WIDTH/SCREEN_WIDTH);// meters - keeps world dimensions in same aspect ratio as screen dimensions, so that circles get transformed into circles as opposed to ovals
	public static final float GRAVITY=9.8f;
	public static final boolean ALLOW_MOUSE_POINTER_TO_DRAG_BODIES_ON_SCREEN=false;// There's a load of code in basic mouse listener to process this, if you set it to true

	public static World world; // Box2D container for all bodies and barriers 

	// sleep time between two drawn frames in milliseconds 
	public static final int DELAY = 20;
	public static final int NUM_EULER_UPDATES_PER_SCREEN_REFRESH=10;
	// estimate for time between two frames in seconds 
	public static final float DELTA_T = DELAY / 1000.0f;
	
        //Score
        public int score;
        
        //Identifiers
        private String particle = "particle";
        private String bullet = "bullet";
        private String player = "player";
        
        //Particle Spawn
        int iterations = 0;
        
        //Ball characteristics
        float ballRollingFriction = 0f;
        float ballRadius = 0.15f;
        float ballMass = 2f;
        float ballRestitition = 1.0f;
        
        //Starting ball characteristics
        float s=1.2f;
        float startingPosYBall = WORLD_HEIGHT/2+2f;
        float startingVelXBall = 1.0f*s;
        float startingVelYBall = 0;
        
        //Bullet characteristics
        float bulletRollingFriction = 0f;
        float bulletSpeed = 7.5f;
        float bulletRadius = 0.05f;
        float bulletMass = 1f;
        float bulletRestitution = 0f;
        
        //Player characteristics
        float playerRadius = 0.3f;
	
	public static int convertWorldXtoScreenX(float worldX) {
		return (int) (worldX/WORLD_WIDTH*SCREEN_WIDTH);
	}
	public static int convertWorldYtoScreenY(float worldY) {
		// minus sign in here is because screen coordinates are upside down.
		return (int) (SCREEN_HEIGHT-(worldY/WORLD_HEIGHT*SCREEN_HEIGHT));
	}
	public static float convertWorldLengthToScreenLength(float worldLength) {
		return (worldLength/WORLD_WIDTH*SCREEN_WIDTH);
	}
	public static float convertScreenXtoWorldX(int screenX) {
		return screenX*WORLD_WIDTH/SCREEN_WIDTH;
	}
	public static float convertScreenYtoWorldY(int screenY) {
		return (SCREEN_HEIGHT-screenY)*WORLD_HEIGHT/SCREEN_HEIGHT;
	}
	
	
	
	public List<BasicParticle> particles;
	public List<BasicPolygon> polygons;
	public List<AnchoredBarrier> barriers;
	public List<ElasticConnector> connectors;
	public static MouseJoint mouseJointDef;
	
	public static enum LayoutMode {RECTANGLE, PINBALL_ARENA};
	public BasicPhysicsEngineUsingBox2D() {
		world = new World(new Vec2(0, -GRAVITY));// create Box2D container for everything
		world.setContinuousPhysics(true);
                CollisionDetection listener = new CollisionDetection();
                world.setContactListener(listener);
                
                score = 0;
                
		particles = new ArrayList<BasicParticle>();
		polygons = new ArrayList<BasicPolygon>();
		barriers = new ArrayList<AnchoredBarrier>();
		connectors=new ArrayList<ElasticConnector>();
		LayoutMode layout=LayoutMode.RECTANGLE;
                
		float rollingFriction = .02f;
		float r=.3f;
//			rectangles.add(new BasicRectangle(WORLD_WIDTH/2,WORLD_HEIGHT*3/4,  -4,3, r*4, r*8, 0, 5,  false, Color.BLUE, 1,0.5));
//			public BasicRectangle(double sx, double sy, double vx, double vy, double width, double height, double orientation, double angularVeloctiy, boolean improvedEuler, Color col, double mass) {

		
		particles.add(new BasicParticle(0,startingPosYBall,startingVelXBall,startingVelYBall, ballRadius,Color.GREEN, ballMass, ballRollingFriction, ballRestitition, particle));
		particles.add(new BasicParticle(WORLD_WIDTH,startingPosYBall,-startingVelXBall,startingVelYBall, ballRadius,Color.GREEN, ballMass, ballRollingFriction, ballRestitition, particle));
		//polygons.add(new BasicPolygon(WORLD_WIDTH/2-2,WORLD_HEIGHT/2+1.4f,-1.5f*s,1.2f*s, r*2,Color.RED, 1, rollingFriction,3));
		//polygons.add(new BasicPolygon(WORLD_WIDTH/2-2,WORLD_HEIGHT/2+1.4f,-1.5f*s,1.2f*s, r*4,Color.RED, 1, rollingFriction,3));
		//polygons.add(new BasicPolygon(WORLD_WIDTH/2-2,WORLD_HEIGHT/2+1.3f,-1.2f*s,1.2f*s, r*2,Color.WHITE, 1, rollingFriction,5));
		polygons.add(new BasicPlayer(WORLD_WIDTH/2,playerRadius,0,0, playerRadius,Color.YELLOW, 1, rollingFriction,4,BodyType.DYNAMIC, player));
		
//		particles.add(new BasicParticle(WORLD_WIDTH/2+2,WORLD_HEIGHT/2+2f,-1.2f*s,-1.4f*s, r,Color.BLUE, 2, 0));
//		particles.add(new BasicParticle(3*r+WORLD_WIDTH/2,WORLD_HEIGHT/2,2,6.7f, r*3,Color.BLUE, 90, 0));
//		particles.add(new BasicParticle(r+WORLD_WIDTH/2,WORLD_HEIGHT/2,3.5f,5.2f, r,Color.RED, 2, 0));
		
//		// Example revolute joint creation:
//		BasicPolygon p1 = polygons.get(0);
//		BasicParticle p2 = particles.get(0);
//		RevoluteJointDef jointDef=new RevoluteJointDef();
//		jointDef.bodyA = p1.body;
//		jointDef.bodyB = p2.body;
//		jointDef.collideConnected = false;  // this means we don't want these two connected bodies to have collision detection.
//		jointDef.localAnchorA=new Vec2(0.2f,0.2f);
//		jointDef.localAnchorB=new Vec2(-0.2f,-0.2f);
//		world.createJoint(jointDef);
//		

		
		//particles.add(new BasicParticle(r,r,5,12, r,false, Color.GRAY, includeInbuiltCollisionDetection));

		
		barriers = new ArrayList<AnchoredBarrier>();
		
		switch (layout) {
			case RECTANGLE: {
				// rectangle walls:
				// anticlockwise listing
				// These would be better created as a JBox2D "chain" type object for efficiency and potentially better collision detection at joints. 
				barriers.add(new AnchoredBarrier_StraightLine(0, 0, WORLD_WIDTH, 0, Color.WHITE));
				barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH, 0, WORLD_WIDTH, WORLD_HEIGHT, Color.WHITE));
				barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH, WORLD_HEIGHT, 0, WORLD_HEIGHT, Color.WHITE));
				barriers.add(new AnchoredBarrier_StraightLine(0, WORLD_HEIGHT, 0, 0, Color.WHITE));
				break;
			}
                        
                        case PINBALL_ARENA: {
				// These would be better created as a JBox2D "chain" type object for efficiency and potentially better collision detection at joints. 
				// simple pinball board
				barriers.add(new AnchoredBarrier_StraightLine(0, 0, WORLD_WIDTH, 0, Color.WHITE));
				barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH, 0, WORLD_WIDTH, WORLD_HEIGHT, Color.WHITE));
				barriers.add(new AnchoredBarrier_StraightLine(WORLD_WIDTH, WORLD_HEIGHT, 0, WORLD_HEIGHT, Color.WHITE));
				barriers.add(new AnchoredBarrier_StraightLine(0, WORLD_HEIGHT, 0, 0, Color.WHITE));
				barriers.add(new AnchoredBarrier_Curve(WORLD_WIDTH/2, WORLD_HEIGHT-WORLD_WIDTH/2, WORLD_WIDTH/2, 0.0f, 200.0f,Color.WHITE));
				barriers.add(new AnchoredBarrier_Curve(WORLD_WIDTH/2, WORLD_HEIGHT*3/4, WORLD_WIDTH/15, -0.0f, 360.0f,Color.WHITE));
				barriers.add(new AnchoredBarrier_Curve(WORLD_WIDTH*1/3, WORLD_HEIGHT*1/2, WORLD_WIDTH/15, -0.0f, 360.0f,Color.WHITE));
				barriers.add(new AnchoredBarrier_Curve(WORLD_WIDTH*2/3, WORLD_HEIGHT*1/2, WORLD_WIDTH/15, -0.0f, 360.0f,Color.WHITE));
				break;
			}
		}
	}
        
	public static void main(String[] args) throws Exception {
		final BasicPhysicsEngineUsingBox2D game = new BasicPhysicsEngineUsingBox2D();
		final BasicView view = new BasicView(game);
		JEasyFrame frame = new JEasyFrame(view, "Particle");
		frame.addKeyListener(new BasicKeyListener());
		view.addMouseMotionListener(new BasicMouseListener());
		game.startThread(view);
	}
	private void startThread(final BasicView view) throws InterruptedException {
		final BasicPhysicsEngineUsingBox2D game=this;
		while (true) {
			game.update();
			view.repaint();

			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
			}
		}
	}
	


	public void update() {
		int VELOCITY_ITERATIONS=NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
		int POSITION_ITERATIONS=NUM_EULER_UPDATES_PER_SCREEN_REFRESH;
                
                if (BasicKeyListener.isSpaceKeyPressed()) {
                    float sx = polygons.get(0).getPosition().x;
                    float sy = polygons.get(0).getPosition().y;
                    polygons.add(new BasicPolygon(sx,sy+0.3f,0,bulletSpeed, bulletRadius,Color.RED, bulletMass, bulletRollingFriction,3, BodyType.KINEMATIC,bullet));
                    BasicKeyListener.falseSpaceKey();
                }
                
                for (int i = 0; i < particles.size(); i++) {
		//for (BasicParticle p:particles) {
                    BasicParticle p = particles.get(i);
                    // give the objects an opportunity to add any bespoke forces, e.g. rolling friction
                    if (!p.destroyed) {
                        p.notificationOfNewTimestep();
                    } else {
                        score += 10;
                        particles.remove(p);
                    }
		}
                
                for (int i = 0; i < polygons.size(); i++) {
		//for (BasicPolygon p:polygons) {
                    BasicPolygon p = polygons.get(i);
                    // give the objects an opportunity to add any bespoke forces, e.g. rolling friction
                    if (!p.destroyed) {
                        p.notificationOfNewTimestep();
                    } else {
                        polygons.remove(p);
                    }
                    
		}
                
                iterations++;
                if (iterations > 300) {
                    if (iterations % 10 == 0) {
                        particles.add(new BasicParticle(0,startingPosYBall,startingVelXBall,startingVelYBall, ballRadius,Color.GREEN, ballMass, ballRollingFriction, ballRestitition, particle));
                        particles.add(new BasicParticle(WORLD_WIDTH,startingPosYBall,-startingVelXBall,startingVelYBall, ballRadius,Color.GREEN, ballMass, ballRollingFriction, ballRestitition, particle));
                    }
                    if (iterations > 380)
                        iterations = 0;
                }
                
		world.step(DELTA_T, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
	}
	

}