package tetris.server.timer;

import tetris.server.ServerOutput;
import tetris.server.command.impl.Tick;

import java.util.List;

/**
 * <p>Headline: tetris.server.timer.TickTask</p>
 * <p>Description: The class TickTask send every "time" an timertask</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Organisation: Tetris Connection</p>
 *
 * @author Gath Sebastian, gath, gath@inf.uni-konstanz.de, 01/556108
 * @author Hug Holger, hug, hug@inf.uni-konstanz.de, 01/566368
 * @author Raedle Roman, raedler, raedler@inf.uni-konstanz.de, 01/546759
 * @author Weiler Andreas, weiler, weiler@inf.uni-konstanz.de, 01/560182
 * @version 1.0
 */

public class TickTask extends Thread {

    //array of objects inherit all TetrisServerThreadOut
    protected List<ServerOutput> serverOutputs;

    //integer value to send the tick (milliseconds)
    protected long time = 0;

    //traffic monitor -> dummyObject
    private final Object tickDummy;

    private boolean ticking = true;

    /**
     * Initialize serverOutputs, time, and tick dummy.
     *
     * @param serverOutputs Object[] All threads server outs
     * @param time          int Tick time
     * @param tickDummy     Object Tick dummy object
     */
    public TickTask(List<ServerOutput> serverOutputs, long time, Object tickDummy) {
        this.serverOutputs = serverOutputs;
        this.time = time;
        this.tickDummy = tickDummy;
    }

    /**
     * The run method implements the timertask; after sleeping "time" sec, the server
     * send a tick to all clients and itself
     */
    public void run() {
        while (ticking) {
            try {
                sleep(time);
                for (ServerOutput serverOutput : serverOutputs) {
                    serverOutput.addSendable(new Tick());

                    //tp monitor for tetris tick
                    synchronized (tickDummy) {
                        tickDummy.notifyAll();
                    }
                }
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Method to set tick time
     *
     * @param time tick time (in millisendons)
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Returns timer task time.
     *
     * @return int Timer task time
     */
    public long getTime() {
        return time;
    }

    public void accelerateTick(double factor) {
        time /= factor;
    }

    /**
     * Destroy thread timer task.
     */
    public void destroyTickTask() {
        this.ticking = false;
    }
}
