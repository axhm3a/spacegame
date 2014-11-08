package org.axhm3a.spacegame;


import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

/**
 * Created by axhm3a on 08.11.14.
 */
public class PlayerControl extends MoveControl {
    private ProjectileFactory projectileFactory;

    public PlayerControl(float metersPerSecond, ProjectileFactory projectileFactory) {
        super(metersPerSecond);
        this.projectileFactory = projectileFactory;
    }

    public void fire() {
        projectileFactory.fire(spatial);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
