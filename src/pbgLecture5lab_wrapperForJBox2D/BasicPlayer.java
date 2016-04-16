
package pbgLecture5lab_wrapperForJBox2D;

/**
 *
 * @author USER END
 */

import java.awt.Color;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import pbgLecture5lab_wrapperForJBox2D.BasicKeyListener;

public class BasicPlayer extends BasicPolygon {
    

    
    public BasicPlayer(float sx, float sy, float vx, float vy, float radius, Color col, float mass, float rollingFriction, int numSides, BodyType bType, String data) {
        super(sx, sy, vx, vy, radius, col, mass, rollingFriction,mkRegularPolygon(numSides, radius),numSides,bType,data);
    }
    
    @Override
    public void notificationOfNewTimestep() {
        if (rollingFriction > 0) {
                Vec2 rollingFrictionForce=new Vec2(body.getLinearVelocity());
                rollingFrictionForce=rollingFrictionForce.mul(-rollingFriction*mass);
                body.applyForceToCenter(rollingFrictionForce);
        }
        
        if(BasicKeyListener.isLeftKeyPressed()) {
            body.applyForceToCenter(new Vec2(-0.1f,0));
        }
        if(BasicKeyListener.isRightKeyPressed()) {
            body.applyForceToCenter(new Vec2(0.1f,0));
        }
        
    }
}
