package org.axhm3a.spacegame;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Created by axhm3a on 08.11.14.
 */
public class ProjectileFactory {
    Node rootNode;
    AssetManager assetManager;

    public ProjectileFactory(AssetManager assetManager, Node rootNode) {
        this.assetManager = assetManager;
        this.rootNode = rootNode;
    }

    public void fire(Spatial shooter) {
        ParticleEmitter fire = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 40);
        Material mat_red = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat_red.setTexture("Texture", assetManager.loadTexture("particle_dot.png"));
        fire.setMaterial(mat_red);
        fire.setImagesX(1); fire.setImagesY(1); // 2x2 texture animation
        fire.setStartColor(new ColorRGBA(0.5f, 1f, 0.5f, 0.5f)); // yellow
        fire.setEndColor(new ColorRGBA(0f, 0.8f, 0f, 1f));   // red
        fire.setStartSize(0.5f);
        fire.setEndSize(0.1f);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(0.1f);
        fire.setHighLife(0.4f);

        fire.rotate(shooter.getLocalRotation());
        fire.setLocalTranslation(shooter.getWorldTranslation());
        fire.addControl(new ProjectileControl(1000f,250f));
        rootNode.attachChild(fire);
    }
}
