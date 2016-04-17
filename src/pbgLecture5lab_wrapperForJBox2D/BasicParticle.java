package pbgLecture5lab_wrapperForJBox2D;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class BasicParticle  {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-02-05 (JBox2d version)
	 * Significant changes applied:
	 */
	public final int SCREEN_RADIUS;

	private final float rollingFriction,mass;
	public final Color col;
	protected final Body body;
        public int life = 0;
        public boolean destroyed = false;


	public BasicParticle(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction, float restitution, String data) {
		World w=BasicPhysicsEngineUsingBox2D.world; // a Box2D object
		BodyDef bodyDef = new BodyDef();  // a Box2D object
		bodyDef.type = BodyType.DYNAMIC; // this says the physics engine is to move it automatically
		bodyDef.position.set(sx, sy);
		bodyDef.linearVelocity.set(vx, vy);
                bodyDef.linearDamping = 0f;
                bodyDef.userData = data; //set data for collision handling
		this.body = w.createBody(bodyDef);
		CircleShape circleShape = new CircleShape();// This class is from Box2D
		circleShape.m_radius = radius;
		FixtureDef fixtureDef = new FixtureDef();// This class is from Box2D
		fixtureDef.shape = circleShape;
		fixtureDef.density = (float) (mass/(Math.PI*radius*radius));
		fixtureDef.friction = 0.0f;// this is surface friction;
		fixtureDef.restitution = restitution;
                
                //Set the group index to a negative number so that particles do not collide with each other
                fixtureDef.filter.groupIndex = -1;
                
		body.createFixture(fixtureDef);
		this.rollingFriction=rollingFriction;
		this.mass=mass;
		this.SCREEN_RADIUS=(int)Math.max(BasicPhysicsEngineUsingBox2D.convertWorldLengthToScreenLength(radius),1);
		this.col=col;
                
                //Set particle lifes depending on the radius of it
                setLife(radius);
	}

	public void draw(Graphics2D g) {
		int x = BasicPhysicsEngineUsingBox2D.convertWorldXtoScreenX(body.getPosition().x);
		int y = BasicPhysicsEngineUsingBox2D.convertWorldYtoScreenY(body.getPosition().y);
		g.setColor(col);
		g.fillOval(x - SCREEN_RADIUS, y - SCREEN_RADIUS, 2 * SCREEN_RADIUS, 2 * SCREEN_RADIUS);
	}


	public void notificationOfNewTimestep() {
		if (rollingFriction > 0) {
			Vec2 rollingFrictionForce=new Vec2(body.getLinearVelocity());
			rollingFrictionForce=rollingFrictionForce.mul(-rollingFriction*mass);
			body.applyForceToCenter(rollingFrictionForce);
			
		}
                
                //Check if a collision between the bullet and particle occurred and if this is the object that caused it
                //Destroy the body that had the collision
                if (CollisionDetection.collisionBetweenParticleAndBullet  && CollisionDetection.particleCollidingBody == this.body) {
                    destroyed = true;
                    body.getWorld().destroyBody(body);
                }
	}
        
        //Set the amount of lifes of the particle
        public void setLife(float l) {
            life = (int) (l*10);
        }
}
