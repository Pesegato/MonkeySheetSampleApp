package batched;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.pesegato.MonkeySheet.MSContainer;
import com.pesegato.MonkeySheet.MSControl;
import com.pesegato.MonkeySheet.MSMaterialControl;
import com.pesegato.MonkeySheet.MonkeySheetAppState;
import com.pesegato.MonkeySheet.batch.BGeometry;
import com.pesegato.MonkeySheet.batch.BNode;
import com.pesegato.goldmonkey.GM;
import com.pesegato.timing.SimpleTimeable;


public class TestBatchedAppState4 extends BaseAppState {
    MSControl msc;
    MSMaterialControl msmc;
    float tTPF = 0;

    public static int SIZE = 2;


    BNode msBatcher;
    BGeometry quads[];
    int point;

    @Override
    protected void initialize(Application app) {

        msBatcher = new BNode(SIZE);
        quads = msBatcher.getQuads();


        MonkeySheetAppState msa = new MonkeySheetAppState();
        MonkeySheetAppState.setTickDuration(GM.getFloat("anim_tick_duration"));

        MSContainer container = msa.initializeContainer("monkey-guy");
        //Geometry geo = MSAction.createGeometry("spatial", 1f, 1f);
        point = msBatcher.addQuad(0, 0);
        point = msBatcher.addQuad(0, 0);

        geo = msBatcher.makeGeo();//new Geometry("monkey", mesh);

        msc = new MSControl("run", new SimpleTimeable());
        geo.addControl(msc);

        msmc = new MSMaterialControl(getApplication().getAssetManager(), geo, container, msc);
        msmc.setAlpha(1);
        msmc.setVertexSheetPos(true);

        ((SimpleApplication) getApplication()).getGuiNode().attachChild(geo);
    }

    float c = 0;

    Geometry geo;

    float timer = 0, lastTimer = 1;
    float localScale;

    public void update(float tpf) {
        tTPF += (tpf);
        //localScale= (float) (5+24*(Math.sin(1)-Math.sin(1+tTPF)));
        c += (60 * tpf);
        quads[0].getTransform().setOffset(new Vector2f(128, 128));
        for (int i = 0; i < SIZE; i++) {
            quads[i].getTransform().setLocalScale(256);
            quads[i].getTransform().setLocalRotation(tTPF * 2);
            if (quads[i] != null) {
                quads[i].applyTransform();
            }
        }
        msBatcher.updateData();
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
