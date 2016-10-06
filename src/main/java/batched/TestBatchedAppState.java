package batched;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Quad;
import com.jme3.util.BufferUtils;
import com.pesegato.MonkeySheet.MSContainer;
import com.pesegato.MonkeySheet.MSControl;
import com.pesegato.MonkeySheet.MSMaterialControl;
import com.pesegato.MonkeySheet.MonkeySheetAppState;
import com.pesegato.MonkeySheet.actions.MSAction;
import com.pesegato.MonkeySheet.quad.SpriteQuad;
import com.pesegato.goldmonkey.GM;

import java.nio.FloatBuffer;

import static com.jme3.scene.VertexBuffer.Type.*;
import static com.pesegato.MonkeySheet.MSGlobals.SPRITE_SIZE;


public class TestBatchedAppState extends BaseAppState {
    MSControl msc;
    float tTPF = 0;
    boolean idling = false;

    public static int SIZE = 1;

    ColorRGBA[] allMyColors = new ColorRGBA[SIZE];
    float cc[] = new float[16];
    FloatBuffer posBuffer, colorBuffer;
    Mesh mesh;
    SpriteQuad[] quads;


    @Override
    protected void initialize(Application app) {


        mesh = new Mesh();
        quads = new SpriteQuad[SIZE];
        Vector3f[] vertices = new Vector3f[4 * SIZE];
        Vector2f[] texCoord = new Vector2f[4 * SIZE];
        int[] indexes = new int[6 * SIZE];
        float[] msPos = new float[SIZE];

        mesh.setBuffer(Position, 3, BufferUtils.createFloatBuffer(vertices));
        posBuffer = (FloatBuffer) mesh.getBuffer(Position).getData();
        for (int i = 0; i < SIZE; i++) {
            int j = i / 500;
            int k = i - j * 500;
            quads[i]=new SpriteQuad(i, posBuffer, vertices, texCoord,indexes);
            quads[i].setPosition(k , j);
            allMyColors[i] = ColorRGBA.randomColor();

        }
        mesh.setBuffer(TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        mesh.setBuffer(Index, 3, BufferUtils.createIntBuffer(indexes));
        //mesh.setBuffer(Size, 1, BufferUtils.createFloatBuffer(msPos));
        mesh.updateBound();



        MonkeySheetAppState msa = new MonkeySheetAppState();
        MonkeySheetAppState.setTickDuration(GM.getFloat("anim_tick_duration"));

        MSContainer container = new MSContainer("monkey-guy");
        msa.loadAnim(container, "run");
        msa.loadAnim(container, "idle");
        //Geometry geo = MSAction.createGeometry("spatial", 1f, 1f);

        //Geometry geo = new Geometry("spatial", new Quad(256, 256));
        Geometry geo = new Geometry("spatial", mesh);
        geo.setLocalScale(128);


        Material mat = new Material(getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color",ColorRGBA.Blue);
        geo.setMaterial(mat);


        msc = new MSControl("run");
        geo.addControl(msc);

        //MSMaterialControl msmc = new MSMaterialControl(getApplication().getAssetManager(), geo, container, msc);

        ((SimpleApplication) getApplication()).getGuiNode().attachChild(geo);
    }

    public void update(float tpf) {
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
