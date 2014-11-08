package org.axhm3a.spacegame;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;

/**
 * Created by axhm3a on 08.11.14.
 */
public class ProjectileControl extends MoveControl {
    private float timeToLive;

    public ProjectileControl(float maxRange, float metersPerSecond) {
        super(metersPerSecond);
        this.timeToLive = maxRange / metersPerSecond;
    }

    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);
        timeToLive -= tpf;
        if (timeToLive < 0) {
            spatial.removeFromParent();
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
