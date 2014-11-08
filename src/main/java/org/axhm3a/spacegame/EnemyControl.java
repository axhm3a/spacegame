package org.axhm3a.spacegame;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Created by axhm3a on 08.11.14.
 */
public class EnemyControl extends PlayerControl
{
    private Spatial player;
    private boolean ai = false;

    public EnemyControl(float metersPerSecond, ProjectileFactory projectileFactory, Spatial player) {
        super(metersPerSecond, projectileFactory);
        this.player = player;
    }

    @Override
    protected void controlUpdate(float tpf) {
        super.controlUpdate(tpf);

        rotate(tpf);
        checkFire();
    }

    private void checkFire() {
        Vector3f direction = player.getLocalTranslation().subtract(
                spatial.getLocalTranslation()).normalizeLocal();
        Vector3f difference = spatial.getLocalRotation().getRotationColumn(2).subtract(direction);

        if (difference.length() < 0.1f) {
            fire();
        }
    }

    private void rotate(float tpf) {
        if(ai) {
            Quaternion oldRot = spatial.getLocalRotation().clone();

            spatial.lookAt(player.getLocalTranslation(), Vector3f.UNIT_Y);

            Quaternion newRot = new Quaternion(spatial.getLocalRotation());

            spatial.setLocalRotation(oldRot.clone());
            spatial.getLocalRotation().slerp(newRot, .5f * tpf);
            spatial.setLocalRotation(spatial.getLocalRotation());
        }
    }


    public boolean isAi() {
        return ai;
    }

    public void setAi(boolean ai) {
        this.ai = ai;
    }
}
