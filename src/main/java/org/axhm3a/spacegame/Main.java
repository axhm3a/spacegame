package org.axhm3a.spacegame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.shape.Quad;
import com.jme3.light.AmbientLight;
import com.jme3.util.SkyFactory;
import com.jme3.util.TangentBinormalGenerator;

/**
 * Created by basten on 03.11.2014.
 */
public class Main extends SimpleApplication{

    private Node player = new Node();
    private Spatial enemy;
    private Geometry marker;
    private ProjectileFactory projectileFactory;
    private PlayerControl playerControl;

    public static void main(String[] argv) {
        Main main = new Main();
        main.start();
    }

    @Override
    public void simpleInitApp() {
        projectileFactory = new ProjectileFactory(assetManager,rootNode);

        rootNode.attachChild(SkyFactory.createSky(assetManager, "BackgroundCube.dds",false));

        initLights();
        initPlayer();
        initEnemy();
        initCamera();
        initInput();
        initMarker();
    }

    private void initLights() {
        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.Gray);
        rootNode.addLight(ambientLight);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(Vector3f.UNIT_Y);
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }

    private void initMarker() {
        marker = new Geometry("marker", new Quad(64,64));
        Material markerMaterial = new Material(assetManager, "Common/MatDefs/Misc/ColoredTextured.j3md");
        markerMaterial.setTexture("ColorMap", assetManager.loadTexture("marker.png"));
        marker.setMaterial(markerMaterial);
        markerMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Additive);
        marker.setQueueBucket(RenderQueue.Bucket.Gui);

        guiNode.attachChild(marker);
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

        marker.setLocalTranslation(cam.getScreenCoordinates(enemy.getWorldTranslation()));
        marker.move(-32,-32,0);
    }


    private void initInput() {
        inputManager.addMapping("D", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("A", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("W", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("S", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("X", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("Y", new KeyTrigger(KeyInput.KEY_Y));

        inputManager.addListener(analogListener, "A", "W","S","D","X","Y");
    }

    private void initCamera() {
        cam.setFrustumFar(10000f);
        flyCam.setEnabled(false);
        CameraNode camNode = new CameraNode("Camera Node", cam);

        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        player.attachChild(camNode);
        camNode.setLocalTranslation(new Vector3f(0f, 2f, -15f));
        camNode.lookAt(player.getWorldTranslation().clone().add(0,2,0), Vector3f.UNIT_Y);//new Vector3f(player.getWorldTranslation()).add(0,2f,0)
    }

    private void initPlayer() {

        Spatial playerGeometry = getSpaceShip();
        player.attachChild(playerGeometry);
        rootNode.attachChild(player);
        playerControl = new PlayerControl(10.0f, projectileFactory);
        player.addControl(playerControl);
    }

    private Spatial getSpaceShip() {
        Spatial playerGeometry = assetManager.loadModel("fighter.obj");
        TangentBinormalGenerator.generate(playerGeometry);
        //playerGeometry.rotate(0,-90f * FastMath.DEG_TO_RAD,0f);
        playerGeometry.scale(1, 0.5f, 1);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", assetManager.loadTexture("ship.png"));
        playerGeometry.updateGeometricState();
        material.setTexture("NormalMap", assetManager.loadTexture("ship_normal.png"));
        material.setTexture("ParallaxMap", assetManager.loadTexture("ship_height.png"));
        material.setFloat("Shininess", 16f);
        playerGeometry.setMaterial(material);
        return playerGeometry;
    }

    private void initEnemy() {
        enemy = getSpaceShip();
        enemy.addControl(new EnemyControl(9.0f,projectileFactory,player));
        rootNode.attachChild(enemy);
    }

    private AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("A")) {
                player.rotate(0, 0, value * -speed);
            }
            if (name.equals("D")) {
                player.rotate(0, 0, value * speed);
            }
            if (name.equals("W")) {
                player.rotate(value * speed, 0, 0);
            }
            if (name.equals("S")) {
                player.rotate(value * -speed, 0, 0);
            }
            if (name.equals("X")) {
                playerControl.fire();
            }
            if (name.equals("Y")) {
                EnemyControl enemyControl = enemy.getControl(EnemyControl.class);
                enemyControl.setAi(!enemyControl.isAi());
            }
        }
    };
}