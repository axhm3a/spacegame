package org.axhm3a.spacegame;

import com.jme3.math.Vector3f;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by axhm3a on 08.11.14.
 */
public abstract class MoveControl extends AbstractControl {
    protected float metersPerSecond = 2.0f;

    public MoveControl(float metersPerSecond) {
        this.metersPerSecond = metersPerSecond;
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f direction = new Vector3f(0f,0f,this.metersPerSecond * tpf);

        spatial.move(spatial.getWorldRotation().mult(direction));
    }
}
