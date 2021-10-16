package virtual_robot.games;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.NarrowphaseCollisionData;
import org.dyn4j.world.listener.CollisionListenerAdapter;
import virtual_robot.controller.Game;
import virtual_robot.controller.VirtualField;
import virtual_robot.controller.VirtualGameElement;
import virtual_robot.game_elements.classes.*;

public class FreightFrenzy extends Game {

    public static final Vector2[] hubPositionsInches = new Vector2[]{
            new Vector2(24, -12),       // Red hub
            new Vector2(-24, -12),      // Blue hub
            new Vector2(0, 48)          // Neutral hub
    };

    private Carousel redCarousel = null;
    private Carousel blueCarousel = null;

    private boolean humanPlayerActive = false;

    @Override
    public void initialize() {
        //Calling the super method causes the game elements to be created, populating the gameElements list.
        super.initialize();

        //Assign the game elements to the appropriate static final lists.
        for (VirtualGameElement e: gameElements){
            if (e instanceof CargoFreight){
                CargoFreight.cargos.add((CargoFreight)e);
                Freight.freightItems.add((Freight)e);
            } else if (e instanceof BoxFreight){
                BoxFreight.boxes.add((BoxFreight)e);
                Freight.freightItems.add((Freight)e);
            } else if (e instanceof DuckFreight){
                DuckFreight.ducks.add((DuckFreight)e);
                Freight.freightItems.add((Freight)e);
            } else if (e instanceof ShippingHub){
                ShippingHub.shippingHubs.add((ShippingHub) e);
            } else if (e instanceof Barrier){
                Barrier.theBarrier = (Barrier) e;
            } else if (e instanceof Carousel) {
                Carousel.carousels.add((Carousel)e);
            }
        }

        /*
         * Set the Shipping Hub Colors -- Red, Blue, and Green (for neutral)
         */
        ShippingHub.shippingHubs.get(0).getOuterCircle().setFill(Color.valueOf("FF0000"));
        ShippingHub.shippingHubs.get(1).getOuterCircle().setFill(Color.valueOf("0000FF"));

        Stop blueStop = new Stop(0.49, Color.BLUE);
        Stop redStop = new Stop(0.51, Color.RED);
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true,
                CycleMethod.NO_CYCLE, blueStop, redStop);
        ShippingHub.shippingHubs.get(2).getOuterCircle().setFill(gradient);

        redCarousel = Carousel.carousels.get(0);
        blueCarousel = Carousel.carousels.get(1);
        redCarousel.setLocationInches(69, -69);
        redCarousel.setOnField(true);
        blueCarousel.setLocationInches(-69, -69);
        blueCarousel.setOnField(true);

        /*
         * Add a collision listener to implement "special" handling of certain types of collision. For example,
         * this includes collisions involving rings that are stacked (to cause the stack to scatter). It should not
         * include collisions resulting in the robot controlling a game element: that should be handled within
         * the specific VirtualBot implementations.
         */
        world.addCollisionListener(new CollisionListenerAdapter<Body, BodyFixture>(){
            @Override
            public boolean collision(NarrowphaseCollisionData<Body, BodyFixture> collision) {
                return handleNarrowPhaseCollision(collision);
            }
        });

    }


    /**
     * Narrowphase Collision event handler
     *
     *
     * This will be called for all collisions, but needn't do any special processing for most of them. It
     * will handle:
     * 1) Collision of Freight and FreightHub
     *
     *  Return true to continue processing the collision, false to stop it.
     *
     *  Note: handling of collisions that result in the robot taking control of a game element should
     *  be handled by a listener set in the VirtualBot implementation.
     */
    private boolean handleNarrowPhaseCollision(NarrowphaseCollisionData<Body, BodyFixture> collision){

        return true;
    }

    @Override
    public void resetGameElements() {

        for (int i=0; i<3; i++){
            ShippingHub.shippingHubs.get(i).setOnField(true);
            ShippingHub.shippingHubs.get(i).setLocationInches(hubPositionsInches[i]);
        }

        Barrier.theBarrier.setOnField(true);
        Barrier.theBarrier.setLocationInches(0, 24);

        for (Freight f: Freight.freightItems){
            f.setOwningShippingHub(null);
        }

        for (int i=0; i<30; i++){
            int row = i / 5;
            int col = i % 5;
            float x = row<3? -66 + col * 8 : 34 + col * 8;
            float y = 32 + (row % 3) * 16;
            BoxFreight.boxes.get(i).setOnField(true);
            BoxFreight.boxes.get(i).setLocationInches(x, y);
        }

        for (int i=0; i<20; i++){
            int row = i / 5;
            int col = i % 5;
            float x = row<2? -66 + col * 8 : 34 + col * 8;
            float y = 40 + (row % 2) * 16;
            CargoFreight.cargos.get(i).setOnField(true);
            CargoFreight.cargos.get(i).setLocationInches(x, y);
        }

        redCarousel.clearAttachedDuck();
        blueCarousel.clearAttachedDuck();

        int numDucks = DuckFreight.ducks.size();
        DuckFreight.ducksOffFieldRed.clear();
        DuckFreight.ducksOffFieldBlue.clear();
        for (int i=0; i<numDucks; i++){
            DuckFreight.ducks.get(i).setOnField(false);
            if (i< numDucks/2) DuckFreight.ducksOffFieldRed.add(DuckFreight.ducks.get(i));
            else DuckFreight.ducksOffFieldBlue.add(DuckFreight.ducks.get(i));
        }

        redCarousel.setDuckToAttach(DuckFreight.ducksOffFieldRed.get(0));
        redCarousel.handleDuckAttach();
        blueCarousel.setDuckToAttach(DuckFreight.ducksOffFieldBlue.get(0));
        blueCarousel.handleDuckAttach();

        updateDisplay();
    }

    /**
     * Freight Frenzy effectively does use a human player, so return true.
     * @return
     */
    @Override
    public boolean hasHumanPlayer() {
        return true;
    }

    @Override
    public boolean isHumanPlayerAuto() {
        return humanPlayerActive;
    }

    @Override
    public void setHumanPlayerAuto(boolean selected) {
        humanPlayerActive = selected;
    }

    @Override
    public void updateHumanPlayerState(double millis) {
        if (redCarousel.getAttachedDuck() == null && redCarousel.getDuckToAttach() == null
                && redCarousel.getTimerMilliseconds() > 1000 && DuckFreight.ducksOffFieldRed.size() > 0
                && Math.abs(redCarousel.getElementBody().getAngularVelocity()) < 0.01) {
            redCarousel.setDuckToAttach(DuckFreight.ducksOffFieldRed.get(0));
            DuckFreight.ducksOffFieldRed.remove(0);
        }
        if (blueCarousel.getAttachedDuck() == null && blueCarousel.getDuckToAttach() == null
                && blueCarousel.getTimerMilliseconds() > 1000 && DuckFreight.ducksOffFieldBlue.size() > 0
                && Math.abs(blueCarousel.getElementBody().getAngularVelocity()) < 0.01) {
            blueCarousel.setDuckToAttach(DuckFreight.ducksOffFieldBlue.get(0));
            DuckFreight.ducksOffFieldBlue.remove(0);
        }
    }

}
