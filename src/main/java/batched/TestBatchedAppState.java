package batched;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.util.BufferUtils;
import com.pesegato.MonkeySheet.MSContainer;
import com.pesegato.MonkeySheet.MSControl;
import com.pesegato.MonkeySheet.MSMaterialControl;
import com.pesegato.MonkeySheet.MonkeySheetAppState;
import com.pesegato.MonkeySheet.quad.SpriteQuad;
import com.pesegato.goldmonkey.GM;

import java.nio.FloatBuffer;

import static com.jme3.scene.VertexBuffer.Type.*;


public class TestBatchedAppState extends BaseAppState {
    MSControl msc;
    float tTPF = 0;

    public static int SIZE = 50000;

    FloatBuffer posBuffer, msPosBuffer;
    Mesh mesh;
    SpriteQuad[] quads;


    @Override
    protected void initialize(Application app) {


        mesh = new Mesh();
        quads = new SpriteQuad[SIZE];
        Vector2f[] texCoord = new Vector2f[4 * SIZE];
        int[] indexes = new int[6 * SIZE];

        mesh.setBuffer(Position, 3, BufferUtils.createFloatBuffer(new Vector3f[4 * SIZE]));
        mesh.setBuffer(TexCoord2, 1, BufferUtils.createFloatBuffer(new float[4 * SIZE]));
        posBuffer = (FloatBuffer) mesh.getBuffer(Position).getData();
        msPosBuffer = (FloatBuffer) mesh.getBuffer(TexCoord2).getData();
        for (int i = 0; i < SIZE; i++) {
            int j = i / 250;
            int k = i - j * 250;
            quads[i] = new SpriteQuad(i, posBuffer, texCoord, indexes, msPosBuffer);
            quads[i].setPosition(k, j);

        }
        mesh.setBuffer(TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        mesh.setBuffer(Index, 3, BufferUtils.createIntBuffer(indexes));
        mesh.updateBound();


        MonkeySheetAppState msa = new MonkeySheetAppState();
        MonkeySheetAppState.setTickDuration(GM.getFloat("anim_tick_duration"));

        MSContainer container = new MSContainer("monkey-guy");
        msa.loadAnim(container, "run");
        msa.loadAnim(container, "idle");
        //Geometry geo = MSAction.createGeometry("spatial", 1f, 1f);

        geo = new Geometry("monkey", mesh);

        msc = new MSControl("run");
        geo.addControl(msc);

        MSMaterialControl msmc = new MSMaterialControl(getApplication().getAssetManager(), geo, container, msc);
        msmc.setVertexSheetPos(true);

        ((SimpleApplication) getApplication()).getGuiNode().attachChild(geo);
    }

    float c = 0;

    Geometry geo;
    float localScale;
    public void update(float tpf) {
        tTPF+=(tpf);
        localScale= (float) (5+24*(Math.sin(1)-Math.sin(1+tTPF)));
        geo.setLocalScale(localScale);
        c += (60 * tpf);
        for (int i = 0; i < SIZE; i++) {
            quads[i].setSFrame((int) (c + i) % 20);
        }
        mesh.setBuffer(TexCoord2, 1, msPosBuffer);
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
