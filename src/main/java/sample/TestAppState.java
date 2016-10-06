package sample;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Geometry;
import com.pesegato.MonkeySheet.MSContainer;
import com.pesegato.MonkeySheet.MSControl;
import com.pesegato.MonkeySheet.MSMaterialControl;
import com.pesegato.MonkeySheet.MonkeySheetAppState;
import com.pesegato.MonkeySheet.actions.MSAction;
import com.pesegato.goldmonkey.GM;

/**
 * Created by Pesegato on 08/06/2016.
 */
public class TestAppState extends BaseAppState {
    MSControl msc;
    float tTPF = 0;
    boolean idling = false;

    @Override
    protected void initialize(Application app) {
        MonkeySheetAppState msa = new MonkeySheetAppState();
        MonkeySheetAppState.setTickDuration(GM.getFloat("anim_tick_duration"));

        MSContainer container = new MSContainer("monkey-guy");
        msa.loadAnim(container, "run");
        msa.loadAnim(container, "idle");
        Geometry geo = MSAction.createGeometry("spatial", 1f, 1f);
        msc = new MSControl("run");
        geo.addControl(msc);
        MSMaterialControl msmc = new MSMaterialControl(getApplication().getAssetManager(), geo, container, msc);
        ((SimpleApplication) getApplication()).getGuiNode().attachChild(geo);

    }

    public void update(float tpf) {
        tTPF -= tpf;
        if (tTPF < 2) {
            if (idling){
                msc.play("run");

                idling= false;
            }
        }
        else {
                if (!idling) {
                    msc.play("idle");
                    idling = true;
                }
            }
        if (tTPF < 0)
            tTPF = 5;
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
