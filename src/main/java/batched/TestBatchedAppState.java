package batched;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Geometry;
import com.pesegato.MonkeySheet.MSContainer;
import com.pesegato.MonkeySheet.MSControl;
import com.pesegato.MonkeySheet.MSMaterialControl;
import com.pesegato.MonkeySheet.MonkeySheetAppState;
import com.pesegato.MonkeySheet.batch.BGeometry;
import com.pesegato.MonkeySheet.batch.BNode;
import com.pesegato.goldmonkey.GM;


public class TestBatchedAppState extends BaseAppState {
    MSControl msc;
    float tTPF = 0;

    public static int SIZE = 50000;


    BNode msBatcher;
    BGeometry quads[];

    @Override
    protected void initialize(Application app) {

        msBatcher=new BNode(SIZE);
        quads=msBatcher.getQuads();


        MonkeySheetAppState msa = new MonkeySheetAppState();
        MonkeySheetAppState.setTickDuration(GM.getFloat("anim_tick_duration"));

        MSContainer container = new MSContainer("monkey-guy");
        msa.loadAnim(container, "run");
        msa.loadAnim(container, "idle");
        //Geometry geo = MSAction.createGeometry("spatial", 1f, 1f);
        for (int i = 0; i < SIZE; i++) {
            int j = i / 250;
            int k = i - j * 250;
            msBatcher.addQuad(i,k,j);
        }

        geo = msBatcher.makeGeo();//new Geometry("monkey", mesh);

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
        msBatcher.updateAnim();
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
