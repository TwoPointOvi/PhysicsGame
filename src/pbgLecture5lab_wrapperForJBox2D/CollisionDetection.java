/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pbgLecture5lab_wrapperForJBox2D;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

/**
 *
 * @author eovill
 */
public class CollisionDetection implements ContactListener {
    
    public static boolean collisionBetweenParticleAndBullet = false;
    public static boolean collisionBetweenParticleAndPlayer = false;
    public static Body bulletCollidingBody = null;
    public static Body particleCollidingBody = null;
    public static Body playerCollidingBody = null;
    
    @Override
    public void beginContact(Contact contact) {
        String aux = (String) contact.m_fixtureA.m_body.getUserData();
        String aux2 = (String) contact.m_fixtureB.m_body.getUserData();
        
        if (aux != null && aux2 != null) {
            if (aux.equals("bullet") && aux2.equals("particle")) {
                collisionBetweenParticleAndBullet = true;
                bulletCollidingBody = contact.m_fixtureA.m_body;
                particleCollidingBody = contact.m_fixtureB.m_body;
                System.out.println("Boom");
            } else if (aux.equals("particle") && aux2.equals("bullet")) {
                collisionBetweenParticleAndBullet = true;
                particleCollidingBody = contact.m_fixtureA.m_body;
                bulletCollidingBody = contact.m_fixtureB.m_body;
            }
            
            if (aux.equals("player") && aux2.equals("particle")) {
                collisionBetweenParticleAndPlayer = true;
                playerCollidingBody = contact.m_fixtureA.m_body;
                particleCollidingBody = contact.m_fixtureB.m_body;
                System.exit(1);
            } else if (aux.equals("particle") && aux2.equals("player")) {
                collisionBetweenParticleAndPlayer = true;
                particleCollidingBody = contact.m_fixtureA.m_body;
                playerCollidingBody = contact.m_fixtureB.m_body;
                System.exit(1);
            }
        }
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void endContact(Contact contact) {
        String aux = (String) contact.m_fixtureA.m_body.getUserData();
        String aux2 = (String) contact.m_fixtureB.m_body.getUserData();
        
        if (aux != null && aux2 != null) {
            if (aux.equals("bullet") && aux2.equals("particle")) {
                collisionBetweenParticleAndBullet = false;
            } else if (aux.equals("particle") && aux2.equals("bullet")) {
                collisionBetweenParticleAndBullet = false;
            }
            
            if (aux.equals("player") && aux2.equals("particle")) {
                collisionBetweenParticleAndPlayer = true;
            } else if (aux.equals("particle") && aux2.equals("player")) {
                collisionBetweenParticleAndPlayer = true;
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
